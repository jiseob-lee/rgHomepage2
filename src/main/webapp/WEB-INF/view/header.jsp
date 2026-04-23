<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form name="logout" action="/logout" method="post" target="_top">
</form>


	<div style="padding: 5px; position: relative; z-index: 10;">
	
		<table style="float: left;">
		<tr><td>
			<%-- --${loginId }-- --%>
			<%
			//String tmp1 = (String)request.getSession().getAttribute("loginId");
			//out.println(tmp1);
			%>
			<c:choose>
			    <c:when test="${empty loginId }">
			        <!-- <h3>&nbsp; <a href="https://jisblee.me/login.do" target="_self"><spring:message code="header.login" text="로그인" /></a></h3> -->
			        <h3>&nbsp; <a href="/login.do" target="_self"><spring:message code="header.login" text="로그인" /></a></h3>
			    </c:when>
			    <c:otherwise>
					<h3>&nbsp; <a href="javascript:document.logout.submit();" target="_self"><spring:message code="header.logout" text="로그아웃" /></a> &nbsp; <a href="/rg/"><spring:message code="header.internal" text="내부" /></a></h3>
			    </c:otherwise>
			</c:choose>
		
		</td>
		<td style="padding-left: 10px;">
		
			<spring:message code="header.language" text="Language" />:
			
		</td>
		<td>
		 
			<select name="language" onchange="location.href = 'https://jisblee.me' + window.location.pathname + '?lang=' + this.value">
				<option value="ko" <c:if test="${currentLocale eq 'ko' }">selected='selected'</c:if>><spring:message code="header.korean" text="Korean" /></option>
				<option value="en" <c:if test="${currentLocale eq 'en' }">selected='selected'</c:if>><spring:message code="header.english" text="English" /></option>
			</select>
		
		</td></tr>
		</table>
		
		<ul class="board">
			<li class="boardItem"><a href="/" style="text-decoration:none; color:#000;"><spring:message code="header.boardTotal" text="전체 게시판" /></a></li>
			
			<c:forEach var="item" items="${manageBoardList }">
				<c:if test="${item.articleCount > 0 }">
					<li class="boardItem">
						<a href="/board/list/${item.boardIdx}/1" style="text-decoration:none; color:#000;">${item.boardName}</a>
					</li>
				</c:if>
			</c:forEach>
			
		</ul>
	</div>
	
