<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko" ng-app="test">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>타자 연습</title>

<style type="text/css">

</style>

<!--
<script type="text/javascript" src="/assets/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/assets/js/angular-1.7.6.min.js"></script>

<script type="text/javascript">
var testModule = angular.module('test', []);

testModule.controller('TestCtrl', ['$scope', 
	function ($scope) {
		
		var test = this;
		
		$scope.checkKeyUp2 = test.checkKeyUp2 = function(elem, event, val) {
			$("#typed").append("<p>" + elem.oldvalue + " : " + val.length + " : " + val + "</p>");
		}

	}
]);
</script>
-->

<script type="text/javascript">
function checkKeyDown2(event, val) {
	//if (event.isComposing) {
		//return;
	//} else {
		//document.writeln(val);
		//$("#typed").append("<p>" + val + "</p>");
	//for (var i=0; i < val.length; i++) {
	if (isHan(val[val.length - 1])) {
		return;
	} else {
		$("#typed").append("<p>" + val + "</p>");
	}
	//}
	
}

function checkKeyUp2(elem, event, val) {
	$("#typed").append("<p>" + elem.oldvalue + " : " + val.length + " : " + val + "</p>");
}

var len = 0;
//var prevCharHan = false;

function checkKeyDown(e) {
	//alert(e.keyCode + "\n" + e.charCode);

	//if (event.keyCode) {
		//;
	//}
}

function checkKeyUp(e, val) {
	
	var returnValue = null;
	
	switch (e.keyCode) {
	case 36 : //home
	case 35 : //end
	case 38 : //up
	case 40 : //down
	case 37 : //left
	case 39 : //right
		if (len != val.length) {
			$("#typed").append("<p>5 : " + val + "</p>");
			returnValue = val;
			len = val.length;
			//prevCharHan = false;
			break;
		}
	default : break;
	}
	

	if (len > val.length) {
		$("#typed").append("<p>4 : " + val + "</p>");
		returnValue = val;
		len = val.length;
		//prevCharHan = false;
	} else {
		//$("#typed").append("<p>" + val + " : " + val[val.length - 1] + " : " + isHan(val[val.length - 1]) + "</p>" );
		if (isHan(val.substring(val.length - 1))) {
			//if (getTextLength(val) == len + 2) {
				//len++;
			//} else
			/*
			if (prevCharHan == false) {
				//if (val.length == len + 2) {
				if (val.length >= len + 2) {
					$("#typed").append("<p>1 : " + val.substring(0, val.length - 1) + "</p>");
					returnValue = val.substring(0, val.length - 1);
					//len++;
					//len++;
					len = val.length - 1;
					prevCharHan = true;
				}
			} else {
				//if (val.length == len + 1) {
				if (val.length >= len + 2) {
					$("#typed").append("<p>2 : " + val.substring(0, val.length - 1) + " : " + val.length + " - " + len + "</p>");
					returnValue = val.substring(0, val.length - 1);
					//len++;
					len = val.length - 1;
					prevCharHan = true;
				}
			}
			*/
			
			if (val.length >= len + 2) {
				$("#typed").append("<p>2 : " + val.substring(0, val.length - 1) + " : " + val.length + " - " + len + "</p>");
				returnValue = val.substring(0, val.length - 1);
				len = val.length - 1;
			}
			
			/*
			if (getTextLength(val) == len + 2) {
				$("#typed").append("<p>" + val.substring(0, val.length - 1) + "</p>");
				len++;
				len++;
				prevCharHan = true;
			} else if (prevCharHan != true && val.length == len + 1) {
				$("#typed").append("<p>" + val.substring(0, val.length - 1) + "</p>");
				len++;
				len++;
				prevCharHan = true;
			}
			*/
		} else {
			if (val.length != len) {
				$("#typed").append("<p>3 : " + val + "</p>");
				returnValue = val;
				//len++;
				len = val.length;
				//prevCharHan = false;
			}
		}
		/*
		if (val.length == len + 1 || !isHan(val[val.length - 1])) {
			$("#typed").append("<p>" + val.substring(0, val.length - 1) + "</p>");
			len = val.length;
		} else {
			return;
		}
		*/
	}

	if (returnValue != null) {
		return returnValue;
	}
}

function getTextLength(str) {
	var len = 0;
    for (var i = 0; i < str.length; i++) {
    	//if (escape(str.charAt(i)).length == 6) {
        if (isHan(str.charAt(i))) {
            //len += 2;
        	len++;
        }
        len++;
    }
    return len;
}

</script>

</head>
<body ng-controller="TestCtrl">


<div style="margin-top: 3em;">
  <input type="text" name="expr" placeholder="Enter an expression" ng-model="expr" ng-change="parseExpr()" on-change="someFunction($event)" />
  <h2>{{parsedValue}}</h2>
</div>


<input type="hidden" name="loginId" id="loginId" value="${loginId }" />

<form name="myForm">
<input name="myInput" ng-model="myInput" required />
</form>

<p>The input's valid state is:</p>
<h1>{{myForm.myInput.$valid}}</h1>


<input type='text' ng-model='name' ng-change='change()' />
<p>changed {{counter}} times</p>

<p>
javascript: <input type="text" onkeydown="this.oldvalue = this.value;" onkeyup="checkKeyUp2(this, event, this.value);" />
</p>

<p>
angularjs : <input type="text" name="tmp" id="tmp" onkeydown="this.oldvalue = this.value;" ng-model="tmp" ng-keyup="checkKeyUp2($event);" />
</p>

<p>
input check : <input type="text" name="input2" id="input2" onkeydown="checkKeyDown(event);" onkeyup="checkKeyUp(event, this.value);" />
</p>

<div id="typed">
</div>

<p>
country : <input type="text" name="inputCountry" id="inputCountry" ng-model="inputCountry" country-enter="checkCountry()" />
<span id="spanCountry"></span>
</p>

<input type="hidden" name="loginId" id="loginId" value="${loginId }" />

<p>
session set - 
name : <input type="text" name="sessionName" ng-model="sessionName" ng-value="123" /> 
value : <input type="text" name="sessionValue" ng-model="sessionValue" ng-value="456" /> 
<input type="button" value="세션 생성" ng-click="setSession()" />
</p>

<p>
uuid : <input type="text" name="uuid" ng-model="uuid" />
<input type="button" value="UUID 구하기" ng-click="getUuid()" />
</p>

</body>
</html>