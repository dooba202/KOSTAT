<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>통계청 Data Explorer PoC</title>
<!-- link rel="shortcut icon" href="images/favicon.ico" -->
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/jquery-ui-1.9.2.custom.min.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/jquery.mCustomScrollbar.css" type="text/css" media="all" />

<style>
	body {
		margin: 0;
		padding: 0;
	}
	#l-container {
		width: 100%;
		min-height: 100%;
		position: absolute;
		background: url('images/kostat_bg.png') repeat-x scroll center center transparent;
		overflow: hidden;
	}
	
	#c-content {
		position: absolute;
		margin: 0 auto;
		width: 100%;
		height: 100%;
		background: url('images/login-l-content-bg.png') no-repeat scroll center center transparent;
		background-position: center;
	}
	
	#l-message {
		position: relative;
		width: 360px;
		margin: 100px auto;
		padding: 10px;
		text-align: center;
		font-family: 돋움, Dotum;
		font-size: 0.8em;
		font-weight: bold;
		color: #fff;
		background: #8997B5;
	    background: -webkit-gradient(linear,left top,left bottom,from(#8997B5),to(#656E84));
	    background: -moz-linear-gradient(top,#8997B5,#656E84);
	    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#8997B5', endColorstr='#656E84');
	    -webkit-border-radius: 2px;
		-moz-border-radius: 2px;
		border-radius: 2px;
		-webkit-box-shadow: 0 8px 6px -6px #aaa;
		-moz-box-shadow: 0 8px 6px -6px #aaa;
		box-shadow: 0 8px 6px -6px #aaa;
	}
	
	#l-form {
		background: url('images/login-l-form-bg.png') no-repeat scroll center center transparent;
		height: 700px;
	    left: 50%;
	    margin: -420px -250px;
	    position: absolute;
	    top: 50%;
	    width: 500px;
	}
	
	#c-form-top {
		width: 386px;
		height: 400px;
		margin: 0 auto;
	}
		
	#c-form-middle {
		width: 386px;
		margin: 0 auto;
		text-align: center;
		background: url(images/login-l-form-input.png) no-repeat scroll center center transparent;
	}
	
	#c-form-bottom {
		width: 386px;
		margin: 0 auto;
		text-align: center;
		padding: 10px;
		box-sizing: border-box ;
		-moz-box-sizing: border-box ;
		-webkit-box-sizing: border-box;
	}
	
	#c-form-btn {
		background: url("images/login-l-btn-login.png") no-repeat scroll center center transparent;
		width: 386px;
		margin: 0 auto;
		text-align: center;
		box-sizing: border-box ;
		-moz-box-sizing: border-box ;
		-webkit-box-sizing: border-box;
	}
	
	#c-form-btn input[type=submit] {
		background: url('images/login-l-btn-login.png');
		width: 347px;
		height: 69px;
		border: none;
		cursor: pointer;
	}
	
	#c-form-btn .disable {
		background: url('images/login-l-btn-login-disable.png') !important;
		cursor: default !important;
	}
	
	#c-form-btn input:hover {
		background: url('images/login-l-btn-login-hover.png');
	}
	
	#c-id {
		background: #fff;
		background: url('images/login-l-id.png');
		margin: 0 auto;
		width: 346px;
		height: 60px;
		border: none;
		border-bottom: 1px solid #ccc;
		text-align: center;
		box-sizing: border-box ;
		-moz-box-sizing: border-box ;
		-webkit-box-sizing: border-box;
		font-family: arial;
		font-size: 1.8em;
		line-height: 60px;
	}
	
	#c-id:focus, .id-focus {
		background: url('images/login-l-id-focus.png') !important;
	}
	
	#c-pw {
		background: #fff;
		background: url('images/login-l-pw.png');
		margin: 0 auto;
		width: 346px;
		height: 60px;
		border: none;
		text-align: center;
		box-sizing: border-box;
		-moz-box-sizing: border-box;
		-webkit-box-sizing: border-box;
		font-family: arial;
		font-size: 1.8em;
		line-height: 60px;
	}
	
	#c-pw:focus, .pw-focus {
		background: url('images/login-l-pw-focus.png') !important;
	}
	#footer {
		margin-top: 60px;
		text-align: center;
	}
	#c-logo-kostat {
		width: 116px;
		height: 40px;
		display: inline-block;
		margin-top: 4px;
		margin-right: 10px;
		background: url("images/footer-c-logo-kostat.png") no-repeat;
	}
	#c-logo-ibm {
		width: 100px;
		height: 40px;
		display: inline-block;
		margin-top: 4px;
		margin-right: 10px;
		background: url("images/footer-c-logo-ibm.png") no-repeat;
	}
	input:focus, input#gText:focus {
		color: #555;
	}
	.ui-widget-header {
		font-size: 0.85em;
		font-family: 돋움;
	}
</style>
<script src="js/libs/jquery-1.8.3.min.js"></script>
<script src="js/libs/jquery-ui-1.9.2.custom.min.js"></script>
<script>
$(function(){
	var checkValidation = function() {
		var id = $("#c-id").val();
		var pw = $("#c-pw").val();
		var ck_id = /^[A-Za-z0-9!@#$%^&*()_.,]{3,20}$/;
		var ck_pw = /^[\S]{6,20}$/;
		if (id.length > 0) {
			$("#c-id").addClass("id-focus");
		} else {
			$("#c-id").removeClass("id-focus");
		}
		
		if (pw.length > 0) {
			$("#c-pw").addClass("pw-focus");
		} else {
			$("#c-pw").removeClass("pw-focus");
		}
		
		if (ck_id.test(id) && ck_pw.test(pw)) {
			$("input[type=submit]").removeClass("disable");
			$("input[type=submit]").removeAttr("disabled", "disabled");
		} else {
			$("input[type=submit]").addClass("disable");
			$("input[type=submit]").attr("disabled", "disabled");
		}
	};
	if ($.browser.msie) {
		if ($.browser.version < 8) {
			alert("경고: 본 사이트는 Internet Explorer 7 이하 버젼은 지원하지 않습니다.");
		}
	} else {
		checkValidation();
	};
	$("input").keyup(checkValidation);
	
	if ($("#l-message").html().length == 0) {
		$("#l-message").css("display","none");
	};
});
</script>
</head>
<body>
<div id="l-container">
	<div id="c-content"></div>
	<div id="l-form">
		<form id="loginform" name="form" method="post" action="index_chart.jsp" enctype="application/x-www-form-urlencoded">
			<div id="c-form-top"></div>
			<div id="c-form-middle">
				<input type="text" id="c-id" name="userID" maxlength="20"></input>
				<input type="password" id="c-pw" name="password" maxlength="20"></input>
			</div>
			<div id="c-form-bottom">
				<!-- <input id="l-l-cache" type="checkbox" name="cache-id"></input> -->
				<!-- <label for="l-l-cache"><img src="images/l-cache-text.png"></label> -->
			</div>
			<div id="c-form-btn">
				<input id="submit-btn" class="disable" type="submit" value="" disabled="disabled"></input>
			</div>
		</form>
		<div id="l-message"></div>
		<div id="footer">
			<div id="c-logo-kostat"></div>
			<div id="c-logo-ibm"></div>
		</div>
	</div>
</div>
</body>
</html>