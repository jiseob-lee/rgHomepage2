var appDownHistory = angular.module('rg.DownHistory', ['angularMoment', 'ngMaterial', 'ngMessages']);

appDownHistory.controller('DownHistoryCtrl', ['$scope', '$http', '$routeParams', '$log', '$location', 

	function ($scope, $http, $routeParams, $log, $location) {

		var downHistory = this;
		
		$scope.loginLogList = null;
	
	    $scope.pageNo = typeof $routeParams['pageNo'] == "undefined" || $routeParams['pageNo'] == "" ? "1" : $routeParams['pageNo'];
	
	    //$log.debug("$location", $location);
	    
	    if ($location.path().indexOf("/getDownHistory") == 0) {
			// IP 접속현황을 구한다.
			$http.get("/rg/getDownHistory.do?pageNo=" + $scope.pageNo).then(function(response) {
	
				//$log.debug("getIpCount.do", response.data);
				
				$scope.totalCount = response.data.totalCount;
				$scope.downHistoryList = response.data.list;
				//$scope.pageNo = response.data.pageNo;
				//$scope.listLimit = response.data.listLimit;
				
				//$scope.pageLinkCount = response.data.totalCount / response.data.listLimit;
				//./$scope.linkStart = response.data.totalCount + response.data.pageNo;
				
	
				$scope.pageNo = response.data.pageNo;
				$scope.listLimit = response.data.listLimit;  // 한 페이지 게시글 수
				
				$scope.pageLinkCount = 10; // 페이지 이동 링크 개수
				
				$scope.pageEnd = Math.ceil($scope.totalCount / $scope.listLimit);  // 마지막 페이지 번호
				
				$scope.linkStart = Math.floor(($scope.pageNo - 1) / $scope.pageLinkCount) * $scope.pageLinkCount + 1;
				$scope.linkEnd = ($scope.pageEnd - $scope.linkStart > $scope.pageLinkCount) 
									? $scope.linkStart + $scope.pageLinkCount : $scope.pageEnd;
			});
	    }

		
		this.myDate = this.myStartDate = this.myEndDate = this.currentDate = new Date();
		this.minDate = new Date("2018-08-09");
		this.isOpen = false;
		
		$scope.myStartDate = this.myStartDate;
		$scope.myEndDate = this.myEndDate;
		$scope.myDate = this.myDate;

		$scope.checkDates2 = function(csrfParameterName, csrfToken) {
			//alert("1");
			//alert(moment($scope.myStartDate).format('YYYY-MM-DD') + " : " + moment($scope.myEndDate).format('YYYY-MM-DD'));
			
			var fromDate = moment($scope.myStartDate).format('YYYY-MM-DD');
			var toDate = moment($scope.myEndDate).format('YYYY-MM-DD');
				
			if (fromDate > toDate) {
				alert("시작일이 종료일보다 클 수 없습니다.");
				return;
			}
			
			$scope.articlesData = [];

            var req = {
    			    method: 'POST',
    			    url: '/rg/getStatisticsArticleSubjects.do',
    			    data: $.param({
    			    		fromDate : fromDate,
    			    		toDate : toDate
    			    	}),
    			    headers: {
	    			        'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8',
	    			        'X-CSRF-TOKEN' : csrfToken
    			    	}
            	};

			$http(req).then(function successCallback(response) {
				//$log.debug(response);
				//alert("분석되었습니다.");
				
				$scope.articlesData = response.data;
				
				if (response.data.length == 0) {
					alert("조회된 글이 없습니다.");
					//toaster.pop('warning', "조회된 글이 없습니다.");
				}

			}, function errorCallback(response) {
				//$log.debug(response);
				alert("에러가 발생하였습니다.");
			});
		}
        
        

        
		$scope.checkDates3 = function(csrfParameterName, csrfToken) {
			
			var myDate = moment($scope.myDate).format('YYYY-MM-DD');
			
			$scope.logData = [];

            var req = {
    			    method: 'POST',
    			    url: '/rg/getStatisticsLogs.do',
    			    data: $.param({
    			    		myDate : myDate
    			    	}),
    			    headers: {
	    			        'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8',
	    			        'X-CSRF-TOKEN' : csrfToken
    			    	}
            	};

			$http(req).then(function successCallback(response) {
				
				$scope.logData = response.data;
				
				if (response.data.length == 0) {
					alert("조회 로그가 없습니다.");
					//toaster.pop('warning', "조회된 글이 없습니다.");
				}

			}, function errorCallback(response) {
				//$log.debug(response);
				alert("에러가 발생하였습니다.");
			});
		}
        
        


    }
]);


appStatistics.filter('range', function() {
	return function(input, scope) {
		if (Number(scope.linkStart) > 0) {
			var linkStart = Number(scope.linkStart);
			var linkEnd = Number(scope.linkEnd);
			var pageLinkCount = scope.pageLinkCount; // 페이지 이동 링크 개수
			for (var i = linkStart, n = 1; i <= linkEnd && n <= pageLinkCount; i++, n++) {
				input.push(i);
			}
		}
		return input;
	};
});


appStatistics.
	config(['$mdDateLocaleProvider', function ($mdDateLocaleProvider) {
		$mdDateLocaleProvider.formatDate = function (date) {
			return moment(date).format('YYYY-MM-DD');
			//return "2018-10-10";
		};
	}]);
