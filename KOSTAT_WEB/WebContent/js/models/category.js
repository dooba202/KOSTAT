var app = app || {};

define([
        'jquery',
        'backbone',
        'underscore'
       ],
function( $, Backbone, _ ) { 
	app.categoryModel = Backbone.Model.extend({
		urlRoot: "./KOSTAT_WEB/json/",
		defaults: {
		},
		// Model Constructor
        initialize: function(){
        },
        // Any time a model attribute is set, this method is called
        validate: function(attr) {
        }
		
	});
	// Returns the Model class
	return app.categoryModel;
});
