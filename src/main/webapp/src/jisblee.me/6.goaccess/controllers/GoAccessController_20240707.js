var appGoAccess = angular.module('rg.GoAccess', ['angularMoment', 'ngMaterial', 'ngMessages']);

appGoAccess.controller('GoAccessCtrl', ['$scope', '$http', '$routeParams', '$log', '$window',

	function ($scope, $http, $routeParams, $log, $window) {

		this.myStartDate = this.myEndDate = this.currentDate = new Date();
		this.minDate = new Date("2018-08-09");
		this.isOpen = false;
		
		$scope.myStartDate = this.myStartDate;
		$scope.myEndDate = this.myEndDate;

		if (document.getElementById('goaccessFrame')) {
			document.getElementById('goaccessFrame').style.backgroundColor = 'white';
		}

		$scope.checkDates = function() {
			//alert("1");
			//alert(moment($scope.myStartDate).format('YYYY-MM-DD') + " : " + moment($scope.myEndDate).format('YYYY-MM-DD'));
			
			var fromDate = moment($scope.myStartDate).format('YYYY-MM-DD');
			var toDate = moment($scope.myEndDate).format('YYYY-MM-DD');
				
			if (fromDate > toDate) {
				alert("시작일이 종료일보다 클 수 없습니다.");
				return;
			}

            var req = {
    			    method: 'POST',
    			    url: '/rg/goAccessParse.do',
    			    data: $.param({
    			    
    			    		fromDate : fromDate,
    			    		toDate : toDate
    			    	}),
    			    headers: {
	    			        'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
    			    	}
            	};

			$http(req).then(function successCallback(response) {
				//$log.debug(response);
				//alert("분석되었습니다.");
				
				document.getElementById('goaccessFrame').src = response.data.url;
				document.getElementById('goaccessFrame').style.backgroundColor = 'white';
				
	            var req1 = {
	    			    method: 'GET',
	    			    url: "/rg/checkGoaccessResultFile.do"
	            	};
				
	            $http(req1).then(function successCallback(response1) {
					//$log.debug(response1);
					if (response1.data == 1) {
						alert("분석되었습니다.");
						//toaster.pop('success', "분석되었습니다.");
						document.getElementById('goaccessFrame').style.backgroundColor = 'white';
					} else if (response1.data == 0) {
						alert("분석되지 않았습니다.");
						//toaster.pop('error', "분석되지 않았습니다.");
						document.getElementById('goaccessFrame').style.backgroundColor = 'white';
					}
				});
				
				//$window.location.href = "#/board/list/" + boardIdx + "/1" + "/" + (typeof $routeParams['openYn'] == "undefined" ? "" : $routeParams['openYn']);

			}, function errorCallback(response) {
				//$log.debug(response);
				alert("에러가 발생하였습니다.");
			});
		}
		
		$scope.viewParsedFile = function() {

            var req = {
    			    method: 'GET',
    			    url: "/rg/getGoaccessCurrentTime.do"
            	};

			$http(req).then(function successCallback(response) {
				//$log.debug(response);
				//alert(response.data);
				if (response.data == "") {
					alert("분석해주시기 바랍니다.");
					//toaster.pop('note', "분석해주시기 바랍니다.");
				} else {
					//var w = 
					$window.open("https://jisblee.me/goaccess/" + response.data + ".html", '_blank');
					//w.close();
				}
			}, function errorCallback(response) {
				//$log.debug(response);
				alert("에러가 발생하였습니다.");
			});
		}

    }
]);


appGoAccess.
	config(['$mdDateLocaleProvider', function ($mdDateLocaleProvider) {
		$mdDateLocaleProvider.formatDate = function (date) {
			return moment(date).format('YYYY-MM-DD');
			//return "2018-10-10";
		};
	}]);

