// entry point for the app
define ([
         'module',
		 'jquery', 
		 'backbone', 
		 'underscore',
		 'utils/local_logger',
		 'views/listMenu',
		 'views/chart',
         'jquery.ui',
         'jquery.mCustomScrollbar',
         'jquery.mousewheel',
         'jquery.showLoading'
		 ], 

function( module, $, Backbone, _, Logger, listMenu, chart){
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
	                  {"id": "prod000","name": "전체"},
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
	
	var temp_data1 = [
		{
			name: "내수",				
			marker : {
				enabled : false,
				radius : 3
			},
			data: [[1107216000000,82.8],[1109635200000,97.9],[1112313600000,93],[1114905600000,92.6],[1120176000000,96.9],[1122854400000,98.1],[1125532800000,107.1],[1130803200000,111.4],[1133395200000,120.4],[1136073600000,112.5],[1138752000000,113.4],[1141171200000,126.9],[1143849600000,119.6],[1146441600000,127.6],[1149120000000,125.1],[1151712000000,113.7],[1154390400000,129.6],[1157068800000,138.4],[1159660800000,140.2],[1162339200000,138.8],[1164931200000,121],[1167609600000,127.3],[1170288000000,125],[1172707200000,141],[1177977600000,142.5],[1180656000000,139.2],[1183248000000,144],[1185926400000,150.3],[1188604800000,146.5],[1196467200000,143.6],[1199145600000,150.3],[1201824000000,142.9],[1204329600000,165.5],[1207008000000,156.6],[1209600000000,151.9],[1212278400000,154.7],[1214870400000,163.8],[1217548800000,159.4],[1225497600000,137.7],[1228089600000,103.2],[1230768000000,113.7],[1233446400000,129.4],[1235865600000,156.5],[1243814400000,177.5],[1246406400000,183.8],[1249084800000,174.7],[1251763200000,177.7],[1254355200000,171.5],[1257033600000,174.2],[1259625600000,174],[1262304000000,163.8],[1264982400000,161.3],[1267401600000,190.8],[1270080000000,180.7],[1272672000000,192.2],[1275350400000,205],[1277942400000,204.7],[1285891200000,197.4],[1288569600000,191.8],[1291161600000,186.9],[1293840000000,175.6],[1296518400000,163.6],[1304208000000,184.4],[1306886400000,185.6],[1309478400000,178.2],[1312156800000,173.8],[1314835200000,182.4],[1317427200000,186.2],[1320105600000,171.1],[1322697600000,179.3],[1325376000000,175.9],[1328054400000,172.8],[1330560000000,185.3],[1333238400000,178.4],[1335830400000,182.2],[1346457600000,188.4],[1351728000000,194.6]]
		},
		{
			name: "수출",				
			marker : {
				enabled : false,
				radius : 3
			},
			data: [[1107216000000,82.5],[1109635200000,97.2],[1112313600000,91.1],[1114905600000,91.3],[1117584000000,93.6],[1120176000000,91.9],[1122854400000,96.8],[1125532800000,106.8],[1130803200000,126.1],[1133395200000,107.8],[1136073600000,116.5],[1138752000000,108.7],[1141171200000,116.1],[1143849600000,112.5],[1146441600000,112],[1149120000000,110.8],[1151712000000,111.5],[1154390400000,121],[1159660800000,136.5],[1162339200000,137.4],[1164931200000,111.2],[1167609600000,119.2],[1170288000000,109.1],[1175385600000,119.6],[1180656000000,128.3],[1183248000000,134.9],[1185926400000,139],[1193875200000,163.9],[1199145600000,152.4],[1201824000000,138.7],[1204329600000,160.9],[1207008000000,155.4],[1209600000000,157.2],[1214870400000,154.5],[1217548800000,153.9],[1220227200000,164.2],[1222819200000,157.8],[1225497600000,122.7],[1228089600000,95.1],[1230768000000,105.5],[1233446400000,120.8],[1235865600000,137.5],[1238544000000,143.5],[1241136000000,150.9],[1243814400000,156.5],[1249084800000,158.6],[1251763200000,168.4],[1254355200000,175.7],[1257033600000,175.7],[1259625600000,156.9],[1262304000000,167.7],[1264982400000,156.3],[1267401600000,179.5],[1270080000000,182.3],[1272672000000,189.2],[1277942400000,192.7],[1280620800000,197.7],[1291161600000,202.1],[1293840000000,203.9],[1296518400000,186.6],[1298937600000,217],[1301616000000,214.6],[1309478400000,191.7],[1312156800000,199.6],[1314835200000,225.6],[1317427200000,231.8],[1320105600000,219.6],[1322697600000,206.9],[1325376000000,204.6],[1333238400000,207.8],[1338508800000,217.4],[1341100800000,192.9],[1343779200000,214.8],[1349049600000,231.3],[1351728000000,243.4]]
		},
		{
			name: "재고",				
			marker : {
				enabled : false,
				radius : 3
			},
			data: [[1104537600000,106.8],[1107216000000,99.1],[1109635200000,101.4],[1112313600000,108.3],[1114905600000,107.55],[1117584000000,96.15],[1120176000000,94.7],[1122854400000,95.25],[1125532800000,96],[1128124800000,96.2],[1130803200000,94],[1133395200000,95.9],[1136073600000,91.4],[1138752000000,92.6],[1141171200000,99.7],[1143849600000,121.7],[1146441600000,127.25],[1149120000000,128.25],[1151712000000,127.9],[1154390400000,126.6],[1157068800000,125.4],[1159660800000,125.6],[1162339200000,106.9],[1164931200000,134.65],[1167609600000,135.85],[1170288000000,157.8],[1172707200000,104.9],[1175385600000,130.35],[1177977600000,135.7],[1180656000000,112.5],[1183248000000,130.9],[1185926400000,130.4],[1188604800000,124.95],[1191196800000,130.45],[1193875200000,133.65],[1196467200000,150.2],[1199145600000,161.9],[1201824000000,165.6],[1204329600000,170.8],[1207008000000,177.05],[1209600000000,188.9],[1212278400000,119.6],[1214870400000,206.25],[1217548800000,198.1],[1220227200000,185.05],[1222819200000,173.05],[1225497600000,173.2],[1228089600000,163.75],[1230768000000,157.45],[1233446400000,144.9],[1235865600000,99.8],[1238544000000,134],[1241136000000,143.3],[1243814400000,147.55],[1246406400000,150],[1249084800000,155.45],[1251763200000,167.15],[1254355200000,175.45],[1257033600000,178.2],[1259625600000,140.9],[1262304000000,185.35],[1264982400000,245.3],[1267401600000,140.8],[1270080000000,206.9],[1272672000000,219.3],[1275350400000,285.9],[1277942400000,235.95],[1280620800000,241.1],[1283299200000,243.05],[1285891200000,296.2],[1288569600000,232],[1291161600000,233.95],[1293840000000,219.2],[1296518400000,302.1],[1298937600000,233.4],[1301616000000,294.3],[1304208000000,163.9],[1306886400000,238.75],[1309478400000,176.6],[1312156800000,180.5],[1314835200000,265.85],[1317427200000,274.3],[1320105600000,183.8],[1322697600000,173.6],[1325376000000,184],[1328054400000,306.65],[1330560000000,378.3],[1333238400000,280.05],[1335830400000,283.5],[1341100800000,350],[1343779200000,384],[1346457600000,352.2],[1349049600000,287.85],[1351728000000,213.1]]
		},
		{
			name: "생산",				
			marker : {
				enabled : false,
				radius : 3
			},
			data: [[1104537600000,92.3],[1107216000000,81.9],[1109635200000,98.35],[1112313600000,96],[1114905600000,97],[1117584000000,93.55],[1120176000000,92.9],[1122854400000,95.2],[1125532800000,107],[1128124800000,113.7],[1130803200000,118.35],[1133395200000,108.9],[1136073600000,96.4],[1138752000000,106.45],[1141171200000,117.9],[1143849600000,106.9],[1146441600000,110.4],[1149120000000,111.55],[1151712000000,106.6],[1154390400000,116.85],[1157068800000,129.55],[1159660800000,130.55],[1162339200000,131.45],[1164931200000,113.75],[1167609600000,126.9],[1170288000000,110.5],[1172707200000,119.9],[1175385600000,116.55],[1177977600000,116.5],[1180656000000,102.3],[1183248000000,142.4],[1185926400000,148.6],[1188604800000,132.1],[1191196800000,151.75],[1193875200000,147.3],[1196467200000,140.1],[1199145600000,137.45],[1201824000000,124.75],[1204329600000,143.85],[1207008000000,138.4],[1209600000000,140.25],[1212278400000,137.15],[1214870400000,140.75],[1217548800000,130.3],[1220227200000,134.95],[1222819200000,135.2],[1225497600000,112.3],[1228089600000,89.1],[1230768000000,95.25],[1233446400000,80.1],[1235865600000,127.25],[1238544000000,130],[1241136000000,135.95],[1243814400000,147.9],[1246406400000,103.5],[1249084800000,146.3],[1251763200000,155.85],[1254355200000,199.9],[1257033600000,155.15],[1259625600000,150.2],[1262304000000,144.35],[1264982400000,137.95],[1267401600000,219.1],[1270080000000,163.3],[1272672000000,172.2],[1275350400000,174.35],[1277942400000,179.6],[1280620800000,176.4],[1283299200000,175],[1285891200000,174.65],[1288569600000,121.3],[1291161600000,226.1],[1293840000000,228.5],[1298937600000,116.6],[1301616000000,240.4],[1304208000000,181.5],[1306886400000,239.7],[1309478400000,173.3],[1312156800000,110.7],[1314835200000,183.05],[1317427200000,191.65],[1320105600000,187.25],[1322697600000,181.75],[1325376000000,104.7],[1328054400000,114],[1330560000000,250],[1335830400000,183.15],[1338508800000,253.3],[1341100800000,171.9],[1343779200000,255.5],[1346457600000,260.2],[1349049600000,198.9],[1351728000000,206.1]]
		},
		{
			name: "출하",				
			marker : {
				enabled : false,
				radius : 3
			},
			data: [[1104537600000,92.8],[1107216000000,82.9],[1109635200000,98.1],[1112313600000,96.6],[1114905600000,94.1],[1117584000000,95.45],[1120176000000,93.45],[1122854400000,95.5],[1125532800000,106.9],[1128124800000,113.5],[1130803200000,111.1],[1133395200000,111.9],[1136073600000,95.6],[1138752000000,105.05],[1141171200000,114.75],[1143849600000,107.65],[1146441600000,114.6],[1149120000000,110.45],[1151712000000,104],[1154390400000,116.5],[1157068800000,127.3],[1159660800000,127.55],[1162339200000,129.1],[1164931200000,114.5],[1167609600000,124.2],[1170288000000,110.35],[1172707200000,120.55],[1175385600000,115.05],[1177977600000,116.25],[1180656000000,135.1],[1183248000000,142.2],[1185926400000,129.5],[1188604800000,153.3],[1191196800000,147.9],[1193875200000,141.3],[1196467200000,152.4],[1199145600000,105.2],[1201824000000,119.85],[1204329600000,143.15],[1207008000000,134.5],[1209600000000,135.25],[1212278400000,159.3],[1214870400000,137],[1217548800000,131.55],[1220227200000,137.95],[1222819200000,137.75],[1225497600000,111.25],[1228089600000,93.55],[1230768000000,93],[1233446400000,103.8],[1235865600000,124.15],[1238544000000,126],[1241136000000,130.35],[1243814400000,141.3],[1246406400000,138.7],[1249084800000,137.6],[1251763200000,145.05],[1254355200000,143.45],[1257033600000,147.4],[1259625600000,142.15],[1262304000000,177.7],[1264982400000,128.9],[1267401600000,153.95],[1270080000000,192.3],[1272672000000,158.2],[1275350400000,162.6],[1277942400000,160.75],[1280620800000,161.7],[1283299200000,116.2],[1285891200000,164.55],[1288569600000,163.35],[1291161600000,207.2],[1293840000000,157.75],[1296518400000,140.2],[1298937600000,220.5],[1301616000000,159.45],[1304208000000,222.6],[1306886400000,104.8],[1309478400000,150],[1312156800000,102.6],[1314835200000,167.7],[1317427200000,171.1],[1320105600000,163.35],[1322697600000,165.3],[1325376000000,152.35],[1328054400000,210.7],[1330560000000,170.05],[1333238400000,97.4],[1335830400000,99.9],[1338508800000,225.1],[1341100800000,205.8],[1343779200000,223.6],[1346457600000,169.85],[1349049600000,173.4],[1351728000000,247.4]]
		}
	];
	
	//전월비
	var temp_data2 = [
		{
			name: "내수",				
			marker : {
				enabled : false,
				radius : 3
			},
			data: [[1107216000000,-14.3],[1109635200000,16.5],[1112313600000,-5.9],[1114905600000,-1.5],[1117584000000,1],[1120176000000,3.9],[1122854400000,2.2],[1125532800000,9.1],[1128124800000,2.7],[1130803200000,2.6],[1133395200000,5],[1136073600000,-4.7],[1138752000000,-0.4],[1141171200000,12],[1143849600000,-4.8],[1146441600000,6.7],[1149120000000,0],[1151712000000,-9.5],[1154390400000,10.5],[1157068800000,8.2],[1159660800000,1],[1162339200000,2],[1164931200000,-13.9],[1167609600000,7.4],[1170288000000,-2.1],[1172707200000,13.8],[1175385600000,-5],[1177977600000,4.2],[1180656000000,-1.1],[1183248000000,1.4],[1185926400000,6.2],[1188604800000,-1.4],[1191196800000,8.3],[1193875200000,-1.3],[1196467200000,-8.6],[1199145600000,7.6],[1201824000000,-1.2],[1204329600000,13.3],[1207008000000,-3.9],[1209600000000,-3.7],[1212278400000,-2.2],[1214870400000,3.9],[1217548800000,-5.7],[1220227200000,2.9],[1222819200000,0.4],[1225497600000,-14.3],[1228089600000,-21.4],[1230768000000,8.8],[1233446400000,16.2],[1235865600000,16.9],[1238544000000,1.6],[1241136000000,13.6],[1243814400000,1.8],[1246406400000,1.7],[1249084800000,-4.9],[1251763200000,1.7],[1254355200000,-3.1],[1257033600000,-1.3],[1259625600000,-1.5],[1262304000000,-5.1],[1264982400000,-0.8],[1267401600000,21.2],[1270080000000,-7.9],[1272672000000,7.2],[1275350400000,5.4],[1277942400000,0.8],[1280620800000,-4.2],[1283299200000,-1.1],[1285891200000,3.5],[1288569600000,-2.6],[1291161600000,-1.5],[1293840000000,-7.5],[1296518400000,-5],[1298937600000,6.5],[1301616000000,-0.9],[1304208000000,9.4],[1306886400000,1.3],[1309478400000,-8.3],[1312156800000,0],[1314835200000,5],[1317427200000,5.2],[1320105600000,-9.7],[1322697600000,3.9],[1325376000000,-3.4],[1328054400000,-3.5],[1330560000000,4.2],[1333238400000,-3.1],[1335830400000,-1.3],[1338508800000,7.7],[1341100800000,4.9],[1343779200000,-1.5],[1346457600000,-1.5],[1349049600000,5.6],[1351728000000,-3.2]]
		},
		{
			name: "수출",				
			marker : {
				enabled : false,
				radius : 3
			},
			data: [[1107216000000,-10.8],[1109635200000,16.7],[1112313600000,-5.6],[1114905600000,1.8],[1117584000000,1.2],[1120176000000,-1.3],[1122854400000,4.5],[1125532800000,9.9],[1128124800000,13.3],[1130803200000,3.8],[1133395200000,-13.2],[1136073600000,6.4],[1138752000000,-6.3],[1141171200000,7.7],[1143849600000,-3.3],[1146441600000,0.2],[1149120000000,-0.9],[1151712000000,1.2],[1154390400000,8],[1157068800000,5.6],[1159660800000,6],[1162339200000,0.9],[1164931200000,-17.9],[1167609600000,5.9],[1170288000000,-7.8],[1172707200000,9.1],[1175385600000,0.5],[1177977600000,-1.5],[1180656000000,9.4],[1183248000000,5],[1185926400000,3.9],[1188604800000,6.2],[1191196800000,13.8],[1193875200000,-3.6],[1196467200000,-7.3],[1199145600000,1],[1201824000000,-8.9],[1204329600000,15.8],[1207008000000,-3.5],[1209600000000,1.5],[1212278400000,-3.1],[1214870400000,1.7],[1217548800000,-0.6],[1220227200000,6.5],[1222819200000,-4.6],[1225497600000,-22.3],[1228089600000,-21.9],[1230768000000,8.5],[1233446400000,15.2],[1235865600000,13.9],[1238544000000,4.3],[1241136000000,5.3],[1243814400000,4.3],[1246406400000,1.8],[1249084800000,1],[1251763200000,5.6],[1254355200000,3.7],[1257033600000,-0.2],[1259625600000,-10.4],[1262304000000,6],[1264982400000,-6.1],[1267401600000,15.2],[1270080000000,0.8],[1272672000000,4.5],[1275350400000,-0.4],[1277942400000,2.5],[1280620800000,2.5],[1283299200000,1.2],[1285891200000,0.2],[1288569600000,0.4],[1291161600000,1],[1293840000000,0.9],[1296518400000,-8.8],[1298937600000,17.4],[1301616000000,-1.7],[1304208000000,0.7],[1306886400000,-4.6],[1309478400000,-6.4],[1312156800000,4.1],[1314835200000,13.1],[1317427200000,2.4],[1320105600000,-5.3],[1322697600000,-5.4],[1325376000000,-1.4],[1328054400000,0.9],[1330560000000,6.8],[1333238400000,-5.6],[1335830400000,7.4],[1338508800000,-2.6],[1341100800000,-10.6],[1343779200000,11.6],[1346457600000,4.7],[1349049600000,2.4],[1351728000000,5.2]]
		},
		{
			name: "재고",				
			marker : {
				enabled : false,
				radius : 3
			},
			data: [[1104537600000,5.1],[1107216000000,-6.5],[1109635200000,0.1],[1112313600000,2.3],[1114905600000,0.2],[1117584000000,-7.4],[1120176000000,0.3],[1122854400000,1.7],[1125532800000,3.9],[1128124800000,-4.8],[1130803200000,0],[1133395200000,15.6],[1136073600000,9.7],[1138752000000,-2.6],[1141171200000,6.5],[1143849600000,4.4],[1146441600000,9.1],[1149120000000,4.5],[1151712000000,-1.8],[1154390400000,-1.6],[1157068800000,-1],[1159660800000,1.5],[1162339200000,2.3],[1164931200000,4.2],[1167609600000,4],[1170288000000,-6.7],[1172707200000,-4.1],[1175385600000,-4.3],[1177977600000,7.3],[1180656000000,-1.3],[1183248000000,-2.2],[1185926400000,-0.6],[1188604800000,-4.6],[1191196800000,4.8],[1193875200000,3],[1196467200000,19.3],[1199145600000,14.2],[1201824000000,3.3],[1204329600000,1],[1207008000000,2.9],[1209600000000,12.9],[1212278400000,7.8],[1214870400000,0.7],[1217548800000,-4.9],[1220227200000,-5.9],[1222819200000,-8.7],[1225497600000,-6.7],[1228089600000,-3.9],[1230768000000,-0.3],[1233446400000,-11],[1235865600000,0.8],[1238544000000,-9.2],[1241136000000,4.8],[1243814400000,7],[1246406400000,0.3],[1249084800000,2.7],[1251763200000,5.9],[1254355200000,-0.1],[1257033600000,0.8],[1259625600000,11.9],[1262304000000,-0.3],[1264982400000,6.4],[1267401600000,6.1],[1270080000000,3.2],[1272672000000,5.1],[1275350400000,1.3],[1277942400000,7.6],[1280620800000,1.5],[1283299200000,0.3],[1285891200000,-4.9],[1288569600000,-3.8],[1291161600000,2.5],[1293840000000,-6.1],[1296518400000,9.5],[1298937600000,-0.3],[1301616000000,-1.7],[1304208000000,3.7],[1306886400000,1.6],[1309478400000,7.8],[1312156800000,5],[1314835200000,-1.9],[1317427200000,4.4],[1320105600000,2.9],[1322697600000,14.6],[1325376000000,-1.2],[1328054400000,-0.6],[1330560000000,-9.9],[1333238400000,-3.2],[1335830400000,1.3],[1338508800000,-8.1],[1341100800000,3.6],[1343779200000,8.6],[1346457600000,-7.5],[1349049600000,4.3],[1351728000000,4.3]]
		},
		{
			name: "생산",				
			marker : {
				enabled : false,
				radius : 3
			},
			data: [[1104537600000,5.3],[1107216000000,-11.6],[1109635200000,17.7],[1112313600000,-5.2],[1114905600000,1.4],[1117584000000,-1.4],[1120176000000,1.5],[1122854400000,4.7],[1125532800000,8.8],[1128124800000,11.7],[1130803200000,4.6],[1133395200000,-6.1],[1136073600000,1.9],[1138752000000,-5.8],[1141171200000,8.5],[1143849600000,-2.9],[1146441600000,0.9],[1149120000000,-2.2],[1151712000000,-2.4],[1154390400000,8.5],[1157068800000,6.9],[1159660800000,7],[1162339200000,0.1],[1164931200000,-16.5],[1167609600000,6],[1170288000000,-11.7],[1172707200000,11.2],[1175385600000,-0.6],[1177977600000,2],[1180656000000,5.9],[1183248000000,5.3],[1185926400000,4.9],[1188604800000,5],[1191196800000,13.8],[1193875200000,-2.9],[1196467200000,-6.4],[1199145600000,3.7],[1201824000000,-10.2],[1204329600000,13.3],[1207008000000,-2.5],[1209600000000,4],[1212278400000,-4.8],[1214870400000,1.3],[1217548800000,-4.4],[1220227200000,4.2],[1222819200000,-3.5],[1225497600000,-19.2],[1228089600000,-24.9],[1230768000000,19.2],[1233446400000,12.3],[1235865600000,17.8],[1238544000000,3.5],[1241136000000,7.5],[1243814400000,4.5],[1246406400000,2.9],[1249084800000,0.2],[1251763200000,6.2],[1254355200000,-0.3],[1257033600000,-1.1],[1259625600000,-3.4],[1262304000000,1.8],[1264982400000,-4.8],[1267401600000,18.4],[1270080000000,-2.6],[1272672000000,4.6],[1275350400000,0.3],[1277942400000,3.3],[1280620800000,-0.7],[1283299200000,-0.9],[1285891200000,-0.9],[1288569600000,-1],[1291161600000,1.9],[1293840000000,-0.5],[1296518400000,-4.5],[1298937600000,10],[1301616000000,-0.2],[1304208000000,5.1],[1306886400000,-4.9],[1309478400000,-0.6],[1312156800000,-1.2],[1314835200000,7.1],[1317427200000,5.7],[1320105600000,-4.2],[1322697600000,-4.1],[1325376000000,-2.6],[1328054400000,1.3],[1330560000000,3.7],[1333238400000,-3.9],[1335830400000,6.7],[1338508800000,-1.9],[1341100800000,-5.4],[1343779200000,6.6],[1346457600000,2.2],[1349049600000,6.2],[1351728000000,3.7]]
		},
		{
			name: "출하",				
	      		marker : {
	  				enabled : false,
	  				radius : 3
	  		},
	  		data: [[1107216000000,-12.5],[1109635200000,18.1],[1112313600000,-6],[1114905600000,0.1],[1117584000000,1.9],[1120176000000,-0.2],[1122854400000,4.2],[1125532800000,10],[1128124800000,11],[1130803200000,3.1],[1133395200000,-9],[1136073600000,3.8],[1138752000000,-4.8],[1141171200000,8.2],[1143849600000,-3.9],[1146441600000,1.7],[1149120000000,-1.4],[1151712000000,-2.2],[1154390400000,10],[1157068800000,6.3],[1159660800000,4.9],[1162339200000,0.2],[1164931200000,-17.4],[1167609600000,6.6],[1170288000000,-6.6],[1172707200000,9.7],[1175385600000,-0.8],[1177977600000,0.3],[1180656000000,6.1],[1183248000000,4.6],[1185926400000,3.4],[1188604800000,4],[1191196800000,13.2],[1193875200000,-3.5],[1196467200000,-8.1],[1199145600000,2.4],[1201824000000,-7.9],[1204329600000,16],[1207008000000,-3.9],[1209600000000,0.1],[1212278400000,-2.1],[1214870400000,3],[1217548800000,-1],[1220227200000,4.5],[1222819200000,-2.4],[1225497600000,-20.1],[1228089600000,-23.1],[1230768000000,10.7],[1233446400000,14.3],[1235865600000,15.8],[1238544000000,3.4],[1241136000000,5.8],[1243814400000,4],[1246406400000,2.3],[1249084800000,-1.7],[1251763200000,4.8],[1254355200000,2.2],[1257033600000,0.4],[1259625600000,-7.9],[1262304000000,3.3],[1264982400000,-5.4],[1267401600000,15.7],[1270080000000,-0.3],[1272672000000,4.5],[1275350400000,1.2],[1277942400000,1.9],[1280620800000,0.8],[1283299200000,0.3],[1285891200000,1.1],[1288569600000,-0.6],[1291161600000,-0.4],[1293840000000,-0.9],[1296518400000,-8.1],[1298937600000,14.5],[1301616000000,-1.3],[1304208000000,1.8],[1306886400000,-3.5],[1309478400000,-6.1],[1312156800000,2.4],[1314835200000,11.1],[1317427200000,2.6],[1320105600000,-5.9],[1322697600000,-3.5],[1325376000000,-1.3],[1328054400000,0.1],[1330560000000,6.8],[1333238400000,-5],[1335830400000,6.2],[1338508800000,-1.4],[1341100800000,-8.4],[1343779200000,8.3],[1346457600000,3.7],[1349049600000,3.4],[1351728000000,3.4]]
	  	}
	];
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
		var chartView1 = new chart({id: 'chartA', title:"물량", width: '100%', height: '350px'});
		var chartView2 = new chart({id: 'chartB', title:"전월비", width: '100%', height: '350px'});
		var chartView3 = new chart({id: 'chartC', title:"전년동월비", width: '100%', height: '350px'});
		
		$("#accordion").append(listMenuView1.render({listNumber:1, className: "sanup", list: temp_list1}).el);
		$("#accordion").append(listMenuView2.render({listNumber:3, className: "product", list: temp_list2}).el);
		$("#accordion").append(listMenuView3.render({listNumber:2, className: "saup", list: temp_list3}).el);
		mCustomScrollSelectable(".l-list li");
		
		$('#chartContainer').append(chartView1.el);
		$('#chartContainer').append(chartView2.el);
		$('#chartContainer').append(chartView3.el);
		
		$(function(){
			var resizeWindow = function(){
				$("#side, #main").css("height", function(){
					var winHeight = document.documentElement.offsetHeight;
					return winHeight - 168;
				});
				$(".l-chart, .placeholder").css("height", function(){
					var winHeight = document.documentElement.offsetHeight;
					return winHeight - 280;
				});
			};
			$(window).bind("resize", resizeWindow);
			resizeWindow();
			
			$("#accordion").accordion({ heightStyle: "content" });
			
			$(".l-chart").mCustomScrollbar({
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
					$(".l-category div").css({display:"block"});
					chartView1.render(temp_data1, "");
					chartView2.render(temp_data2, "%");
					chartView3.render(temp_data3, "%");
					$("#c-chart-title-1").text("사업체별");
				} else {
					alert("모든 분류가 선택되지 않았습니다.");
				}
			});
		});		
		$(".c-date-from, .c-date-to").datepicker({
			dateFormat: 'yy년 m월',
			currentText: "이번달",
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
	        	var selectedDate = new Date(inst.drawYear, inst.drawMonth);
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
		//$(".c-date-from, .c-date-to").datepicker( "option", "dateFormat","yy-mm");
		//this.router = new router();
		
		eventHandler = {
				"selectClick": function(className, id, label) {
					//$('.c-display-selected-1').showLoading();
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
					//setTimeout(function(){$('.c-display-selected-1').hideLoading();},1500);
				}
		};
		
		eventListenerRegister = function(eventName, eventSource) {
			eventSource.on(eventName, eventHandler[eventName], this);
		};
		
		/* event binding start */
		eventListenerRegister("selectClick", listMenuView1);
		/* event binding end */
	};
	return {
		'init': init
	};
});
