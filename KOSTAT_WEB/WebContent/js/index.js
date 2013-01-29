// entry point for the app
define ([
         'module',
		 'jquery', 
		 'backbone', 
		 'underscore',
		 'utils/local_logger',
		 'views/xmlResult',
         'jquery.ui'
		 ], 

function( module, $, Backbone, _, Logger, xmlResult){
	var logger = new Logger("index.js");
		logger.setLevel("ALL");
	
	var init = function(){
		logger.log("index.js init");
		
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
	};
	return {
		'init': init
	};
});
