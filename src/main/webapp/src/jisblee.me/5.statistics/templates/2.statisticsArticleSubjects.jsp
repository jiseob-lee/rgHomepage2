<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

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


<h3>조회된 글 제목과 갯수</h3>

    <div style="float: left; margin-left:30px; width: 220px;">
      <h4>시작 날짜</h4>
      <md-datepicker ng-model="myStartDate" md-placeholder="Enter date" md-min-date="statistics.minDate" md-max-date="statistics.currentDate" ></md-datepicker>
    </div>

    <div style="float: left;">
      <h4>종료 날짜</h4>
      <md-datepicker ng-model="myEndDate" md-placeholder="Enter date" md-min-date="statistics.minDate" md-max-date="statistics.currentDate" ></md-datepicker>
    </div>

	<div style="clear: both; margin-left:30px; padding-top: 30px;">
		<input type="button" value="조회된 글 일람" ng-click="checkDates2('${_csrf.parameterName}', '${_csrf.token}')" />
	</div>

	<toaster-container toaster-options="{'time-out': 2000, 'position-class': 'toast-top-right'}"></toaster-container>
	
	<div style="clear: both; margin-left:30px; padding-top: 30px;">
		<table class="articleViewed">
			<tr>
				<td align="center" style="text-decoration: underline;">번호</td>
				<td align="center" style="text-decoration: underline;">글 제목</td>
				<td align="center" style="text-decoration: underline;">조회수 (한글/영어)</td>
				<td align="center" style="text-decoration: underline;">referer</td>
			</tr>
			<tr ng-repeat="article in articlesData">
				<td align="center">{{$index + 1}}</td>
				<td style="width: 550px;">{{article.subject}}</td>
				<td align="center">{{article.articleCount}} <span ng-if="article.countKo > 0 || article.countEn > 0"> &nbsp; ( {{article.countKo }} / {{article.countEn }} )</span></td>
				<td align="center">{{article.refererCount}}</td>
			</tr>
		</table>
	</div>
	
</body>
</html>