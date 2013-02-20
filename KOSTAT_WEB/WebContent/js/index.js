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
         'jquery.showLoading'
		 ], 

function( module, $, Backbone, _, Logger, category, listMenu, dataExplorer){
	var logger = new Logger("index.js");
		logger.setLevel("ALL");
		
	var selections = []; //to store category selections
	/* sample data */
	var temp_list1 = [
	                  {"id": "san001","name": "의약품"},
	                  {"id": "san002","name": "1차금속"},
	                  {"id": "san003","name": "반도체및부품"},
	                  {"id": "san004","name": "영상음향통신"},
	                  {"id": "san005","name": "컴퓨터"},
	                  {"id": "san006","name": "전기장비"}
	                 ];	
	var temp_list2 = [
	                  {"id": "prod001","name": "의약품"},
	                  {"id": "prod002","name": "선철"},
	                  {"id": "prod003","name": "슬랩"},
	                  {"id": "prod004","name": "블룸"},
	                  {"id": "prod005","name": "빌렛"},
	                  {"id": "prod006","name": "합금철"},
	                  {"id": "prod007","name": "봉강"},
	                  {"id": "prod008","name": "철근"},
	                  {"id": "prod009","name": "선재"},
	                  {"id": "prod0010","name": "형강"},
	                  {"id": "prod0011","name": "중후판"},
	                  {"id": "prod0012","name": "열연대강"}
	                  ];
	var temp_list3 = [
	                  {"id": "saup000","name": "전체"},
	                  {"id": "saup001","name": "아이티씨(주)"},
	                  {"id": "saup002","name": "라니(주)"},
	                  {"id": "saup003","name": "홍우선재(주)"},
	                  {"id": "saup004","name": "대양전기공업(주)"},
	                  {"id": "saup005","name": "금화전선(주)"}
	                  ];

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
		dataExplorerView1.setSource("http://211.109.180.11/vivisimo/cgi-bin/query-meta.exe?v%3Aproject=KS-C24-PROJ&query=");
		dataExplorerView2.setSource("http://211.109.180.11/vivisimo/cgi-bin/query-meta.exe?v%3Aproject=KS-ECO-PROJ&query=");
		dataExplorerView3.setSource("http://211.109.180.11/vivisimo/cgi-bin/query-meta.exe?v%3Aproject=KS-PTL-PROJ&query=");
		
		var category_load = function(data1,data2,data3) {
			$("#accordion").append(listMenuView1.render({'listNumber':1,'line-height': 5, className: "sanup", list: data1[0] }).el);
			$("#accordion").append(listMenuView2.render({'listNumber':3,'line-height': 5, className: "product", list: data2[0] }).el);
			$("#accordion").append(listMenuView3.render({'listNumber':2,'line-height': 10, className: "saup", list: data3[0] }).el);
			mCustomScrollSelectable(".l-list li");
			$("#accordion").accordion({ heightStyle: "content" });
			$("#accordion").on( "accordionactivate", function( event, ui ) {
				
			});
			$(".l-list-mid-5, .l-list-mid-10").mCustomScrollbar({
				scrollButtons:{
					enable:true
				},
				advanced:{
					updateOnBrowserResize:true, 
					updateOnContentResize:true
				}
			});
		};
		
		var load_error = function() {
			logger.log("네트워크 장애입니다.");
		};
		
		var sanupCollection = new category({ "url" : "./json/sanup.json" });
		/*
		sanupCollection.fetch({
			success : _.bind(category_load, this),
			error : load_error
		});
		*/
		
		var productCollection = new category({ "url" : "./json/product.json" });
		/*
		productCollection.fetch({
			success : _.bind(category_load, this),
			error : load_error
		});
		*/
		
		var saupCollection = new category({ "url" : "./json/saup.json" });
		/*
		saupCollection.fetch({
			success : _.bind(category_load, this),
			error : load_error
		});
		*/
		
		$.when(
			sanupCollection.fetch(),
			productCollection.fetch(),
			saupCollection.fetch()
		).then( function(data1,data2,data3) {
			category_load(data1,data2,data3);
		}).fail( function(e) {
			load_error();
		}).always( function() {

		});
		
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
			
			/*$(".l-chart").mCustomScrollbar({
					scrollButtons:{
						enable:true
					},
					
					advanced:{
						updateOnBrowserResize:true, 
						updateOnContentResize:true
					}
			});*/
			
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
					var selectedDepthCode = "";
					var selectedBtnId = "";
					var selectedBtnResult = "";
					var selectedKeywordSet = "";
					if ( selections[1] == "" ) {
						selectedDepthCode = selections[0];
					} else if ( selections[2] == "" ) {
						selectedDepthCode = selections[1]; 
					} else {
						selectedDepthCode = selections[2];
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
					
					$.when(
						$.ajax({
							'url' : 'json/filter.json',
							'dataType' : 'json',
							'success' : function(data){
								selectedBtnId = data[selectedBtn].id; //all, increase, decrease
								selectedBtnResult = data[selectedBtn].name; //키워드 조합
							},
							error: function (xhr, ajaxOptions, thrownError) {

							}
				        }),
						$.ajax({
							'url' : 'json/saup.json',
							'dataType' : 'json',
							'success' : function(data){
								selectedKeywordSet = 'sample'; //키워드 조합
							},
							error: function (xhr, ajaxOptions, thrownError) {

							}
				        })
				        
				        // /kostat/rest/keywords/{{증가,감소구분}}/{{selectedDepthCode}}/{{from : yyyymmdd}}/{{to : yyyymmdd}}
				        /*$.ajax({
							'url' : 'kostat/rest/keywords/' + selectedBtnId + '/' + selectedDepthCode + '/' + timeFrom + '/' + timeTo,
							'dataType' : 'json',
							'success' : function(data){
								selectedKeywordSet = data.name; //키워드 조합
							}
			        	})	*/
					).then(function(data){
						selectedKeywordSet = selectedKeywordSet + ' ' + selectedBtnResult;
						console.log(selectedKeywordSet);
						dataExplorerView1.setDefWords(selectedKeywordSet);
						dataExplorerView2.setDefWords(selectedKeywordSet);
						dataExplorerView3.setDefWords(selectedKeywordSet);
					});
					
					if (lastWord.length > 0 && $("#c-search-check").is(':checked')) {
						dataExplorerView1.query(lastWord + queryString);
						dataExplorerView2.query(lastWord + queryString);
						dataExplorerView3.query(lastWord + queryString);
					} else {
						dataExplorerView1.query(queryString);
						dataExplorerView2.query(queryString);
						dataExplorerView3.query(queryString);
					}
					if ($("#c-search-check").is(':checked')) {
						lastWord += " " + queryString + " ";
					} else { 
						lastWord = queryString + " ";
					}
					
					requestingObj[dataExplorerView1.reportID] = $.Deferred();
					requestingObj[dataExplorerView2.reportID] = $.Deferred();
					requestingObj[dataExplorerView3.reportID] = $.Deferred();
					doNothing = true;
					
					$.when(requestingObj[dataExplorerView1.reportID], requestingObj[dataExplorerView2.reportID], requestingObj[dataExplorerView3.reportID]).then(function(){
						//$('body').css("overflow", "auto");
						doNothing = false; 
						requestingObj = {};
					});
				} else {
					alert("모든 분류가 선택되지 않았습니다.");
				}
				
				/*if (selections.length > 2) {
					$(".placeholder").css({display:"none"});
					
					var queryString = $('#c-search-box').val(); 
					if (!notFirst) {
						$('#frame1').append(dataExplorerView1.el);
						$('#frame2').append(dataExplorerView2.el);
						$('#frame3').append(dataExplorerView3.el);
						notFirst = true;
					}*/
					
					/*
					 * ServerSide job emulate
					 */
					/*var selectedBtn = $(".c-keyword-set :radio:checked").val();
					if (selectedBtn == "all") {
						dataExplorerView1.setDefWords("동일산업 합금철");
						dataExplorerView2.setDefWords("동일산업 합금철");
						dataExplorerView3.setDefWords("동일산업 합금철");
					} else if(selectedBtn == "increase") {
						dataExplorerView1.setDefWords("동일산업 합금철 증가 OR 증대 OR 반등 OR 상승 OR 확대");
						dataExplorerView2.setDefWords("동일산업 합금철");
						dataExplorerView3.setDefWords("동일산업 합금철 증가 OR 증대 OR 반등 OR 상승 OR 확대");
					} else if(selectedBtn == "decrease") {
						dataExplorerView1.setDefWords("동일산업 합금철 감소 OR 하락 OR 축소");
						dataExplorerView2.setDefWords("동일산업 합금철");
						dataExplorerView3.setDefWords("동일산업 합금철 감소 OR 하락 OR 축소");
					};*/
					// 선택을 바꾸면 defWords도 바꾸고 lastWord도 날리고 추가 입력어로 쿼리 요청도 받고
					
					/*if (lastWord.length > 0 && $("#c-search-check").is(':checked')) {
						dataExplorerView1.query(lastWord + queryString);
						dataExplorerView2.query(lastWord + queryString);
						dataExplorerView3.query(lastWord + queryString);
					} else {
						dataExplorerView1.query(queryString);
						dataExplorerView2.query(queryString);
						dataExplorerView3.query(queryString);
					}
					if ($("#c-search-check").is(':checked')) {
						lastWord += " " + queryString + " ";
					} else { 
						lastWord = queryString + " ";
					}*/
					
					/*requestingObj[dataExplorerView1.reportID] = $.Deferred();
					requestingObj[dataExplorerView2.reportID] = $.Deferred();
					requestingObj[dataExplorerView3.reportID] = $.Deferred();
					doNothing = true;
					
					$.when(requestingObj[dataExplorerView1.reportID], requestingObj[dataExplorerView2.reportID], requestingObj[dataExplorerView3.reportID]).then(function(){
						//$('body').css("overflow", "auto");
						doNothing = false; 
						requestingObj = {};
					});*/
				/*} else {
					alert("모든 분류가 선택되지 않았습니다.");
				}*/
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
			/*
	        beforeShow: function(input, instance) { 
	        	$(input).datepicker('setDate', new Date());
	        },
	        */
	        onClose: function(dateText, inst) {
	        	var selectedDate = new Date(inst.selectedYear, inst.selectedMonth, inst.selectedDay);
	        	$(this).datepicker('setDate', selectedDate);
	        }
			/*,
	        onChangeMonthYear: function(year, month) {
	        	var selectedDate = new Date(year, month -1);
	        	$(this).datepicker('setDate', selectedDate);
	        }*/
		});
		var today = new Date();
		var monthAgo = new Date(today.getFullYear(), today.getMonth() - 1);
		$(".c-date-from").datepicker('setDate', monthAgo);
		$(".c-date-to").datepicker('setDate', today);
		
		/*
		var resultView = new xmlResult;
		function parseXml(xml) {
			var resultObj = {"documents": []};
			var item = $(xml).find("document");

			  $(item).each(function() {
				  var document = {};
				  $(this).find('content').each(function() {
					  document[$(this).attr('name')] = $(this).text();
				  });
				  resultObj["documents"].push(document);
				  //$("#results").append($(this).text()+ "<br />");
			  });
			  return resultObj;

		}
		*/
		$(function(){
		    $("#c-search-box").keypress(function(e){
		        if(e.keyCode==13)
		            $('.c-search-button').click();
		    });
			/*
		    $.ajax({
		        url: "http://211.109.180.11/vivisimo/cgi-bin/velocity.exe",
		        //url: 'xml/test.xml',
		    	type: 'GET',
		        dataType: "xml",
		        data: {
		        	'query': "철강",
		        	'sources': "KS-KMJ-SC",
		        	'v.app': "api-rest",
		        	'v.function': "query-search",
		        	'v.indent':	"true",
		        	'v.password': "passw0rd",
		        	'v.username': "admin"
		        },
		        success: function(data) {
		        	$("#results").append(
		        			resultView.render(parseXml(data)).el
		        	);
		        }
		    });
		    */
		});		
		//this.router = new router();
		
		eventHandler = {
				"selectClick": function(className, id, label) {
					if (className =="sanup") {
						$(".c-display-selected-1").text(label.slice(0,4));
						selections[0] = id;
						$("#accordion").accordion("option","active",1);
					} else if (className == "product") {
						$(".c-display-selected-2").text(label.slice(0,6));
						selections[1] = id;
						$("#accordion").accordion("option","active",2);
					} else if (className == "saup") {
						$(".c-display-selected-3").text(label.slice(0,7));
						selections[2] = id;
						lastWord = "";
						$("#c-search-check").prop("checked", false);
						$('#c-search-box').val("");
					} 
				},
				"loadStart": function(name) {
					//$('body').css("overflow", "hidden");
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
