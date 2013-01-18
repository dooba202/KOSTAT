var app = app || {};

define([
	'jquery', 
	'backbone'
	],
function($, Backbone, tab2){
	app.router = Backbone.Router.extend({
		initialize: function(){
			Backbone.history.start();
		},
		routes: {
			//'': 'schoolList',
			'test/:id': 'home',
			'map/:id': 'map'
		},
		'schoolList': function(){
			//this.mainView.render();
		},
		'home': function(id){
			//this.mainView.render();
			alert("home " + id);
			//tab2.init();
		},
		'map': function(id) {
			console.log("drawing a map id:"+ id);
		}
	});
	
	return app.router;
});
