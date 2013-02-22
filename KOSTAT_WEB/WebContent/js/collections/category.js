var app = app || {};

define(['jquery',
        'backbone',
        'underscore',
        'utils/local_logger',
        'models/category',
       ],
function( $, Backbone, _, Logger, category) { 
	//var logger = new Logger("schools");
	app.categoryCollection = Backbone.Collection.extend({
		// Collection Constructor
        'model': category,
        
		initialize: function(options) {
			//catch base URL for REST requesting
			//this.url = options.url;
        }
	});
	return app.categoryCollection;
});
