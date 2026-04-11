<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 목록</title>
</head>

<body>

<h3>성경 타자 연습 내역</h3>

<form name="frm">

  <div style="margin: 10px; float: left; font-size: 10pt;">
  총 연습 개수 : {{totalCount | number}}
  </div>
  
  <table class="notice">
    <colgroup>
      <col style="width: 70px;" />
      <col style="width: 200px;" />
      <col style="width: 80px;" />
      <col style="width: 130px;" />
      <col style="width: 90x;" />
      <col style="width: 170px;" />
    </colgroup>
    <tr align="center">
      <td>번호</td>
      <td>성경 구절</td>
      <td>글자수</td>
      <td>소요 시간</td>
      <td>평균 타자수</td>
      <td>완료 시간</td>
    </tr>
    <tr ng-if="totalCount == 0">
      <td colspan="6" align="center">타자 연습 내역이 없습니다.</td>
    <tr>
    <tr ng-repeat="dummy in list2">
      <td align="center">{{totalCount - $index - (pageNo - 1) * listLimit | number }}</td>
      <td align="center">{{list2[$index].title }}</td>
      <td align="center">{{list2[$index].charCount | number }} 자</td>
      <td align="center">{{list2[$index].timeElaspedString }}</td>
      <td align="center">분당 {{list2[$index].average | number }} 자</td>
      <td align="center">{{list2[$index].timeCreated }}</td>
    </tr>
    <tr>
      <td colspan="6" style="border: 0px; padding: 14px; text-align: center; word-spacing: 2px;">

  <!--
  linkStart : {{linkStart}}<br/>
  pageLinkCount : {{pageLinkCount}}<br/>
  linkEnd : {{linkEnd}}<br/>
  -->
  <span ng-if="linkStart > pageLinkCount">&nbsp;<a href="#/typingHistory/{{linkStart - pageLinkCount}}">&lt;</a></span>
  <span ng-repeat="n in [] | range:this">
    <span ng-if="n == pageNo">&nbsp;<b>{{n}}</b>&nbsp;</span>
    <span ng-if="n != pageNo">&nbsp;<a href="#/typingHistory/{{n}}">{{n}}</a>&nbsp;
    </span>
  </span>
  <span ng-if="linkStart + pageLinkCount <= linkEnd">&nbsp;<a href="#/typingHistory/{{linkStart + pageLinkCount}}">&gt;</a></span>
  &nbsp;
 
      </td>
    </tr>
  </table>

  <input type="hidden" name="csrfToken" id="csrfToken" value="${_csrf.token }" />
  <input type="hidden" name="loginId" id="loginId" value="${loginId }" />

</form>

</body>
</html>