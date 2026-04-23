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

<div class="bible" id="hev1">
<a href="#typing/hev1">창세기</a><br/>
</div>
<div class="bible" id="hev2">
<a href="#typing/hev2">출애굽기</a><br/>
</div>
<div class="bible" id="hev3">
<a href="#typing/hev3">레위기</a><br/>
</div>
<div class="bible" id="hev4">
<a href="#typing/hev4">민수기</a><br/>
</div>

<br />

<div class="bible" id="hev5">
<a href="#typing/hev5">신명기</a><br/>
</div>
<div class="bible" id="hev6">
<a href="#typing/hev6">여호수아</a><br/>
</div>
<div class="bible" id="hev7">
<a href="#typing/hev7">사사기</a><br/>
</div>
<div class="bible" id="hev8">
<a href="#typing/hev8">룻기</a><br/>
</div>

<br />

<div class="bible" id="hev9">
<a href="#typing/hev9">사무엘상</a><br/>
</div>
<div class="bible" id="hev10">
<a href="#typing/hev10">사무엘하</a><br/>
</div>
<div class="bible" id="hev11">
<a href="#typing/hev11">열왕기상</a><br/>
</div>
<div class="bible" id="hev12">
<a href="#typing/hev12">열왕기하</a><br/>
</div>

<br />

<div class="bible" id="hev13">
<a href="#typing/hev13">역대상</a><br/>
</div>
<div class="bible" id="hev14">
<a href="#typing/hev14">역대하</a><br/>
</div>
<div class="bible" id="hev15">
<a href="#typing/hev15">에스라</a><br/>
</div>
<div class="bible" id="hev16">
<a href="#typing/hev16">느헤미야</a><br/>
</div>

<br />

<div class="bible" id="hev17">
<a href="#typing/hev17">에스더</a><br/>
</div>
<div class="bible" id="hev18">
<a href="#typing/hev18">욥기</a><br/>
</div>
<div class="bible" id="hev19">
<a href="#typing/hev19">시편</a><br/>
</div>
<div class="bible" id="hev20">
<a href="#typing/hev20">잠언</a><br/>
</div>

<br />

<div class="bible" id="hev21">
<a href="#typing/hev21">전도서</a><br/>
</div>
<div class="bible" id="hev22">
<a href="#typing/hev22">아가</a><br/>
</div>
<div class="bible" id="hev23">
<a href="#typing/hev23">이사야</a><br/>
</div>
<div class="bible" id="hev24">
<a href="#typing/hev24">예레미야</a><br/>
</div>

<br />

<div class="bible" id="hev25">
<a href="#typing/hev25">예레미야애가</a><br/>
</div>
<div class="bible" id="hev26">
<a href="#typing/hev26">에스겔</a><br/>
</div>
<div class="bible" id="hev27">
<a href="#typing/hev27">다니엘</a><br/>
</div>
<div class="bible" id="hev28">
<a href="#typing/hev28">호세아</a><br/>
</div>

<br />

<div class="bible" id="hev29">
<a href="#typing/hev29">요엘</a><br/>
</div>
<div class="bible" id="hev30">
<a href="#typing/hev30">아모스</a><br/>
</div>
<div class="bible" id="hev31">
<a href="#typing/hev31">오바댜</a><br/>
</div>
<div class="bible" id="hev32">
<a href="#typing/hev32">요나</a><br/>
</div>

<br />

<div class="bible" id="hev33">
<a href="#typing/hev33">미가</a><br/>
</div>
<div class="bible" id="hev34">
<a href="#typing/hev34">나훔</a><br/>
</div>
<div class="bible" id="hev35">
<a href="#typing/hev35">하박국</a><br/>
</div>
<div class="bible" id="hev36">
<a href="#typing/hev36">스바냐</a><br/>
</div>

<br />

<div class="bible" id="hev37">
<a href="#typing/hev37">학개</a><br/>
</div>
<div class="bible" id="hev38">
<a href="#typing/hev38">스가랴</a><br/>
</div>
<div class="bible" id="hev39">
<a href="#typing/hev39">말라기</a><br/>
</div>

</div>

<input type="hidden" name="loginId" id="loginId" value="${loginId }" />


</body>
</html>