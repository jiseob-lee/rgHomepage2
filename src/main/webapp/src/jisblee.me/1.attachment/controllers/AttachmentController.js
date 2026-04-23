var appAttachment = angular.module('rg.Attachment', ['ngFileUpload', 'ngCookies']);

appAttachment.controller('AttachmentCtrl', ['$scope', '$location', '$log', '$http', '$routeParams', 'Upload', '$timeout', '$route', '$window', '$mdDialog', '$q', 'downloadService',
    function ($scope, $location, $log, $http, $routeParams, Upload, $timeout, $route, $window, $mdDialog, $q, downloadService) {
        
    	var attachment = this;
    	
    	attachment.manageBoardList;
        
        //attachment.menu = Menu_HUMANBASE;
        
        $scope.mainMenuTitle = "";
        $scope.$parent.subMainImage = "";
        
        //$log.debug($location);
        
        //alert($location.$$path);
        
        $scope.boardNo = attachment.boardNo = $routeParams['boardNo'];

    	// 게시판 목록을 구한다.
		$http.get("/getManageBoardList.do")
		.then(function(response) {
			attachment.manageBoardList = $scope.manageBoardList = response.data;
			//$log.debug(response.data);
		});

		$scope.boardChange = function() {
			$window.location.href = "/rg/#/attachment/" + $scope.boardNo + "/1";
		}
		
		  function DialogController($scope, $mdDialog, attachmentIdx, attachmentName, attachmentExt) {

			$scope.attachmentIdx = attachmentIdx;
		    $scope.attachmentName = attachmentName;
		    $scope.attachmentExt = attachmentExt;
		    
		    $scope.manageBoardList = attachment.manageBoardList;
		    $scope.boardNo = attachment.manageBoardList[0].boardIdx;
		    $scope.boardArticleIdx;
		    
		    $scope.hide = function() {
		      $mdDialog.hide();
		    };

		    $scope.cancel = function() {
		      $mdDialog.cancel();
		    };

		    $scope.answer = function(answer) {
		      $mdDialog.hide(answer);
		    };

		    $scope.mapping = function() {

				// 첨부파일 목록의 총 개수를 구한다.
				$http.get("/rg/updateFileMapping.do?" + "boardIdx=" + $scope.boardNo + "&boardArticleIdx=" + $scope.boardArticleIdx + "&attachmentIdx=" + $scope.attachmentIdx)
				.then(function(response) {
					alert("매핑되었습니다.");
					$mdDialog.hide();
				});

		    }

			$scope.boardSelect = function() {
				
				var board = this;
				
	        	board.pageNo = 1;//Number($routeParams['pageNo']);
	        	board.pageNo = board.pageNo > 0 ? board.pageNo : 1;
	        	board.listLimit = 10;  // 한 페이지 게시글 수
	        	board.listOffset = board.listLimit * (board.pageNo - 1);  // 페이지 이동
	        	board.pageLinkCount = 10; // 페이지 이동 링크 개수
	        	board.pageEnd = 0;
	        	
	        	$scope.listLimit = board.listLimit;
	            
	        	board.listURL = "/rg/getBoardList.do";
	        	board.listURL += "?" + "boardIdx=" + $scope.boardNo;
	        	board.listURL += "&" + "listOffset=" + board.listOffset;
	        	board.listURL += "&" + "listLimit=" + board.listLimit;
	        	
	        	$log.debug("board.listURL", board.listURL);

	        	if ($scope.boardNo == 0) {
	        		$scope.boardList = null;
	        		$scope.boardArticleIdx = 0;
	        		return;
	        	}
	        	
	        	// 게시판 목록을 구한다.
				$http.get(board.listURL)
				.then(function(response) {
					//$log.debug("getBoardList.do", response.data);
					$scope.boardList = response.data;
					$scope.boardArticleIdx = response.data[0].boardArticleIdx + "";
					$log.debug(response.data);
				});
				
				
				// 게시판 목록의 총 개수를 구한다.
				$http.get("/rg/getBoardListCount.do?" + "boardIdx=" + $scope.boardNo)
				.then(function(response) {

					board.boardListCount = (response.data == null || response.data == "") ? 0 : response.data;

					$scope.boardListCount = board.boardListCount;
					$scope.pageEnd = Math.ceil(board.boardListCount / board.listLimit);  // 마지막 페이지 번호
					board.pageEnd = $scope.pageEnd;
					
					if (board.pageNo > board.pageEnd) {
						board.pageNo = board.pageEnd;
					}

					$scope.pageNo = board.pageNo;
					$scope.listLimit = board.listLimit;  // 한 페이지 게시글 수
					$scope.pageLinkCount = board.pageLinkCount; // 페이지 이동 링크 개수
					
					board.linkStart = Math.floor((board.pageNo - 1) / board.pageLinkCount) * board.pageLinkCount + 1;
					board.linkEnd = (board.pageEnd - board.linkStart > board.pageLinkCount) 
										? board.linkStart + board.pageLinkCount : board.pageEnd;
					
					$scope.linkStart = board.linkStart;
					$scope.linkEnd = board.linkEnd;
				});
				
								
			}

		  }
		  
		  
		  $scope.showAdvanced = function(ev, attachmentIdx, attachmentName, attachmentExt) {
			document.getElementById("tr_" + attachmentIdx).style.backgroundColor = "lightblue";
		    $mdDialog.show({
		      controller: DialogController,
		      templateUrl: '/src/jisblee.me/1.attachment/templates/dialog1.tmpl.html',
		      parent: angular.element(document.body),
		      targetEvent: ev,
		      clickOutsideToClose:true,
		      fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
		      locals: {
		    	attachmentIdx : attachmentIdx,
		    	attachmentName : attachmentName,
		    	attachmentExt : attachmentExt
		      },
		    })
		    .then(function(answer) {
		      $scope.status = 'You said the information was "' + answer + '".';
		    }, function() {
		      $scope.status = 'You cancelled the dialog.';
		    });
		  };

        /*
        for (var m in attachment.menu) {
        	//alert(m);
        	$log.debug(attachment.menu[m].url);
        	if (attachment.menu[m].url == "/#" + $location.$$path) {
        		$scope.menuTitle = attachment.menu[m].name;
        		for (var ms in Menus) {
        			if (Menus[ms].url.split('/')[2] == attachment.menu[m].url.split('/')[2]) {
        				$scope.mainMenuTitle = Menus[ms].name;
        				$scope.mainMenuUrl = Menus[ms].url;
        				$scope.$parent.subMainImage = Menus[ms].subMainImage;
        			}
        		}
        		break;
        	}
        }
        */
        
        //$log.debug($scope.menuTitle);
        //$log.debug($scope.mainMenuTitle);
        

    	attachment.pageNo = Number($routeParams['pageNo']);

		if (attachment.pageNo < 1) {
			$window.location.href = "/rg/#/attachment/" + $scope.boardNo + "/1";
		}

    	attachment.pageNo = attachment.pageNo > 0 ? attachment.pageNo : 1;
    	attachment.listLimit = 10;  // 한 페이지 게시글 수
    	attachment.listOffset = attachment.listLimit * (attachment.pageNo - 1);  // 페이지 이동
    	attachment.pageLinkCount = 10; // 페이지 이동 링크 개수
    	attachment.pageEnd = 0;
    	
    	$scope.listLimit = attachment.listLimit;
        
    	attachment.listURL = "/rg/getAttachmentList.do";
    	attachment.listURL += "?" + "boardIdx=" + attachment.boardNo;
    	attachment.listURL += "&" + "listOffset=" + (attachment.listOffset == undefined ? "" : attachment.listOffset);
    	attachment.listURL += "&" + "listLimit=" + (attachment.listLimit == undefined ? "" : attachment.listLimit);
    	
    	//alert(attachment.listURL);
    	
    	// 첨부파일 목록을 구한다.
		$http.get(attachment.listURL)
		.then(function(response) {
			$scope.attachmentList = response.data;
			//$log.debug(response.data);
		});

		// 첨부파일 목록의 총 개수를 구한다.
		$http.get("/rg/getAttachmentListTotalCount.do?" + "boardIdx=" + attachment.boardNo)
		.then(function(response) {

			attachment.attachmentListCount = (response.data == null || response.data == "") ? 0 : response.data;

			$scope.attachmentListCount = attachment.attachmentListCount;
			$scope.pageEnd = Math.ceil(attachment.attachmentListCount / attachment.listLimit);  // 마지막 페이지 번호
			attachment.pageEnd = $scope.pageEnd;
			
			if (attachment.pageNo > attachment.pageEnd) {
				//attachment.pageNo = attachment.pageEnd;
				$window.location.href = "/rg/#/attachment/" + $scope.boardNo + "/" + attachment.pageNo;
			}
			
			$scope.pageNo = attachment.pageNo;
			$scope.listLimit = attachment.listLimit;  // 한 페이지 게시글 수
			$scope.pageLinkCount = attachment.pageLinkCount; // 페이지 이동 링크 개수
			
			attachment.linkStart = Math.floor((attachment.pageNo - 1) / attachment.pageLinkCount) * attachment.pageLinkCount + 1;
			attachment.linkEnd = (attachment.pageEnd - attachment.linkStart > attachment.pageLinkCount) 
								? attachment.linkStart + attachment.pageLinkCount : attachment.pageEnd;
			
			$scope.linkStart = attachment.linkStart;
			$scope.linkEnd = attachment.linkEnd;
		});

		
		
        // 어드민
        $scope.uploadFile = function($event, file) {
        	//$log.debug(file);
        	
        	var ext = "";
        	if (file[0].name.lastIndexOf(".") > -1) {
        		ext = file[0].name.substr(file[0].name.lastIndexOf(".") + 1);
        	} else {
        		alert("업로드가 불가한 파일입니다.");
        		return;
        	}
        	
        	// 파일 크기 검사
        	if (Number(file[0].size) > 50000000000) {
        		alert("파일 크기가 제한 용량을 초과하였습니다.\n파일 크기 : " + file[0].size);
        		return;
        	}
        	
        	var data = {
                file: file,
                attachmentExt: ext,
                userIdCreated: $scope.$parent.loginId,
                boardIdx: attachment.boardIdx == null || attachment.boardIdx == "" ? "0" : attachment.boardIdx,
                boardArticleIdx: 0
            };
        	
        	
        	var formData = new FormData();
        	formData.append('file', $('input[type=file]')[0].files[0]);
        	formData.append('attachmentExt', ext);
        	formData.append('userIdCreated', $scope.$parent.loginId);
        	formData.append('boardIdx', attachment.boardIdx == null || attachment.boardIdx == "" ? "0" : attachment.boardIdx);
        	formData.append('boardArticleIdx', 0);
        	
        	//alert($scope.$parent.loginId);
        	$log.debug(formData);
        	
        	$.ajax({
        	    url: '/rg/insertAttachment.do',
        	    data: formData,
        	    type: 'POST',
        	    contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
        	    processData: false, // NEEDED, DON'T OMIT THIS

                success : function(response) {
                	
                    $log.debug(response);

                    attachment.addFileInput(response);
                    
                    // input file 초기화
                    angular.forEach(
                    	angular.element("input[type='file']"),
                    	function(inputElem) {
                    	    angular.element(inputElem).val(null);
                    	}
                    );
                },
                error: function(xhr, status, error){
                    alert(error);
                }
        	});
        	
            
        	/*
        	var startTime = new Date(); 
            
        	$scope.progress = "true";
        	
            file.upload = Upload.upload({
                url: '/rg/insertAttachment.do',
                data: data,
			    headers: {
			    	"Content-Type": file.type != '' ? file.type : 'application/octet-stream',
			    }
            });

            file.upload.then(function (response) {
                $timeout(function () {
                    file.result = response.data;
                    $log.debug(file.result);
                    $scope.progress = "false";
                    attachment.addFileInput(file.result);
                    
                    // input file 초기화
                    angular.forEach(
                    	angular.element("input[type='file']"),
                    	function(inputElem) {
                    	    angular.element(inputElem).val(null);
                    	}
                    );

                    var diff = new Date() - startTime;

                    var msec = diff;
                    var hh = Math.floor(msec / 1000 / 60 / 60);
                    msec -= hh * 1000 * 60 * 60;
                    var mm = Math.floor(msec / 1000 / 60);
                    msec -= mm * 1000 * 60;
                    var ss = Math.round(msec / 1000);
                    msec -= ss * 1000;
                    
                    var timeMessage = "시간 : " + (hh > 0 ? hh + "시간" : "") + " " + (mm > 0 ? mm + "분" : "") + " " + (ss > 0 ? ss + "초" : "");

                    $log.debug(timeMessage);
                    
                });
            }, function (response) {
                if (response.status > 0) {
                	$scope.errorMsg = response.status + ': ' + response.data;
                }
            }, function (evt) {
                // Math.min is to fix IE which reports 200% sometimes
                file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
            });
            */
            
        };
        
        attachment.addFileInput = function(fileResult) {
            
        	var appendHtml = "<tr id='tr" + fileResult.attachmentIdx + "'><td>";
        	appendHtml += "<input type='hidden' name='attachmentIdx' value='" + fileResult.attachmentIdx + "' /> "
        	appendHtml += "<a href=\"/fileDownload.do?idx=" + fileResult.attachmentIdx + "&filename=" + fileResult.serverFileName + "\">";
        	appendHtml += fileResult.attachmentName + "." + fileResult.attachmentExt + "</a> ("+ attachment.addCommas(fileResult.attachmentSize) + " byte) ";
        	appendHtml += "<input type='button' value='삭제' ";
        	appendHtml += "onclick=\"delAttachment2('" + fileResult.attachmentIdx + "', '" + fileResult.serverFileName + "." + fileResult.attachmentExt + "')\" />";
        	appendHtml += "</td></tr>";
        	
        	$('#tableFile tr:last').after(appendHtml);
        }
        
        attachment.addCommas = function(nStr) {
            nStr += '';
            x = nStr.split('.');
            x1 = x[0];
            x2 = x.length > 1 ? '.' + x[1] : '';
            var rgx = /(\d+)(\d{3})/;
            while (rgx.test(x1)) {
                x1 = x1.replace(rgx, '$1' + ',' + '$2');
            }
            return x1 + x2;
        }
	
        // 어드민
        $scope.delAttachment = function(attachmentIndex, attachmentIdx, serverFileName, attachmentExt) {
        	
        	var serverFile = serverFileName + "." + attachmentExt;
        	
        	//alert("attachmentIdx : " + attachmentIdx + "\nserverFile : " + serverFile);
        	
			var param = {
        			attachmentIdx : attachmentIdx,
        			serverFileName : serverFile
				};

            var req = {
    			    method: 'GET',
    			    url: '/rg/deleteAttachment.do',
    			    params: param
            	};

			$http(req).then(function successCallback(response) {

				if (attachmentIndex > -1) {
					$scope.attachmentList.splice(attachmentIndex, 1);
				}
        	    
        		alert("삭제되었습니다.");
        		
        		if (attachmentIndex == -1) {
        			$route.reload();
        		}
        		
			}, function errorCallback(response) {
				$log.debug(response);
				alert("에러가 발생하였습니다.");
			});
        }
        
        $scope.checkFileExists = function() {

        	//alert("1");
        	
            var req = {
    			    method: 'GET',
    			    url: "/rg/checkAttachmentExists.do"
            };
            
            //alert("2");
            
			$http(req).then(function successCallback(response) {
        		alert("검사하였숩니다.");
        		$route.reload();
			}, function errorCallback(response) {
				$log.debug(response);
				alert("에러가 발생하였습니다.");
			});        	
        }

        

		var listIdxs = null;
		var listFilenames = null;

    	$scope.downloadFileMultiple = function() {
    		
    		downloadService.downloadFile();

    		/*
    		var param = $.param({});
    		
            var req = {
    			method: 'GET',
    			url: "/fileDownloadMultiple.do?" + param
            };
            
            alert("1");
            
    		$http(req).then(function successCallback(response) {
    			
    			$log.debug("############### 1-1 ##############");
    			$log.debug(response);
    			$log.debug("############### 1-2 ##############");
    			//return;
    			
    			listIdxs = response.data.idxs;
    			listFilenames = response.data.filenames;
    			
    			if (listIdxs != null && listIdxs.length > 0) {
    				downloadFileMultiple(listIdxs.length, 0);
    			}
    			
    		}, function errorCallback(response) {
    			$log.debug(response);
    			alert("error occurred.");
    		});
    		 */
    	};
    	

		function downloadFileMultiple(total, i) {

    		var params = {};
    		var loadingText = 'Loading Data';
    		var options = ['.', '..', '...'];
    		
			if (total > i) {

				$scope.downloadFileText = loadingText + options[0];

				var defer = $q.defer();
				
				var promise = downloadService.validateBeforeDownload(params).then(null, function (reason) {
		      
					alert(reason);
		      
					// you can also throw the error
					// throw reason;
					return $q.reject(reason);
				
				}).then(downloadService.downloadFile([listIdxs[i], listFilenames[i]])).then(function() {
					$scope.downloadFileText = 'Loaded';
				}, function() {
					$scope.downloadFileText = 'Failed';
				}, function(i) {
					i = (i + 1) % 3;
					$scope.downloadFileText = loadingText + options[i];
				});
		
				promise.then(defer.reject, defer.resolve, defer.notify);
				
				promise.finally(function() {
					
					$timeout(function() {
						
						delete $scope.downloadFileText;
						
						//downloadFileMultiple(total, i + 1);
						
					}, 2000);
				});
				
			}
		}

    }]);

/*
myModule.value('Menus', [
    {name: 'HUMANBASE',    url: '/#/company/introduction'},
    {name: 'Service',      url: '/#/service/isp'},
    {name: 'Solution',     url: '/#/solution/costs'},
    {name: 'Recruit',      url: '/#/recruit/figure'},
    {name: 'Info/Contact', url: '/#/contact/itnews'}
]);

myModule.value('Menu_HUMANBASE', [
    {name: '회사소개', url: '/#/company/introduction'},
    {name: '회사연혁', url: '/#/company/history'},
    {name: '주요고객', url: '/#/company/customer'},
    {name: 'CI 소개', url: '/#/company/ci'}
]);
*/


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


appAttachment.factory('batchLog', ['$interval', '$log', function($interval, $log) {
	  
	var messageQueue = [];

	  function log() {
	    //if (messageQueue.length) {
	      //$log.log('batchLog messages: ', messageQueue);
	      //messageQueue = [];
	    //}
	    alert("1");
	  }

	  // start periodic checking
	  //$interval(log, 50000);
	  

	  this.log2 = function () { alert("2"); };
	  
	  return function() {
	    //messageQueue.push(message);
		  alert("1");
	  }
	}]);


appAttachment.service('dataService', function ($http, $q) {
	
	this.test = function() {
		alert("1");
	};
	
    this.doHttpRequest = function (method, url, params)
    {
        var getParams = null;
        var postParams = null;

        if (method == 'GET')
            getParams = params;
        else if (method == 'POST')
            postParams = params;

        var deferred = $q.defer();
        $http({
            method: method,
            url: url,
            params: getParams,
            data: postParams
        }).
         success(function (data, status, headers, config) {
             deferred.resolve(data)
         }).
         error(function (data, status)
         {
             debugger;
             deferred.reject(data);
         });

        return deferred.promise;
    };
    
    this.GetUser = function (sUserId)
    {
        return this.doHttpRequest('GET', '/Home/GetUser', { "sUserId": sUserId });
    };
});

appAttachment.factory('MyService', ['$http', '$q', function ($http, $q) {
    return new function () {

        this.GetName = function () {
            alert("MyName");
        };

        this.doHttpRequest = function (method, url, params)
        {
            var getParams = null;
            var postParams = null;

            if (method == 'GET')
                getParams = params;
            else if (method == 'POST')
                postParams = params;

            var deferred = $q.defer();
            $http({
                method: method,
                url: url,
                params: getParams,
                data: postParams
            }).then(function successCallback(response) {
                // this callback will be called asynchronously
                // when the response is available
            	deferred.resolve(response)
              }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            	  deferred.reject(response);
              });
            /*
             success(function (data, status, headers, config) {
                 deferred.resolve(data)
             }).
             error(function (data, status)
             {
                 debugger;
                 deferred.reject(data);
             });
             */

            return deferred.promise;
        };
        
    };
}]);



// 어드민
function delAttachment2(attachmentIdx, serverFile) {
	var param = {
			attachmentIdx : attachmentIdx,
			serverFileName : serverFile
		};
	$.ajax({
		type: "GET",
		url: '/rg/deleteAttachment.do',
		data: param
	}).done(function( data ) {
		alert("삭제되었습니다.2");
		$( "#tr" + attachmentIdx ).remove();
	}).fail(function() {
		alert("에러가 발생하였습니다.");
	});
}