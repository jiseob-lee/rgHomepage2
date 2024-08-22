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

id : <%=session.getAttribute("loginId") %>

<br/>

name : <%=session.getAttribute("loginUserName") %>

</div>

</body>
</html>