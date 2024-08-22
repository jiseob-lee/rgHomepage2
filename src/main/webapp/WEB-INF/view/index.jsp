<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html 
<c:if test="${currentLocale eq 'ko' }">
lang="ko"
</c:if>
<c:if test="${currentLocale eq 'en' }">
lang="en"
</c:if>
>
<head>

<meta charset="UTF-8">

<link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png">
<link rel="manifest" href="/manifest.json">
<link rel="mask-icon" href="/safari-pinned-tab.svg" color="#5bbad5">

<meta name="theme-color" content="#ffffff">

<title><spring:message code="header.headTitle" text="이지섭의 홈페이지 입니다." /></title>

<link rel="stylesheet" type="text/css" href="/assets/css/backgroundPartialFill.css" />
<link rel="stylesheet" type="text/css" href="/assets/css/board_20200907.css" />

<style type="text/css">

@font-face {
	font-family: 'NotoKrR';
	font-style: normal;
	font-weight: 300;
	src: url('/assets/notosanskr_webfont/notokr-regular.eot');
	src: url('/assets/notosanskr_webfont/notokr-regular.eot?#iefix') format('embedded-opentype'),
		 url('/assets/notosanskr_webfont/notokr-regular.woff2') format('woff2'),
		 url('/assets/notosanskr_webfont/notokr-regular.woff') format('woff');
}

html, body {color:#666; font:14px/120% 'NotoKrR','dotum','Gulim',sans-serif,Arial,Helvetica,Clean,AppleGothic; min-width:1100px; font-weight:100;}
/*
body {
<c:if test="${currentLocale eq 'ko' }">
  font-family: 굴림, Sans-serif, Arial;
</c:if>
<c:if test="${currentLocale eq 'en' }">
  font-family: Sans-serif, Arial;
</c:if>
}
*/
</style>

<script type="text/javascript">
//alert(location.hostname);
//if (location.hostname.substr(0, 4).toLowerCase() != "www.") {
	//location.href = location.protocol + "//" + "www." + location.hostname + location.href.substr(location.href.indexOf(location.hostname) + location.hostname.length);
//}
</script>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

</head>

<body>

<!--h1>jisblee.me</h1-->

<div class="main">


	<jsp:include page="header.jsp" />
	

	<img src="https://jisblee.me/assets/images/DSC00080_03.jpg" style="border-radius: 20px;"/>
	
	
	
	
	<div class="content">



	  <div style="margin: 10px; float: left; font-size: 10pt;">
		<spring:message code="board.totalArticleCount" text="총 게시글 수" /> :
		<fmt:formatNumber value="${boardListCount }" pattern="#,###" />
	  </div>
	  

	  <table class="board">
	    <colgroup>
	      <col style="width: 60px;" />
	      <col style="width: 450px;" />
	      <col style="width: 120px;" />
	      <col style="width: 110px;" />
	      <col style="width: 110px;" />
	    </colgroup>
	    <tr align="center">
	      <td><spring:message code="board.no" text="번호" /></td>
	      <td><spring:message code="board.title2" text="제 목" /></td>
	      <td><spring:message code="board.board" text="게시판" /></td>
	      <td><spring:message code="board.writeDate" text="작성일" /></td>
	      <td><spring:message code="board.updateDate" text="수정일" /></td>
	    </tr>
	    
	    <c:if test="${boardListCount == 0 }">
		    <tr>
		      <td colspan="5" align="center"><spring:message code="board.noArticle" text="게시글이 없습니다." /></td>
		    </tr>
	    </c:if>
	    
	    
	    
	    <c:forEach var="item" items="${boardList }" varStatus="status">
		    <tr onmouseover="this.className = 'partialFill2';" onmouseout="this.className = '';">
		      <td align="center">${boardListCount - status.index - (pageNo - 1) * listLimit }</td>
		      <td align="left">
		        <a href="/board/view/${boardNo }/${pageNo }/${item.boardArticleIdx }">${item.subject }</a>
		      </td>
		      <td align="center">${item.boardName }</td>
		      <td align="center">${item.dateCreated }</td>
		      <td align="center">${item.dateModified }</td>
		    </tr>
	    </c:forEach>
	    
	    
	    
	    <tr>
	      <td colspan="5" style="border: 0px; padding: 16px; text-align: center; word-spacing: 2px;">
	
	        <div style="float: left; width: 100px;">
	        &nbsp;
	        </div>
			
			
			<!--
			linkStart : ${linkStart }<br/>
			linkEnd : ${linkEnd }<br/>
			pageLinkCount : ${pageLinkCount }<br/>
			pageNo : ${pageNo }<br/>
			-->
			<c:if test="${linkStart > pageLinkCount }">
				&nbsp;<a href="/board/list/{{boardNo}}/{{linkStart - pageLinkCount}}">&lt;</a>
			</c:if>
			
			<c:forEach var="i" begin="${linkStart }" end="${linkEnd }">
			
				<c:if test="${i == pageNo }">
			    	&nbsp;<b>${i }</b>&nbsp;
			    </c:if>
			    <c:if test="${i != pageNo }">
			    	&nbsp;<a href="/board/list/${boardNo }/${i }">${i }</a>&nbsp;
			    </c:if>
			</c:forEach>
			
			<c:if test="${linkStart + pageLinkCount <= linkEnd }">
				&nbsp;<a href="/board/list/{{boardNo}}/{{linkStart + pageLinkCount}}">&gt;</a>
			</c:if>
			
			&nbsp;
	
	      </td>
	    </tr>
	  </table>

	    
	</div>



</div>

</body>
</html>