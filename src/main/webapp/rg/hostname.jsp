<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.net.InetAddress" %>
<%@ page import="java.net.UnknownHostException" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
try {
	out.println(InetAddress.getLocalHost().getHostName());
	out.println("<br/>");
	out.println(InetAddress.getLocalHost().getHostAddress());
} catch ( UnknownHostException e ) {
	e.printStackTrace();
}

//출처: https://5dol.tistory.com/208 [5dol Story]
%>
</body>
</html>