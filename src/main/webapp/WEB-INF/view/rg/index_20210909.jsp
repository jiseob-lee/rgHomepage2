<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="ko" ng-app="rg">
<head>

<meta charset="UTF-8" />
<meta http-equiv="x-ua-compatible" content="IE=edge" />

<title>이지섭의 홈페이지 입니다.</title>

<link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png" />
<link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png" />
<link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png" />
<link rel="manifest" href="/manifest.json" />
<link rel="mask-icon" href="/safari-pinned-tab.svg" color="#5bbad5" />

<meta name="theme-color" content="#ffffff" />

<link rel="stylesheet" type="text/css" href="/assets/css/ProgressCircular.css" />
<!-- <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.12/angular-material.min.css"> -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/angular-material/1.2.5/angular-material.min.css">

<link rel="stylesheet" type="text/css" href="/assets/css/backgroundPartialFill.css" />

<link rel="stylesheet" type="text/css" href="/assets/css/board_20200907.css" />

<!--
<link href="https://fonts.googleapis.com/css?family=Gugi&amp;subset=korean" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Song Myung&amp;subset=korean" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Jua&amp;subset=korean" rel="stylesheet">
-->
<link href="https://fonts.googleapis.com/css?family=Nanum Gothic&amp;subset=korean" rel="stylesheet" />

<!-- <link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap.min.css" /> -->
<!-- <link rel="stylesheet" type="text/css" href="/assets/css/toaster.css" rel="stylesheet" type="text/css" /> -->

<style type="text/css">

@font-face {
	font-family: 'NotoKrR';
	font-style: normal;
	font-weight: 150;
	src: url('/assets/notosanskr_webfont/notokr-regular.eot');
	src: url('/assets/notosanskr_webfont/notokr-regular.eot?#iefix') format('embedded-opentype'),
		 url('/assets/notosanskr_webfont/notokr-regular.woff2') format('woff2'),
		 url('/assets/notosanskr_webfont/notokr-regular.woff') format('woff');
}

html, body {
	/* color:#666; */
	font:15px/120% 'NotoKrR','dotum','Gulim',sans-serif,Arial,Helvetica,Clean,AppleGothic; 
	min-width:1100px; 
	font-weight:100;
	
	/* background-color: #202b38; */
	background-color: #303030;
    color: #fff;
     
}

h1,
h2,
h3 {
  color: #fff;
}

p,
dl {
  color: #dbdbdb;
}

a {
  color: #41adff;
  text-decoration: none;
}


img {
  border: 0;
}

/*
body {
  font-family: "굴림", gulim, "바탕", batang, "돋움", dotum, arial, helvetica, sans-serif;
  font-size: 10pt;
}
*/

div.menu {
  float: left;
  border-right: 2px solid gray;
  width: 140px;
}

div.user {
  width: 140px;
  padding: 5px;
  white-space: pre-line;
  overflow-wrap: break-word;
}

div.content {
  float: none;
  display: inline-block;
}

footer {
  clear: both;
  font-size: 13px;
  margin-top: 35px;
  margin-left: 10px;
  width: 980px;
  float: left;
}

h3.menu {
  margin-bottom: 20px;
}
</style>

<script type="text/javascript">
var API_URL = "https://business.juso.go.kr";
var GIS_SERVER_URL = "https://m1.juso.go.kr";
</script>

<!--
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular.min.js"></script>
-->

<script type="text/javascript" src="/assets/js/jquery-3.7.1.min.js"></script>
<!-- <script type="text/javascript" src="/assets/js/angular-1.7.9.min.js"></script> -->

<!-- <script type="text/javascript" src="/assets/js/angular.1.8.0.min.js"></script> -->
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.8.2/angular.min.js"></script>


<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.7.6/angular-route.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.7.6/angular-cookies.js"></script>

<!-- Angular Material requires Angular.js Libraries -->
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.6/angular-animate.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.6/angular-aria.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.6/angular-messages.min.js"></script>

<!-- Angular Material Library -->
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.12/angular-material.min.js"></script>
<!-- <script src='https://material.angularjs.org/HEAD/angular-material.js'></script> -->

<!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.7/angular-material.min.js"></script> -->

<!-- <script type="text/javascript" src="/assets/js/package/dist/js/service/HuskyEZCreator.js" charset="utf-8"></script> -->

<script type="text/javascript" src="/assets/js/tinymce_7.9.0/tinymce.min.js"></script>
<script type="text/javascript" src="/assets/js/angular-ui-tinymce/tinymce.js"></script>

<script type="text/javascript" src="/assets/js/moment-with-locales.js"></script>
<!-- <script type="text/javascript" src="/assets/js/moment.js"></script> -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular-moment/1.3.0/angular-moment.min.js"></script>

<script type="text/javascript" src="/assets/js/angular-translate.min.js"></script>
<!-- <script type="text/javascript" src="/assets/js/angular-translate-loader-url.min.js"></script> -->
<!-- <script type="text/javascript" src="/assets/js/services.js"></script> -->
<script type="text/javascript" src="/assets/js/angular-translate-storage-local.min.js"></script>
<script type="text/javascript" src="/assets/js/angular-translate-storage-cookie.min.js"></script>
<script type="text/javascript" src="/assets/js/angular-translate-loader-static-files.js"></script>

<script type="text/javascript" src="/assets/json/message-common_en.js"></script>
<script type="text/javascript" src="/assets/json/message-common_ko.js"></script>


<!--
<script type="text/javascript" src="/assets/js/tinymce-angular-demo/bower_components/tinymce/tinymce.js"></script>
<script type="text/javascript" src="/assets/js/tinymce-angular-demo/bower_components/angular-ui-tinymce/src/tinymce.js"></script>
-->

<!-- <script type="text/javascript" src="/assets/js/toaster.js"></script> -->


<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/FileAPI.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/ng-file-upload-all.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/ng-file-upload-shim.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/ng-file-upload.js"></script>

<script type="text/javascript" src="/src/jisblee.me/0.common/controllers/ProgressCircularController.js"></script>


<script type="text/javascript" src="/src/jisblee.me/1.attachment/controllers/AttachmentController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/1.attachment/service/fileDownload.js"></script>


<script type="text/javascript" src="/src/jisblee.me/2.board/controllers/BoardController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/2.board/controllers/ManageBoardController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/3.user/controllers/UserController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/5.statistics/controllers/StatisticsController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/6.goaccess/controllers/GoAccessController_20240707.js"></script>
<script type="text/javascript" src="/src/jisblee.me/7.typing/controllers/TypingIntroController_20200329.js"></script>
<script type="text/javascript" src="/src/jisblee.me/7.typing/controllers/TypingController_20240519.js"></script>

<script type="text/javascript" src="/src/jisblee.me/8.test/controllers/TestController.js"></script>

<script type="text/javascript" src="/src/jisblee.me/9.s3/controllers/S3Controller.js"></script>

<script type="text/javascript" src="/src/jisblee.me/10.juso/controllers/JidoController.js"></script>

<script type="text/javascript" src="/src/jisblee.me/13.loginlog/controllers/LoginLogController.js"></script>

<script type="text/javascript" src="/src/jisblee.me/14.downhistory/controllers/DownHistoryController.js"></script>

<script type="text/javascript" src="/src/jisblee.me/rg_20240707_1.js"></script>


<!-- <script type="text/javascript" src="https://business.juso.go.kr/juso_support_center/js/jquery-1.12.4.min.js"></script> -->
<script type="text/javascript" src="https://business.juso.go.kr/juso_support_center/js/rnic-search.js"></script>
<script type="text/javascript" src="https://business.juso.go.kr/juso_support_center/js/addrlink/map/ol.js"></script>
<script type="text/javascript" src="https://business.juso.go.kr/juso_support_center/js/addrlink/map/OverlayScrollbars.min.js"></script>
<script type="text/javascript" src="https://business.juso.go.kr/juso_support_center/js/addrlink/map/jquery-ui.min.js"></script>
<script type="text/javascript" src="https://business.juso.go.kr/juso_support_center/js/addrlink/map/view-map.min.js"></script>
<script type="text/javascript" src="https://business.juso.go.kr/juso_support_center/js/addrlink/map/search-map.min.js"></script>

<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=48db2a9456ff7710ab38d35097440853&libraries=services,clusterer,drawing"></script>

<script type="text/javascript">
$(document).ready(function() {
//$(function() {
	/*
    var $sidebar   = $("#menu"), 
        $window    = $(window),
        offset     = $sidebar.offset(),
        topPadding = 15;

	//console.log($sidebar.height());
	//console.log(offset);
    $window.scroll(function() {
        if ($window.scrollTop() > $sidebar.height()) {
            $sidebar.stop().animate({
                marginTop: $window.scrollTop()
            });
        } else {
            $sidebar.stop().animate({
                marginTop: 0
            });
        }
    });
    */
});

function setCookie(name, value, exp) {
    var date = new Date();
    date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000);
    var domain = ".jisblee.me";
    document.cookie = name + '=' + escape(value) + ';expires=' + date.toUTCString() + ";path=/;" + "domain=" + domain + ";";
};
</script>

</head>

<body ng-controller="RgCtrl">

  <div style="white-space: nowrap;">
  
    <div class="menu">

		<div class="user">${loginId } (${loginUserName }) 님께서
		로그인하셨습니다.</div>
				
		<sec:authorize access="hasRole('ROLE_SUPER')">

		<!--
		<div class="user">${loginId } (${loginUserName }) 님께서 님께서 님께서
		로그인하셨습니다.</div>
		-->
				
		<!-- "${currentLocale }" -->
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/attachment/0/1" target="_self" class="menuLink">첨부 파일</a></h3>
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/board/list/0/1" target="_self" class="menuLink">게시글</a></h3>

		<h3 class="menu">&nbsp;- <a href="/rg/#/manageBoard" target="_self" class="menuLink">게시판</a></h3>

		<h3 class="menu">&nbsp;- <a href="/rg/#/board/list/0/1/1" target="_self" class="menuLink">공개 게시글</a></h3>
		
		</sec:authorize>
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/getIpCount" target="_self" class="menuLink">글 조회 수</a></h3>

		<h3 class="menu">&nbsp;- <a href="/rg/#/getStatisticsArticleSubjects" target="_self" class="menuLink">조회된 글</a></h3>

		<h3 class="menu">&nbsp;- <a href="/rg/#/getStatisticsLogs" target="_self" class="menuLink">조회 글 로그</a></h3>

		<h3 class="menu">&nbsp;- <a href="/rg/#/goaccess" target="_self" class="menuLink">goaccess</a></h3>

		<sec:authorize access="hasRole('ROLE_SUPER')">
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/test" target="_self" class="menuLink">테스트</a></h3>
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/juso" target="_self" class="menuLink">주소</a></h3>
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/Check_the_latitude_and_longitude" target="_self" class="menuLink">위도, 경도 조회</a></h3>
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/textLength" target="_self" class="menuLink">문자열 길이</a></h3>
		
		<!--
		<h3 class="menu">&nbsp;- <a href="/rg/#/s3" target="_self" class="menuLink">S3</a></h3>
		-->
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/getLoginLog" target="_self" class="menuLink">로그인 로그</a></h3>
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/getDownHistory" target="_self" class="menuLink">다운로드 내역</a></h3>
		
		</sec:authorize>

		
		<h3 class="menu">&nbsp;- <a href="/rg/#/typingIntro1" target="_self" class="menuLink">성경 타자(신약)</a></h3>
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/typingIntro2" target="_self" class="menuLink">성경 타자(구약)</a></h3>
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/typingRecord" target="_self" class="menuLink">타자 연습 내역</a></h3>

		<h3 class="menu">&nbsp;- <a href="/rg/#/user" target="_self" class="menuLink">사용자</a></h3>

		<h3 class="menu">&nbsp;- <a href="/" target="_self">프론트 메인</a></h3>

		
		<sec:authorize access="hasRole('ROLE_SUPER')">
		
		&nbsp; &nbsp;
		
		<input type="hidden" name="currentLocale" id="currentLocale" value="${currentLocale }" />
		
		<select name="language" onchange="setCookie('lang', this.value, 30); location.href = 'https://jisblee.me' + window.location.pathname + '?lang=' + this.value + window.location.hash">
			<option value="ko" <c:if test="${currentLocale eq 'ko' }">selected='selected'</c:if>><spring:message code="header.korean" text="Korean" /></option>
			<option value="en" <c:if test="${currentLocale eq 'en' }">selected='selected'</c:if>><spring:message code="header.english" text="English" /></option>
		</select>
		
		</sec:authorize>

    	<div style="text-align: right; margin-top: 10px; padding: 10px;">
			<form name="logout" action="/logout" method="post" target="_top">
			  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			  <input type="submit" value="로그아웃" />
			</form>
		</div>
		
    </div>
  
    <div style="width: 25px; float: left;">&nbsp;</div>

    <div class="content" ng-view="">
    
    </div>

  </div>
  
</body>
</html>

