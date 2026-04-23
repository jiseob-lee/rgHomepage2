var appAttachment = angular.module('rg.ManageBoard', []);

appAttachment.controller('ManageBoardCtrl',
    function ($scope, $location, $log, $http, $routeParams, $httpParamSerializerJQLike, $window, $route) {
        
    	var manageBoard = this;

    	manageBoard.boardIdx = 0;
		
    	// 게시판 목록을 구한다.
		$http.get("/getManageBoardList.do")
		.then(function(response) {
			$scope.manageBoardList = response.data;
			//$log.debug(response.data);
		});

    	// 게시판 개수를 구한다.
		$http.get("/getManageBoardListCount.do")
		.then(function(response) {
			$scope.manageBoardListCount = response.data;
			//$log.debug(response.data);
		});
		
		
		//$scope.boardName
		$scope.greaterThan = function(prop, val){
		    return function(item){
		      return item[prop] > val;
		    }
		}

		$scope.editTitle = function(boardIdx, boardName) {
			
			//alert($("#boardNewName_" + boardIdx).val());
			
			if (manageBoard.boardIdx == boardIdx) {

				var param = {
						boardName : $("#boardNewName_" + boardIdx).val(),
						boardIdx : manageBoard.boardIdx
					};

	            var req = {
	    			    method: 'POST',
	    			    url: '/rg/editBoardName.do',
	    			    data: $httpParamSerializerJQLike(param),
	    			    headers: {
	    			        'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
	    			    }
	            };

				$http(req).then(function successCallback(response) {
					//$log.debug(response);
					alert("수정되었습니다.");

		        	//var url = $location.path();
		        	
		        	//$window.location.href = "/rg/index.do#/manageBoard";
					$route.reload();
		        	
				}, function errorCallback(response) {
					$log.debug(response);
					alert("에러가 발생하였습니다.");
				});
				
			} else {

				jQuery('span[id^="a_"]').show();
				jQuery('span[id^="e_"]').hide();
				
				$("#a_" + boardIdx).hide();
				$("#e_" + boardIdx).show();

				$("#boardNewName_" + boardIdx).val(boardName);
				manageBoard.boardIdx = boardIdx;
			}
		}
		
		$scope.createBoard = function() {
			//alert($scope.boardName);
			
			var param = {
					boardName : $scope.boardName
				};

            var req = {
    			    method: 'POST',
    			    url: '/rg/createBoard.do',
    			    data: $httpParamSerializerJQLike(param),
    			    headers: {
    			        'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8',
    			    }
            };

			$http(req).then(function successCallback(response) {
				//$log.debug(response);
				alert("입력되었습니다.");

	        	//var url = $location.path();
	        	
	        	//$window.location.href = "/rg/index.do#/manageBoard";
				$route.reload();
	        	
			}, function errorCallback(response) {
				//$log.debug(response);
				alert("에러가 발생하였습니다.");
			});

		}
        
        //$scope.mainMenuTitle = "";
        //$scope.$parent.subMainImage = "";
        
        //$log.debug($location);
        
        //alert($location.$$path);

    });



appAttachment.filter('range', function() {
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
