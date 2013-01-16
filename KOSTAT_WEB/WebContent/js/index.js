// entry point for the app
define ([
         'module',
		 'jquery', 
		 'backbone', 
		 'underscore',
		 'utils/local_logger',
         'routers/home',
         'jquery.ui',
         'jquery.showLoading',
         'jquery.toastmessage'
		 ], 

function( module, $, Backbone, _, Logger, router){
	var logger = new Logger("index.js");
		logger.setLevel("ALL");
	/*
	var addCommas = function(nStr){
		nStr += '';
		x = nStr.split('.');
		x1 = x[0];
		x2 = x.length > 1 ? '.' + x[1] : '';
		var rgx = /(\d+)(\d{3})/;
		while (rgx.test(x1)) {
			x1 = x1.replace(rgx, '$1' + ',' + '$2');
		}
		return x1 + x2;
	};
	
	var growl = function(type, msgObj) {
		var showType = 'showErrorToast'; //default showing type is error
		if (type == 'error') {
			showType = 'showErrorToast';
		} else if (type =='success'){
			showType = 'showSuccessToast';
		} // showNoticeToast //showWarningToast //showStickyWarningToast
		
		var responsed = $.parseJSON(msgObj.responseText);
		if (typeof(msgObj) == 'string') {
			$().toastmessage(showType,msgObj);
		} else {
			if (responsed.redirect) {
				$().toastmessage('showToast', {
				    text     : responsed.userMessage + "<br/> 로그인 페이지로 이동합니다. <br/> 바로 이동하시려면 이 창을 닫거나, <br/>5초 뒤에 로그인 페이지로 이동 합니다.",
				    sticky   : true,
				    position : 'top-right',
				    type     : 'error',
				    closeText: '',
				    close    : function () {
				    	window.location = responsed.redirect;
				    }
				});
				setTimeout(function(){ window.location = responsed.redirect;}, 5000);
			} else {
				$().toastmessage(showType,responsed.userMessage);
			}
		}
	};
	*/
	
	var init = function(){
		logger.log("index.js init");
		
		$(function(){


		});		
		this.router = new router();
	};
	return {
		'init': init, 
//		'addCommas': addCommas, 
//		'growl': growl,
	};
});
