<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% // @ page import="jakarta.servlet.ServletContext" %>
<% // @ page import="jakarta.servlet.RequestDispatcher" %>

<% // @ page import="javax.servlet.RequestDispatcher" %>

<%
//RequestDispatcher requestDispatcher = request.getRequestDispatcher("/rg/index.do");

RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/rg/index.do");

if (requestDispatcher instanceof jakarta.servlet.RequestDispatcher) {
	System.out.println("#### requestDispatcher instanceof jakarta.servlet.RequestDispatcher.");
//} else if (requestDispatcher instanceof javax.servlet.RequestDispatcher) {
	//System.out.println("#### requestDispatcher instanceof javax.servlet.RequestDispatcher.");
} else {
	System.out.println("#### other type.");
}

String classLocation = RequestDispatcher.class.getName().replace('.', '/') + ".class";

System.out.println("#### RequestDispatcher : " + classLocation);


requestDispatcher.forward(request, response);
%>

<%--
<jsp:forward page="/rg/index.do" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<script type='text/javascript'>
location.href = "/rg/index.do";
</script>

</body>
</html>
--%>
