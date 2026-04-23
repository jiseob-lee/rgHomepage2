<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 관리</title>
</head>
<body>

<h3>게시판 관리</h3>

<div style="margin: 10px;">
총 게시판 수 : {{manageBoardListCount - 1 | number}}
</div>

  <form name="frm2" method="post" action="/rg/editBoardName.do">
    <input type="hidden" name="boardIdx" value="" />
    <input type="hidden" name="boardName" value="" />
  
  
  <table class="notice">
    <colgroup>
      <col style="width: 60px;" />
      <col style="width: 290px;" />
      <col style="width: 100px;" />
      <col style="width: 100px;" />
      <col style="width: 100px;" />
      <col style="width: 100px;" />
    </colgroup>
    <tr align="center">
      <td>번호</td>
      <td>게시판명 (게시글/첨부파일 수)</td>
      <td>생성자</td>
      <td>생성일</td>
      <td>수정자</td>
      <td>수정일</td>
    </tr>
    <tr ng-if="manageBoardListCount == 0">
      <td colspan="6" align="center">게시판이 없습니다.</td>
    <tr>
    <tr ng-repeat="a in manageBoardList | filter : greaterThan('boardIdx', 0)">
      <td align="center">{{manageBoardListCount - $index - 1}}</td>
      <td class="breakWord">
      
      <span id="a_{{a.boardIdx}}"><a href="/rg/#/board/list/{{a.boardIdx}}/1">{{a.boardName}}</a> ({{a.articleCount | number}}, {{a.attachmentCount | number}})</span>
      <span id="e_{{a.boardIdx}}" style="display:none;"><input type="text" id="boardNewName_{{a.boardIdx}}" name="boardNewName" /></span>
      <input type="button" value="수정" ng-click="editTitle(a.boardIdx, a.boardName);" />
      
      </td>
      <td align="center">{{a.userNameCreated}}</td>
      <td align="center">{{a.dateCreated}}</td>
      <td align="center">{{a.userNameModified}}</td>
      <td align="center">{{a.dateModified}}</td>
    </tr>
  </table>
  
  </form>

<div style="margin: 10px;">
<form name="frm" action="" method="post">
  게시판 명 : 
  <input type="text" name="boardName" ng-model="boardName" />
  <input type="button" value="게시판 생성" ng-click="createBoard()" />
</form>
</div>

</body>
</html>