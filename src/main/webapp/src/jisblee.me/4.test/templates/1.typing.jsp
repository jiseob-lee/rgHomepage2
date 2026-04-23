<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>타자 연습</title>

<style type="text/css">

ul.title {
  margin-top: 13px;
  margin-bottom: 5px;
  /*
  margin-block-start: 0em;
  margin-block-end: 0em;
  */
  padding-inline-start: 0px;
  padding: 0px;
}

ul.title li {
  display: inline-block;
  margin: 10px;
  padding: 0px;
  width: 125px;
  font-size: 15pt;
}

input[type=text]:focus {
  background-color: #FFFF99;
}

input[type=text] {
  border-radius: 5px;
  padding-left: 3px;
  padding-right: 3px;
  padding-top: 3px;
  padding-bottom: 3px;
}

.dialogdemoBasicUsage #popupContainer {
  position: relative;
}

.dialogdemoBasicUsage .footer {
  width: 100%;
  text-align: center;
  margin-left: 20px;
}

.dialogdemoBasicUsage .footer, .dialogdemoBasicUsage .footer > code {
  font-size: 0.8em;
  margin-top: 50px;
}

.dialogdemoBasicUsage button {
  width: 200px;
}

.dialogdemoBasicUsage div#status {
  color: #c60008; 
}

.dialogdemoBasicUsage .dialog-demo-prerendered md-checkbox {
  margin-bottom: 0; 
}

select.bibleSelect, select.bibleSelect > option, input[type=button] {
  font-size: 15pt;
}

.typing1 {
  font-family: 문체부 제목 바탕체;
  font-size: 16pt;
}

.typing2 {
  font-family: 문체부 제목 바탕체;
  font-size: 16pt;
  margin-left: -4px;
}

/*
input[type=text]::-ms-clear {
  display: none;
}
*/

</style>

</head>
<body>

<!--
<h3>타자 연습</h3>
-->

<!-- <div ng-controller="TestCtrl"> -->

<!--
<div>
  <input ng-model="expr" type="text" placeholder="Enter an expression" />
  <h2>{{parsedValue}}</h2>
</div>
-->

<div style="margin-top: 10px;">

  <select ng-model="bibleName" ng-change="bibleSelect()" class="bibleSelect">
    <option ng-repeat="i in list" ng-value="i.bibleName">{{i.bibleName}}</option>
  </select>
  
  <select ng-model="bibleChapters" class="bibleSelect">
    <option ng-repeat="i in bibleChaptersList" ng-value="i">{{i}}장</option>
  </select>
  
  <input type="button" value="시작" ng-click="startTyping()" />
  
  <!--
  <ul class="title">
    <li ng-repeat="i in list"><a href="javascript:void(0);" ng-click="getContent(i)">{{i}}</a></li>
  </ul>
  -->
  
  <span style="font-size: 13pt;" id="typingMessage"></span>
  
</div>

<br/>

<div id="sourceDiv" style="font-size: 20pt;"></div>

<!--
<div>
  <div id="sourceDiv" style="font-size: 25pt;"></div>
  <input type="hidden" id="sourceHidden" value="가나다라 마바사아자차카 타파하" />
  <input id="typing" ng-model="typing" type="text" ng-click="checkTyping" style="font-size: 25pt;" />
</div>
-->

  <div style="visibility: hidden">
    <div class="md-dialog-container" id="myDialog">
      <md-dialog layout-padding>
        <h2>Pre-Rendered Dialog</h2>
        <p>
          This is a pre-rendered dialog, which means that <code>$mdDialog</code> doesn't compile its
          template on each opening.
          <br/><br/>
          The Dialog Element is a static element in the DOM, which is just visually hidden.<br/>
          Once the dialog opens, we just fetch the element from the DOM into our dialog and upon close
          we restore the element back into its old DOM position.
        </p>
      </md-dialog>
    </div>
  </div>

<input type="hidden" name="loginId" id="loginId" value="${loginId }" />

&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>
&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>
&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>
&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>
&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>
&nbsp;

</body>
</html>