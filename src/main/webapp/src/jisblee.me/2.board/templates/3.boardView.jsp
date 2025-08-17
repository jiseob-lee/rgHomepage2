<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 내용 조회</title>
</head>

<body>

<h3 ng-if="urlPath == 'rg' && openYn == '1'"><legend translate>board.manageOpenArticle</legend></h3>
<h3 ng-if="urlPath == 'rg' && openYn != '1'"><legend translate>board.managePrivateArticle</legend></h3>

<div style="text-align: left; margin: 10px;">
  
  <table class="notice">
    <colgroup>
      <col style="width: 80px;" />
      <col style="width: 170px;" />
      <col style="width: 80px;" />
      <col style="width: 170px;" />
      <col style="width: 80px;" />
      <col style="width: 170px;" />
    </colgroup>
    <tbody>
    <tr>
      <td colspan="6" style="border: 0px; padding: 12px; text-align: right;">
        <div style="float: left;">
          <spring:message code="board.language" text="언어" /> : 
          <input type="radio" name="language1" value="korean" id="korean1" ng-model="language1" ng-change="changeViewLanguage(1);" /><label for="korean1"><spring:message code="board.korean" text="한글" /></label> &nbsp;
          <input type="radio" name="language1" value="english" id="english1" ng-model="language1" ng-change="changeViewLanguage(1);" /><label for="english1"><spring:message code="board.english" text="영어" /></label>
        </div>
        <div style="float: right;">
          <button ng-click="goEdit()" ng-if="urlPath == 'rg'" translate>board.modify</button>
          <button ng-click="goDelete('${_csrf.parameterName}', '${_csrf.token}')" ng-if="urlPath == 'rg'" translate>board.delete</button>
          <button ng-click="goList()" translate>board.list</button>
        </div>
      </td>
    </tr>
    <tr>
      <td align="center"><legend translate>board.title</legend></td>
      <td colspan="5" align="left" ng-bind-html="subject"></td>
    </tr>
    <tr>
      <td align="center"><legend translate>board.writer</legend></td>
      <td align="left">{{userNameCreated}}</td>
      <td align="center"><legend translate>board.writeDate</legend></td>
      <td align="left">{{dateCreated}}</td>
      <td align="center" ng-if="openYn == '1'"><legend translate>board.viewCount</legend></td>
      <td align="center" ng-if="openYn == '0'"><legend translate>board.board</legend></td>
      <td align="left" ng-if="openYn == '1'">{{hitCount}}</td>
      <td align="left" ng-if="openYn == '0'">{{boardName}}</td>
    </tr>
    <tr align="center">
      <!-- <td align="center">내용</td> -->
      <td colspan="6" align="left" ng-bind-html="content" style="font-size: 13pt; padding: 1em;"></td>
    </tr>
    <tr align="center" ng-if="attachmentList.length > 0">
      <td align="center"><legend translate>board.attachmentFile</legend></td>
      <td colspan="5" align="left">
        <div ng-repeat="l in attachmentList" style="line-height: 175%;">
          <a href="/fileDownload.do?idx={{l.attachmentIdx}}&filename={{l.serverFileName}}">{{l.attachmentName}}.{{l.attachmentExt}}</a>
          ({{l.attachmentSize | number}} <span translate>board.byte</span>)
        </div>
      </td>
    </tr>
    <tr>
      <td colspan="6" style="border: 0px; padding: 12px; text-align: right;">
        <div style="float: left;">
          <spring:message code="board.language" text="언어" /> : 
          <input type="radio" name="language2" value="korean" id="korean2" ng-model="language2" ng-change="changeViewLanguage(2);" /><label for="korean2"><spring:message code="board.korean" text="한글" /></label> &nbsp;
          <input type="radio" name="language2" value="english" id="english2" ng-model="language2" ng-change="changeViewLanguage(2);" /><label for="english2"><spring:message code="board.english" text="영어" /></label>
        </div>
        <div style="float: right;">
          <button ng-click="goEdit()" ng-if="urlPath == 'rg'" translate>board.modify</button>
          <button ng-click="goDelete('${_csrf.parameterName}', '${_csrf.token}')" ng-if="urlPath == 'rg'" translate>board.delete</button>
          <button ng-click="goList()" translate>board.list</button>
        </div>
      </td>
    </tr>
    </tbody>
  </table>
  
</div>

</body>
</html>