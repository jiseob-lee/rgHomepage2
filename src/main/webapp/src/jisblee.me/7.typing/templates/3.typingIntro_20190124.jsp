<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>타자 연습</title>

<style type="text/css">

select.bibleSelect, select.bibleSelect > option, input[type=button] {
  font-size: 14pt;
}


.bible {
  float: left;
  width: 150px;
  height: 150px;
  border-radius: 10px;
  border-style: solid;
  border-width: 1px;
  border-color: gray;
  padding: 16px;
  margin: 9px;
  text-align: center;
  font-size: 20pt;
  
  white-space: normal;
  overflow-wrap: break-word;
}

.innerTable {
  border-spacing: 0;
  border-collapse: collapse;
  width: 140px;
  margin: 3px;
}

.inner1 {
  background-color: #0594CE;
  border: 0px;
  height: 17px;
  padding: 0px;
}

.inner2 {
  background-color: white;
  border: 0px;
  height: 17px;
  padding: 0px;
}

.inner3 {
  clear: both;
  font-size: 15pt;
}

.inner4 {
  clear: both;
  font-size: 14pt;
}

.hundred {
  background-color: #053c4f;
}

.zero {
  background-color: #4e4949;
}

.partial {
  /* background-color: #bde8f6; */
  background-color: #053c4f;
  /* background-color: #6f6e53; */
}

.doing {
  /* background-color: #bde8f6; */
  /* background-color: #053c4f; */
  background-color: #6f6e53;
}
</style>

</head>

<body>

<div style="margin-top: 10px;">

  <select ng-model="bibleName" ng-change="bibleSelect()" class="bibleSelect">
    <option ng-repeat="i in list" ng-value="i.bibleName">{{i.bibleName }} ({{i.typedChaptersCount == null ? 0 : i.typedChaptersCount }}/{{i.bibleChapters }})</option>
  </select>
  
  <select ng-model="bibleChapters" class="bibleSelect">
    <option ng-repeat="i in bibleChaptersList.$$state.value" ng-value="i.index">{{i.string}}</option>
  </select>
  
  <input type="button" value="시작" ng-click="startTyping()" />
  
</div>

<br/>

<div style="width: 820px;">

<div class="bible" id="hev40">
<a href="#typing/hev40">마태복음</a><br/>
</div>
<div class="bible" id="hev41">
<a href="#typing/hev41">마가복음</a><br/>
</div>
<div class="bible" id="hev42">
<a href="#typing/hev42">누가복음</a><br/>
</div>
<div class="bible" id="hev43">
<a href="#typing/hev43">요한복음</a><br/>
</div>

<br />

<div class="bible" id="hev44">
<a href="#typing/hev44">사도행전</a><br/>
</div>
<div class="bible" id="hev45">
<a href="#typing/hev45">로마서</a><br/>
</div>
<div class="bible" id="hev46">
<a href="#typing/hev46">고린도전서</a><br/>
</div>
<div class="bible" id="hev47">
<a href="#typing/hev47">고린도후서</a><br/>
</div>

<br />

<div class="bible" id="hev48">
<a href="#typing/hev48">갈라디아서</a><br/>
</div>
<div class="bible" id="hev49">
<a href="#typing/hev49">에베소서</a><br/>
</div>
<div class="bible" id="hev50">
<a href="#typing/hev50">빌립보서</a><br/>
</div>
<div class="bible" id="hev51">
<a href="#typing/hev51">골로새서</a><br/>
</div>

<br />

<div class="bible" id="hev52">
<a href="#typing/hev52">데살로니가전서</a><br/>
</div>
<div class="bible" id="hev53">
<a href="#typing/hev53">데살로니가후서</a><br/>
</div>
<div class="bible" id="hev54">
<a href="#typing/hev54">디모데전서</a><br/>
</div>
<div class="bible" id="hev55">
<a href="#typing/hev55">디모데후서</a><br/>
</div>

<br />

<div class="bible" id="hev56">
<a href="#typing/hev56">디도서</a><br/>
</div>
<div class="bible" id="hev57">
<a href="#typing/hev57">빌레몬서</a><br/>
</div>
<div class="bible" id="hev58">
<a href="#typing/hev58">히브리서</a><br/>
</div>
<div class="bible" id="hev59">
<a href="#typing/hev59">야고보서</a><br/>
</div>

<br />

<div class="bible" id="hev60">
<a href="#typing/hev60">베드로전서</a><br/>
</div>
<div class="bible" id="hev61">
<a href="#typing/hev61">베드로후서</a><br/>
</div>
<div class="bible" id="hev62">
<a href="#typing/hev62">요한1서</a><br/>
</div>
<div class="bible" id="hev63">
<a href="#typing/hev63">요한2서</a><br/>
</div>

<br />

<div class="bible" id="hev64">
<a href="#typing/hev64">요한3서</a><br/>
</div>
<div class="bible" id="hev65">
<a href="#typing/hev65">유다서</a><br/>
</div>
<div class="bible" id="hev66">
<a href="#typing/hev66">요한계시록</a><br/>
</div>

</div>

<input type="hidden" name="csrfToken" id="csrfToken" value="${_csrf.token }" />
<input type="hidden" name="loginId" id="loginId" value="${loginId }" />


</body>
</html>