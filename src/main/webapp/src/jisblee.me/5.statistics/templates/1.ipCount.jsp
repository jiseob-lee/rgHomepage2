<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 목록</title>
</head>

<body>

<h3>글 조회 수</h3>

<form name="frm">

  <div style="margin: 10px; float: left; font-size: 10pt;">
  총 날짜 개수 : {{totalCount | number}}
  </div>
  
  <table class="notice">
    <colgroup>
      <col style="width: 75px;" />
      <col style="width: 125px;" />
      <col style="width: 150px;" />
      <col style="width: 25px;" />
      <col style="width: 75px;" />
      <col style="width: 125px;" />
      <col style="width: 150px;" />
      <col style="width: 25px;" />
    </colgroup>
    <tr align="center">
      <td>번호</td>
      <td>날짜</td>
      <td>IP/Total Count</td>
      <td>&nbsp;</td>
      <td>번호</td>
      <td>날짜</td>
      <td>IP/Total Count</td>
      <td>&nbsp;</td>
    </tr>
    <tr ng-if="totalCount == 0">
      <td colspan="8" align="center">로그가 없습니다.</td>
    <tr>
    <tr ng-repeat="dummy in ipCountList">
      <td align="center" ng-if="$index < 10">{{totalCount - $index - (pageNo - 1) * listLimit}}</td>
      <td align="center" ng-if="$index < 10">{{ipCountList[$index].date}}</td>
      <td align="center" ng-if="$index < 10">{{ipCountList[$index].ipCount}} / {{ipCountList[$index].totalCount}}</td>
      <td ng-if="$index < 10">&nbsp;</td>
      <td align="center" ng-if="$index < 10">{{ipCountList.length > $index + 10 ? totalCount - $index - (pageNo - 1) * listLimit - 10 : ''}}</td>
      <td align="center" ng-if="$index < 10">{{ipCountList[$index + 10].date}}</td>
      <td align="center" ng-if="$index < 10">{{ipCountList[$index + 10].ipCount}}{{ipCountList.length > $index + 10 ? ' / ' : ''}}{{ipCountList[$index + 10].totalCount}}</td>
      <td ng-if="$index < 10">&nbsp;</td>
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
  <span ng-if="linkStart > pageLinkCount">&nbsp;<a href="#/getIpCount/{{linkStart - pageLinkCount}}">&lt;</a></span>
  <span ng-repeat="n in [] | range:this">
    <span ng-if="n == pageNo">&nbsp;<b>{{n}}</b>&nbsp;</span>
    <span ng-if="n != pageNo">&nbsp;<a href="#/getIpCount/{{n}}">{{n}}</a>&nbsp;
    </span>
  </span>
  <span ng-if="linkStart + pageLinkCount <= linkEnd">&nbsp;<a href="#/getIpCount/{{linkStart + pageLinkCount}}">&gt;</a></span>
  &nbsp;
 
      </td>
    </tr>
  </table>

</form>

</body>
</html>