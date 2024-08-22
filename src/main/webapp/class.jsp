<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
java.net.URL clazz = null;
clazz = this.getClass().getResource("/org/springframework/data/redis/core/StringRedisTemplate.class");
out.println( clazz.getFile() + "<br/>" );
%>

</body>
</html>