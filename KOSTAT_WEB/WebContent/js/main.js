require.config({
	//default baseUrl will be /js
	//'baseUrl': 'js/libs',
	'paths': {
		'jquery': 'libs/jquery-1.8.3.min',
		'jquery.ui': 'libs/jquery-ui-1.9.2.custom.min',
		'jquery.showLoading': 'libs/jquery.showLoading.min',
		'jquery.toastmessage': 'libs/jquery.toastmessage',
		'underscore': 'libs/underscore-min',
		'backbone': 'libs/backbone-min',
		'mustache': 'libs/mustache'
	},
	'waitSeconds': 45,
	'shim': 	{
		'backbone': {
			'deps': ['jquery', 'underscore'],
			'exports': 'Backbone'
		},
		'jquery.ui': {
			'deps': ['jquery']
		},
		'jquery.showLoading': {
			'deps': ['jquery']
		},
		'jquery.toastmessage': {
			'deps': ['jquery']
		}
	}
});
//'jquery','underscore','backbone', $, _, Backbone, 
require(['index'], function(index) {
    index.init();
});