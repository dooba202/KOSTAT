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
		width: 25%;
		min-height: 600px;
		vertical-align: top;
		background: transparent;
	}
	#main {
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
		display: inline-block;
		width: 70%;
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
	.l-logout {
		float: right;
		margin-top: -10px;
		margin-right: -40px;
		width: 250px;
		height: 120px;
		background: url("images/header-l-logout.png") no-repeat;
	}
	.l-side {
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
		width: 50%;
		padding: 0 10px 0 0;
		display: inline-block;
		vertical-align: top;
	}
	.l-list {
		border: 1px solid #ccc;
		position: relative;
	}
	.l-list .l-list-top {
		width: 100%;
		height: 50px;
		position: absolute;
		background: url("images/side-l-list-top.png") no-repeat;
		z-index: 999;
	}
	.l-list .l-list-top-bg {
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
	    text-align: center;
	    text-decoration: none;
	    font-family: "Malgun Gothic", NG, 나눔고딕;
	    font-size: 0.7em;
	    line-height: 30px;
	    background: -webkit-gradient(linear,left top,left bottom,from(#ffffff),to(#dcdcdc));
		background: -moz-linear-gradient(top,#ffffff,#dcdcdc);
		filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#dcdcdc');
	    cursor: pointer;
	}
	.l-list ul li:hover {
	    background: #eee;
	    -webkit-box-shadow: inset 0 0 6px #aaa;
	    -moz-box-shadow: inset 0 0 6px #aaa;
	    box-shadow: inset 0 0 6px #aaa;
	    cursor: pointer;
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
		background: -webkit-gradient(linear,left top,left bottom,from(#f1f6fd),to(#e3eefb));
		background: -moz-linear-gradient(top,#f1f6fd,#e3eefb);
		filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#f1f6fd', endColorstr='#e3eefb');
	}
	.l-chart {
		height: 540px;
	}
	
	.l-chart .l-chart-title {
		width: 250px;
		height: 55px;
		background: url("images/chart-l-chart-title.png") no-repeat;
	}
	.l-chart .chart {
		/* height: 350px; should be much higher */
		/* border: 1px solid #ccc; */
		background: -webkit-gradient(linear,left top,left bottom,from(#f5f5f5),to(#cccccc));
		background: -moz-linear-gradient(top,#f5f5f5,#cccccc);
		filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#f5f5f5', endColorstr='#cccccc');
	}
	.c-date-from, .c-date-to {
		min-width: 90px;
		width: 120px;
		height: 40px;
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
	.c-display-selected-1 {
		width: 148px;
		height: 50px;
		margin-left: 2px;
		display: inline-block;
		background: url("images/main-c-display-selected-1.png") no-repeat;
	}
	.c-display-selected-2 {
		width: 175px;
		height: 50px;
		margin-left: -28px;
		display: inline-block;
		background: url("images/main-c-display-selected-2.png") no-repeat;
	}
	.c-date-spinner {
		width: auto;
		min-width: 100px;
		height: 60px;
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
	
	.ui-spinner {
		border: 1px solid #656e84;
		background: url("images/main-spinner-bg.png") repeat-x center center;
	}
	.ui-spinner a.ui-spinner-button {
		border-left: 1px solid #656e84;
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
				<div class="l-list">
						<div id="c-list-1">
							<!-- radio template -->
							<div class="l-list-top"></div>
							<div class="l-list-top-bg"></div>
							<div class="l-list-mid-5">
								<ul>
									<!-- {{#sanup}} -->
									<li>
										<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
										<label> {{name}} </label>
									</li>
									<li>
										<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
										<label> {{name}} </label>
									</li>
									<li>
										<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
										<label> {{name}} </label>
									</li>
									<li>
										<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
										<label> {{name}} </label>
									</li>
									<li>
										<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
										<label> {{name}} </label>
									</li>
									<li>
										<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
										<label> {{name}} </label>
									</li>
									<!-- {{/sanup}} -->
								</ul>
							</div>
							<div class="l-list-bottom"></div>
							<!-- radio template end -->
						</div>
				</div>
				<div class="l-list">
					<div id="c-list-2">
						<!-- radio template -->
						<div class="l-list-top"></div>
						<div class="l-list-top-bg"></div>
						<div class="l-list-mid-5">
							<ul>
								<!-- {{#sanup}} -->
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<!-- {{/sanup}} -->
							</ul>
						</div>
						<div class="l-list-bottom"></div>
						<!-- radio template end -->
					</div>
					
				</div>
			</div><div class="l-side">
				<div class="l-list">
					<div id="c-list-3">
						<!-- radio template -->
						<div class="l-list-top"></div>
						<div class="l-list-top-bg"></div>
						<div class="l-list-mid-10">
							<ul>
								<!-- {{#sanup}} -->
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<li>
									<input type="radio" value="{{id}}" name="{{className}}" style="display: none;">
									<label> {{name}} </label>
								</li>
								<!-- {{/sanup}} -->
							</ul>
						</div>
						<div class="l-list-bottom"></div>
						<!-- radio template end -->
					</div>
				</div>
			</div>
		</div><div id="main" style="border:1px solid #ccc;"> <!--너비%값-->
			<div class="l-selector">
				<div class="c-display-selected">
					<div class="c-display-selected-1"></div>
					<div class="c-display-selected-2"></div>
					<div class="c-display-selected-2"></div>
				</div>
				<div class="c-date-spinner">
					<input class="c-date-from" id="spinner" name="value" />
					<input class="c-date-to" id="spinner" name="value" />
				</div>
				<div class="c-search">
					<div class="c-search-button"></div>
				</div>
			</div>
			<div class="l-category"></div>
			<div class="l-chart" id="chartContainer"> <!-- 스크롤러 적용부분 -->
			</div>
		</div>
	</div>
</body>
</html>
