/*
 * 2013.1.18 
 * triggering events list (if silent mode is not true)
 */
var app = app || {};

define([ 'jquery', 
         'backbone', 
         'underscore', 
         'mustache', 
         'text!templates/listMenu.html!strip',
         'utils/local_logger'
         ], 

function($,Backbone, _, Mustache, template, Logger) {
	var logger = new Logger("listMenuView");
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
		
		el: $('#accordion'),
		
		initialize : function(options) {
			logger.log("listMenuView init");
		},
		
		events: {
			"click ul li" : "select"
		},
		
		render : function(data) {
			this.el = Mustache.to_html(template, data);
			return this;
		},
		
		select: function(e) {
			if (e && e.currentTarget) {
				var id = $(e.currentTarget).attr("id");
				var className = $(e.currentTarget).find('input').attr("name");
				var label = $(e.currentTarget).find('label').text(); 
				if (id !== "n/a") {
					this.eventTrigger("selectClick", className, id, label);
				}
			}
		}
	});
});


