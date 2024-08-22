<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 목록</title>
</head>

<body>

<h3 ng-if="urlPath == 'rg' && openYn == '1'"><legend translate>board.manageOpenArticle</legend></h3>
<h3 ng-if="urlPath == 'rg' && openYn != '1'"><legend translate>board.managePrivateArticle</legend></h3>

<form name="frm">

  <div style="width: 750px">
  
  <div style="margin: 10px; float: left; font-size: 10pt;">
  <span translate>board.totalArticleCount</span> : {{boardListCount | number}}
  </div>
  
  <div style="margin: 10px; float: right;" ng-hide="urlPath != 'rg'">
    <span translate>board.selectBoard</span> :
    <select name="board" ng-model="boardNo" ng-change="boardChange()">
      <option ng-repeat="a in manageBoardList" value="{{a.boardIdx}}">{{a.boardName}} {{a.articleCount > 0 ? '(' + a.articleCount + ')' : ''}}</option>
    </select>
  </div>
  
  </div>
  
  <table class="notice">
    <colgroup>
      <col style="width: 60px;" />
      <col style="width: 450px;" />
      <col style="width: 120px;" />
      <col style="width: 120px;" />
    </colgroup>
    <tr align="center">
      <td><legend translate>board.no</legend></td>
      <td><legend translate>board.title2</legend></td>
      
      <td ng-if="urlPath == 'rg' && openYn == '1'"><legend translate>board.viewCount</legend></td>
      <!-- <td ng-if="urlPath == 'rg' && openYn != '1'"><legend translate>board.writer</legend></td> -->
      <td ng-if="urlPath == 'rg' && openYn != '1'"><legend translate>board.board</legend></td>
      <td ng-if="urlPath == ''"><legend translate>board.board</legend></td>
      
      <td><legend translate>board.writeDate</legend></td>
    </tr>
    <tr ng-if="boardListCount == 0">
      <td colspan="4" align="center"><legend translate>board.noArticle</legend></td>
    <tr>
    <tr ng-repeat="a in boardList">
      <td align="center">{{boardListCount - $index - (pageNo - 1) * listLimit}}</td>
      <td ng-if="searchKind == '' || searchValue == ''">
        <a href="/rg/#/board/view/{{boardNo}}/{{pageNo}}/{{a.boardArticleIdx}}/{{openYn}}" ng-if="urlPath == 'rg' && openYn != ''">{{a.subject}}</a>
        <a href="/rg/#/board/view/{{boardNo}}/{{pageNo}}/{{a.boardArticleIdx}}" ng-if="urlPath == 'rg' && openYn == ''">{{a.subject}}</a>
        <a href="/#/board/view/{{boardNo}}/{{pageNo}}/{{a.boardArticleIdx}}" ng-if="urlPath != 'rg' && openYn == ''">{{a.subject}}</a>
      </td>
      <td ng-if="searchKind != '' && searchValue != ''">
        <a href="/rg/#/board/view/{{boardNo}}/{{pageNo}}/{{a.boardArticleIdx}}/{{openYn}}/{{searchKind}}/{{searchValue}}" ng-if="urlPath == 'rg' && openYn != ''">{{a.subject}}</a>
        <a href="/rg/#/board/view/{{boardNo}}/{{pageNo}}/{{a.boardArticleIdx}}/{{searchKind}}/{{searchValue}}" ng-if="urlPath == 'rg' && openYn == ''">{{a.subject}}</a>
        <a href="/#/board/view/{{boardNo}}/{{pageNo}}/{{a.boardArticleIdx}}/{{searchKind}}/{{searchValue}}" ng-if="urlPath != 'rg' && openYn == ''">{{a.subject}}</a>
      </td>
      
      <td align="center" ng-if="urlPath == 'rg' && openYn == '1'">{{a.hitCount }}</td>
      <!-- <td align="center" ng-if="urlPath == 'rg' && openYn != '1'">{{a.userNameCreated }}</td> -->
      <td align="center" ng-if="urlPath == 'rg' && openYn != '1'">{{board.getBoardName(a.boardIdx) }}</td>
      <td align="center" ng-if="urlPath == ''">{{board.getBoardName(a.boardIdx )}}</td>
      
      <td align="center">{{a.dateCreated}}</td>
    </tr>
    <tr>
      <td colspan="4" style="border: 0px; padding: 14px; text-align: center; word-spacing: 2px;">

        <div style="float: left; width: 100px;">
        &nbsp;
        </div>
  <!--
  linkStart : {{linkStart}}<br/>
  pageLinkCount : {{pageLinkCount}}<br/>
  linkEnd : {{linkEnd}}<br/>
  -->
  <span ng-if="linkStart > pageLinkCount">&nbsp;<a href="/rg/#/board/list/{{boardNo}}/{{linkStart - pageLinkCount}}">&lt;</a></span>
  <span ng-repeat="n in [] | range:this">
    <span ng-if="n == pageNo">&nbsp;<b>{{n}}</b>&nbsp;</span>
    <span ng-if="n != pageNo">&nbsp;<a href="javascript:void 0;" ng-click="goList2(n)" ng-if="urlPath == 'rg'">{{n}}</a><a href="javascript:void 0;" ng-click="goList3(n)" ng-if="urlPath != 'rg'">{{n}}</a>&nbsp;
    </span>
  </span>
  <span ng-if="linkStart + pageLinkCount <= linkEnd">&nbsp;<a href="/rg/#/board/list/{{boardNo}}/{{linkStart + pageLinkCount}}">&gt;</a></span>
  &nbsp;

        <div style="float: right; width: 100px;" ng-if="urlPath == 'rg'">
          <button ng-click="goInput()" translate>board.newArticle</button>
        </div>
        
      </td>
    </tr>
  </table>

  <div style="text-align: center; width: 750px;">

    <%-- <select name="searchKind" id="searchKind" ng-options="data as data.name for data in searchKindOptions" ng-model="searchKind"> --%>
    <select name="searchKind" id="searchKind" ng-model="searchKind">
      
      <option value="subject">제목</option>
      <option value="content">본문</option>
      <option value="both">제목과 본문</option>
      
    </select>

    <input type="text" name="searchValue" id="searchValue" ng-model="searchValue" size="30" 
    	style="font-size: 15pt; cursor: default; background-color: gray;" 
    	ng-keydown="$event.keyCode === 13 && goSearch(searchKind, searchValue)" 
    	onkeydown="disableScrolling(); if (event.keyCode == 13) { event.preventDefault(); }"
    	onkeyup="enableScrolling();" 
    	onfocusout="enableScrolling();"
    	maxlength="200" />
    <%-- <input type="text" name="searchValue" id="searchValue" ng-model="searchValue" size="30" style="font-size: 15pt; cursor: default; background-color: gray;" maxlength="200" /> --%>

    <button type="button" ng-click="goSearch(searchKind, searchValue)" translate>board.search</button>

    <br/>&nbsp;
  </div>

</form>

  <table ng-if="urlPath == 'rg' && openYn != '1'">
    <tr ng-repeat="a in boardList">
      <td>
        <table border="1">
        <tr>
          <td>{{boardListCount - $index - (pageNo - 1) * listLimit}}</td>
          <td>{{a.subject}}</td>
          <td>{{a.dateCreated}}</td>
        </tr>
        <tr>
          <td colspan="3" ng-bind-html="a.content | unsafe"></td>
        </tr>
        </table>
      </td>
    </tr>
  </table>


<script type="text/javascript">

//if (document.frm.searchKind.value) {
	//document.frm.searchKind.value = "subject";
	//document.frm.searchKind.value = "content";
	//document.frm.searchKind.value = document.frm.searchKind.value;
//}

function disableScrolling(){
    var x=window.scrollX;
    var y=window.scrollY;
    window.onscroll=function(){window.scrollTo(x, y);};
}

function enableScrolling(){
    window.onscroll=function(){};
}
</script>


</body>
</html>