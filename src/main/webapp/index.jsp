<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko" ng-app="rg">
<head>
<meta charset="UTF-8">
<link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png">
<link rel="manifest" href="/manifest.json">
<link rel="mask-icon" href="/safari-pinned-tab.svg" color="#5bbad5">
<meta name="theme-color" content="#ffffff">
<title>이지섭의 홈페이지 입니다.</title>

<link rel="stylesheet" type="text/css" href="/assets/css/board_20200907.css" />
<link rel="stylesheet" type="text/css" href="/assets/css/backgroundPartialFill.css" />

<style type="text/css">

@font-face {
	font-family: 'NotoKrR';
	font-style: normal;
	font-weight: 300;
	src: url('/assets/notosanskr_webfont/notokr-regular.eot');
	src: url('/assets/notosanskr_webfont/notokr-regular.eot?#iefix') format('embedded-opentype'),
		 url('/assets/notosanskr_webfont/notokr-regular.woff2') format('woff2'),
		 url('/assets/notosanskr_webfont/notokr-regular.woff') format('woff');
}

html, body {color:#666; font:14px/120% 'NotoKrR','dotum','Gulim',sans-serif,Arial,Helvetica,Clean,AppleGothic; min-width:1100px; font-weight:100;}
/*
body {
<c:if test="${currentLocale eq 'ko' }">
  font-family: 굴림, Sans-serif, Arial;
</c:if>
<c:if test="${currentLocale eq 'en' }">
  font-family: Sans-serif, Arial;
</c:if>
}
*/
</style>

<%--

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular-route.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular-cookies.js"></script>

<!-- Angular Material requires Angular.js Libraries -->
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.7/angular-animate.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.7/angular-aria.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.7/angular-messages.min.js"></script>


<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.7/angular-material.min.js"></script>

<script type="text/javascript" src="/assets/js/tinymce_4.8.5/tinymce.min.js"></script>
<script type="text/javascript" src="/assets/js/angular-ui-tinymce/tinymce.min.js"></script>

<!--
<script type="text/javascript" src="/assets/js/tinymce-angular-demo/bower_components/tinymce/tinymce.js"></script>
<script type="text/javascript" src="/assets/js/tinymce-angular-demo/bower_components/angular-ui-tinymce/src/tinymce.js"></script>
-->

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/FileAPI.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/ng-file-upload-all.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/ng-file-upload-shim.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/ng-file-upload.js"></script>

<script type="text/javascript" src="/src/jisblee.me/1.attachment/controllers/AttachmentController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/1.attachment/service/fileDownload.js"></script>
<script type="text/javascript" src="/src/jisblee.me/2.board/controllers/BoardController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/2.board/controllers/ManageBoardController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/3.user/controllers/UserController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/4.test/controllers/TestController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/5.statistics/controllers/StatisticsController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/6.goaccess/controllers/GoAccessController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/0.common/controllers/ProgressCircularController.js"></script>

<script type="text/javascript" src="/src/jisblee.me/rg_20200420.js"></script>


<script type="text/javascript" src="/src/jisblee.me/1.attachment/controllers/AttachmentController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/1.attachment/service/fileDownload.js"></script>


<script type="text/javascript" src="/src/jisblee.me/2.board/controllers/BoardController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/2.board/controllers/ManageBoardController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/3.user/controllers/UserController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/5.statistics/controllers/StatisticsController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/6.goaccess/controllers/GoAccessController_20230205.js"></script>
<script type="text/javascript" src="/src/jisblee.me/7.typing/controllers/TypingIntroController_20200329.js"></script>
<script type="text/javascript" src="/src/jisblee.me/7.typing/controllers/TypingController_20240331.js"></script>

<script type="text/javascript" src="/src/jisblee.me/8.test/controllers/TestController.js"></script>

<script type="text/javascript" src="/src/jisblee.me/9.s3/controllers/S3Controller.js"></script>

<script type="text/javascript" src="/src/jisblee.me/10.juso/controllers/JidoController.js"></script>

<script type="text/javascript" src="/src/jisblee.me/rg_20200420.js"></script>
--%>

</head>

<body ng-controller="RgCtrl">

<!--h1>jisblee.me</h1-->

<h3>지섭의 홈페이지 <a href="/login.do" target="_self">로그인</a></h3>

<div class="content" ng-view="">
    
</div>

</body>
</html>