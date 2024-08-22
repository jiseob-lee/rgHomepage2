<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%--

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page import="com.rg.util.LocaleUtil" %>
<%@ page import="com.rg.util.GeoLite2" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.rg.login.service.LoginService" %>
<%@ page import="java.util.Locale" %>

<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.nio.charset.Charset" %>

<%@ page import="com.rg.util.vo.LocaleVO" %>
<%@ page import="com.google.gson.Gson" %>

<%

String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/rg/getLocale.do";

if (request.getParameter("lang") != null && !"".equals(request.getParameter("lang"))) {
	url += "?lang=" + request.getParameter("lang");
}
System.out.println("############################# url : " + url);

/*
URL obj = new URL(url);
HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//전송방식
con.setRequestMethod("GET");
con.setConnectTimeout(10000);       //컨텍션타임아웃 10초
con.setReadTimeout(5000);           //컨텐츠조회 타임아웃 5총

int responseCode = con.getResponseCode();
System.out.println("responseCode : " + responseCode);

Charset charset = Charset.forName("UTF-8");
BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),charset));
String inputLine;
StringBuffer buf = new StringBuffer();
while ((inputLine = in.readLine()) != null) {
    buf.append(inputLine);
}
in.close();

//출처: https://bobr2.tistory.com/entry/HttpConnection-Get-Post-사용법 [나만의공간]

LocaleVO localeVO = new Gson().fromJson(buf.toString(), LocaleVO.class);
String currentLocale = localeVO.getLocale();
*/

//LocaleUtil localeUtil = (LocaleUtil)wac.getBean("localeUtil");

//String localeCode = localeUtil.getLocale().getLanguage();

//String localeCode = ((Locale)pageContext.getAttribute("localeCode")).getLanguage();

//Locale locale = request.getLocale();

LocaleUtil localeUtil = new LocaleUtil();
String currentLocale = localeUtil.getLocale().getLanguage();
//String currentLocale = locale.getLanguage();

//System.out.println("###################### localeCode (/rg/) : " + localeCode);
System.out.println("###################### locale (/rg/) : " + currentLocale);

//if ("fr".equals(currentLocale)) {
	//String country = GeoLite2.getCountry(request, request.getParameter("ip"));
	//logger.debug("###################### country : " + country);
	//if ("KR".equals(country)) {
		//currentLocale = "ko";
	//} else {
		//currentLocale = "en";
	//}
	//localeUtil.setLocale(currentLocale, request, response);
//}

System.out.println("################################## /rg/index.jsp Current locale : " + currentLocale);

localeUtil.setLocale(currentLocale, request, response);

//mav.addObject("currentLocale", currentLocale);
//pageContext.setAttribute("currentLocale", currentLocale);

//HttpSession session = request.getSession();

if (session.getAttribute("loginId") == null) {
	
	String id = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	//logger.error("id : " + id);

	//ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	//TestServiceClass tsc1 = (TestServiceClass) ac.getBean("tsc");
	
	ServletContext servletContext = this.getServletContext();
	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	
	LoginService loginService = (LoginService)wac.getBean("loginService");
	
	String userName = loginService.getUserName(id);
	
	if (userName == null || "".equals(userName)) {
		//return "redirect:/login.jsp";
		//mav.setViewName("redirect:/login.jsp");
		response.sendRedirect("/login.jsp");  
		//return mav;
	}
	
	session.setAttribute("loginId", id);
	session.setAttribute("redisLoginId", "^" + id);
	session.setAttribute("loginUserName", userName);

	//Cookie myCookie1 = new Cookie("loginId", id);
	//response.addCookie(myCookie1);
	
	//Cookie myCookie2 = new Cookie("loginUserName", userName);
	//response.addCookie(myCookie2);
}

System.out.println("################################ session getUuidCount 2 : " + (Integer)session.getAttribute("getUuidCount"));
System.out.println("################################ session loginUserId1 2 : " + (String)session.getAttribute("loginUserId1"));

%>

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
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.12/angular-material.min.css">

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
	font:14px/120% 'NotoKrR','dotum','Gulim',sans-serif,Arial,Helvetica,Clean,AppleGothic; 
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
  width: 135px;
}

div.user {
  width: 135px;
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

<!--

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

html, body {color:#666; font:14px/120% 'NotoKrR','dotum','Gulim',sans-serif,Arial,Helvetica,Clean,AppleGothic; min-width:1100px; font-weight:100;}

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
  width: 135px;
}

div.user {
  width: 135px;
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


-->

<!--
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular.min.js"></script>
-->

<script type="text/javascript" src="/assets/js/jquery-3.3.1.min.js"></script>
<!-- <script type="text/javascript" src="/assets/js/angular-1.7.9.min.js"></script> -->
<script type="text/javascript" src="/assets/js/angular.1.8.0.min.js"></script>
<!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.8.2/angular.min.js"></script> -->

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

<script type="text/javascript" src="/assets/js/tinymce_6.7.2/tinymce.min.js"></script>
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
<script type="text/javascript" src="/src/jisblee.me/6.goaccess/controllers/GoAccessController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/7.typing/controllers/TypingIntroController_20200329.js"></script>
<script type="text/javascript" src="/src/jisblee.me/7.typing/controllers/TypingController_20220417.js"></script>

<script type="text/javascript" src="/src/jisblee.me/8.test/controllers/TestController.js"></script>

<script type="text/javascript" src="/src/jisblee.me/9.s3/controllers/S3Controller.js"></script>

<script type="text/javascript" src="/src/jisblee.me/10.juso/controllers/JidoController.js"></script>

<script type="text/javascript" src="/src/jisblee.me/rg_20200420.js"></script>

<!--
<script type="text/javascript" src="https://business.juso.go.kr/juso_support_center/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="https://business.juso.go.kr/juso_support_center/js/rnic-search.js"></script>
<script type="text/javascript" src="https://business.juso.go.kr/juso_support_center/js/addrlink/map/ol.js"></script>
<script type="text/javascript" src="https://business.juso.go.kr/juso_support_center/js/addrlink/map/OverlayScrollbars.min.js"></script>
<script type="text/javascript" src="https://business.juso.go.kr/juso_support_center/js/addrlink/map/jquery-ui.min.js"></script>
<script type="text/javascript" src="https://business.juso.go.kr/juso_support_center/js/addrlink/map/view-map.min.js"></script>
<script type="text/javascript" src="https://business.juso.go.kr/juso_support_center/js/addrlink/map/search-map.min.js"></script>
-->

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

    //$.ajax({
        //url : "<%=url%>",
        //type : "get",
        //success : function(result) {
            //$("#language").val(result.locale);
        //}
    //});
	
});
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
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/s3" target="_self" class="menuLink">S3</a></h3>
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/juso" target="_self" class="menuLink">주소</a></h3>
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/textLength" target="_self" class="menuLink">문자열 길이</a></h3>
		
		</sec:authorize>

		
		<h3 class="menu">&nbsp;- <a href="/rg/#/typingIntro1" target="_self" class="menuLink">성경 타자 (신약)</a></h3>
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/typingIntro2" target="_self" class="menuLink">성경 타자 (구약)</a></h3>
		
		<h3 class="menu">&nbsp;- <a href="/rg/#/typingRecord" target="_self" class="menuLink">타자 연습 내역</a></h3>

		<h3 class="menu">&nbsp;- <a href="/rg/#/user" target="_self" class="menuLink">사용자</a></h3>

		<h3 class="menu">&nbsp;- <a href="https://jisblee.me/" target="_self">프론트 메인</a></h3>

		
		<sec:authorize access="hasRole('ROLE_SUPER')">
		
		&nbsp; &nbsp;
		
		<input type="hidden" name="currentLocale" id="currentLocale" value="${currentLocale }" />
		
		<select name="language" ng-model="language" onchange="location.href = 'https://jisblee.me' + window.location.pathname + '?lang=' + this.value + window.location.hash">
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

--%>