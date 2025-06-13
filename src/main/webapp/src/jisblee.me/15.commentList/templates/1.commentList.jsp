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

<h3>댓글 목록</h3>

<form name="frm">

  <div style="margin: 10px; float: left; font-size: 10pt;">
  총 댓글 개수 : {{totalCount | number}}
  </div>
  
  <table class="notice">
    <colgroup>
      <col style="width: 65px;" />
      <col style="width: 235px;" />
      <col style="width: 325px;" />
      <col style="width: 115px;" />
      <col style="width: 115px;" />
    </colgroup>
    <tr align="center">
      <td>No.</td>
      <td>게시글명</td>
      <td>댓글 내용</td>
      <td>사용자명</td>
      <td>작성일</td>
    </tr>
    <tr ng-if="totalCount == 0">
      <td colspan="8" align="center">댓글이 없습니다.</td>
    <tr>
    <tr ng-repeat="dummy in commentList">
      <td align="center">{{totalCount - $index - (pageNo - 1) * listLimit}}</td>
      <td align="center">{{commentList[$index].boardArticleSubject}}</td>
      <td align="center">{{commentList[$index].content}}</td>
      <td align="center">{{commentList[$index].userName}}</td>
      <td align="center">{{commentList[$index].dateCreated}}</td>
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