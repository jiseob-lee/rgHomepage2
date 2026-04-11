<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib  prefix="c"    uri="jakarta.tags.core" %>
<%@ taglib  prefix="fmt"  uri="jakarta.tags.fmt" %>
<%@ taglib  prefix="fn"   uri="jakarta.tags.functions" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="UTF-8">

<link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png">
<link rel="manifest" href="/manifest.json">
<link rel="mask-icon" href="/safari-pinned-tab.svg" color="#5bbad5">

<meta name="theme-color" content="#ffffff">

<c:choose>
	<c:when test="${currentLocale eq 'ko'}">
		<title>${boardContent.subject }</title>
	</c:when>
	<c:otherwise>
		<title>${boardContent.subjectEng }</title>
	</c:otherwise>
</c:choose>

<style type="text/css">
body {
<c:if test="${currentLocale eq 'ko' }">
  font-family: 굴림, Sans-serif, Arial;
</c:if>
<c:if test="${currentLocale eq 'en' }">
  font-family: Sans-serif, Arial;
</c:if>
}
</style>

<link rel="stylesheet" type="text/css" href="/assets/css/board_2020907.css" />


<script type="text/javascript">
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

	
	
	<div class="content">



		<div style="text-align: left; margin: 10px;">
		
		  <table class="board">
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
		        <input type="button" value="<spring:message code="board.list" text="목록" />" onclick="location.href = '/board/list/${boardNo }/${pageNo }'" class="button" />
		      </td>
		    </tr>
		    <tr>
		      <td align="center"><spring:message code="board.title" text="제목" /></td>
		      <td colspan="5" align="left"><c:choose>
					<c:when test="${currentLocale eq 'ko'}">
						${boardContent.subject }
					</c:when>
					<c:otherwise>
						${boardContent.subject }<br/>${boardContent.subjectEng }
					</c:otherwise>
				</c:choose></td>
		    </tr>
		    <tr>
		      <td align="center"><spring:message code="board.writer" text="글쓴이" /></td>
		      <td align="left">${boardContent.userNameCreated }</td>
		      <td align="center"><spring:message code="board.writeDate" text="작성일" /></td>
		      <td align="left">${boardContent.dateCreated }</td>
		      <td align="center"><spring:message code="board.viewCount" text="조회수" /></td>
		      <td align="left">${boardContent.hitCount }</td>
		    </tr>
		    <tr align="center">
		      <!-- <td align="center">내용</td> -->
		      <td colspan="6" align="left" class="body"><c:choose>
					<c:when test="${currentLocale eq 'ko'}">
						${boardContent.content }
					</c:when>
					<c:otherwise>
						${boardContent.contentEng }
					</c:otherwise>
				</c:choose></td>
		    </tr>
		    
		    <c:if test="${fn:length(attachmentList) > 0 }">
		    <tr align="center">
		      <td align="center"><spring:message code="board.attachmentFile" text="첨부파일" /></td>
		      <td colspan="5" align="left">
		      
		        <c:forEach var="attach" items="${attachmentList }">
		        	<div style="line-height: 175%;">
		          		<a href="/fileDownload.do?idx=${attach.attachmentIdx }&filename=${attach.serverFileName }">${attach.attachmentName }.${attach.attachmentExt }</a>
		          		(<fmt:formatNumber value="${attach.attachmentSize }" pattern="#,###" /> <spring:message code="board.byte" text="byte" />)
		        	</div>
		        </c:forEach>
		        
		      </td>
		    </tr>
		    </c:if>
		    
		    <tr>
		      <td colspan="6" style="border: 0px; padding: 12px; text-align: right;">
		        <input type="button" value="<spring:message code="board.list" text="목록" />" onclick="location.href = '/board/list/${boardNo }/${pageNo }'" class="button" />
		      </td>
		    </tr>
		    </tbody>
		  </table>
		  
		</div>


		<jsp:include page="comment/comment.jsp?articleNo=${articleNo }" />


	</div>



</div>

</body>
</html>