/*
 * 2013.1.18 
 * triggering events list (if silent mode is not true)
 */
var app = app || {};

define([ 'jquery', 
         'backbone', 
         'underscore', 
         'mustache', 
         'text!templates/xmlResult.html!strip',
         'utils/local_logger' ], 

function($,Backbone, _, Mustache, template, Logger) {
	var logger = new Logger("xmlResult");
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

		initialize : function(options) {
			logger.log("xmlResultView init");
		},
		
		render : function(document) {
			$(this.el).html( Mustache.to_html(template, document));
			return this;
		}
	});
});


