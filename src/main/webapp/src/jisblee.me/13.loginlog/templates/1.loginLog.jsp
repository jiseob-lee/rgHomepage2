<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 목록</title>

<style type="text/css">
td {
  font-size: 10pt !important;
}
</style>
</head>

<body>

<h3>로그인 로그</h3>

<form name="frm">

  <div style="margin: 10px; float: left; font-size: 10pt;">
  총 로그 개수 : {{totalCount | number}}
  </div>
  
  <table class="notice">
    <colgroup>
      <col style="width: 65px;" />
      <col style="width: 100px;" />
      <col style="width: 90px;" />
      <col style="width: 190px;" />
      <col style="width: 115px;" />
      <col style="width: 115px;" />
      <col style="width: 140px;" />
      <col style="width: 115px;" />
    </colgroup>
    <tr align="center">
      <td>No.</td>
      <td>로그인 아이디</td>
      <td>로그인 결과</td>
      <td>로그인 시간</td>
      <td>아이피</td>
      <td>국가</td>
      <td>지역</td>
      <td>도시</td>
    </tr>
    <tr ng-if="totalCount == 0">
      <td colspan="8" align="center">로그가 없습니다.</td>
    <tr>
    <tr ng-repeat="dummy in loginLogList">
      <td align="center">{{totalCount - $index - (pageNo - 1) * listLimit}}</td>
      <td align="center">{{loginLogList[$index].loginId}}</td>
      <td align="center">{{loginLogList[$index].loginResult}}</td>
      <td align="center">{{loginLogList[$index].loginTime}}</td>
      <td align="center">{{loginLogList[$index].ip}}</td>
      <td align="center">{{loginLogList[$index].country}}</td>
      <td align="center">{{loginLogList[$index].subdivision}}</td>
      <td align="center">{{loginLogList[$index].city}}</td>
    </tr>
    <tr>
      <td colspan="8" style="border: 0px; padding: 14px; text-align: center; word-spacing: 2px;">

        <div style="float: left; width: 100px;">
        &nbsp;
        </div>
  <!--
  linkStart : {{linkStart}}<br/>
  pageLinkCount : {{pageLinkCount}}<br/>
  linkEnd : {{linkEnd}}<br/>
  -->
  <span ng-if="linkStart > pageLinkCount">&nbsp;<a href="#/getLoginLog/{{linkStart - pageLinkCount}}">&lt;</a></span>
  <span ng-repeat="n in [] | range:this">
    <span ng-if="n == pageNo">&nbsp;<b>{{n}}</b>&nbsp;</span>
    <span ng-if="n != pageNo">&nbsp;<a href="#/getLoginLog/{{n}}">{{n}}</a>&nbsp;
    </span>
  </span>
  <span ng-if="linkStart + pageLinkCount <= linkEnd">&nbsp;<a href="#/getLoginLog/{{linkStart + pageLinkCount}}">&gt;</a></span>
  &nbsp;
 
      </td>
    </tr>
  </table>

</form>

</body>
</html>