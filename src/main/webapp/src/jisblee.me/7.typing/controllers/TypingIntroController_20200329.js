var appTypingIntro = angular.module('rg.TypingIntro', ['ngMaterial']);

appTypingIntro.controller('TypingIntroCtrl', ['$scope', '$log', '$http', '$q', '$location', '$routeParams',
	
    function ($scope, $log, $http, $q, $location, $routeParams) {

		var typingIntro = this;

		var start = 0;
		var end = 0;
		
		if ($location.path() == "/typingIntro1") {
			start = 0;
			end = 27;
		} else if ($location.path() == "/typingIntro2") {
			start = 27;
			end = 66;
		}
		
		var param = {
				loginId : $("#loginId").val()
			};
		
        var req = {
			    method: 'POST',
			    url: '/rg/getTypingIntro.do',
			    params: param,
			    headers: {
			        'X-CSRF-TOKEN' : $("#csrfToken").val()
			    }
        	};

		$http(req).then(function successCallback(response) {

			//$log.debug(response);
			//$log.debug($location.path());
			//$log.debug("length", response.data.length);

			for (var i = start; i < end; i++) {
				if (response.data[i].percent != null) {
					$("#" + response.data[i].abbreviationEng).append("<table align='center' class='innerTable'><tr><td class='inner1' width='" + response.data[i].percent + "%'></td><td class='inner2' width='" + (100 - response.data[i].percent) + "%'></td></tr></table>");
					var div = "<div class='inner3'>" + response.data[i].percent + "% (" + response.data[i].completeCount + "/" + response.data[i].bibleChapters + ")</div><div class='inner4'>" + response.data[i].completeTotalCount + "장 완료";
					if (response.data[i].doneCount > 0) {
						div += ", 전체 : " + response.data[i].doneCount;	
					}
					div += "</div>";
					$("#" + response.data[i].abbreviationEng).append(div);
				} else {
					$("#" + response.data[i].abbreviationEng).append("<table align='center' class='innerTable'><tr><td class='inner1' width='0%'></td><td class='inner2' width='100%'></td></tr></table>");
					$("#" + response.data[i].abbreviationEng).append("<div class='inner3'>0% (0/" + response.data[i].bibleChapters + ")");
				}
				//$log.debug($("#" + response.data[i].abbreviationEng).html());
				if (response.data[i].doingCount > 0) {
					document.getElementById(response.data[i].abbreviationEng).classList.add("doing");
				} else if (Number(response.data[i].percent) == 100) {
					document.getElementById(response.data[i].abbreviationEng).classList.add("hundred");
				} else if (Number(response.data[i].percent) == 0) {
					document.getElementById(response.data[i].abbreviationEng).classList.add("zero");
				} else {
					document.getElementById(response.data[i].abbreviationEng).classList.add("partial");
				}
			}

		}, function errorCallback(response) {
			alert("에러가 발생하였습니다.");
		});
		


		typingIntro.list = null;
		
		$scope.bibleName = "";
		$scope.bibleChapters = 1;
		$scope.bibleChaptersList = null;
		
		typingIntro.init = function() {

			$http.get("/rg/getBibleList.do")
			.then(function(response) {
				//$log.debug(response.data);
				$scope.list = typingIntro.list = response.data.slice(start, end);
				$scope.bibleName = typingIntro.list[0].bibleName;
				$scope.bibleChaptersList = typingIntro.makeBibleChaptersList(typingIntro.list[0].bibleName, typingIntro.list[0].bibleChapters);
			});

			var param = {
					pageNo : 1,
					loginId : $("#loginId").val()
				};

            var req = {
    			    method: 'POST',
    			    url: '/rg/getTypingRecord.do',
    			    params: param,
    			    headers: {
    			        'X-CSRF-TOKEN' : $("#csrfToken").val()
    			    }
            	};

			$http(req).then(function successCallback(response) {
				//if (response.data.totalCount < 7) {
					//$("#typingMessage").html(" &nbsp; &nbsp; 한 행을 타이핑하시고 엔터를 누르시면 되겠습니다.");
				//}
			}, function errorCallback(response) {
				alert("에러가 발생하였습니다.");
			});
			
		}

		typingIntro.makeBibleChaptersList = function(phrase, articleCount, bibleChapter) {
			
			var deferred= $q.defer();
			
			var param = {
					phrase : phrase,
					loginId : $("#loginId").val()
				};
			
            var req = {
    			    method: 'POST',
    			    url: '/rg/getRecordCountList.do',
    			    params: param,
    			    headers: {
    			        'X-CSRF-TOKEN' : $("#csrfToken").val()
    			    }
            	};

			$http(req).then(function successCallback(response) {
				
				var chapterList = new Array();
				
				for (var i=1; i <= articleCount; i++) {
					var cnt = typeof response.data[phrase + " " + i + "장"] == "undefined" ? 0 : response.data[phrase + " " + i + "장"];
					chapterList.push({ index : i, 
						count : cnt, 
						string : i + "장" + (cnt > 0 ? " (" + addCommas(cnt) + ")" : "")
					});
				}
				
				deferred.resolve(chapterList);
					
				$scope.bibleChapters = bibleChapter == null ? 1 : bibleChapter;
				
			}, function errorCallback(response) {
				alert("에러가 발생하였습니다.");
			});
			
			return deferred.promise;
			
		}

		typingIntro.init();

		function addCommas(nStr) {
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

		$scope.bibleSelect = typingIntro.bibleSelect = function() {
			$scope.bibleChapters = doingChapter = 1;
			for (var i=0; i < typingIntro.list.length; i++) {
				if (typingIntro.list[i].bibleName == $scope.bibleName) {
					$scope.bibleChaptersList = typingIntro.makeBibleChaptersList(typingIntro.list[i].bibleName, typingIntro.list[i].bibleChapters, $scope.bibleChapters);
					break;
				}
			}
		}

		$scope.startTyping = function() {
			for (var i=0; i < typingIntro.list.length; i++) {
				if (typingIntro.list[i].bibleName == $scope.bibleName) {
					location.href = "#/typing/" + typingIntro.list[i].abbreviationEng + "/" + $scope.bibleChapters;
					break;
				}
			}
		}

	}
]);
