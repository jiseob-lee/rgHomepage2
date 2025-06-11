<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 목록</title>
</head>

<body>

<h3>다운로드 로그</h3>

<form name="frm">

  <div style="margin: 10px; float: left; font-size: 10pt;">
  총 로그 개수 : {{totalCount | number}}
  </div>
  
  <table class="notice">
    <colgroup>
      <col style="width: 75px;" />
      <col style="width: 125px;" />
      <col style="width: 250px;" />
      <col style="width: 225px;" />
      <col style="width: 125px;" />
      <col style="width: 100px;" />
      <col style="width: 125px;" />
      <col style="width: 150px;" />
      <col style="width: 125px;" />
    </colgroup>
    <tr align="center">
      <td>No.</td>
      <td>게시판명</td>
      <td>게시글명</td>
      <td>다운 파일명</td>
      <td>파일 크기</td>
      <td>다운 시간</td>
      <td>국가</td>
      <td>지역</td>
      <td>도시</td>
    </tr>
    <tr ng-if="totalCount == 0">
      <td colspan="8" align="center">로그가 없습니다.</td>
    <tr>
    <tr ng-repeat="dummy in downHistoryList">
      <td align="center">{{totalCount - $index - (pageNo - 1) * listLimit}}</td>
      <td align="center">{{downHistoryList[$index].board_name}}</td>
      <td align="center">{{downHistoryList[$index].subject}}</td>
      <td align="center">{{downHistoryList[$index].fileName}}</td>
      <td align="center">{{downHistoryList[$index].attachment_size | number}}</td>
      <td align="center">{{downHistoryList[$index].date_created}}</td>
      <td align="center">{{downHistoryList[$index].user_country}}</td>
      <td align="center">{{downHistoryList[$index].user_subdivision}}</td>
      <td align="center">{{downHistoryList[$index].user_city}}</td>
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
  <span ng-if="linkStart > pageLinkCount">&nbsp;<a href="#/getDownHistory/{{linkStart - pageLinkCount}}">&lt;</a></span>
  <span ng-repeat="n in [] | range:this">
    <span ng-if="n == pageNo">&nbsp;<b>{{n}}</b>&nbsp;</span>
    <span ng-if="n != pageNo">&nbsp;<a href="#/getDownHistory/{{n}}">{{n}}</a>&nbsp;
    </span>
  </span>
  <span ng-if="linkStart + pageLinkCount <= linkEnd">&nbsp;<a href="#/getDownHistory/{{linkStart + pageLinkCount}}">&gt;</a></span>
  &nbsp;
 
      </td>
    </tr>
  </table>

</form>

</body>
</html>