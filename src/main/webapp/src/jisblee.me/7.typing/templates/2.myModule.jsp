<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko" ng-app="myModule">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular-route.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular-cookies.js"></script>

<script type="text/javascript" src="/src/jisblee.me/4.test/controllers/MyModule.js"></script>
</head>

<body ng-controller="MyCtrl">


<div>
    <div ng-repeat="item in items" my-component isolated-expression-foo="updateItem(item,temp)">
        {{item|json}}
    </div>
</div>


</body>
</html>