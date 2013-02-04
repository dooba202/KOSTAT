// entry point for the app
define ([
         'module',
		 'jquery', 
		 'backbone', 
		 'underscore',
		 'utils/local_logger',
		 'views/listMenu',
		 'views/xmlResult',
		 'views/dataExplorer',
         'jquery.ui',
         'jquery.mCustomScrollbar',
         'jquery.mousewheel',
         'jquery.showLoading'
		 ], 

function( module, $, Backbone, _, Logger, listMenu, xmlResult, dataExplorer){
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
	var resolved = false;
	
	var init = function(){
		logger.log("index.js init");
		
		var listMenuView1 = new listMenu;
		var listMenuView2 = new listMenu;
		var listMenuView3 = new listMenu;
		var dataExplorerView1 = new dataExplorer({"width": "100%","height": "320px"});
		var dataExplorerView2 = new dataExplorer({"width": "100%","height": "320px"});
		var dataExplorerView3 = new dataExplorer({"width": "100%","height": "320px"});
		$("#accordion").append(listMenuView1.render({listNumber:1, className: "sanup", list: temp_list1}).el);
		$("#accordion").append(listMenuView2.render({listNumber:3, className: "product", list: temp_list2}).el);
		$("#accordion").append(listMenuView3.render({listNumber:2, className: "saup", list: temp_list3}).el);
		mCustomScrollSelectable(".l-list li");
		
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
			
			$("#accordion").accordion({ heightStyle: "content" });
			$("#accordion").on( "accordionactivate", function( event, ui ) {
				
			});
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
			
			$(".l-list-mid-5, .l-list-mid-10").mCustomScrollbar({
					scrollButtons:{
						enable:true
					},
					advanced:{
						updateOnBrowserResize:true, 
						updateOnContentResize:true
					}
			});
			
			$(".c-search-button").click(function(){
				if (selections.length > 2) {
					$(".placeholder").css({display:"none"});
					//$("#c-chart-title-1").text("사업체별");
					var queryString = $('#c-search-box').val(); 
					if (!resolved) {
						//var frame1 = $("<iframe src='http://211.109.180.11/vivisimo/cgi-bin/query-meta.exe?v%3Aproject=Poc_Test&query=%EC%B2%A0%EA%B0%95' frameborder='no' align='center' height = '350px' width = '100%'></iframe>");
						//var frame2 = $("<iframe src='js/templates/example.html' frameborder='no' align='center' height = '350px' width = '100%'></iframe>");
						//var frame3 = $("<iframe src='js/templates/example.html' frameborder='no' align='center' height = '350px' width = '100%'></iframe>");
						$('#frame1').append(dataExplorerView1.el);
						$('#frame2').append(dataExplorerView2.el);
						$('#frame3').append(dataExplorerView3.el);
						
						dataExplorerView1.query(queryString);
						dataExplorerView2.query(queryString);
						dataExplorerView3.query(queryString);
						//$('#frame2').append(frame2);
						//$('#frame3').append(frame3);
						resolved = true;
					} else {
						dataExplorerView1.query(queryString);
						dataExplorerView2.query(queryString);
						dataExplorerView3.query(queryString);
					}
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
			/*
	        beforeShow: function(input, instance) { 
	        	$(input).datepicker('setDate', new Date());
	        },
	        */
	        onClose: function(dateText, inst) {
	        	var selectedDate = new Date(inst.selectedYear, inst.selectedMonth, inst.selectedDay);
	        	$(this).datepicker('setDate', selectedDate);
	        },
	        onChangeMonthYear: function(year, month) {
	        	var selectedDate = new Date(year, month -1);
	        	$(this).datepicker('setDate', selectedDate);
	        }
		});
		var today = new Date();
		var eightYago = new Date(2005, 6);
		$(".c-date-from").datepicker('setDate', eightYago);
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
						$(".c-display-selected-1").text(label);
						selections[0] = id;
					} else if (className == "product") {
						$(".c-display-selected-2").text(label);
						selections[1] = id;
					} else if (className == "saup") {
						$(".c-display-selected-3").text(label);
						selections[2] = id;
					} 
				},
				"loadStart": function(name) {
					if (name == "dataExplorer0"){
						$('#frame1').showLoading();
					} else if (name == "dataExplorer1"){
						$('#frame2').showLoading();
					} else if (name == "dataExplorer2"){
						$('#frame3').showLoading();
					} 
				},
				"loadFinish": function(name) {
					if (name == "dataExplorer0"){
						$('#frame1').hideLoading();
					} else if (name == "dataExplorer1"){
						$('#frame2').hideLoading();
					} else if (name == "dataExplorer2"){
						$('#frame3').hideLoading();
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
