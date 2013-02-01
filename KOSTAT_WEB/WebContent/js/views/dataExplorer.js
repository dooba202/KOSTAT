/*
 * 2013.1.18 
 * triggering events list (if silent mode is not true)
 */
var app = app || {};

define([ 'jquery', 
         'backbone', 
         'underscore', 
         'mustache', 
         'text!templates/dataExplorer.html',
         'utils/local_logger'
         ], 

function($,Backbone, _, Mustache, template, Logger) {
	var logger = new Logger("dataExplorerView");
		logger.setLevel("ALL");
		
	var width = '100%';
	var height = '100%';
	app.dataExplorerView = Backbone.View.extend({
		/* < --common event handling */
		silent : false,
		setSilent: function(boolean) {
			var booleanValue = !(!boolean);
			this.silent = booleanValue;
		},
		eventTrigger: function() {
			if (arguments.length && !this.silent) {
				this.trigger.apply(this, arguments);
				return true;
			}
			return false;
		},
		/* common event handling --> */
		
		defaults: {
			//"cv.toolbar": false
		},
		//el: node,
		
		initialize : function(options) {
			logger.log("dataExplorerView init");
			_.defaults(this.options, this.defaults);
		    this.reportID = "dataExplorer" + app.dataExplorerView.REPORTID;
		    width = options.width;
		    height = options.height;
		    this.el = Mustache.to_html(template, {"target": this.reportID, "wrapId": this.reportID + "FormDiv", "width": width,"height": height });
		    app.dataExplorerView.REPORTID++;
		},
		
		events: {
			//"click ul li" : "select"
		},
		
		render : function() {
			return this;
		},
		
		query: function(qstring) {
			var $iframe = $("iframe[name ="+ this.reportID + "]");
			var $this = this;
			$iframe.load(function(param){
				var deName = $(param.currentTarget).attr("name");
				$this.eventTrigger("loadFinish", deName);
			});
			if ($iframe.length) {
				this.eventTrigger("loadStart",$iframe.attr("name"));
				$iframe.attr("src", "http://211.109.180.11/vivisimo/cgi-bin/query-meta.exe?v%3Aproject=Poc_Test&query=" + qstring);
				//iframe.update();
			}
		}
	});
	app.dataExplorerView.REPORTID = 0;
	return app.dataExplorerView;
});


