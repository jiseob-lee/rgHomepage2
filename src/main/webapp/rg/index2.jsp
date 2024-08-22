<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
//alert(location.hash);
location.href = "./index.do" + location.hash;
</script>
</head>
<body>

<div>
rg index.jsp
<br/>
<%--
<c:if test="${not empty pageContext.request.userPrincipal}">
    User: <c:out value="${pageContext.request.userPrincipal.name}" />
</c:if>
--%>
</div>

</body>
</html>