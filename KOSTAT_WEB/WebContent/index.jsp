<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ibm.green.kostat.rest.services.util.*,com.ibm.green.kostat.enums.*" %>
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
<link rel="stylesheet" href="css/jquery.toastmessage.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
<script>
	var require = {
		config : {
			'index' : {
				sanup :  <%=CodeUtil.getIndustryCodeList(IndustryCodeType.SanId)%>,
				pum: <%=CodeUtil.getIndustryCodeList(IndustryCodeType.PumId)%>,
				saup: <%=CodeUtil.getIndustryCodeList(IndustryCodeType.SaupId)%>
			}
		}
	};
</script>
<script data-main="js/main" src="js/require.js"></script>
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
							<input type="radio" id="all" name="keyword" value="all" checked><label for="all">전체</label>
							<input type="radio" id="plus" name="keyword" value="up"><label for="plus">증가</label>
							<input type="radio" id="minus" name="keyword" value="down"><label for="minus">감소</label>
						</div>
						<div class="c-date-picker">
							<input class="c-date-from" name="value" /> ~ <input class="c-date-to" name="value" />
						</div>
					</div>
				</div>
				<div id="accordion">
					<div class="l-list-top-title">
						<div class="l-list-top">
							<div class="l-list-title1"></div>
						</div>
						<div class="l-list-top-bg"></div>
					</div>
					<div id="category1" class="l-list"></div>
					<div class="l-list-top-title">
						<div class="l-list-top">
							<div class="l-list-title3"></div>
						</div>
						<div class="l-list-top-bg"></div>
					</div>
					<div id="category2" class="l-list"></div>
					<div class="l-list-top-title">
						<div class="l-list-top">
							<div class="l-list-title2"></div>
						</div>
						<div class="l-list-top-bg"></div>
					</div>
					<div id="category3" class="l-list"></div>				
				</div>
			</div>
		</div><div id="main"> <!--너비%값-->
			<div class="l-main-inner">
				<div class="l-selector">
					<div class="l-selector-inner">
						<div class="c-display-selected">
							<div class="c-display-selected-1">산업 분류</div>
							<div class="c-display-selected-2">사업체 분류</div>
							<div class="c-display-selected-3">품목 분류</div>
						</div>
						<div class="c-search">
							<div class="c-search-button"></div>
						</div>
						<div class="l-search-box">
							<input id="c-search-box" type="text" placeholder="추가 검색어를 입력해주세요." />
							<input id="c-search-check" type="checkbox" /><label>결과내재검색</label>
						</div>
					</div>
				</div>
				<div class="l-chart" id="chartContainer"><!-- 스크롤러 적용부분 -->
				<div class="placeholder"></div>
					<div class="l-chart-inner">
						<div class="chart" id="target1">
							<div class="l-chart-title">
								<div id="c-chart-title-1">지정사이트</div>
							</div>
							<div class="l-chart-title-bg"></div>
							<div class="iframe" id="frame1" width='320px'></div>
						</div>
						<div class="chart" id="target2">
							<div class="l-chart-title">
								<div id="c-chart-title-1">경제뉴스</div>
							</div>
							<div class="l-chart-title-bg"></div>
							<div class="iframe" id="frame2"></div>
						</div>
						<div class="chart" id="target3">
							<div class="l-chart-title">
								<div id="c-chart-title-1">포털</div>
							</div>
							<div class="l-chart-title-bg"></div>
							<div class="iframe" id="frame3"></div>
						</div>
					</div>
				</div>
			</div>
			
		</div>
		<div id="footer">
				<div id="c-logo-ibm"></div>
			</div>
	</div>
	<!-- <div id="results"></div> -->
</body>
</html>
