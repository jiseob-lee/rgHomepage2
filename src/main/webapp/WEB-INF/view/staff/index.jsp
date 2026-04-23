<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko" ng-app="HumanBaseStaff">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HUMANBASE ADMIN</title>

<style type="text/css">
img {
  border: 0;
}
div.menu {
  float: left;
  border-right: 2px solid gray;
  width: 150px;
}
div.content {
  float: left;
}
footer {
  clear: both;
  font-size: 13px;
  margin-top: 35px;
  margin-left: 10px;
  width: 980px;
  float: left;
}
</style>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular-route.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular-cookies.js"></script>

<script type="text/javascript" src="/src/HumanBase/6.staff/controllers/UserController.js"></script>
<script type="text/javascript" src="/src/HumanBase/6.staff/controllers/BoardController.js"></script>
<script type="text/javascript" src="/src/HumanBase/6.staff/controllers/CustomerController.js"></script>
<script type="text/javascript" src="/src/HumanBase/6.staff/controllers/HistoryController.js"></script>

<script type="text/javascript" src="/assets/js/tinymce/tinymce.min.js"></script>
<script type="text/javascript" src="/assets/js/angular-ui-tinymce/tinymce.min.js"></script>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/FileAPI.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/ng-file-upload-all.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/ng-file-upload-shim.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/ng-file-upload.js"></script>

<script type="text/javascript" src="/src/HumanBase/HumanBaseStaff.js"></script>

</head>
  

<body ng-controller="StaffCtrl">



    <div class="menu">

      <img src="/assets/images/HUMANBASE_CI_118x45.png" alt="CI" />

      <br/><br/>

      로그인 사용자

      <br/>&nbsp; : {{loginUserName}}

      <br/><br/>

      - <a href="/staff/index.do#/history">회사연혁</a>

      <br/><br/>

      - <a href="/staff/index.do#/customer">주요고객</a>

      <br/><br/>

      - <a href="/staff/index.do#/hire">채용안내</a>

      <br/><br/>

      - <a href="/staff/index.do#/itnews">IT News</a>

      <br/><br/>

      - <a href="/staff/index.do#/notice">공지사항</a>

      <br/><br/>

      - <a href="/staff/index.do#/user">사용자 관리</a>

      <br/><br/><br/>

      <form name="logout" action="/logout" method="post" target="_top">
        <input type="submit" value="로그아웃" />
      </form>

    </div>
  
    <div style="width: 25px; float: left;">&nbsp;</div>
  

    <div class="content">
    
      <div ng-view=""></div>
    
    </div>

    <footer>
      <ng-include src="'/src/HumanBase/0.main/tmpl/footer.html'"></ng-include> 
    </footer>
    
</body>
</html>