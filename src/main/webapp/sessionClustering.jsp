<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%  

System.out.println( "Session ID : " + session.getId() );  

%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>Session Clustering Test</TITLE> 
</HEAD> 

<BODY> 
<h1>Session Clustering Test</h1> 
<% 
    Integer ival = (Integer)session.getAttribute("_session_counter"); 

    if(ival==null) { 
        ival = Integer.valueOf(1); 
    } 
    else { 
        ival = Integer.valueOf(ival.intValue() + 1); 
    } 
    session.setAttribute("_session_counter", ival); 
    System.out.println("here~~~~"); 
%> 
Session Counter = [<b> <%= ival %> </b>]<p> 
<a href="./sessionClustering.jsp">[Reload]</a> 
<p>
Session ID : <%= session.getId() %><br/>
Current Session ID : <%= request.getRequestedSessionId() %><br /> 
 
 
<div style="margin-left: auto; margin-right: auto;"><h3>[ 세션 정보를 얻어오는 메소드를 사용한 예제 ]</h3></div> 
<hr> 
<% 
 
String id_str=session.getId(); 
 
long lasttime=session.getLastAccessedTime(); 
 
long createdtime=session.getCreationTime(); 
  
long time_used=(lasttime-createdtime)/60000; 
 
int inactive=session.getMaxInactiveInterval()/60; 
 
boolean b_new=session.isNew(); 
%> 
 
[1] 세션 ID는 [<%=session.getId()%>] 입니다.<br><hr> 
[2] 당신의 웹사이트에 머문 시간은 <%=time_used%> 입니다.<br><hr> 
[3] 세션의 유효시간은 <%=inactive%> 분입니다.<br><hr> 
[4] 세션이 새로 만들어 졌나요?<br><hr> 
<% 
if(b_new) 
 out.println("예 !! 새로운 세션을 만들었습니다.");  
else 
 out.println("아니오 !! 새로운 세션을 만들지 않았습니다."); 
%> 
<hr> 

 
</BODY> 
</HTML>