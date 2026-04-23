var appTest = angular.module('rg.Test', [ 'ngMaterial' ]);

appTest.controller('TestCtrl', [
		'$scope',
		'$log',
		'$http',
		'$compile',
		'$mdDialog',
		'$q',
		'$location',
		'$routeParams',
		'$parse',

		function($scope, $log, $http, $compile, $mdDialog, $q, $location,
				$routeParams, $parse) {
			var test = this;
			// $scope.parsedValue = $parse($scope.expr);
			// $scope.parsedValue = $scope.expr;
			$scope.parsedValue = "12345";
			$scope.expr = "1234567890";

			$scope.parseExpr = function() {
				// $scope.parsedValue = $parse($scope.expr);
				$scope.parsedValue = $scope.expr;
				//var parseFun = $parse($scope.expr);
				// alert(parseFun);
				//$scope.parsedValue = parseFun($scope);
				//alert("4");
			}

			$scope.name = 'Australia';
			$scope.counter = 0;
			$scope.change = function() {
				$scope.counter++;
				alert($scope.name);
			};
			
			$scope.someFunction = function(event) {
				// alert("2");
				// $log.debug(event.isComposing) ;
			}

			$scope.checkKeyUp2 = test.checkKeyUp2 = function($event) {
				$log.debug("checkKeyUp2....");
				var status = $event.target.oldvalue == $scope.tmp;
				$("#typed").append("<p>" + document.getElementById("tmp").oldvalue + " : " + $event.target.oldvalue + " : " +$scope.tmp.length + " : " + $scope.tmp + "</p>");
			}
			
			$scope.checkCountry = function() {
				//alert("ip : " + $scope.inputCountry);
				var param = {
						ip : $scope.inputCountry
					};
				
	            var req = {
	    			    method: 'POST',
	    			    url: '/rg/getCountry.do',
	    			    params: param
	            	};

				$http(req).then(function successCallback(response) {
					$log.debug(response.data);
					$("#spanCountry").html(response.data.country);
				}, function errorCallback(response) {
					$log.debug(response);
					alert("에러가 발생하였습니다.");
				});

				/*
				$http(req).then(function successCallback(response) {
	        		alert("검사하였숩니다.");
	        		$route.reload();
				}, function errorCallback(response) {
					$log.debug(response);
					alert("에러가 발생하였습니다.");
				});
				*/
			}

			$scope.sessionName = "1234"; 
			$scope.sessionValue = "5678";
			
			$scope.setSession = function() {
				
				alert("세션을 세팅합니다.\nname : " + $scope.sessionName + "\nvalue : " + $scope.sessionValue);
				//return;
				
				var param = {
						sessionName : $scope.sessionName,
						sessionValue : $scope.sessionValue,
					};
				
	            var req = {
	    			    method: 'POST',
	    			    url: '/rg/setSession.do',
	    			    params: param
	            	};

				$http(req).then(function successCallback(response) {
					$log.debug(response.data);
					alert("세션 세팅 완료.");
				}, function errorCallback(response) {
					$log.debug(response);
					alert("에러가 발생하였습니다.");
				});
			}

			$scope.getUuid = function() {
				
				var param = {
						sessionName : $scope.sessionName,
						sessionValue : $scope.sessionValue,
					};
				
	            var req = {
	    			    method: 'POST',
	    			    url: '/rg/getUuid.do',
	    			    params: param
	            	};

				$http(req).then(function successCallback(response) {
					$log.debug(response.data);
					$scope.uuid = response.data.uuid;
				}, function errorCallback(response) {
					$log.debug(response);
					alert("에러가 발생하였습니다.");
				});
			}
			
		} ]);

appTest.directive('someDirective2', [ '$parse', function($parse) {

	// alert("3");

	return {
		require : 'ngModel',
		restrict : 'A',
		link : function($scope, element, attrs, ngModel) {

			// Get the function provided in the file-change attribute.
			// Note the attribute has become an angular expression,
			// which is what we are parsing. The provided handler is
			// wrapped up in an outer function (attrHandler) - we'll
			// call the provided event handler inside the handler()
			// function below.
			var attrHandler = $parse(attrs['fileChange']);

			// This is a wrapper handler which will be attached to the
			// HTML change event.
			var handler = function(e) {

				$scope.$apply(function() {

					// Execute the provided handler in the directive's scope.
					// The files variable will be available for consumption
					// by the event handler.
					attrHandler($scope, {
						$event : e,
						files : e.target.files
					});
				});
			};

			// Attach the handler to the HTML change event
			element[0].addEventListener('change', handler, false);
		}
	};
} ]);

appTest.directive('someDirective3', function() {

	// var DblClickInterval = 300; // milliseconds

	// var firstClickTime;
	// var waitingSecondClick = false;

	return {
		restrict : 'A',

		link : function(scope, element, attrs) {
			element.bind('change', function(e) {

				alert("1");
				/*
				 * if (!waitingSecondClick) { firstClickTime = (new
				 * Date()).getTime(); waitingSecondClick = true;
				 * 
				 * setTimeout(function() { waitingSecondClick = false; },
				 * DblClickInterval); } else { waitingSecondClick = false;
				 * 
				 * var time = (new Date()).getTime(); if (time - firstClickTime <
				 * DblClickInterval) { scope.$eval(attrs.iosDblclick, { $event :
				 * e }); } }
				 */
			});
		}
	};
});

appTest.directive('onChange', function() {
	return {
		restrict : 'A',
		scope : {
			'onChange' : '='
		},
		link : function(scope, elm, attrs) {
			/*
			 * scope.$watch('onChange', function(nVal) { elm.val(nVal); });
			 * elm.bind('blur', function() { var currentValue = elm.val(); if
			 * (scope.onChange !== currentValue) { scope.$apply(function() {
			 * scope.onChange = currentValue; }); } });
			 */
			elm[0].addEventListener("input", function(event) {
				  // Need to run "input" event handler here because "input"
					// event whose isComposing is false
				  // won't be fired until user inputs something without IME
					// later.
				  // onInput();
					// alert("3");
					//scope.$parent.parseExpr();
					if (event.isComposing) {
						return;
					} else {
						scope.$parent.parseExpr();
					}
				});
		}
	};
});

appTest.directive('countryEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if (event.which === 13) {
            	//alert("1");
                scope.$apply(function () {
                    scope.$eval(attrs.countryEnter);
                });
                event.preventDefault();
            }
        });
    };
});
