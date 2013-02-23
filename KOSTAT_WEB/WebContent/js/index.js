// entry point for the app
define ([
         'module',
		 'jquery', 
		 'backbone', 
		 'underscore',
		 'utils/local_logger',
		 'collections/category',
		 'views/listMenu',
		 'views/dataExplorer',
         'jquery.ui',
         'jquery.mCustomScrollbar',
         'jquery.mousewheel',
         'jquery.showLoading',
         'jquery.toastmessage'
		 ], 

function( module, $, Backbone, _, Logger, category, listMenu, dataExplorer){
	var logger = new Logger("index.js");
		logger.setLevel("ALL");
		
	var selections = []; //to store category selections
	
	var restURL = "http://localhost:9081/kostat/rest/";
	var dataExplorerURL = "http://211.109.180.11/vivisimo/cgi-bin/query-meta.exe?";
     
    var growl = function (type, msgObj) {
    	var showType = 'showErrorToast' ; //default showing type is error
        	if (type == 'error' ) {
                  showType = 'showErrorToast' ;
        	} else if (type == 'success'){
                  showType = 'showSuccessToast' ;
        	} // showNoticeToast //showWarningToast //showStickyWarningToast
           
            var responsed = $.parseJSON(msgObj.responseText);
            if (typeof (msgObj) == 'string') {
                  $().toastmessage(showType,msgObj);
            } else {
                   if (responsed.redirect) {
                        $().toastmessage( 'showToast' , {
                            text     : responsed.userMessage + "<br/> 로그인 페이지로 이동합니다. <br/> 바로 이동하시려면 이 창을 닫거나, <br/>5초 뒤에 로그인 페이지로 이동 합니다.",
                            sticky   : true ,
                            position : 'top-right' ,
                            type     : 'error',
                            closeText: '',
                            close    : function () {
                               window.location = responsed.redirect;
                            }
                        });
                        setTimeout( function (){ window.location = responsed.redirect;}, 5000);
                  } else {
                        $().toastmessage(showType,responsed.userMessage);
                  }
           }
    };
	
	/* utility function to make menu selectable */
	var mCustomScrollSelectable = function(className) {
		$(className).on("click", function(e) {
			e.preventDefault();
			var inputName = $(this).children("input").attr("name");
			var liClass = $(this).attr("class");
			var liId = $(this).attr("id");
			var nameFilter = "[name='"+inputName+"']";
			var valueFilter = "[value='"+liId+"']";
			if($(this).hasClass("selected")) { 
				// already selected 
			} else {
				$("li." + liClass ).removeClass("selected");
				$("input").filter(nameFilter).removeAttr("checked");
				$(this).addClass("selected");
				$(this).children("input").filter(valueFilter).attr("checked", true);
			}
		});
	};	
	var notFirst = false;
	var doNothing = false;
	var lastWord = "";
	var requestingObj = {};
	var init = function(){
		logger.log("index.js init");
		
		var listMenuView1 = new listMenu;
		var listMenuView2 = new listMenu;
		var listMenuView3 = new listMenu;
		
		var dataExplorerView1 = new dataExplorer({"width": "100%","height": "320px"});
		var dataExplorerView2 = new dataExplorer({"width": "100%","height": "320px"});
		var dataExplorerView3 = new dataExplorer({"width": "100%","height": "320px"});
		
		var moduleData = module.config();
		var sanupCollection = new category(moduleData.sanup);
		var pumCollection = new category(moduleData.pum);
		var saupCollection = new category(moduleData.saup);

		var emptyCollection = new category();
		
		var category_init = function() {
			$("#category1").append(listMenuView1.render({'listNumber':1,'line-height': 5, className: "sanup", list: sanupCollection.toJSON() }).el);
			$("#category2").append(listMenuView2.render({'listNumber':3,'line-height': 0, className: "product", list: emptyCollection.toJSON() }).el);
			$("#category3").append(listMenuView3.render({'listNumber':2,'line-height': 0, className: "saup", list: emptyCollection.toJSON() }).el);
			
			mCustomScrollSelectable(".l-list li");
			$("#accordion").accordion({ heightStyle: "content" });
			//$("#accordion").on( "accordionactivate", function( event, ui ) {});
			$("#category1 .l-list-mid-5, .l-list-mid-10").mCustomScrollbar({
				scrollButtons:{
					enable:true
				},
				advanced:{
					updateOnBrowserResize:true, 
					updateOnContentResize:true
				}
			});
		}();
		
		$(function(){
			var resizeWindow = function(){
				$( ".chart").css("min-height", function(){
					var winHeight = document.documentElement.scrollHeight;
					return winHeight - 810;
				});
				$(".l-chart").css("height", function(){
					var winHeight = document.documentElement.offsetHeight;
					return winHeight - 100;
				});
				/*$(".placeholder").css("height", function(){
					var winHeight = document.documentElement.scrollHeight;
					return winHeight - 200;
				});*/
			};
			/*$(window).bind("resize", resizeWindow);
			resizeWindow();*/
			
			$(".c-keyword-set").buttonset();
			
			$("iframe").mCustomScrollbar({
				scrollButtons:{
					enable:true
				},
				
				advanced:{
					updateOnBrowserResize:true, 
					updateOnContentResize:true
				}
			});
			
			$(".c-search-button").click(function(){
				/* 선택된 Filter 정보 읽어오기 */
				// 1. 전체 / 증가 / 감소 ==> var selectedBtn = $(".c-keyword-set :radio:checked").val();
				// 2. 시작기간 / 끝기간
				// 3. 산업분류 / 품목 / 사업체 ==> 결과 코드
				// var a1 = $(".c-keyword-set :radio:checked").attr('id');
				// var a1 = $(".c-keyword-set :radio:checked + label").text();
				
				if ( doNothing ) {
					return
				}
				
				if (selections.length > 2) {
					$(".placeholder").css({display:"none"});
					
					var queryString = $('#c-search-box').val();
					if (!notFirst) {
						$('#frame1').append(dataExplorerView1.el);
						$('#frame2').append(dataExplorerView2.el);
						$('#frame3').append(dataExplorerView3.el);
						notFirst = true;
					}
					
					/*
					 * ServerSide job emulate
					 */
					var selectedBtn = $(".c-keyword-set :radio:checked").val();
					var selectedBtnResult = [];
					var sanId = selections[0];
					var pumId = selections[1];
					var saupId = '';
					if ( selections[2]=="" ){
						saupId = '0';
					} else {
						saupId = selections[2];
					}
					//사업체 전체 선택할 경우 selectedDepthCode는 품목 번호
					
					var timeFrom = $(".c-date-from").datepicker("getDate");
					var timeTo = $(".c-date-to").datepicker("getDate");
					if ( timeFrom > timeTo ) {
						var timeTemp = timeFrom;
						timeFrom = timeTo;
						$(".c-date-from").datepicker( 'setDate', timeTo );
						timeTo = timeTemp;
						$(".c-date-to").datepicker( 'setDate', timeTemp );
					}
					//날짜 순서가 바뀌었을때 앞뒤 선택 변환
					
					$.ajax({
						'url' : restURL + 'keywords/'+ selectedBtn +'/'+ sanId +'/'+ pumId + '/' + saupId ,
						'dataType' : 'json' ,
						'success' : function (data){
							_.each(data, function (val, i){
								var query = val.query;
								selectedBtnResult[i] = query;
							});
							
							if (data[0].source !== null) {
								dataExplorerView1.setSource(dataExplorerURL + "v%3Aproject=" + data[0].source + "&query=");
								//make it show
								$("#target1").css("display","block");
							} else {
								// make it hide
								$("#target1").css("display","none");
							}
							dataExplorerView2.setSource(dataExplorerURL + "v%3Aproject=" + data[1].source + "&query=");
							dataExplorerView3.setSource(dataExplorerURL + "v%3Aproject=" + data[2].source + "&query=");
							
							dataExplorerView1.setDefWords(selectedBtnResult[0]);
							dataExplorerView2.setDefWords(selectedBtnResult[1]);
							dataExplorerView3.setDefWords(selectedBtnResult[2]);
							
							if (lastWord.length > 0 && $("#c-search-check" ).is(':checked' )) {
								if (data[0].source !== null) {
									dataExplorerView1.query(selectedBtnResult[0] + lastWord + ' ' + queryString); //TODO: time from to add
								}
								dataExplorerView2.query(selectedBtnResult[1] + lastWord + ' ' + queryString);
								dataExplorerView3.query(selectedBtnResult[2] + lastWord + ' ' + queryString);
								logger.log( "결과내재검색1: " + selectedBtnResult[0] + lastWord + ' ' + queryString );
								logger.log( "결과내재검색2: " + selectedBtnResult[1] + lastWord + ' ' + queryString );
								logger.log( "결과내재검색3: " + selectedBtnResult[2] + lastWord + ' ' + queryString );
							} else {
								if (data[0].source !== null) {
									dataExplorerView1.query(selectedBtnResult[0] + ' ' + queryString); //TODO: time from to add
								}
								dataExplorerView2.query(selectedBtnResult[1] + ' ' + queryString);
								dataExplorerView3.query(selectedBtnResult[2] + ' ' + queryString);
								logger.log( "추가검색1: " + selectedBtnResult[0] + ' ' + queryString);
								logger.log( "추가검색2: " + selectedBtnResult[1] + ' ' + queryString);
								logger.log( "추가검색3: " + selectedBtnResult[2] + ' ' + queryString);
							}
							if ($("#c-search-check" ).is(':checked' )) {
								lastWord += " " + queryString;
							} else {
								lastWord = " " + queryString;
							}
							
							requestingObj[dataExplorerView1.reportID] = $.Deferred();
							if (data[0].source == null) {
								requestingObj[dataExplorerView1.reportID].resolve();
							};
							requestingObj[dataExplorerView2.reportID] = $.Deferred();
							requestingObj[dataExplorerView3.reportID] = $.Deferred();
							doNothing = true ;
                              
							$.when(requestingObj[dataExplorerView1.reportID], requestingObj[dataExplorerView2.reportID], requestingObj[dataExplorerView3.reportID]).then( function(){
								doNothing = false ;
								requestingObj = {};
							});
						},
						'error' : function (e){
							$( ".placeholder" ).css({display:"block" });
							growl( "showErrorToast" , e);
						}
					});
				} else {
					alert("모든 분류가 선택되지 않았습니다.");
				}
			});
		});
		$(".c-date-from, .c-date-to").datepicker({
			dateFormat: 'yy년 m월 d일',
			currentText: "오늘",
			closeText: "완료",
			monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
			changeMonth:true,
			changeYear:true,
			showButtonPanel: true,
	        onClose: function(dateText, inst) {
	        	var selectedDate = new Date(inst.selectedYear, inst.selectedMonth, inst.selectedDay);
	        	$(this).datepicker('setDate', selectedDate);
	        }
		});
		var today = new Date();
		var monthAgo = new Date(today.getFullYear(), today.getMonth() - 1);
		$(".c-date-from").datepicker('setDate', monthAgo);
		$(".c-date-to").datepicker('setDate', today);
		
		$(function(){
		    $("#c-search-box").keypress(function(e){
		        if(e.keyCode==13)
		            $('.c-search-button').click();
		    });
		});		
		
		eventHandler = {
				"selectClick": function(className, id, label) {
					if (className =="sanup") {
						$(".c-display-selected-1").text(label).effect( "bounce", "slow" );
						$(".c-display-selected-2").text("품목 분류");
						$(".c-display-selected-3").text("사업체 분류");
						selections[0] = id;
						selections = selections.slice(0,1);
						
						var filteredCollection = new category( pumCollection.where({parent: id}) ).toJSON();
						var minHeight = Math.min(filteredCollection.length ,10); 
						listMenuView2.render({'listNumber':3,'line-height': minHeight, 'className': "product", 'list': filteredCollection});
						
						$("#category2 > .l-list-mid-5, .l-list-mid-10").mCustomScrollbar({
							scrollButtons:{
								enable:true
							},
							advanced:{
								updateOnBrowserResize:true, 
								updateOnContentResize:true
							}
						});
						
						mCustomScrollSelectable(".l-list li");
						
						if (minHeight > 0) {
							$("#accordion").accordion("option","active",1);
						}
					} else if (className == "product") {
						$(".c-display-selected-2").text(label).effect( "bounce", "slow" );
						$(".c-display-selected-3").text("사업체 분류");
						selections[1] = id;
						selections = selections.slice(0,2);
						var filteredCollection = new category( saupCollection.where({parent: id}) ).toJSON();
						filteredCollection.reverse();
						filteredCollection.push({"id":0, "name": "전체"});
						filteredCollection.reverse();
						var minHeight = Math.min(filteredCollection.length ,10); 
						listMenuView3.render({'listNumber':2,'line-height': minHeight, 'className': "saup", 'list': filteredCollection});

						
						if (minHeight > 0) {
							$("#accordion").accordion("option","active",2);
							mCustomScrollSelectable(".l-list li");
							/*
							if (minHeight = 10) {
								
								$("#category3 > .l-list-mid-5, .l-list-mid-10").mCustomScrollbar({
									scrollButtons:{
										enable:true
									},
									advanced:{
										updateOnBrowserResize:true, 
										updateOnContentResize:true
									}
								});
							}
							*/
						}
					} else if (className == "saup") {
						$(".c-display-selected-3").text(label).effect( "bounce", "slow" );
						selections[2] = id;
						lastWord = "";
						$("#c-search-check").prop("checked", false);
						$('#c-search-box').val("");
					} 
				},
				"loadStart": function(name) {
					if (name == "dataExplorer0"){
						$('#target1').showLoading();
					} else if (name == "dataExplorer1"){
						$('#target2').showLoading();
					} else if (name == "dataExplorer2"){
						$('#target3').showLoading();
					} 
				},
				"loadFinish": function(name) {
					
					if (name == "dataExplorer0"){
						$('#target1').hideLoading();
					} else if (name == "dataExplorer1"){
						$('#target2').hideLoading();
					} else if (name == "dataExplorer2"){
						$('#target3').hideLoading();
					} 
					if (requestingObj[name]) {
						requestingObj[name].resolve();
					}
				}
		};
		
		eventListenerRegister = function(eventName, eventSource) {
			eventSource.on(eventName, eventHandler[eventName], this);
		};
		
		/* event binding start */
		eventListenerRegister("selectClick", listMenuView1);
		eventListenerRegister("selectClick", listMenuView2);
		eventListenerRegister("selectClick", listMenuView3);
		eventListenerRegister("loadStart", dataExplorerView1);
		eventListenerRegister("loadStart", dataExplorerView2);
		eventListenerRegister("loadStart", dataExplorerView3);
		eventListenerRegister("loadFinish", dataExplorerView1);
		eventListenerRegister("loadFinish", dataExplorerView2);
		eventListenerRegister("loadFinish", dataExplorerView3);
		/* event binding end */
	};
	return {
		'init': init
	};
});
