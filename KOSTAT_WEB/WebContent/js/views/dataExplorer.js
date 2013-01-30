/*
 * 2013.1.18 
 * triggering events list (if silent mode is not true)
 */
var app = app || {};

define([ 'jquery', 
         'backbone', 
         'underscore', 
         'mustache', 
         'text!templates/example.html',
         'utils/local_logger'
         ], 

function($,Backbone, _, Mustache, template, Logger) {
	var logger = new Logger("dataExplorerView");
		logger.setLevel("ALL");
		
	return Backbone.View.extend({
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
		
		//el: node,
		
		initialize : function(options) {
			logger.log("dataExplorerView init");
		},
		
		events: {
			//"click ul li" : "select"
		},
		
		render : function() {
			this.el = Mustache.to_html(template);
			return this;
		}
	});
});


