<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그 조회</title>
<style type="text/css">
table.logViewed {
  table-layout: fixed;
  border-collapse: collapse;
  width: 850px;
  clear: both;
}

table.logViewed tbody:nth-child(odd) { background: #3a3737; }

table.logViewed tbody:nth-child(even) { background: #2a2626; }

table.logViewed td {
  word-wrap: break-word;
  white-space: normal;
}


md-datepicker {
  background: white;
}

</style>
</head>

<body>

<h3>조회 글 로그</h3>

    <div style="float: left; margin-left:30px; width: 220px;">
      <h4>날짜 선택</h4>
      <md-datepicker ng-model="myDate" md-placeholder="Enter date" md-min-date="statistics.minDate" md-max-date="statistics.currentDate" ></md-datepicker>
    </div>

	<div style="clear: both; margin-left:30px; padding-top: 30px;">
		<input type="button" value="로그 일람" ng-click="checkDates3()" />
	</div>

	<div style="clear: both; font-size: 14px;">
		<table class="logViewed">
			<thead>
				<tr align="center">
					<td rowspan="2" style="width:60px;">no.</td>
					<td rowspan="2" style="width:285px;">User Agent</td>
					<td style="width:120px;">IP</td>
					<td style="width:170px;">Path</td>
					<td rowspan="2" style="width:180px;">Title</td>
					<td style="width:90px;">Country<br/>subdivision</td>
				</tr>
				<tr align="center">
					<td>Time</td>
					<td>Referer</td>
					<td>City<br/>Locale</td>
				</tr>
			</thead>
			<tbody ng-repeat="log in logData">
				<tr align="center">
					<td rowspan="2">{{log.logIndex}}</td>
					<td rowspan="2" align="left">{{log.userAgent}}</td>
					<td>{{log.ip}}</td>
					<td align="left">{{log.path}}</td>
					<td rowspan="2" align="left">{{log.subject}}</td>
					<td>{{log.country}}<br/>{{log.subdivision}}</td>
				</tr>
				<tr align="center">
					<td>{{log.logTime}}</td>
					<td align="left">{{log.referer}}</td>
					<td>{{log.city}}<br/>{{log.language}}</td>
				</tr>
			</tbody>
		</table>
	</div>
	
</body>
</html>