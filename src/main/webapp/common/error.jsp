<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" %><%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>에러가 발생하였습니다.</title>
</head>
<body>

<h3>에러가 발생하였습니다.</h3>

    <div>
        <label>Time Stamp</label>
        <div>${timestamp}</div>
    </div>
    <div>
        <label>Exception name</label>
        <div>${exception}</div>
    </div>
    <div>
        <label>Trace</label>
        <div>${trace}</div>
    </div>


</body>
</html>
<%
out.clear();
%>