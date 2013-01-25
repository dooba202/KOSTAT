require.config({
	//default baseUrl will be /js
	//'baseUrl': 'js/libs',
	'paths': {
		'jquery': 'libs/jquery-1.8.3.min',
		'jquery.ui': 'libs/jquery-ui-1.9.2.custom.min',
		'jquery.showLoading': 'libs/jquery.showLoading.min',
		'jquery.toastmessage': 'libs/jquery.toastmessage',
		'jquery.mCustomScrollbar': 'libs/jquery.mCustomScrollbar',
		'jquery.mousewheel': 'libs/jquery.mousewheel.min',
		'underscore': 'libs/underscore-min',
		'backbone': 'libs/backbone-min',
		'mustache': 'libs/mustache',
		'highstock': 'libs/highstock',
		'highstock.export': 'libs/modules/exporting',
		'highstock.themes.gray': 'libs/themes/gray',
		'highstock.themes.grid': 'libs/themes/grid'
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
		},
		'jquery.mCustomScrollbar': {
			'deps': ['jquery']
		},
		'jquery.mousewheel': {
			'deps': ['jquery']
		},
		'highstock.export': {
			'deps': ['highstock']
		},
		'highstock.themes.gray': {
			'deps': ['highstock']
		},
		'highstock.themes.grid': {
			'deps': ['highstock']
		}
	}
});
//'jquery','underscore','backbone', $, _, Backbone, 
require(['index_chart'], function(index) {
    index.init();
});