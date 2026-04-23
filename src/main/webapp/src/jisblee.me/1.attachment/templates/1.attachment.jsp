<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>첨부파일</title>
</head>

<body ng-app="rg">

<h3>첨부 파일</h3>

<form name="frm">

  <div style="margin: 10px; float: left;">
  총 첨부파일 수 : {{attachmentListCount | number}}
  </div>
  
  <div style="margin: 10px; float: right;">
    게시판 선택 :
    <select name="board" ng-model="boardNo" ng-change="boardChange()">
      <option ng-repeat="a in manageBoardList" value="{{a.boardIdx}}">{{a.boardName}} {{a.boardIdx > 0 ? '(' + a.attachmentCount + ')' : ''}}</option>
    </select>
  </div>
  
  <table class="notice">
    <colgroup>
      <col style="width: 60px;" />
      <col style="width: 490px;" />
      <col style="width: 90px;" />
      <col style="width: 110px;" />
    </colgroup>
    <tr align="center">
      <td>번호</td>
      <td>제 목</td>
      <td>글쓴이</td>
      <td>작성일</td>
    </tr>
    <tr ng-if="attachmentListCount == 0">
      <td colspan="4" align="center">첨부파일이 없습니다.</td>
    <tr>
    <tr ng-repeat="a in attachmentList" id="tr_{{a.attachmentIdx}}">
      <td align="center">{{attachmentListCount - $index - (pageNo - 1) * listLimit}}</td>
      <td class="breakWord">
        <a href="/fileDownload.do?idx={{a.attachmentIdx}}&filename={{a.serverFileName}}">{{a.attachmentName}}.{{a.attachmentExt}}</a>
        ({{a.attachmentSize | number}} bytes)
        <span ng-if="a.fileExists == 0" style="color: red;">x</span>
        <input type='button' value='삭제' ng-click="delAttachment(-1, a.attachmentIdx, a.serverFileName, a.attachmentExt)" />
        <input type="button" value="매핑" ng-click="showAdvanced($event, a.attachmentIdx, a.attachmentName, a.attachmentExt)" />
      </td>
      <td align="center">{{a.userNameCreated}}</td>
      <td align="center">{{a.dateCreated}}</td>
    </tr>
    <tr>
      <td colspan="4" style="border: 0px; padding: 22px; text-align: center;">


  <!--
  linkStart : {{linkStart}}<br/>
  pageLinkCount : {{pageLinkCount}}<br/>
  linkEnd : {{linkEnd}}<br/>
  -->
  <span ng-if="linkStart > pageLinkCount">&nbsp;<a href="/rg/#/attachment/{{boardNo}}/{{linkStart - pageLinkCount}}">&lt;</a></span>
  <span ng-repeat="n in [] | range:this">
    <span ng-if="n == pageNo"><b>&nbsp;{{n}}</b> &nbsp;</span>
    <span ng-if="n != pageNo">&nbsp;<a href="/rg/#/attachment/{{boardNo}}/{{n}}">{{n}}</a> &nbsp;</span>
  </span>
  <span ng-if="linkStart + pageLinkCount <= linkEnd">&nbsp;<a href="/rg/#/attachment/{{boardNo}}/{{linkStart + pageLinkCount}}">&gt;</a></span>
  &nbsp;

      </td>
    </tr>
  </table>
  

</form>

<div style="float: left;">
<form name="frmFile" method="post" enctype="multipart/form-data">
  <table id="tableFile">
    <tr>
      <td align="left" style="text-align: left;">

        <input name="inputFile" ng-model="inputFile" file-change="uploadFile($event, files)" type="file" />
        
        <!-- <input name="inputFile" ng-model="inputFile" onchange="angular.element(this).scope().uploadFile(files)" type="file" /> -->
    
        <div ng-controller="AppCtrl as vm" ng-switch on="$parent.progress">
          <div layout="row" layout-sm="column" layout-align="space-around" ng-switch-when="true">
            <md-progress-circular md-mode="indeterminate"></md-progress-circular>
          </div>
        </div>
    
      </td>
    </tr>
  </table>
</form>
</div>

<div style="float: right;">
<input type="button" value="파일 존재 여부 검사" ng-click="checkFileExists()" />
</div>

<br/><br/><br/>

<input type="button" value="download files" ng-click="downloadFileMultiple()" />

<%=
session.getAttribute("123")
%>

</body>
</html>