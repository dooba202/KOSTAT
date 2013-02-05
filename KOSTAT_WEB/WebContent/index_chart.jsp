<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" >
<meta name='viewport' content='width=1440' />

<title>통계청 Data Explorer PoC</title>
<link rel="shortcut icon" href="css/favicon.ico">
<link rel="stylesheet" href="css/jquery-ui-1.9.2.custom.min.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/jquery.mCustomScrollbar.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/showLoading.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/style_chart.css" type="text/css" media="all" />
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
			<div class="l-header-content">
				<div class="l-title">
					<div class="c-logo"></div><!--로고 영역-->
				</div> <!--너비 고정-->
				<div class="l-logout">
					<div class="c-logout"></div><!--로그아웃버튼 영역-->
				</div> <!--너비 고정-->
				</div>
			<div class="l-header-border"></div>
		</div>
		<div id="side"> <!--너비%값-->
			<div class="l-side">
				<div id="l-side-selector">
					<div class="l-side-selector-inner">
						<div class="c-keyword-set">
							<input type="radio" id="quantity" name="keyword" value="물량" checked><label for="quantity">물량</label>
							<input type="radio" id="index" name="keyword" value="지수"><label for="index">지수</label>
						</div>
						<div class="c-date-picker">
							<input class="c-date-from" name="value" /> ~ <input class="c-date-to" name="value" />
						</div>
					</div>
				</div>
				<div id="accordion">
				</div>
			</div>
		</div><div id="main"> <!--너비%값-->
			<div class="l-selector">
				<div class="l-selector-inner">
					<div class="c-display-selected">
						<div class="c-display-selected-1">산업 분류</div>
						<div class="c-display-selected-2">품목 분류</div>
						<div class="c-display-selected-3">사업체 분류</div>
					</div>
					<div class="c-search">
						<div class="c-search-button"></div>
					</div>
					<!-- <div class="c-date-picker">
						<span class="c-date-from-text"></span><input class="c-date-from" name="value" />
						<span class="c-date-to-text"></span><input class="c-date-to" name="value" />
					</div> -->
				</div>
			</div>
			<div class="l-main-inner">
				<div class="placeholder"></div>
				<div class="l-category">
					<div id="c-category-title2"></div>
				</div>
				<div class="l-chart" id="chartContainer"><!-- 스크롤러 적용부분 -->
				</div>
			</div>
			<div id="footer">
				<div id="c-logo-ibm"></div>
			</div>
		</div>
	</div>
</body>
</html>
