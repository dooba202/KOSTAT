<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" >
<meta name='viewport' content='width=1440' />

<title>통계청 Data Explorer PoC</title>
<!-- link rel="shortcut icon" href="images/favicon.ico" -->
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/jquery-ui-1.9.2.custom.min.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/jquery.mCustomScrollbar.css" type="text/css" media="all" />
<style>
	@font-face{
		font-family:NGE;
		src:url(css/NGE.eot);
		src:local(※),url(css/NGE.woff) format('woff')
	}
	html, body {
		height: 100%;
		margin: 0;
		padding: 0;
		overflow-x: hidden;
	}
	#wrap {
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
		width: 100%;
		min-height: 100%;
		padding: 10px;
		background: url("images/kostat_bg.png");
		background-repeat:no-repeat;
		background-attachment:fixed;
		background-position:center;
		text-align: center;
		overflow: hidden;
	}
	#header {
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
		display: inline-block;
		width: 95%;
		height: 100px;
		margin-bottom: 5px;
		background: url("images/header.png") repeat-x;
	}
	#side {
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
		display: inline-block;
		width: 20%;
		min-height: 600px;
		vertical-align: top;
		background: transparent;
	}
	#main {
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
		display: inline-block;
		width: 75%;
		/* min-height: 600px; */
		vertical-align: top;
		border: 1px solid #ccc;
		background: #fff;
	}
	.l-title {
		float: left;
		margin-top: -10px;
		margin-left: -40px;
		width: 650px;
		height: 120px;
		background: url("images/header-l-title.png") no-repeat;
	}
	.c-logo {
		width: 497px;
		height: 84px;
		margin: 17px auto;
		background: url("images/header-c-logo.png") no-repeat;
	}
	.l-logout {
		float: right;
		margin-top: -10px;
		margin-right: -40px;
		width: 250px;
		height: 120px;
		background: url("images/header-l-logout.png") no-repeat;
	}
	.c-logout {
		width: 120px;
		height: 36px;
		margin: 44px auto;
		background: url("images/header-c-logout.png") no-repeat;
		cursor: pointer;
	}
	.l-side {
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
		width: 100%;
		padding: 0 10px 0 0;
		display: inline-block;
		vertical-align: top;
	}
	.l-list {
		border: 1px solid #ccc;
		position: relative;
	}
	.l-list-top-title {
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
		width: 100%;
		height: 50px;
		border: 1px solid #ccc !important;
		-webkit-border-radius: 0 !important;
		-moz-border-radius: 0 !important;
		border-radius: 0 !important;
	}
	.l-list-top {
		width: 100%;
		height: 50px;
		position: absolute;
		background: url("images/side-l-list-top.png") no-repeat;
		z-index: 999;
	}
	.l-list-top .l-list-title1 {
		width: 70px;
		height: 24px;
		margin-top: 12px;
		margin-left: 10px;
		background: url(images/side-l-list-title.png) 0 0 no-repeat;
	}
	.l-list-top .l-list-title2 {
		width: 70px;
		height: 24px;
		margin-top: 12px;
		margin-left: 10px;
		background: url(images/side-l-list-title.png) 0 -48px no-repeat;
	}
	.l-list-top .l-list-title3 {
		width: 70px;
		height: 24px;
		margin-top: 12px;
		margin-left: 10px;
		background: url(images/side-l-list-title.png) 0 -24px no-repeat;
	}
	.l-list-top-bg {
		width: 100%;
		height: 50px;
		position: relative;
		background: url("images/side-l-list-top-bg.png") repeat-x;
	}
	.l-list .l-list-mid-5 {
		height: 150px;
	}
	.l-list .l-list-mid-10 {
		height: 300px;
	}
	.l-list ul {
		padding: 0;
		margin: 0;
		width: 100%;
	}
	.l-list ul li {
	    display: inline-block;
	    -webkit-box-sizing: border-box;
	    -moz-box-sizing: border-box;
	    box-sizing: border-box;
	    width: 100%;
	    height: 30px;
	    padding-left: 10px;
	    text-align: center;
	    text-decoration: none;
	    font-family: NGE, "NanumGothic", 나눔고딕,  "Malgun Gothic";
	    font-size: 0.8em;
	    text-align: left;
	    line-height: 30px;
	    color: #777;
	    background: -webkit-gradient(linear,left top,left bottom,from(#ffffff),to(#dcdcdc));
		background: -moz-linear-gradient(top,#ffffff,#dcdcdc);
		filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#dcdcdc');
	    cursor: pointer;
	}
	.l-list ul li:hover {
		font-weight: bold;
		color: #fff;
		background: #ccc;
		cursor: pointer;
	}
	.l-list ul li.selected {
		font-weight: bold;
	    color: #fff;
	    background: #7dbae7;
		background: #ffb001;
		background: -webkit-gradient(linear,left top,left bottom,from(#ffb001),to(#ff8400));
		background: -moz-linear-gradient(top,#ffb001,#ff8400);
		-webkit-box-shadow: inset 0 0 6px #e42b00;
		-moz-box-shadow: inset 0 0 6px #e42b00;
		box-shadow: inset 0 0 6px #e42b00;
		filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffb001', endColorstr='#ff8400');
	}
	.l-list ul li label {
		cursor: pointer;
	}
	.l-list-bottom {
		width: 100%;
		height: 25px;
		background: url("images/side-l-list-bottom.png") repeat-x;
	}
	.l-selector {
		width: 100%;
		height: 60px;
		background: #fff;
	}
	.l-category {
		width: 100%;
		height: 50px;
		background: -webkit-gradient(linear,left top,left bottom,from(#fefefe),to(#cecece));
		background: -moz-linear-gradient(top,#fefefe,#cecece);
		filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#fefefe', endColorstr='#cecece');
	}
	#c-category-title1 {
		width: 134px;
		height: 36px;
		top: 12px;
		position: relative;
		background: url("images/main-c-category-title.png") 0 0 no-repeat;
	}
	#c-category-title2 {
		width: 134px;
		height: 36px;
		top: 12px;
		position: relative;
		background: url("images/main-c-category-title.png") 0 -36px no-repeat;
	}
	#c-category-title3 {
		width: 134px;
		height: 36px;
		top: 12px;
		position: relative;
		background: url("images/main-c-category-title.png") 0 -72px no-repeat;
	}
	
	.l-chart {
		height: 540px;
	}
	
	.l-chart .l-chart-title {
		width: 250px;
		height: 55px;
		background: url("images/main-l-chart-title.png") no-repeat;
		padding: 8px;
	}
	.l-chart .l-chart-title div {
		font-family: NGE, "NanumGothic", 나눔고딕,  "Malgun Gothic";
		font-size: 20pt;
		color: #777;
		text-align: left;
	}
	.l-chart .chart {
		/* height: 350px; */
		/* border: 1px solid #ccc; */
		background: -webkit-gradient(linear,left top,left bottom,from(#f5f5f5),to(#cccccc));
		background: -moz-linear-gradient(top,#f5f5f5,#cccccc);
		filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#f5f5f5', endColorstr='#cccccc');
	}
	.hasDatepicker {
		-webkit-box-sizing: border-box;
	    -moz-box-sizing: border-box;
	    box-sizing: border-box;
	    border: 1px solid #555;
	    -webkit-border-radius: 4px;
	    -moz-border-radius: 4px;
	    border-radius: 4px;
		background: url("images/main-datepicker-bg.png") center center  repeat-x;
	}
	.c-date-from, .c-date-to {
		min-width: 90px;
		width: 120px;
		height: 46px;
		font-family: arial;
		font-size: 1.8em;
		font-weight: bold;
		color: #fff;
		text-align: center;
	}
	.c-date-from .ui-spinner-button, .c-date-to .ui-spinner-button {
		background: red !important;
	}
	.c-display-selected {
		width: auto;
		min-width: 100px;
		height: 60px;
		float: left;
	}
	.c-display-selected div {
		font-family: NGE, "NanumGothic", 나눔고딕,  "Malgun Gothic";
		font-size: 0.8em;
		color: #fff;
		text-align: center;
		line-height: 50px;
	}
	.c-display-selected-1 {
		width: 148px;
		height: 50px;
		margin-left: 2px;
		display: inline-block;
		background: url("images/main-c-display-selected-1.png") no-repeat;
	}
	.c-display-selected-2, .c-display-selected-3 {
		width: 175px;
		height: 50px;
		margin-left: -28px;
		display: inline-block;
		background: url("images/main-c-display-selected-2.png") no-repeat;
	}
	.c-date-picker {
		width: auto;
		min-width: 100px;
		height: 60px;
		margin-left: 20px;
		padding: 1px;
		float: left;
	}
	.c-search {
		width: 170px;
		height: 60px;
		float: right;
		text-align: right;
	}
	.c-search-button {
		width: 154px;
		height: 56px;
		display: inline-block;
		background: url("images/main-c-search-button.png") no-repeat;
	}
	.c-search-button {
		display: inline-block;
	}
	#c-logo-kostat {
		width: 116px;
		height: 40px;
		float: right;
		margin-top: 4px;
		margin-right: 10px;
		background: url("images/footer-c-logo-kostat.png") no-repeat;
	}
	#c-logo-ibm {
		width: 100px;
		height: 40px;
		float: right;
		margin-top: 4px;
		margin-right: 10px;
		background: url("images/footer-c-logo-ibm.png") no-repeat;
	}
	.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active {
		border: none;
	}
	.ui-accordion .ui-accordion-icons {
		padding: 0 !important;
	}
	.ui-accordion .ui-accordion-content {
		padding: 0 !important;
	}
	.ui-datepicker-calendar {
    	display: none; !important
	}
	#ui-datepicker-div {
		border: none;
		border-radius: 0;
		padding: 0;
		background: transparent;
	}
</style>
<script>
	var require = {
		config : {
			'index' : {
				//key value for shared config
			}
		}
	};
</script>
<script data-main="js/main_chart" src="js/require.js"></script>
</head>
<body>
	<div id="wrap"> <!--브라우저 사이즈에 따라서 동적으로 가로 너비 변경-->
		<div id="header">
			<div class="l-title">
				<div class="c-logo"></div><!--로고 영역-->
			</div> <!--너비 고정-->
			<div class="l-logout">
				<div class="c-logout"></div><!--로그아웃버튼 영역-->
			</div> <!--너비 고정-->
		</div>
		<div id="side"> <!--너비%값-->
			<div class="l-side">
				<div id="accordion">
				</div>
			</div>
		</div><div id="main" style="border:1px solid #ccc;"> <!--너비%값-->
			<div class="l-selector">
				<div class="c-display-selected">
					<div class="c-display-selected-1">산업 분류</div>
					<div class="c-display-selected-2">사업체 분류</div>
					<div class="c-display-selected-3">품목 분류</div>
				</div>
				<div class="c-date-picker">
					<input class="c-date-from" name="value" />
					<input class="c-date-to" name="value" />
				</div>
				<div class="c-search">
					<div class="c-search-button"></div>
				</div>
			</div>
			<div class="l-category">
				<div id="c-category-title2"></div>
			</div>
			<div class="l-chart" id="chartContainer"> <!-- 스크롤러 적용부분 -->
			</div>
			<div id="footer">
				<div id="c-logo-ibm"></div>
				<div id="c-logo-kostat"></div>
			</div>
		</div>
	</div>
</body>
</html>
