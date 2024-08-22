<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.rg.util.SessionConfig" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String id = request.getParameter("v");
String userId = SessionConfig.getSessionidCheck("login_id", id);
session.setMaxInactiveInterval(60);
session.setAttribute("login_id", id);
%>
<%=userId %>
</body>
</html>