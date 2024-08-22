<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
Image Check

<div>
contentType : <c:out value="${contentType }" />
</div>

<div>
image : <c:out value="${image }" />
</div>

<div>
contentType 2 : <c:out value="${contentType2 }" />
</div>

<div>
image 2 : <c:out value="${image2 }" />
</div>

</body>
</html>