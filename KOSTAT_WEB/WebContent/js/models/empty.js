var app = app || {};

define(['jquery',
        'backbone',
        'underscore'
       ],
function( $, Backbone, _ ) { 
	app.emptyModel = Backbone.Model.extend({
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
	return app.emptyModel;
});
