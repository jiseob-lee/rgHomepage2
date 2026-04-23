<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시판 관리</title>
</head>

<body ng-app="rg">

<h3 ng-if="urlPath == 'rg' && openYn == '1'">공개 게시글 관리</h3>
<h3 ng-if="urlPath == 'rg' && openYn != '1'">게시글 관리</h3>

  <table>
    <tr>
      <td>제목</td>
      <td colspan="3"><input type="text" name="subject" id="subject" style="width: 740px;" ng-model="subject" />
      <br/><input type="text" name="subjectEng" id="subjectEng" style="width: 740px;" ng-model="subjectEng" />
      </td>
    </tr>
    <tr>
      <td style="padding-top: 10px; padding-bottom: 10px;" ng-if="action == 'edit'">수정자</td>
      <td style="padding-top: 10px; padding-bottom: 10px;" ng-if="action == 'input'">등록자</td>
      <td style="padding-top: 10px; padding-bottom: 10px;" width="300">{{loginUserName}}</td>
      <td style="padding-top: 10px; padding-bottom: 10px;" width="70">게시판</td>
      <td style="padding-top: 10px; padding-bottom: 10px;">
        <select name="boardSelect" ng-model="boardIdx">
          <option ng-repeat="a in manageBoardList" value="{{a.boardIdx}}" ng-selected="boardIdx == a.boardIdx">{{a.boardName}} {{a.articleCount > 0 ? '(' + a.articleCount + ')' : ''}}</option>
        </select>
      </td>
    </tr>
    <tr>
      <td>본문</td>
      <td colspan="3">
        
        <textarea name="content" id="content" style="width: 750px; height: 540px;" ui-tinymce="tinymceOptions" ng-model="tinymceModel"></textarea>
        
        <!-- <textarea name="ir1" id="ir1" rows="30" cols="110"></textarea> -->
            	
      </td>
    </tr>
    <tr style="height: 5px;">
      <td></td>
    </tr>
    <tr>
      <td>공개 여부</td>
      <td colspan="3">
        <input type="radio" name="openYn" id="openYnY" value="y" ng-model="openYn" /><label for="openYnY">공개함</label> &nbsp;
        <input type="radio" name="openYn" id="openYnN" value="n" ng-model="openYn" /><label for="openYnN">공개안함</label>
      </td>
    </tr>
    <tr style="height: 5px;">
      <td></td>
    </tr>
    <tr>
      <td>언어</td>
      <td colspan="3">
        <input type="radio" name="eng" id="engN" value="n" ng-model="eng" ng-change="changeLanguage();" /><label for="engN">한글</label> &nbsp;
        <input type="radio" name="eng" id="engY" value="y" ng-model="eng" ng-change="changeLanguage();" /><label for="engY">영어</label>
        
        <!-- <input type="button" value="영문으로 국문 내용 복사" ng-click="copyKorToEng();" style="margin-left: 50px;" /> -->
      </td>
    </tr>
    <tr style="height: 5px;">
      <td></td>
    </tr>
    <tr>
      <td>첨부파일</td>
      <td colspan="3">

      
<form name="frmFile" method="post" enctype="multipart/form-data">
  <table id="tableFile">
    <tr>
      <td>
        <input name="inputFile" ng-model="inputFile" file-change="uploadFile($event, files)" type="file" />
      
        <div ng-controller="AppCtrl as vm" ng-switch on="$parent.progress">
          <div layout="row" layout-sm="column" layout-align="space-around" ng-switch-when="true">
            <md-progress-circular md-mode="indeterminate"></md-progress-circular>
          </div>
        </div>
    
      </td>
    </tr>

    <tr data-ng-repeat="(attachmentIndex, attachment) in attachmentList">
      <td>
        <input type='hidden' id='attachmentIdx_{{attachment.attachmentIdx}}' name='attachmentIdx' value='{{attachment.attachmentIdx}}' />
        <a href="/fileDownload.do?idx={{attachment.attachmentIdx}}&filename={{attachment.serverFileName}}">{{attachment.attachmentName}}.{{attachment.attachmentExt}}</a>
        ({{attachment.attachmentSize | number}} byte)
        <input type='button' value='삭제' data-ng-click="delAttachment(attachmentIndex, attachment.attachmentIdx, attachment.serverFileName, attachment.attachmentExt)" />
      </td>
    </tr>

  </table>
</form>
      
      
      </td>
    </tr>
    <tr>
      <td colspan="4" align="right">
        <input type="button" value="수정" ng-click="updateBoard()" ng-if="action == 'edit'" />
        <input type="button" value="등록" ng-click="inputBoard()" ng-if="action == 'input'" />
        <input type="button" value="삭제" ng-click="goDelete()" ng-if="action == 'edit'" />
        <input type="button" value="목록" ng-click="goList()" ng-if="boardName != '채용안내'" />
      </td>
    </tr>
  </table>

</body>
</html>