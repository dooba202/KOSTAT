// entry point for the app
define ([
         'module',
		 'jquery', 
		 'backbone', 
		 'underscore',
		 'utils/local_logger',
		 'views/chart',
         'jquery.ui'
		 ], 

function( module, $, Backbone, _, Logger, chart){
	var logger = new Logger("index.js");
		logger.setLevel("ALL");
	
	var init = function(){
		logger.log("index.js init");
		
		var chartView1 = new chart({el: "chart1", title:"지수"});
		var chartView2 = new chart({el: "chart2", title:"가자"});
		
		$(function(){

		});		
		//this.router = new router();
	};
	return {
		'init': init
	};
});
