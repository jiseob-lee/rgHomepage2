<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Endpoint list</title>
</head>
<body>

	<div>
		<table>
			<thead>
				<tr>
					<th>path</th>
					<th>methods</th>
					<th>consumes</th>
					<th>produces</th>
					<th>params</th>
					<th>headers</th>
					<th>custom</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${map}" var="obj">
					<tr>
						<td>${obj}</td>
						<td>${obj.key.patternsCondition}</td>
						<td>${obj.value}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>


	<div>
		<h2>endPointsList</h2>
		<c:forEach items="${endPointsList}" var="obj">
			${obj}<br/>
		</c:forEach>
	</div>



</body>
</html>