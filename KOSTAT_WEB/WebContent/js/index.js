// entry point for the app
define ([
         'module',
		 'jquery', 
		 'backbone', 
		 'underscore',
		 'utils/local_logger',
         'jquery.ui'
		 ], 

function( module, $, Backbone, _, Logger){
	var logger = new Logger("index.js");
		logger.setLevel("ALL");
	
	var init = function(){
		logger.log("index.js init");
		
		function parseXml(xml) {
			var item = $(xml).find("content");

			  $(item).each(function() {
			    $("#results").append($(this).text()+ "<br />");
			  });

		}
		//var myTest = new restTest;
		$(function(){
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
		           parseXml(data);
		        }
		    });

		});		
		//this.router = new router();
	};
	return {
		'init': init
	};
});
