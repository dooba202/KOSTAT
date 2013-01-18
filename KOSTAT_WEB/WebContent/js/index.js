// entry point for the app
define ([
         'module',
		 'jquery', 
		 'backbone', 
		 'underscore',
		 'utils/local_logger',
         //'routers/home',
		 'views/restTest',
         'jquery.ui'
		 ], 

function( module, $, Backbone, _, Logger, restTest){
	var logger = new Logger("index.js");
		logger.setLevel("ALL");
	
	var init = function(){
		logger.log("index.js init");
		
		var myTest = new restTest;
		$(function(){
			alert("hi");

		});		
		//this.router = new router();
	};
	return {
		'init': init
	};
});
