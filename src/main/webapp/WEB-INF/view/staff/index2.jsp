<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko" ng-app="HumanBaseStaff">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HUMANBASE ADMIN</title>

<style type="text/css">
div.menu {
  float: left;
  border-right: 2px solid gray;
  width: 150px;
}
div.content {
  float: left;
}
</style>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular-route.js"></script>

<script type="text/javascript" src="/src/HumanBase/6.staff/controllers/UserController.js"></script>
<script type="text/javascript" src="/src/HumanBase/6.staff/controllers/ITNewsController.js"></script>
<script type="text/javascript" src="/src/HumanBase/6.staff/controllers/NoticeController.js"></script>
<script type="text/javascript" src="/src/HumanBase/6.staff/controllers/HireController.js"></script>

<script type="text/javascript" src="/src/HumanBase/HumanBaseStaff.js"></script>

</head>
  

<body ng-controller="StaffCtrl">



    <div class="menu">

      <img src="/assets/images/HUMANBASE_CI_118x45.png" alt="CI" />

      <br/><br/>

      로그인 사용자

      <br/>&nbsp; : {{loginUserName}}

      <br/><br/>

      - <a href="/staff/index.do#/hire">채용정보</a>

      <br/><br/>

      - <a href="/staff/index.do#/itnews">IT 뉴스</a>

      <br/><br/>

      - <a href="/staff/index.do#/notice">공지사항</a>

      <br/><br/>

      - <a href="/staff/index.do#/user">사용자 관리</a>

      <br/><br/><br/>

      <form name="logout" action="/logout" method="post" target="_top">
        <input type="submit" value="로그아웃" />
      </form>

    </div>
  
    <div style="width: 20px; float: left;">&nbsp;</div>
  

    <div class="content">
    
      <div ng-view=""></div>
    
    </div>

</body>
</html>