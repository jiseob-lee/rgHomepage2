angular.module('rg.User', [])
    .controller('UserCtrl',
    function ($scope, $log, $http, $route, $httpParamSerializerJQLike) {
        
    	var user = this;
        
		$http.get("/rg/getUserList.do")
		.then(function(response) {

			//$log.debug(response);
			
			user.userList = response.data;
			
			//alert($scope.$parent.loginId);
			
			if ($scope.$parent.loginId != 'rg' && $scope.$parent.loginId != 'yoon') {

				user.userList = user.userList.filter(function(value, index, arr){

					//$log.debug(value);
					
				    return value.userId == $scope.$parent.loginId;

				});

				/*
				for (var i=0; i < user.userList.length; i++) {
					if (user.userList[i].loginId != $scope.$parent.loginId) {
						user.userList.splice(i, 1);
					}
				}
				*/
			}

			$scope.userList = user.userList;
		});
		
		$scope.addUser = function() {

			if ($scope.userId == undefined || $scope.userId == "") {
				alert("아이디를 입력해주시기 바랍니다.");
				return;
			}
			if ($scope.userNameKo == undefined || $scope.userNameKo == "") {
				alert("성함을 입력해주시기 바랍니다.");
				return;
			}
			if ($scope.userPassword == undefined || $scope.userPassword == "") {
				alert("비밀번호를 입력해주시기 바랍니다.");
				return;
			}
			
			var param = {userId : $scope.userId,
					userNameKo : $scope.userNameKo,
					userPassword : $scope.userPassword
				};
			
            var req = {
    			    method: 'POST',
    			    url: '/rg/insertUser.do',
    			    data: $httpParamSerializerJQLike(param),
    			    headers: {
    			        'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8',
    			    }
            };

			$http(req).then(function successCallback(response) {
				//$log.debug(response);
				alert("사용자가 추가되었습니다.");
				$route.reload();
			}, function errorCallback(response) {
				//$log.debug(response);
				alert("에러가 발생하였습니다.");
			});

		}
		
		$scope.updateUser = function(userId, i) {

			$log.debug(document.frm.userPassword);
			
			var userPassword = document.frm.userPassword[Number(i)].value;
			var userNameKo = document.frm.userNameKo[Number(i)].value;
			
			var param = {
			    	userId : userId,
			    	userNameKo : userNameKo,
			    	userPassword : userPassword,
			    	userIdModified : $scope.$parent.loginId
			    };
			
			if (userPassword == undefined || userPassword == "") {
				//alert("1");
				param = {
				    	userId : userId,
				    	userNameKo : userNameKo,
				    	userIdModified : $scope.$parent.loginId
				    };
			}
			
			$http({
			    method: 'POST',
			    url: '/rg/updatetUser.do',
			    data: $.param(param),
			    headers: {
			        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
			    }
			}).then(function successCallback(response) {
				//$log.debug(response);
				alert("변경되었습니다.");
				$route.reload();
			}, function errorCallback(response) {
				//$log.debug(response);
				alert("에러가 발생하였습니다.");
			});
		}

		$scope.deleteUser = function(userId) {

			var param = {
			    	userId : userId,
			    	userIdDeleted : $scope.$parent.loginId
			    };
			
			$http({
			    method: 'POST',
			    url: '/rg/deleteUser.do',
			    data: $.param(param),
			    headers: {
			        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
			    }
			}).then(function successCallback(response) {
				//$log.debug(response);
				alert("삭제되었습니다.");
				$route.reload();
			}, function errorCallback(response) {
				//$log.debug(response);
				alert("에러가 발생하였습니다..");
			});
		}
    });
