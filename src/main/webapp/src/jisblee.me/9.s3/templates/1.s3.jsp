<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>S3</title>
<!-- <script type="text/javascript" src="https://sdk.amazonaws.com/js/aws-sdk-2.283.1.min.js"></script> -->
<script type="text/javascript" src="/assets/aws-sdk-js-2.442.0/dist/aws-sdk.min.js"></script>
<script type="text/javascript" src="/assets/js/s3.js"></script>
<script type="text/javascript">
function getHtml(template) {
	return template.join('\n');
}
listAlbums();
</script>
</head>
<body>


<h1>My Photo Albums App 1</h1>
<div id="app"></div>

</body>
</html>