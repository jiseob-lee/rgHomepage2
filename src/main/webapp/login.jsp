<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page import="com.rg.util.LocaleUtil" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>

<style type="text/css">
.login {
  margin: 20px;
  padding: 20px;
}
.login td {
  padding: 6px;
}
</style>

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
<script type="text/javascript" src="/src/jisblee.me/0.common/controllers/ProgressCircularController.js"></script>

<script type="text/javascript" src="/src/jisblee.me/rg_20200420.js"></script>

<script type="text/javascript">
if (location.protocol != 'https:') {
	//location.href = 'https:' + window.location.href.substring(window.location.protocol.length);
}
</script>

</head>

<body onload="document.frm.username.focus();">

<%

LocaleUtil localeUtil = new LocaleUtil();
String currentLocale = localeUtil.getLocale().getLanguage();

out.println(currentLocale);

%>

<div class="login">
<spring:message code="header.login" text="로그인" />
<spring:message code="login.login" text="로그인1" />
1111
<form name="frm" action="/login" method="post">
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
    <td><input type="submit" value="<spring:message code="login.login" text="로그인" />" /></td>
  </tr>
  <c:if test="${param.error == 'true'}">
  <tr>
    <td>&nbsp;</td>
    <td><font color="blue"><spring:message code="login.incorrectLogin" text="로그인 정보가 올바르지 않습니다." /></font></td>
  </tr>
  </c:if>
</table>
</form>

</div>

</body>
</html>
