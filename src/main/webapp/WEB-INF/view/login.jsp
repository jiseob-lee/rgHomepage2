<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page import="com.rg.util.LocaleUtil" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="login.login" text="로그인" /></title>

<style type="text/css">
.login {
  margin: 20px;
  padding: 20px;
}
.login td {
  padding: 6px;
}
</style>

<!--
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular-route.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular-cookies.js"></script>
-->

<!-- Angular Material requires Angular.js Libraries -->
<!--
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.7/angular-animate.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.7/angular-aria.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.7/angular-messages.min.js"></script>


<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.7/angular-material.min.js"></script>

<script type="text/javascript" src="/assets/js/tinymce_4.8.5/tinymce.min.js"></script>
<script type="text/javascript" src="/assets/js/angular-ui-tinymce/tinymce.min.js"></script>
-->

<!--
<script type="text/javascript" src="/assets/js/tinymce-angular-demo/bower_components/tinymce/tinymce.js"></script>
<script type="text/javascript" src="/assets/js/tinymce-angular-demo/bower_components/angular-ui-tinymce/src/tinymce.js"></script>
-->

<!--
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
<script type="text/javascript" src="/src/jisblee.me/0.common/controllers/ProgressCircularController.js"></script>

<script type="text/javascript" src="/src/jisblee.me/rg.js"></script>
-->

<script type="text/javascript">
//alert(location.hostname);
if (location.hostname != "localhost" && location.protocol != 'https:') {
	//location.href = 'https:' + window.location.href.substring(window.location.protocol.length);
}

function loginProcess() {
	var frm = document.frm;
	if (frm.username == null || frm.username == "") {
		alert("로그인 아이디를 입력해주세요.");
		return;
	}
	if (frm.password == null || frm.password == "") {
		alert("로그인 비밀번호를 입력해주세요.");
		return;
	}
	setCookie("loginId", frm.username.value, 90);
	frm.submit();
}

function setCookie(cookie_name, value, days) {

	//var exdate = new Date(); 

	//exdate.setDate(exdate.getDate() + days); // 설정 일수만큼 현재시간에 만료값으로 지정

	if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days*24*60*60*1000));
        expires = "; expires=" + date.toUTCString();
    }
    
	//var cookie_value = escape(value) + ((days == null) ? '' : '; expires=' + exdate.toUTCString()) + "; path=/";
	
	var cookie_value = value ? escape(value) : "";
	
	//document.cookie = cookie_name + '=' + cookie_value;
	
	document.cookie = cookie_name + "=" + (cookie_value || "")  + expires + "; path=/";
}

function getCookie(cookie_name) {
	var x, y; var val = document.cookie.split(';');
	for (var i = 0; i < val.length; i++) {
		x = val[i].substr(0, val[i].indexOf('='));
		y = val[i].substr(val[i].indexOf('=') + 1);
		x = x.replace(/^\s+|\s+$/g, ''); // 앞과 뒤의 공백 제거하기
		if (x == cookie_name) {
			return unescape(y); // unescape로 디코딩 후 값 리턴 
		} 
	} 
}

function delCookie(cookie_name) {
	document.cookie = cookie_name + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/'
}

function onLoad() {
	//var loginId = getCookie("loginId");
	//if (loginId != null && loginId != "") {
		//document.frm.username.value = loginId;
		//document.frm.password.focus();
	//} //else {
		//document.frm.username.focus();
	//}
	//document.frm.username.focus();
}

function autoLogin1() {
	//alert("1");
	if (document.getElementById('autoLogin').checked) {
	    // 쿠키 세팅
		setCookie('autoLogin', "1", 100);
	} else {
		// 쿠키 삭제
		delCookie('autoLogin');
	}
}

</script>

</head>

<body onload="onLoad();">

<%

LocaleUtil localeUtil = new LocaleUtil();
String currentLocale = localeUtil.getLocale().getLanguage();

//out.println(currentLocale);

%>

<%
    //RequestContext rc = new RequestContext(request);
    //java.util.Locale locale = rc.getLocale();
    //out.println("현재 Locale: " + locale.toString());
%>

<div class="login">

<spring:message code="login.login" text="로그인" />

<form name="frm" action="/login" method="post" autocomplete="on">
<table class="login">
  <tr>
    <td><spring:message code="login.id" text="아이디" /></td>
    <td><input type="text" name="username" /></td>
  </tr>
  <tr>
    <td><spring:message code="login.password" text="비밀번호" /></td>
    <td><input type="password" name="password" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="checkbox" name="autoLogin" id="autoLogin" value="1" onclick="autoLogin1();" /> <spring:message code="login.autoLogin" text="자동 로그인 설정" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="submit" value="<spring:message code="login.login" text="로그인" />" /></td>
    <!-- <td><input type="button" value="<spring:message code="login.login" text="로그인" />" onclick="loginProcess();" /></td> -->
  </tr>
  <c:if test="${param.error == 'true'}">
  <tr>
    <td>&nbsp;</td>
    <td><font color="blue"><spring:message code="login.incorrectLogin" text="로그인 정보가 올바르지 않습니다." /></font></td>
  </tr>
  </c:if>
  <tr>
    <td colspan="2"><br/><a href="/"><spring:message code="login.toFrontMain" text="프론트 메인으로.." /></a></td>
  </tr>
</table>
</form>

</div>

</body>
</html>
