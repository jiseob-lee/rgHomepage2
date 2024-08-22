<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style type="text/css">

md-datepicker {
  background: white;
}

</style>
</head>
<body>


<h3>Access 로그 분석 날짜 설정</h3>

    <div style="float: left; margin-left:30px; width: 220px;">
      <h4>시작 날짜</h4>
      <md-datepicker ng-model="myStartDate" md-placeholder="Enter date" md-min-date="access.minDate" md-max-date="access.currentDate" ></md-datepicker>
    </div>

    <div style="float: left;">
      <h4>종료 날짜</h4>
      <md-datepicker ng-model="myEndDate" md-placeholder="Enter date" md-min-date="access.minDate" md-max-date="access.currentDate" ></md-datepicker>
    </div>

	<div style="clear: both; margin-left:30px; padding-top: 30px;">
		<input type="button" value="Access 로그 분석 시작" ng-click="checkDates('${_csrf.parameterName}', '${_csrf.token}')" />
	</div>

	<div style="clear: both; margin-left:30px; padding-top: 30px;">
		<input type="button" value="분석 파일 일람" ng-click="viewParsedFile()" />
	</div>

	<toaster-container toaster-options="{'time-out': 2000, 'position-class': 'toast-top-right'}"></toaster-container>

	<div style="clear: both; margin-left:30px; padding-top: 30px;">
		<iframe name="goaccessFrame" id="goaccessFrame"></iframe>
	</div>
	
</body>
</html>