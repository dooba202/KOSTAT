// entry point for the app
define ([
         'module',
		 'jquery', 
		 'backbone', 
		 'underscore',
		 'utils/local_logger',
		 'collections/category',
		 'views/listMenu',
		 'views/chart',
         'jquery.ui',
         'jquery.mCustomScrollbar',
         'jquery.mousewheel',
         'jquery.showLoading',
         'jquery.toastmessage'
		 ], 

function( module, $, Backbone, _, Logger, category, listMenu, chart){
	var logger = new Logger("index.js");
		logger.setLevel("ALL");
	
	var selections = []; //to store category selections
	
	var restURL = window.location.protocol + "//" + window.location.host+ "/kostat/rest/";
	
	var growl = function(type, msgObj) {
		var showType = 'showErrorToast'; //default showing type is error
		if (type == 'error') {
			showType = 'showErrorToast';
		} else if (type =='success'){
			showType = 'showSuccessToast';
		} // showNoticeToast //showWarningToast //showStickyWarningToast
		
		var responsed = $.parseJSON(msgObj.responseText);
		if (typeof(msgObj) == 'string') {
			$().toastmessage(showType,msgObj);
		} else {
			if (responsed.redirect) {
				$().toastmessage('showToast', {
				    text     : responsed.userMessage + "<br/> 로그인 페이지로 이동합니다. <br/> 바로 이동하시려면 이 창을 닫거나, <br/>5초 뒤에 로그인 페이지로 이동 합니다.",
				    sticky   : true,
				    position : 'top-right',
				    type     : 'error',
				    closeText: '',
				    close    : function () {
				    	window.location = responsed.redirect;
				    }
				});
				setTimeout(function(){ window.location = responsed.redirect;}, 5000);
			} else {
				$().toastmessage(showType,responsed.userMessage);
			}
		}
	};
	/*
	//전년동월비
	var temp_data3 = [
		{
			name: "내수",				
			marker : {
				enabled : false,
				radius : 3
			},
			data: [[1136073600000,14.3],[1138752000000,32.8],[1141171200000,27.7],[1143849600000,29.1],[1146441600000,39.8],[1149120000000,38.4],[1151712000000,20.6],[1154390400000,30.4],[1157068800000,29.4],[1159660800000,27.3],[1162339200000,26.5],[1164931200000,3.6],[1167609600000,16.8],[1170288000000,14.8],[1172707200000,16.6],[1175385600000,16.3],[1177977600000,13.7],[1180656000000,12.4],[1183248000000,26],[1185926400000,21.1],[1188604800000,10.3],[1191196800000,18.3],[1193875200000,14.5],[1196467200000,21.7],[1199145600000,21.8],[1201824000000,23],[1204329600000,22.4],[1207008000000,24],[1209600000000,14.5],[1212278400000,13.3],[1214870400000,16],[1217548800000,3.1],[1220227200000,7.6],[1222819200000,-0.3],[1225497600000,-13.5],[1228089600000,-25.6],[1230768000000,-24.8],[1233446400000,-11.7],[1235865600000,-8.8],[1238544000000,-3.6],[1241136000000,13.8],[1243814400000,18.3],[1246406400000,15.8],[1249084800000,16.7],[1251763200000,15.4],[1254355200000,11.4],[1257033600000,28.2],[1259625600000,60.8],[1262304000000,40.4],[1264982400000,20],[1267401600000,24.4],[1270080000000,12.7],[1272672000000,6.4],[1275350400000,10.1],[1277942400000,9.1],[1280620800000,10],[1283299200000,7],[1285891200000,14.2],[1288569600000,12.7],[1291161600000,12.6],[1293840000000,9.7],[1296518400000,4.9],[1298937600000,-7.8],[1301616000000,-0.7],[1304208000000,1.4],[1306886400000,-2.6],[1309478400000,-11.3],[1312156800000,-7.4],[1314835200000,-1.8],[1317427200000,-0.1],[1320105600000,-7.4],[1322697600000,-2.3],[1325376000000,2.1],[1328054400000,3.7],[1330560000000,1.5],[1333238400000,-0.8],[1335830400000,-10.5],[1338508800000,-4.8],[1341100800000,8.8],[1343779200000,7.3],[1346457600000,0.6],[1349049600000,0.9],[1351728000000,8.3]]	
		},
		{
			name: "수출",				
			marker : {
				enabled : false,
				radius : 3
			},
			data: [[1136073600000,24.6],[1138752000000,30.8],[1141171200000,20.7],[1143849600000,23.7],[1146441600000,21.8],[1149120000000,19.3],[1151712000000,22.2],[1154390400000,26.3],[1157068800000,21.4],[1159660800000,13.6],[1162339200000,10.5],[1164931200000,4.5],[1167609600000,4.1],[1170288000000,2.4],[1172707200000,3.8],[1175385600000,7.8],[1177977600000,6],[1180656000000,17],[1183248000000,21.5],[1185926400000,16.8],[1188604800000,17.4],[1191196800000,26.2],[1193875200000,20.4],[1196467200000,35.9],[1199145600000,29.5],[1201824000000,28.1],[1204329600000,35.9],[1207008000000,30.5],[1209600000000,34.4],[1212278400000,19.1],[1214870400000,15.3],[1217548800000,10.4],[1220227200000,10.7],[1222819200000,-7.2],[1225497600000,-25.2],[1228089600000,-37],[1230768000000,-32.2],[1233446400000,-14.4],[1235865600000,-15.8],[1238544000000,-9],[1241136000000,-5.6],[1243814400000,1.6],[1246406400000,1.7],[1249084800000,3.3],[1251763200000,2.4],[1254355200000,11.3],[1257033600000,43.1],[1259625600000,64],[1262304000000,60.1],[1264982400000,30.5],[1267401600000,32],[1270080000000,27.6],[1272672000000,26.6],[1275350400000,20.9],[1277942400000,21.6],[1280620800000,23.4],[1283299200000,18.4],[1285891200000,14.5],[1288569600000,15.2],[1291161600000,29.9],[1293840000000,23.7],[1296518400000,20.2],[1298937600000,22.5],[1301616000000,19.4],[1304208000000,15.2],[1306886400000,10.4],[1309478400000,0.8],[1312156800000,2.4],[1314835200000,14.3],[1317427200000,16.8],[1320105600000,10.1],[1322697600000,3.1],[1325376000000,0.8],[1328054400000,11.5],[1330560000000,1.5],[1333238400000,-2.5],[1335830400000,4],[1338508800000,6.1],[1341100800000,1.4],[1343779200000,8.7],[1346457600000,0.7],[1349049600000,0.7],[1351728000000,11.8]]	
		},
		{
			name: "재고",				
			marker : {
				enabled : false,
				radius : 3
			},
			data: [[1104537600000,38],[1107216000000,26.2],[1109635200000,21.1],[1112313600000,22],[1114905600000,17.7],[1117584000000,8.1],[1120176000000,3.1],[1122854400000,-0.3],[1125532800000,6.1],[1128124800000,-0.7],[1130803200000,6],[1133395200000,9],[1136073600000,13.7],[1138752000000,18.4],[1141171200000,26],[1143849600000,28.7],[1146441600000,40.1],[1149120000000,58.1],[1151712000000,54.8],[1154390400000,49.8],[1157068800000,42.7],[1159660800000,52.1],[1162339200000,55.6],[1164931200000,40.3],[1167609600000,33.1],[1170288000000,27.4],[1172707200000,14.7],[1175385600000,5.1],[1177977600000,3.3],[1180656000000,-2.3],[1183248000000,-2.7],[1185926400000,-1.7],[1188604800000,-5.2],[1191196800000,-2.2],[1193875200000,-1.5],[1196467200000,12.8],[1199145600000,23.9],[1201824000000,37.1],[1204329600000,44.4],[1207008000000,55.2],[1209600000000,63.3],[1212278400000,78.4],[1214870400000,83.7],[1217548800000,75.7],[1220227200000,73.3],[1222819200000,51],[1225497600000,36.8],[1228089600000,10.2],[1230768000000,-3.9],[1233446400000,-17.1],[1235865600000,-17.3],[1238544000000,-26.9],[1241136000000,-32.2],[1243814400000,-32.7],[1246406400000,-33],[1249084800000,-27.7],[1251763200000,-18.6],[1254355200000,-10.9],[1257033600000,-3.8],[1259625600000,12],[1262304000000,12],[1264982400000,33.8],[1267401600000,40.8],[1270080000000,60],[1272672000000,60.4],[1275350400000,51.9],[1277942400000,62.8],[1280620800000,61],[1283299200000,52.5],[1285891200000,45],[1288569600000,38.4],[1291161600000,26.8],[1293840000000,19.4],[1296518400000,23],[1298937600000,15.6],[1301616000000,10.1],[1304208000000,8.6],[1306886400000,8.9],[1309478400000,9.1],[1312156800000,12.8],[1314835200000,10.4],[1317427200000,21.3],[1320105600000,29.8],[1322697600000,45.2],[1325376000000,52.8],[1328054400000,38.7],[1330560000000,25.2],[1333238400000,23.3],[1335830400000,20.5],[1338508800000,9],[1341100800000,4.7],[1343779200000,8.3],[1346457600000,2.1],[1349049600000,1.9],[1351728000000,3.3]]	
		},
		{
			name: "생산",				
			marker : {
				enabled : false,
				radius : 3
			},
			data: [[1104537600000,21.7],[1107216000000,2.4],[1109635200000,12.2],[1112313600000,10.1],[1114905600000,6.2],[1117584000000,8.6],[1120176000000,16.3],[1122854400000,18.8],[1125532800000,22.9],[1128124800000,25.1],[1130803200000,31.8],[1133395200000,32],[1136073600000,27.6],[1138752000000,36],[1141171200000,25.4],[1143849600000,28.4],[1146441600000,27.8],[1149120000000,26.8],[1151712000000,21.9],[1154390400000,26.2],[1157068800000,24],[1159660800000,18.8],[1162339200000,13.8],[1164931200000,1.2],[1167609600000,5.3],[1170288000000,-1.3],[1172707200000,1.1],[1175385600000,3.5],[1177977600000,4.6],[1180656000000,13.2],[1183248000000,22.1],[1185926400000,18],[1188604800000,15.9],[1191196800000,23.2],[1193875200000,19.4],[1196467200000,33.9],[1199145600000,30.9],[1201824000000,33.1],[1204329600000,35.7],[1207008000000,33.1],[1209600000000,35.7],[1212278400000,22],[1214870400000,17.4],[1217548800000,7],[1220227200000,6.2],[1222819200000,-9.9],[1225497600000,-25],[1228089600000,-39.8],[1230768000000,-30.8],[1233446400000,-13.4],[1235865600000,-10],[1238544000000,-4.5],[1241136000000,-1.2],[1243814400000,8.4],[1246406400000,10.2],[1249084800000,15.6],[1251763200000,17.8],[1254355200000,21.7],[1257033600000,48.9],[1259625600000,91.5],[1262304000000,63.5],[1264982400000,38.5],[1267401600000,39.3],[1270080000000,31],[1272672000000,27.5],[1275350400000,22.4],[1277942400000,22.8],[1280620800000,21.7],[1283299200000,13.6],[1285891200000,12.9],[1288569600000,13],[1291161600000,19.2],[1293840000000,16.5],[1296518400000,17],[1298937600000,8.7],[1301616000000,11.5],[1304208000000,12],[1306886400000,6.2],[1309478400000,2.1],[1312156800000,1.7],[1314835200000,9.8],[1317427200000,17.1],[1320105600000,13.4],[1322697600000,6.7],[1325376000000,4.4],[1328054400000,10.7],[1330560000000,4.4],[1333238400000,0.4],[1335830400000,1.9],[1338508800000,5.1],[1341100800000,0],[1343779200000,7.9],[1346457600000,3],[1349049600000,3.5],[1351728000000,12]]	
		},
		{
			name: "출하",				
			marker : {
				enabled : false,
				radius : 3
			},
			data: [[1136073600000,22.4],[1138752000000,33.2],[1141171200000,22.1],[1143849600000,24.8],[1146441600000,26.7],[1149120000000,22.7],[1151712000000,20.3],[1154390400000,27],[1157068800000,22.8],[1159660800000,16],[1162339200000,12.8],[1164931200000,2.3],[1167609600000,5.1],[1170288000000,3.1],[1172707200000,4.5],[1175385600000,7.9],[1177977600000,6.5],[1180656000000,14.5],[1183248000000,22.5],[1185926400000,15.2],[1188604800000,12.7],[1191196800000,21.6],[1193875200000,17.1],[1196467200000,30.3],[1199145600000,25.1],[1201824000000,23.4],[1204329600000,30.4],[1207008000000,26.3],[1209600000000,25.9],[1212278400000,16.2],[1214870400000,14.3],[1217548800000,9.4],[1220227200000,10],[1222819200000,-5.2],[1225497600000,-21.5],[1228089600000,-34.4],[1230768000000,-29.1],[1233446400000,-11.9],[1235865600000,-12],[1238544000000,-5.3],[1241136000000,0.1],[1243814400000,6.3],[1246406400000,5.6],[1249084800000,4.8],[1251763200000,5.2],[1254355200000,10.2],[1257033600000,38.5],[1259625600000,65.9],[1262304000000,54.8],[1264982400000,28.1],[1267401600000,28],[1270080000000,23.4],[1272672000000,21.9],[1275350400000,18.6],[1277942400000,18.2],[1280620800000,21.2],[1283299200000,15.9],[1285891200000,14.6],[1288569600000,13.5],[1291161600000,22.7],[1293840000000,17.8],[1296518400000,14.5],[1298937600000,13.2],[1301616000000,12.1],[1304208000000,9.3],[1306886400000,4.2],[1309478400000,-4],[1312156800000,-2.4],[1314835200000,8.2],[1317427200000,9.8],[1320105600000,3.9],[1322697600000,0.7],[1325376000000,0.3],[1328054400000,9.2],[1330560000000,1.9],[1333238400000,-1.9],[1335830400000,2.3],[1338508800000,4.6],[1341100800000,2],[1343779200000,7.9],[1346457600000,0.7],[1349049600000,1.4],[1351728000000,11.5]]		
		}
	];
	*/
	
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
	
	var init = function(){
		logger.log("index.js init"); 
		
		var listMenuView1 = new listMenu;
		var listMenuView2 = new listMenu;
		var listMenuView3 = new listMenu;
		/* chartView를 생성해서 el을 존재하는 dom node에 append한 후 render를 호출한다 */
		var chartView1 = new chart({id: 'chartA', title: 0, width: '100%', height: '350px'});
		var chartView2 = new chart({id: 'chartB', title: 1, width: '100%', height: '350px'});
		var chartView3 = new chart({id: 'chartC', title: 2, width: '100%', height: '350px'});
		
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
			$("#accordion").accordion({ heightStyle: "content",
				activate: function( event, ui ) {
					//something
				}	
			});
			//$("#accordion").on( "accordionactivate", function( event, ui ) {});
			$(".l-list-mid-5, .l-list-mid-10").mCustomScrollbar({
				scrollButtons:{
					enable:true
				},
				advanced:{
					updateOnBrowserResize:true, 
					updateOnContentResize:true
				}
			});
		}();
		
		$('#chartContainer').append(chartView1.el);
		$('#chartContainer').append(chartView2.el);
		$('#chartContainer').append(chartView3.el);
		
		$(function(){
			var resizeWindow = function(){
				$("#side, #main").css("height", function(){
					var winHeight = document.documentElement.offsetHeight;
					return winHeight - 140;
				});
				$(".l-chart").css("height", function(){
					var winHeight = document.documentElement.offsetHeight;
					return winHeight - 250;
				});
				$(".placeholder").css("height", function(){
					var winHeight = document.documentElement.offsetHeight;
					return winHeight - 190;
				});
			};
			$(window).bind("resize", resizeWindow);
			resizeWindow();
			
			$(".c-keyword-set").buttonset();
			
			$(".l-chart").mCustomScrollbar({
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
					//$(".placeholder").css({display:"none"});
					$(".l-category div").css({display:"block"});
					$('.c-date-from-selected').text($(".c-date-from").val());
					$('.c-date-to-selected').text($(".c-date-to").val());
					
					var gubun = $(".c-keyword-set :radio:checked").val();
					var fromDate = $('.c-date-from').datepicker("getDate");
					var toDate = $('.c-date-to').datepicker("getDate");
					
					function zeroAdder(monthStr) {
						if (monthStr < 10) {
							return monthStr = "0" + monthStr;
						} else {
							return ""+ monthStr;
						}
					} 
					var fromDateStr = fromDate.getFullYear() + zeroAdder(fromDate.getMonth() + 1);
					var toDateStr = toDate.getFullYear() + zeroAdder(toDate.getMonth() + 1);
					
					var querySet = [];
					var firstParam = "";
					var secondParam = "";
					var jisuSet = ["jisu", "jisuLM", "jisuSM"];
					var mulyangSet = ["mulyang", "mulyangLM", "mulyangSM"];
					if (gubun == "jisu") {
						querySet = jisuSet;
						firstParam = selections[0];
						secondParam = selections[1];
					} else if (gubun == "mul") {
						querySet = mulyangSet;
						firstParam = selections[1];
						secondParam = selections[2];
					} 
					$.when(
						$.ajax({
							'url' : restURL + "chart/" + querySet[0] + "/" + firstParam + "/" + secondParam + "?from=" + fromDateStr + "&to=" + toDateStr,
							'dataType' : 'json',
							'success' : function(data){

							},
							error: function(e) {
								$(".placeholder").css({display:"block"});
								growl("showErrorToast", e);
								//alert(e.userMessage);
							}
				        }),
						$.ajax({
							'url' : restURL + "chart/" + querySet[1] + "/" + firstParam + "/" + secondParam + "?from=" + fromDateStr + "&to=" + toDateStr,
							'dataType' : 'json'
				        }),
						$.ajax({
							'url' : restURL + "chart/" + querySet[2] + "/" + firstParam + "/" + secondParam + "?from=" + fromDateStr + "&to=" + toDateStr,
							'dataType' : 'json'
				        })
					).then( function(data1, data2, data3) {
						$(".placeholder").css({display:"none"});
						if (gubun == "jisu") { 
							$("#c-chart-title-0").text("지수");
						} else {
							$("#c-chart-title-0").text("물량");
						}
						$("#c-chart-title-1").text("전월비");
						$("#c-chart-title-2").text("전년동월비");
						chartView1.render(data1[0], "");
						chartView2.render(data2[0], "%");
						chartView3.render(data3[0], "%");
						
						if ( selections[1] == 0) {
							$("#c-category-title").addClass("c-category-title1");
							$("#c-category-title").removeClass("c-category-title2");
							$("#c-category-title").removeClass("c-category-title2");
						} else if ( selections[2] == 0){
							$("#c-category-title").addClass("c-category-title2");
							$("#c-category-title").removeClass("c-category-title1");
							$("#c-category-title").removeClass("c-category-title3");
						} else {
							$("#c-category-title").addClass("c-category-title3");
							$("#c-category-title").removeClass("c-category-title1");
							$("#c-category-title").removeClass("c-category-title2");
						}
					}).fail( function(e) {
						$(".placeholder").css({display:"block"});
					});
				} else {
					alert("모든 분류가 선택되지 않았습니다.");
				}
			});
		});		
		$(".c-date-from").datepicker({
			dateFormat: 'yy년 m월 dd일',
			currentText: "오늘",
			closeText: "완료",
			monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
			changeMonth:true,
			changeYear:true,
			showButtonPanel: true,
	        onClose: function(dateText, inst) {

	        	var selectedDate = new Date(inst.drawYear, inst.drawMonth, 1);
	        	$(this).datepicker('setDate', selectedDate);
	        }
		});
		$(".c-date-to").datepicker({
			dateFormat: 'yy년 m월 dd일',
			currentText: "오늘",
			closeText: "완료",
			monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
			changeMonth:true,
			changeYear:true,
			showButtonPanel: true,
	        onClose: function(dateText, inst) {
	        	var selectedDate = new Date(inst.selectedYear, inst.selectedMonth + 1, 0);
	        	$(this).datepicker('setDate', selectedDate);
	        }
		});
		var today = new Date();
		var toDate = new Date(today.getFullYear(), today.getMonth() + 1, 0);
		var eightYago = new Date(2005, 6);
		$(".c-date-from").datepicker('setDate', eightYago);
		$(".c-date-to").datepicker('setDate', toDate);
		//$(".c-date-from, .c-date-to").datepicker( "option", "dateFormat","yy-mm");
		//this.router = new router();
		
		eventHandler = {
				"selectClick": function(className, code, label) {
					if (className =="sanup") {
						$(".c-display-selected-1").effect("pulsate","slow");
						$(".c-display-selected-1 .center").text(label);
						$(".c-display-selected-2 .center").text("품목 분류");
						$(".c-display-selected-3 .center").text("사업체 분류");
						selections[0] = code;
						selections = selections.slice(0,1);
						
						var filteredCollection = new category( pumCollection.where({parent: code}) ).toJSON();
						
						filteredCollection.reverse();
						filteredCollection.push({"id":0, "code": 0, "name": "전체"});
						filteredCollection.reverse();
						
						var minHeight = Math.min(filteredCollection.length ,10); 
						listMenuView2.render({'listNumber':3,'line-height': minHeight, 'className': "product", 'list': filteredCollection});
						
						//$("listMenuView2.el .l-list-mid-5, .l-list-mid-10").mCustomScrollbar("destroy");
						mCustomScrollSelectable(".l-list li");
						
						if (minHeight > 0) {
							$("#accordion").accordion("option","active",1);
							$("#category2 > .l-list-mid-5, .l-list-mid-10").mCustomScrollbar({
								scrollButtons:{
									enable:true
								},
								advanced:{
									updateOnBrowserResize:true, 
									updateOnContentResize:true
								}
							});
						}
					} else if (className == "product") {
						$(".c-display-selected-2").effect("pulsate","slow");
						$(".c-display-selected-2 .center").text(label);
						$(".c-display-selected-3 .center").text("사업체 분류");
						selections[1] = code;
						selections = selections.slice(0,2);
						var filteredCollection = new category( saupCollection.where({parent: code}) ).toJSON();
						
						filteredCollection.reverse();
						filteredCollection.push({"id":0, "code": 0, "name": "전체"});
						filteredCollection.reverse();
						
						var minHeight = Math.min(filteredCollection.length ,10); 
						
						listMenuView3.render({'listNumber':2,'line-height': minHeight, 'className': "saup", 'list': filteredCollection});
						
						//$("listMenuView3.el .l-list-mid-5, .l-list-mid-10").mCustomScrollbar("destroy");
						/*
						$("#category3 > .l-list-mid-5, .l-list-mid-10").mCustomScrollbar({
							scrollButtons:{
								enable:true
							},
							advanced:{
								updateOnBrowserResize:true, 
								updateOnContentResize:true
							}
						});
						*/
						
						mCustomScrollSelectable(".l-list li");
						if (minHeight > 0) {
							$("#accordion").accordion("option","active",2);
						}
					} else if (className == "saup") {
						$(".c-display-selected-3").effect("pulsate","slow");
						$(".c-display-selected-3 .center").text(label);
						selections[2] = code;
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
		/* event binding end */
	};
	return {
		'init': init
	};
});
