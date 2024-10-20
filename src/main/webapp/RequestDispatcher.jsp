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


clazz = this.getClass().getResource("/org/springframework/scheduling/quartz/JobDetailFactoryBean.class");
out.println( (clazz == null ? "null" : clazz.getFile()) + "<br/>" );

clazz = this.getClass().getResource("/org/springframework/scheduling/quartz/JobDetailBean.class");
out.println( (clazz == null ? "null" : clazz.getFile()) + "<br/>" );

clazz = this.getClass().getResource("/javax/servlet/RequestDispatcher.class");
out.println( (clazz == null ? "null" : clazz.getFile()) + "<br/>" );

clazz = this.getClass().getResource("/jakarta/servlet/RequestDispatcher.class");
out.println( (clazz == null ? "null" : clazz.getFile()) + "<br/>" );

%>

</body>
</html>