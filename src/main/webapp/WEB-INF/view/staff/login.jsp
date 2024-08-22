<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HUMANBASE 관리자 로그인</title>

<style type="text/css">
.login {
  margin: 20px;
  padding: 20px;
}
.login td {
  padding: 6px;
}
</style>

</head>

<body onload="document.frm.username.focus();">

<div class="login">

HUMANBASE 관리자 로그인

<form name="frm" action="<c:url value='/login' />" method="post">
<table class="login">
  <tr>
    <td>아이디</td>
    <td><input type="text" name="username" /></td>
  </tr>
  <tr>
    <td>비밀번호</td>
    <td><input type="password" name="password" /></td>
  </tr>
  <tr>
    <td><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /></td>
    <td><input type="submit" value="로그인" /></td>
  </tr>
  <c:if test="${param.error == 'true'}">
  <tr>
    <td>&nbsp;</td>
    <td><font color="blue">로그인 정보가 올바르지 않습니다.</font></td>
  </tr>
  </c:if>
</table>
</form>

</div>

</body>
</html>