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
		    this.defWords = "";
		    this.$iframe = null;
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
		
		setSource: function(url) {
			this.sourceURL = url;
		},
		
		setDefWords: function(words) {
			this.defWords = words;
		},
		
		query: function(qstring) {
			var $this = this;
			if (!this.$iframe) {
				this.$iframe = $("iframe[name ="+ this.reportID + "]");
				this.$iframe.load(function(param){
					var deName = $(param.currentTarget).attr("name");
					$this.eventTrigger("loadFinish", deName);
				});
			}

			if (this.$iframe.length) {
				this.eventTrigger("loadStart",this.$iframe.attr("name"));
				/*
				//polyfill for cross-browser issue
				var myframe = $iframe[0];
				if(myframe !== null){
					if(myframe.src){
						myframe.src = this.sourceURL + this.defWords + " " + qstring; }
					else if(myframe.contentWindow !== null && myframe.contentWindow.location !== null){
						myframe.contentWindow.location = this.sourceURL + this.defWords + " " + qstring; }
					else{ 
						myframe.setAttribute('src', this.sourceURL + this.defWords + " " + qstring); 
					}
				}
				*/
				var encodedQuery = encodeURIComponent(this.defWords + " " + qstring);
				this.$iframe.attr("src", this.sourceURL + encodedQuery);
			}
		}
	});
	app.dataExplorerView.REPORTID = 0;
	return app.dataExplorerView;
});


