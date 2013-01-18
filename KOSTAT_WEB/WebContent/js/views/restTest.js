/*
 * 2013.1.18 
 * triggering events list (if silent mode is not true)
 */
var app = app || {};

define([ 'jquery', 
         'backbone', 
         'underscore', 
         'mustache', 
         'text!templates/restTest.html!strip',
         'utils/local_logger' ], 

function($,Backbone, _, Mustache, template, Logger) {
	var logger = new Logger("cognosView");
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
			logger.log("restTest init");
		    this.template = Mustache.to_html(template);
		},
		
		render : function() {
			this.$el.html( this.template );
			return this;
		}
	});
});


