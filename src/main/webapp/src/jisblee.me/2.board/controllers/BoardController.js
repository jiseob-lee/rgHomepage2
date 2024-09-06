var appBoard = angular.module('rg.Board', ['ui.tinymce', 'ngFileUpload']);

appBoard.controller('BoardCtrl', 
    function ($scope, $log, $window, $http, $location, $routeParams, $sce, Upload, $httpParamSerializerJQLike, $timeout, $document) {
        
    	var board = this;
    	
    	$scope.boardList = null;

    	//alert("at board : " + location.href);

    	//$log.debug("routeParams", $routeParams);
    	
    	//alert("$routeParams[boardNo] : " + $routeParams['boardNo']);
    	
        board.loginUserName = $scope.loginUserName = $scope.$parent.loginUserName;
        board.loginUserId = $scope.loginUserId = $scope.$parent.loginId;

        var url = $location.path().split('/');
        
        //$log.debug("URL", url);
        //console.log("123", url);

        $scope.boardNo = typeof $routeParams['boardNo'] == "undefined" || $routeParams['boardNo'] == "" ? "0" : $routeParams['boardNo'];
        $scope.pageNo = typeof $routeParams['pageNo'] == "undefined" || $routeParams['pageNo'] == "" ? "1" : $routeParams['pageNo'];

		//alert("$scope.boardNo : " + $scope.boardNo);
        
        $scope.openYn = typeof $routeParams['openYn'] == "undefined" || $routeParams['openYn'] == "" ? "0" : $routeParams['openYn'];
        
        $scope.searchKind = typeof $routeParams['searchKind'] == "undefined" || $routeParams['searchKind'] == "" ? "both" : $routeParams['searchKind'];
        $scope.searchValue = typeof $routeParams['searchValue'] == "undefined" || $routeParams['searchValue'] == "" ? "" : $routeParams['searchValue'];
		
		if ($scope.searchValue) {
			$scope.searchValue = decodeURIComponent($scope.searchValue);
		}
		
		//if ($scope.searchKind) {
			//document.frm.searchKind.value = $scope.searchKind;
		//}
		
		//$scope.searchKind = "subject";

		$scope.searchKindOptions = [
			{ code:'subject', name:'제목' },
			{ code:'content', name:'본문' },
			{ code:'both', name:'제목과 본문' },
		];
		
		//for (var i=0; i < $scope.searchKindOptions.length; i++) {
			//if ($scope.searchKindOptions[i].code) {
				
			//}
		//}
		
		//$scope.searchKind = $scope.searchKindOptions[2];
		
        $scope.$parent.action = null;

        $scope.content = null;
        $scope.contentKor = null;
        $scope.contentEng = null;
        
        $scope.subjectKor = null;
        $scope.subjectEng = null;
        
        $scope.eng = "n";
        $scope.language = "korean";
        $scope.language1 = "korean";
        $scope.language2 = "korean";
        
        $scope.tinymceModel = null;
        
        $scope.boardName = "";
        
        //alert(window.location.pathname.substring(0, 3));
        
        if (window.location.pathname.length >= 3 && window.location.pathname.substring(0, 3) == "/rg") {
        	$scope.urlPath = "rg";
        	//alert("1");
        } else {
        	$scope.urlPath = "";
        }
        

        board.manageBoardList = null;
        
    	// 게시판 목록을 구한다.
        var getManageBoardList = "/getManageBoardList.do" + "?openYn=" + ($scope.urlPath == "" || ($scope.urlPath == "rg" && $scope.openYn == "1") ? "1" : "0");
        //var getManageBoardList = "/getManageBoardList.do";
        
        //$log.debug("getManageBoardList URL", getManageBoardList);
        
		$http.get(getManageBoardList)
		.then(function(response) {
			board.manageBoardList = response.data;
			$scope.manageBoardList = board.manageBoardList;
			$scope.$parent.manageBoardList = board.manageBoardList;
			
			//$log.debug("getManageBoardList", board.manageBoardList);
		});
		
		board.getBoardName = function(boardIdx) {
			var boardName = "";
			if (board.manageBoardList != null) {
				for (var i = 0; i < board.manageBoardList.length; i++) {
					if (board.manageBoardList[i].boardIdx == boardIdx) {
						boardName = board.manageBoardList[i].boardName;
						break;
					}
				}
			}
			
			if (boardName != "") {
				return boardName;
			} else {
				return boardIdx;
			}
		}

		
		$scope.boardChange = function() {
			//alert("boardNo : " + $scope.boardNo);
			
			if ($scope.urlPath == "") {
				$window.location.href = "/#/board/list/" + $scope.boardNo + "/1";

			} else if ($scope.urlPath == 'rg' && $routeParams['openYn'] == "1") {
				$window.location.href = "/rg/#/board/list/" + $scope.boardNo + "/1/1";
			
			} else {
				$window.location.href = "/rg/#/board/list/" + $scope.boardNo + "/1";
			}
			
			//$window.location.href = "/rg/#/board/list/" + $scope.boardNo + "/1/" + ($scope.urlPath == "" || ($scope.urlPath == 'rg' && $routeParams['openYn'] == "1") ? "1" : "0");
		}

		$scope.goList = function() {
			//alert("goList.");
			
			if ($scope.searchKind != "" && $scope.searchValue != "") {
				$window.location.href = "/rg/#/board/list/" + $routeParams['boardNo'] + "/" + $routeParams['pageNo'] + (typeof $routeParams['openYn'] == "undefined" ? "" : "/" + $routeParams['openYn']) + "/" + $scope.searchKind + "/" + encodeURIComponent(encodeURIComponent($scope.searchValue));
			} else {
				$window.location.href = "/rg/#/board/list/" + $routeParams['boardNo'] + "/" + $routeParams['pageNo'] + "/" + (typeof $routeParams['openYn'] == "undefined" ? "" : $routeParams['openYn']);
			}
		}

		$scope.goList2 = function(n) {
			//alert("goList.");
			
			if ($scope.searchKind != "" && $scope.searchValue != "") {
				$window.location.href = "/rg/#/board/list/" + $routeParams['boardNo'] + "/" + n + (typeof $routeParams['openYn'] == "undefined" ? "" : "/" + $routeParams['openYn']) + "/" + $scope.searchKind + "/" + encodeURIComponent(encodeURIComponent($scope.searchValue));
			} else {
				$window.location.href = "/rg/#/board/list/" + $routeParams['boardNo'] + "/" + n + "/" + (typeof $routeParams['openYn'] == "undefined" ? "" : $routeParams['openYn']);
			}
		}

		$scope.goList3 = function(n) {
			//alert("goList.");
			
			if ($scope.searchKind != "" && $scope.searchValue != "") {
				$window.location.href = "#/board/list/" + $routeParams['boardNo'] + "/" + n + "/" + $scope.searchKind + "/" + encodeURIComponent(encodeURIComponent($scope.searchValue));
			} else {
				$window.location.href = "#/board/list/" + $routeParams['boardNo'] + "/" + n;
			}
		}

		$scope.enableScrolling = function() {
			window.onscroll = function(){};
			return true;
		}

		$scope.goSearch = function(searchKind, searchValue) {
			//document.frm.searchKind.value, document.frm.searchValue.value)
			console.log("searchKind", searchKind);
			console.log("searchValue", searchValue);
			console.log("searchValue", encodeURIComponent(searchValue));
			if (searchKind != "" && searchValue != "" && typeof searchKind != "undefined" && typeof searchValue != "undefined") {
				//$window.location.href = "/rg/#/board/list/" + $routeParams['boardNo'] + "/1" + (typeof $routeParams['openYn'] == "undefined" ? "" : "/" + $routeParams['openYn']) + "/" + searchKind + "/" + encodeURIComponent(searchValue);
				$window.location.href = "/rg/#/board/list/" + $routeParams['boardNo'] + "/1" + (typeof $routeParams['openYn'] == "undefined" ? "" : "/" + $routeParams['openYn']) + "/" + searchKind + "/" + encodeURIComponent(encodeURIComponent(searchValue));
			} else {
				$window.location.href = "/rg/#/board/list/" + $routeParams['boardNo'] + "/1" + (typeof $routeParams['openYn'] == "undefined" ? "" : "/" + $routeParams['openYn']);
			}
		}

		$scope.goEdit = function() {
			$window.location.href = "/rg/#/board/edit/" + $routeParams['boardNo'] + "/" + $routeParams['pageNo'] + "/" + $routeParams['articleNo'] + "/" + (typeof $routeParams['openYn'] == "undefined" ? "" : $routeParams['openYn']);
		}

		$scope.goDelete = function(csrfName, csrfToken) {

			//alert("1, " + board.loginUserId + ", " + $routeParams['articleNo']);
			
			if (!confirm("삭제하시겠습니까?")) {
				return;
			}
			
			var param = {
					boardArticleIdx : $routeParams['articleNo'],
					userIdModified : board.loginUserId
				};

			//$log.debug(param);
			
            var req = {
    			    method: 'POST',
    			    url: '/rg/deleteBoardArticle.do',
    			    params: param,
    			    headers: {
    			        'X-CSRF-TOKEN' : csrfToken
    			    }
            	};

			$http(req).then(function successCallback(response) {

        		alert("삭제되었습니다.");
        		
        		//$route.reload();
        		$window.location.href = "/rg/#/board/list/" + $routeParams['boardNo'] + "/1";
        		
			}, function errorCallback(response) {
				//$log.debug(response);
				alert("에러가 발생하였습니다.");
			});
		}
		
		
        /*
        // 메뉴명을 구한다.
        if (url.length > 0) {
        	if ($location.path().indexOf("notice") > 0) {
            	board.boardIdx = 1;  // board 테이블 board_idx 값
            	$scope.boardName = "공지사항";
        	} else if ($location.path().indexOf("itnews") > 0) {
            	board.boardIdx = 2;  // board 테이블 board_idx 값
            	$scope.boardName = "IT News";
        	} else if ($location.path().indexOf("hire") > 0) {
            	board.boardIdx = 3;  // board 테이블 board_idx 값
            	$scope.boardName = "채용안내";        

            	board.hireArticleIdx = "1";  // 채용안내 게시글 번호
        	}
        }
        
        
        // 메뉴명을 구한다.
        for (var m in board.menu) {	
        	if (board.menu[m].url.substr(0, board.menu[m].url.lastIndexOf('/')) == "/#" + $location.$$path.substr(0, $location.$$path.lastIndexOf('/'))
        		|| board.menu[m].url.substr(0, board.menu[m].url.lastIndexOf('/')) == "/#" + $location.$$path.substr(0, $location.$$path.lastIndexOf('View'))
        	) {
        		$scope.menuTitle = board.menu[m].name;
        		for (var ms in Menus) {
        			if (Menus[ms].url.split('/')[2] == board.menu[m].url.split('/')[2]) {
        				$scope.mainMenuTitle = Menus[ms].name;
        				$scope.mainMenuUrl = Menus[ms].url;
        				$scope.$parent.subMainImage = Menus[ms].subMainImage;
        			}
        		}
        		break;
        	}
        }
        */
        
        if (url[2] == 'list') {
        	
        	//alert("list");

        	board.pageNo = Number($routeParams['pageNo']);
        	board.pageNo = board.pageNo > 0 ? board.pageNo : 1;
        	board.listLimit = 10;  // 한 페이지 게시글 수
        	board.listOffset = board.listLimit * (board.pageNo - 1);  // 페이지 이동
        	board.pageLinkCount = 10; // 페이지 이동 링크 개수
        	board.pageEnd = 0;
        	
        	$scope.listLimit = board.listLimit;
        	
        	//alert($routeParams['openYn']);
        	
        	if ($scope.urlPath == "" || ($scope.urlPath == 'rg' && $routeParams['openYn'] == "1")) {
        		getBoardList = "/getBoardList.do";
        		$scope.openYn = "1";
        	} else {
        		getBoardList = "/rg/getBoardList.do";
        		$scope.openYn = "";
        	}
            
        	board.listURL = getBoardList;
        	board.listURL += "?" + "boardIdx=" + $routeParams['boardNo'];
        	board.listURL += "&" + "listOffset=" + board.listOffset;
        	board.listURL += "&" + "listLimit=" + board.listLimit;
        	
        	if ($scope.searchKind != "" && $scope.searchValue != "") {
        		board.listURL += "&" + "searchKind=" + $scope.searchKind;
        		board.listURL += "&" + "searchValue=" + $scope.searchValue;
        	}
        	
        	//alert(board.listURL);

        	// 게시판 목록을 구한다.
			$http.get(board.listURL)
			.then(function(response) {
				//$log.debug(typeof response.data, response.data);
				if (response.data.length > 0) {
					$scope.boardList = response.data;
				}
				//$log.debug("boardList", response.data);
			});
			
			
			// 게시판 목록의 총 개수를 구한다.
			var getBoardListCount = ($scope.urlPath == "" || ($scope.urlPath == 'rg' && $routeParams['openYn'] == "1")) ? "/getBoardListCount.do" : "/rg/getBoardListCount.do";
        	
			$http.get(getBoardListCount + "?boardIdx=" + $routeParams['boardNo'] + "&" + "searchKind=" + $scope.searchKind + "&" + "searchValue=" + $scope.searchValue)
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
			
			//alert("1");
			
			
        } else if (url[2] == 'view') {
        	
        	//alert("1-1");
        	
        	$scope.$parent.action = "view";
        	
			$scope.pageNo = ($routeParams['pageNo'] == undefined) ? 1 : $routeParams['pageNo'];
			
			var getBoardContent = $scope.urlPath == 'rg' ? "/rg/getBoardContent.do" : "/getBoardContent.do";
			
			$http.get(getBoardContent + "?boardArticleIdx=" + $routeParams['articleNo'])
			.then(function(response) {
				//$log.debug(response.data);
				$scope.subjectKor = response.data.subject;
				$scope.userNameCreated = response.data.userNameCreated;
				$scope.dateCreated = response.data.dateCreated;
				$scope.hitCount = response.data.hitCount;
				$scope.content = $sce.trustAsHtml(response.data.content);

				$scope.subjectEng = response.data.subjectEng;
				$scope.contentKor = $sce.trustAsHtml(response.data.content);
				$scope.contentEng = $sce.trustAsHtml(response.data.contentEng);
		        
				$scope.subject = $sce.trustAsHtml($scope.subjectKor);
				
				$scope.boardName = response.data.boardName;
				
				//if ("noticeView" == url[2] || "itnewsView" == url[2]) {
				//if ($scope.urlPath == "" && $scope.$parent.loginId == null) {
    		        // 조회수 증가 (프론트)
					//alert("boardArticleIdx : " + $routeParams['articleNo']);
    				//$http.get("/increaseHitCount.do?boardArticleIdx=" + $routeParams['articleNo']);
				//}
			});

        	// 첨부파일 목록을 구한다.
			var getAttachmentList = $scope.urlPath == 'rg' ? "/rg/getAttachmentList.do" : "/getAttachmentList.do";
			$http.get(getAttachmentList + "?boardIdx=" + $routeParams['boardNo'] + "&boardArticleIdx=" + $routeParams['articleNo'])
			.then(function(response) {
		        //$log.debug(response);
		        $scope.attachmentList = response.data;
			});
        
        
        
        } else if (url[2] == 'edit') {

        	board.boardArticleIdx = Number($routeParams['articleNo']);
        	
        	if ($location.path().indexOf("hire") > -1) {
        		board.boardArticleIdx = "1";  // 채용안내
        	}

        	// 게시판 내용을 구한다.
			$http.get("/rg/getBoardContent.do?boardArticleIdx=" + board.boardArticleIdx)
			.then(function(response) {
		        $scope.tinymceModel = $sce.trustAsHtml(response.data.content);
		        
		        $scope.boardIdx = response.data.boardIdx + "";
		        $scope.openYn = response.data.openYn;
		        
		        $scope.contentKor = response.data.content;
		        $scope.contentEng = response.data.contentEng;
		        
		        $scope.subject = response.data.subject;
		        $scope.subjectEng = response.data.subjectEng;
		        
		        $scope.eng = 'n';
			});

        	// 첨부파일 목록을 구한다.
			$http.get("/rg/getAttachmentList.do?boardIdx=" + $routeParams['boardNo'] + "&boardArticleIdx=" + board.boardArticleIdx)
			.then(function(response) {
		        //$log.debug(response);
		        $scope.attachmentList = response.data;
			});
			
			$scope.action = "edit";
        
        	
        } else if (url[2] == 'input') {
        	
        	$scope.boardIdx = ($routeParams['boardNo'] == undefined) ? "0" : $routeParams['boardNo'];
        	$scope.action = "input";
        	$scope.openYn = "n";

        	/*
        	var oEditors = [];
        	nhn.husky.EZCreator.createInIFrame({
        	 oAppRef: oEditors,
        	 elPlaceHolder: "ir1",
        	 sSkinURI: "/assets/js/package/dist/SmartEditor2Skin.html",
        	 fCreator: "createSEditor2"
        	});
        	*/
        	
        } else {  // 프론트 메인 인덱스 페이지
        	
        	//alert("프론트 메인 인덱스 페이지");

        	board.pageNo = typeof $routeParams['pageNo'] == "undefined" ? 1 : Number($routeParams['pageNo']);
        	board.pageNo = board.pageNo > 0 ? board.pageNo : 1;
        	board.listLimit = 10;  // 한 페이지 게시글 수
        	board.listOffset = board.listLimit * (board.pageNo - 1);  // 페이지 이동
        	board.pageLinkCount = 10; // 페이지 이동 링크 개수
        	board.pageEnd = 0;
        	
        	$scope.listLimit = board.listLimit;
        	
        	var boardNo = typeof $routeParams['boardNo'] == "undefined" ? "0" : $routeParams['boardNo'];
            
        	board.listURL = "/getBoardList.do";
        	board.listURL += "?" + "boardIdx=" + boardNo;
        	board.listURL += "&" + "listOffset=" + board.listOffset;
        	board.listURL += "&" + "listLimit=" + board.listLimit;
        	
        	//alert(board.listURL);

        	// 게시판 목록을 구한다.
			$http.get(board.listURL)
			.then(function(response) {
				if (response.data.length > 0) {
					$scope.boardList = response.data;
					//$log.debug(response.data);
				}
			});
			
			
			// 게시판 목록의 총 개수를 구한다.
			$http.get("/getBoardListCount.do?" + "boardIdx=" + boardNo)
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
        
        
        
        $scope.goInput = function() {
        	$window.location.href = "/rg/#/board/input/" + $routeParams['boardNo'] + "/" + $routeParams['pageNo'];
        }
        
        // 프론트
        $scope.frontBoardList = function(pageNo) {
        	
        	var url = $location.path();
        	
        	if (url.indexOf("notice") > 0) {
	        	$window.location.href = '/#/contact/notice/' + pageNo;
        	} else if (url.indexOf("itnews") > 0) {
	        	$window.location.href = '/#/contact/itnews/' + pageNo;
        	}
        }

        // 어드민 게시글 삭제
        $scope.deleteBoard = function(csrfParameterName, csrfToken) {
        	
        	if ($routeParams['articleNo'] == undefined || $routeParams['articleNo'] == null || $routeParams['articleNo'] == "") {
        		alert("게시글 번호가 없습니다.");
        		return;
        	}
        	
        	//alert(Number($routeParams['boardArticleIdx']));

			var param = {
					boardArticleIdx : Number($routeParams['articleNo']),
					userIdModified : $scope.$parent.loginId
				};

            var req = {
    			    method: 'POST',
    			    url: '/rg/deleteBoardArticle.do',
    			    data: $httpParamSerializerJQLike(param),
    			    headers: {
    			        'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8',
    			        'X-CSRF-TOKEN' : csrfToken
    			    }
            };

			$http(req).then(function successCallback(response) {
				//$log.debug(response);
				alert("삭제되었습니다.");

				$window.location.href = "/rg/#/board/list/" + $routeParams['boardNo'] + "/1";
				
				/*
	        	var url = $location.path();
	        	
	        	if (url.indexOf("notice") > 0) {
	        		$window.location.href = '/rg/#/notice';
	        	} else if (url.indexOf("itnews") > 0) {
	        		$window.location.href = '/rg/#/itnews';
	        	}
	        	*/
				
			}, function errorCallback(response) {
				//$log.debug(response);
				alert("에러가 발생하였습니다.");
			});

        }
        
        // 어드민
        $scope.inputPage = function() {
        	var url = $location.path();
        	
        	if (url.indexOf("notice") > 0) {
        		$window.location.href = '/rg/#/noticeInput';
        	} else if (url.indexOf("itnews") > 0) {
        		$window.location.href = '/rg/#/itnewsInput';
        	}
        }
        
        // 어드민
        $scope.inputBoard = function(csrfParameterName, csrfToken) {

        	//alert("-" + $scope.subject + "-");
        	//return;
        	
        	if (typeof $scope.subject == "undefined" || $scope.subject == "") {
        		alert("제목을 입력해주시기 바랍니다.");
        		return;
        	}

        	
        	if ($scope.eng == 'y') {
        		$scope.contentEng = $sce.valueOf($scope.tinymceModel);
        	} else if ($scope.eng == 'n') {
        		$scope.contentKor = $sce.valueOf($scope.tinymceModel);
        	}
        	
        	
        	var inputFiles = document.getElementsByName("attachmentIdx");
        	var attachmentIdxs = [];
        	for (var i=0; i<inputFiles.length; i++) {
        		attachmentIdxs.push(inputFiles[i].value);
        	}
        	
        	$log.debug(attachmentIdxs);

			var param = {
					boardIdx : $scope.boardIdx,
					subject : $scope.subject,
					subjectEng : $scope.subjectEng,
					content : $sce.valueOf($scope.contentKor),
					contentEng : $sce.valueOf($scope.contentEng),
					userIdCreated : $scope.$parent.loginId,
					attachmentIdxs : attachmentIdxs,
					openYn : $scope.openYn
				};

			//alert($httpParamSerializerJQLike(param));
			
            var req = {
    			    method: 'POST',
    			    url: '/rg/insertBoardArticle.do',
    			    //data: $httpParamSerializerJQLike(param),
    			    data: $.param(param),
    			    //data: { content : $scope.tinymceModel },
    			    //transformRequest: angular.identity,
    			    headers: {
    			        //'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8',
    			        'Content-Type' : 'application/x-www-form-urlencoded',
    			        //'Content-Type' : 'application/octet-stream',
    			        'X-CSRF-TOKEN' : csrfToken
    			    }
            };

			$http(req).then(function successCallback(response) {
				//$log.debug(response);
				alert("입력되었습니다.");
				
				$window.location.href = "/rg/#/board/list/" + $scope.boardIdx + "/1";

				/*
	        	var url = $location.path();
	        	
	        	if (url.indexOf("notice") > 0) {
	        		$window.location.href = '/rg/#/notice/1';
	        	} else if (url.indexOf("itnews") > 0) {
	        		$window.location.href = '/rg/#/itnews/1';
	        	}
	        	*/
				
			}, function errorCallback(response) {
				//$log.debug(response);
				alert("에러가 발생하였습니다.");
			});

        }
        
        // 어드민
        $scope.updateBoard = function(csrfParameterName, csrfToken) {
        	
        	//alert("-" + $scope.subject + "-");
        	//return;
        	
        	if (typeof $scope.subject == "undefined" || $scope.subject == "") {
        		alert("제목을 입력해주시기 바랍니다.");
        		return;
        	}

        	
        	if ($scope.eng == 'y') {
        		$scope.contentEng = $sce.valueOf($scope.tinymceModel);
        	} else if ($scope.eng == 'n') {
        		$scope.contentKor = $sce.valueOf($scope.tinymceModel);
        	}
        	

        	var inputFiles = document.getElementsByName("attachmentIdx");
        	var attachmentIdxs = [];
        	for (var i=0; i<inputFiles.length; i++) {
        		attachmentIdxs.push(inputFiles[i].value);
        	}
        	
        	$log.debug("attachmentIdxs", attachmentIdxs);
        	//alert($scope.subject);

        	var boardArticleIdx = $routeParams['articleNo'];
        	
        	if ($location.path().indexOf("hire") > -1) {
        		boardArticleIdx = board.hireArticleIdx;  // 채용안내
        	}
        	
        	var boardIdx = $scope.boardIdx;
        	
			var param = {
					boardIdx : boardIdx,
					subject : $scope.subject,
					subjectEng : $scope.subjectEng,
					content : $sce.valueOf($scope.contentKor),
					contentEng : $sce.valueOf($scope.contentEng),
					userIdModified : $scope.$parent.loginId,
					boardArticleIdx : boardArticleIdx,
					attachmentIdxs : attachmentIdxs,
					openYn : $scope.openYn
				};
			
			//$log.debug("################## 1-1 ################");
			//$log.debug("11111", param);
			//$log.debug("################## 1-2 ################");

            var req = {
    			    method: 'POST',
    			    url: '/rg/updateBoardArticle.do',
    			    data: $httpParamSerializerJQLike(param),
    			    headers: {
    			        'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8',
    			        'X-CSRF-TOKEN' : csrfToken
    			    }
            };

			$http(req).then(function successCallback(response) {
				//$log.debug(response);
				alert("수정되었습니다.");
				
				$window.location.href = "#/board/list/" + boardIdx + "/1" + "/" + (typeof $routeParams['openYn'] == "undefined" ? "" : $routeParams['openYn']);

	        	//var url = $location.path();
	        	
	        	/*
	        	if (url.indexOf("notice") > 0) {
	        		$window.location.href = '/rg/#/notice/' + $routeParams['pageNo'];
	        	} else if (url.indexOf("itnews") > 0) {
	        		$window.location.href = '/rg/#/itnews/' + $routeParams['pageNo'];
	        	}
	        	*/
	        	
			}, function errorCallback(response) {
				//$log.debug(response);
				alert("에러가 발생하였습니다.");
			});
            
        }

        // 어드민
        $scope.boardListPage = function() {
        	var url = $location.path();
        	
        	if (url.indexOf("notice") > 0) {
        		$window.location.href = '/rg/#/notice/' + $routeParams['pageNo'];
        	} else if (url.indexOf("itnews") > 0) {
        		$window.location.href = '/rg/#/itnews/' + $routeParams['pageNo'];
        	}
        }
        
        // 어드민
		$scope.tinymceOptions = {
		    language: 'ko_KR',
		    selector: "#content",
			license_key: 'gpl',
		    content_css : '/assets/css/tinymce.css, /assets/css/backgroundPartialFill.css, /assets/css/board_20200907.css',
		    content_style: 'body { font-size: 13pt; }',
		    //plugins: "advlist autolink link image imagetools charmap print preview table hr textcolor colorpicker lists contextmenu code",
		    plugins: "advlist autolink link image charmap preview table lists code importcss",
		    toolbar: 'forecolor backcolor | undo redo | bold italic | numlist bullist outdent indent | link image table | paste | fontsizeselect',
		   	fontsize_formats: '8pt 10pt 12pt 13pt 14pt 18pt 24pt 36pt',
		    // enable title field in the Image dialog
		    image_title: true, 
		    // enable automatic uploads of images represented by blob or data URIs
		    automatic_uploads: false,
		    // URL of our upload handler (for more details check: https://www.tinymce.com/docs/configure/file-image-upload/#images_upload_url)
		    // images_upload_url: 'postAcceptor.php',
		    // here we add custom filepicker only to Image dialog
		    file_picker_types: 'image', 
		    
		    branding: false,
		    
		    promotion: false,
		    
		    relative_urls : false,
		    //remove_script_host : false,
		    //convert_urls : true,
		    
		    // and here's our custom image picker
		    file_picker_callback: function(cb, value, meta) {
		      var input = document.createElement('input');
		      input.setAttribute('type', 'file');
		      input.setAttribute('accept', 'image/*');
		      
		      // Note: In modern browsers input[type="file"] is functional without 
		      // even adding it to the DOM, but that might not be the case in some older
		      // or quirky browsers like IE, so you might want to add it to the DOM
		      // just in case, and visually hide it. And do not forget do remove it
		      // once you do not need it anymore.

		      input.onchange = function() {
		        var file = this.files[0];
		        /*
		        var reader = new FileReader();
		        reader.onload = function () {
		          // Note: Now we need to register the blob in TinyMCEs image blob
		          // registry. In the next release this part hopefully won't be
		          // necessary, as we are looking to handle it internally.
		          var id = 'blobid' + (new Date()).getTime();
		          var blobCache =  tinymce.activeEditor.editorUpload.blobCache;
		          var base64 = reader.result.split(',')[1];
		          var blobInfo = blobCache.create(id, file, base64);
		          blobCache.add(blobInfo);

		          // call the callback and populate the Title field with the file name
		          cb(blobInfo.blobUri(), { title: file.name });
		        };
		        reader.readAsDataURL(file);
		        */

	        	var ext = "";
	        	if (file.name.lastIndexOf(".") > -1) {
	        		ext = file.name.substr(file.name.lastIndexOf(".") + 1);
	        	} else {
	        		alert("업로드가 불가한 파일입니다.");
	        		return;
	        	}
	        	
	        	// 파일 크기 검사
	        	if (Number(file.size) > 50000000000) {
	        		alert("파일 크기가 제한 용량을 초과하였습니다.\n파일 크기 : " + file.size);
	        		return;
	        	}
	        	
	        	//alert(file.name);
	        	
	        	var data = {
	                file: this.files,
	                attachmentExt: ext,
	                userIdCreated: $scope.$parent.loginId,
	                boardIdx: 'img',
	                boardArticleIdx: board.boardArticleIdx == null || board.boardArticleIdx == "" ? "0" : board.boardArticleIdx
	            };
	        	
	        	$scope.progress = "true";
	        	
	            file.upload = Upload.upload({
	                url: '/rg/insertAttachment.do',
	                data: data,
				    headers: {
				    	"Content-Type": file.type != '' ? file.type : 'application/octet-stream',
				        'X-CSRF-TOKEN' : $("#csrfToken").val()
				    }
	            });

	            file.upload.then(function (response) {
	                $timeout(function () {
	                	console.log(response.data);
	                    //file.result = response.data;
	                	var imgFile = "/assets/imageUploaded/" + response.data.serverFileName.substring(0, 4) + "/" 
	                					+ response.data.serverFileName + "." + response.data.attachmentExt
	                    cb(imgFile, { title: file.name });
	                    //$log.debug(file.result);
	                    $scope.progress = "false";
	                    //board.addFileInput(file.result, csrfParameterName, csrfToken);
	                    
	                    // input file 초기화
	                    //angular.forEach(
	                    	//angular.element("input[type='file']"),
	                    	//function(inputElem) {
	                    	    //angular.element(inputElem).val(null);
	                    	//}
	                    //);
	                });
	            }, function (response) {
	                if (response.status > 0) {
	                	$scope.errorMsg = response.status + ': ' + response.data;
	                }
	            }, function (evt) {
	                // Math.min is to fix IE which reports 200% sometimes
	                file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
	            });
	            
		      };
		      
		      input.click();
		    }
		};
		
        // 어드민
        $scope.uploadFile = function($event, file, csrfParameterName, csrfToken) {
        	//$log.debug(file);
        	
        	var ext = "";
        	if (file != null && file[0].name.lastIndexOf(".") > -1) {
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
                boardIdx: $scope.boardIdx == null || $scope.boardIdx == "" ? "0" : $scope.boardIdx,
                boardArticleIdx: board.boardArticleIdx == null || board.boardArticleIdx == "" ? "0" : board.boardArticleIdx
            };
        	
        	
        	var formData = new FormData();
        	formData.append('file', $('input[type=file]')[0].files[0]);
        	formData.append('attachmentExt', ext);
        	formData.append('userIdCreated', $scope.$parent.loginId);
        	formData.append('boardIdx', $scope.boardIdx == null || $scope.boardIdx == "" ? "0" : $scope.boardIdx);
        	formData.append('boardArticleIdx', board.boardArticleIdx == null || board.boardArticleIdx == "" ? "0" : board.boardArticleIdx);
        	
        	//alert($scope.$parent.loginId);
        	$log.debug(formData);
        	
        	$.ajax({
        	    url: '/rg/insertAttachment.do',
        	    data: formData,
        	    type: 'POST',
        	    contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
        	    processData: false, // NEEDED, DON'T OMIT THIS
        	    beforeSend : function(xhr){
                    xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
                },
                success : function(response) {
                    $log.debug(response);

                    board.addFileInput(response, csrfParameterName, csrfToken);
                    
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
        	$scope.progress = "true";
        	
            file.upload = Upload.upload({
                url: '/rg/insertAttachment.do',
                data: data,
			    headers: {
			    	"Content-Type": file.type != '' ? file.type : 'application/octet-stream',
			        'X-CSRF-TOKEN' : csrfToken
			    }
            });

            file.upload.then(function (response) {
                $timeout(function () {
                    file.result = response.data;
                    //$log.debug(file.result);
                    $scope.progress = "false";
                    board.addFileInput(file.result, csrfParameterName, csrfToken);
                    
                    // input file 초기화
                    angular.forEach(
                    	angular.element("input[type='file']"),
                    	function(inputElem) {
                    	    angular.element(inputElem).val(null);
                    	}
                    );
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
        
        board.addFileInput = function(fileResult, csrfParameterName, csrfToken) {
            
        	var appendHtml = "<tr id='tr" + fileResult.attachmentIdx + "'><td>";
        	appendHtml += "<input type='hidden' id='attachmentIdx_" + fileResult.attachmentIdx + "' name='attachmentIdx' value='" + fileResult.attachmentIdx + "' /> "
        	appendHtml += "<a href=\"/fileDownload.do?idx=" + fileResult.attachmentIdx + "&filename=" + fileResult.serverFileName + "\">";
        	appendHtml += fileResult.attachmentName + "." + fileResult.attachmentExt + "</a> ("+ board.addCommas(fileResult.attachmentSize) + " byte) ";
        	appendHtml += "<input type='button' value='삭제' ";
        	appendHtml += "onclick=\"delAttachment('" + fileResult.attachmentIdx + "', '" + fileResult.serverFileName + "." + fileResult.attachmentExt + "')\" />";
        	appendHtml += "</td></tr>";
        	
        	$('#tableFile tr:last').after(appendHtml);
        }
        
        board.addCommas = function(nStr) {
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

        	    //$scope.attachmentList.splice(attachmentIndex, 1);
        	    
        	    $("#attachmentIdx_" + attachmentIdx).parent().parent().remove();
        	    
        		alert("삭제되었습니다.");
        		
			}, function errorCallback(response) {
				$log.debug(response);
				alert("에러가 발생하였습니다.");
			});
        }
        
        
        $scope.changeLanguage = function() {
        	//alert("eng : " + $scope.eng);
        	
        	if ($scope.eng == 'y') {
        		$scope.contentKor = $sce.valueOf($scope.tinymceModel);
        		$scope.tinymceModel = $sce.trustAsHtml($scope.contentEng);
        	} else if ($scope.eng == 'n') {
        		$scope.contentEng = $sce.valueOf($scope.tinymceModel);
        		$scope.tinymceModel = $sce.trustAsHtml($scope.contentKor);
        	}
        	
        	//$log.debug("한글", $scope.content);
        	//$log.debug("영어", $scope.contentEng);
        }
        
        $scope.changeViewLanguage = function(no) {
        	if (no == 1) {
        		$scope.language = $scope.language2 = $scope.language1;
        	} else if (no == 2) {
        		$scope.language = $scope.language1 = $scope.language2;
        	}
        	
	    	if ($scope.language == "korean") {
	    		$scope.subject = $sce.trustAsHtml($scope.subjectKor);
	    		$scope.content = $scope.contentKor;
	    	} else if ($scope.language == "english") {
	    		$scope.subject = $sce.trustAsHtml($scope.subjectKor + "<br/>" + $scope.subjectEng);
	    		$scope.content = $scope.contentEng;
	    	}
        }

    }
);

appBoard.filter('range', function() {
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

appBoard.directive('fileChange', ['$parse', '$log', function($parse, $log) {

  return {
    require: 'ngModel',
    restrict: 'A',
    link: function ($scope, element, attrs, ngModel) {
    	
      //$log.debug(attrs);

      // Get the function provided in the file-change attribute.
      // Note the attribute has become an angular expression,
      // which is what we are parsing. The provided handler is 
      // wrapped up in an outer function (attrHandler) - we'll 
      // call the provided event handler inside the handler()
      // function below.
      var attrHandler = $parse(attrs['fileChange']);
      
      //alert("typeof attrHandler : " + typeof attrHandler);
      
      

      // This is a wrapper handler which will be attached to the
      // HTML change event.
      var handler = function (e, csrf, token) {
    	  
    	//alert("typeof e : " + typeof e);

        $scope.$apply(function () {

          // Execute the provided handler in the directive's scope.
          // The files variable will be available for consumption
          // by the event handler.

          //attrHandler($scope, { $event: e, files: e.target.files, csrf, token });
          attrHandler($scope, { $event: e, files: e.target.files });
        });
      };

      // Attach the handler to the HTML change event 
      element[0].addEventListener('change', handler, false);
    }
  };
}]);


// 어드민
function delAttachment(attachmentIdx, serverFile) {
	var param = {
			attachmentIdx : attachmentIdx,
			serverFileName : serverFile
		};
	$.ajax({
		type: "GET",
		url: '/rg/deleteAttachment.do',
		data: param
	}).done(function( data ) {
		alert("삭제되었습니다.1");
		$( "#attachmentIdx_" + attachmentIdx ).parent().parent().remove();
	}).fail(function() {
		alert("에러가 발생하였습니다.");
	});
}

appBoard.filter('unsafe', function($sce) { return $sce.trustAsHtml; });