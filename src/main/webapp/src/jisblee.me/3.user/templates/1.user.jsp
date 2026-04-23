<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>사용자 관리</title>
<style type="text/css">
table.user {
  border-collapse: collapse;
}
table.user td {
  border: 1px solid gray;
  padding: 5px;
}
</style>
</head>

<body>

<h3>사용자 관리</h3>

<form name="frm">

<table class="user">
  <tr>
    <td>번호</td>
    <td>아이디</td>
    <td>성함</td>
    <td>생성일</td>
    <td>비밀번호</td>
    <td>수정/삭제</td>
  </tr>
  <tr ng-repeat="user in userList">
    <td align="center">{{userList.length - $index}}</td>
    <td ng-if="user.userPassword == null" style="background-color: lightgray;">
      <del><font color="gray">{{user.userId}}</font></del>
    </td>
    <td ng-if="user.userPassword != null">
      {{user.userId}}
    </td>
    <td>
      <input type="text" name="userNameKo" value="{{user.userNameKo}}" ng-if="user.userPassword != null" />
      <input type="text" name="userNameKo" value="{{user.userNameKo}}" ng-if="user.userPassword == null" style="text-decoration: line-through; background-color: gray;" />
    </td>
    <td>
      <span ng-if="user.userPassword == null"><del><font color="gray">{{user.dateCreated}}</font></del></span>
      <span ng-if="user.userPassword != null">{{user.dateCreated}}</span>
    </td>
    <td><input type="text" name="userPassword" /></td>
    <td>
      <input type="button" value="변경" ng-click="updateUser(user.userId, $index)"/>
      <input type="button" value="삭제" ng-click="deleteUser(user.userId)"/>
    </td>
  </tr>
</table>

<br/>

<c:if test="${loginId == 'rg' or loginId == 'yoon' }">
<table>
  <tr>
    <td>아이디</td>
    <td>성함</td>
    <td>비밀번호</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><input type="text" name="userId" ng-model="userId" /></td>
    <td><input type="text" name="userNameKo" ng-model="userNameKo" /></td>
    <td><input type="text" name="userPassword" ng-model="userPassword" /></td>
    <td>
      <input type="button" value="사용자 추가" ng-click="addUser()" />
    </td>
  </tr>
</table>
</c:if>

</form>

<%
session.setAttribute("123", "123");
%>

</body>
</html>