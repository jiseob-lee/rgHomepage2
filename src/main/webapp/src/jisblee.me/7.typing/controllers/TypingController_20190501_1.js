var appTyping = angular.module('rg.Typing', ['ngMaterial']);

appTyping.controller('TypingCtrl', ['$scope', '$log', '$http', '$compile', '$mdDialog', '$q', '$location', '$routeParams',
	
    function ($scope, $log, $http, $compile, $mdDialog, $q, $location, $routeParams) {

		var typing = this;
		
		typing.list = null;
		$scope.count = typing.count = 0;
		
		$scope.typing = [];
		typing.typing = [];
		typing.time = [];
		typing.typingTemporarySaving = [];
		typing.bibleName = "";
		typing.bibleChapters = 0;
		
		$scope.status = '  ';
		$scope.customFullscreen = false;
		
		typing.activeElementIndex = -1;
		
		typing.title = "";
		
		typing.abbreviationEng = "";
		
		var doingChapter = 1;

        typing.enterKeyInputed = false;
        
        
		/*
		$scope.$watch('typing', function(newVal, oldVal, scope) {

			if (newVal !== oldVal) {

				var id = $(document.activeElement).attr("id");
				
				if (id != null) {
					var index = Number(id.substring(id.indexOf("_") + 1));
					typing.activeElementIndex = index;
	        		typing.checkTyping(index);
	        		
	        		if ($("#typing_" + index).val() == "") {
	        			typing.typing[index] = 0;  // 모두 삭제이면 미입력으로 바꿈
	        		}
				}
        	}
        }, true);
		*/
		
		$scope.checkKeyUp = typing.checkKeyUp = function(e, index) {

			var returnValue = null;
			
			var val = $("#typing_" + index).val();
			
			var keyCode = e.which ? e.which : e.keyCode;
			//$log.debug("keyCode", keyCode);
			var functionalKey = false;
			switch (keyCode) {
			case 36 : //home
			case 35 : //end
			case 38 : //up
			case 40 : //down
			case 37 : //left
			case 39 : //right
			case 46 : //delete
				if (e.target.oldvalue != val) {
					//$log.debug("7", val);
					returnValue = val;
					typing.typing[index].length = val.length;
				}
				functionalKey = true;
				break;
			case 8 : //backspace
				if (e.target.oldvalue != val) {
					if (isHan(val.substring(val.length - 1))) {
						//$log.debug("6", val);
						returnValue = val.substring(0, val.length - 1);
						typing.typing[index].length = val.length - 1;
					} else {
						//$log.debug("5", val);
						returnValue = val;
						typing.typing[index].length = val.length;
					}
				}
				functionalKey = true;
				break;
			default : break;
			}
			
			if (!functionalKey) {
				if (typing.typing[index].length > val.length) {
					//$log.debug("4", val);
					returnValue = val;
					typing.typing[index].length = val.length;
				} else {
					//$log.debug(isHan(val.substring(val.length - 1)), val.substring(val.length - 1));
					if (isHan(val.substring(val.length - 1))) {
						//$log.debug("1", val.substring(0, val.length - 1));
						returnValue = val.substring(0, val.length - 1);
						typing.typing[index].length = val.length - 1;
					} else {
						//$log.debug("3", val);
						returnValue = val;
						typing.typing[index].length = val.length;
					}
				}
			}

			if (returnValue != null) {
				typing.activeElementIndex = index;
	    		typing.checkTyping(index, returnValue);
	    		
	    		if ($("#typing_" + index).val() == "") {
	    			typing.typing[index].status = 0;  // 모두 삭제이면 미입력으로 바꿈
	    		}
			}
		}
        

		$scope.checkTyping = typing.checkTyping = function(index, val, trim) {
			
			var deferred1 = $q.defer();
			
			typing.activeElementIndex = index;
			var wrong = false;
			
			// 색상 초기화
			$("[id^=sourceSpan_" + index + "]").css("background-color", "#053c4f");
			
			var source = $("#sourceHidden_" + index).val();
			
			//var target = $("#typing_" + index).val();
			var target = val == null ? $("#typing_" + index).val() : val;
			if (trim) {
				target = target.trim();
			}
			//$log.debug("target", target);

			for (var i=0; i < source.length; i++) {
				
				if (i >= target.length) {
					$("#sourceSpan_" + index + "_" + i).css("background-color", "#053c4f");
				
				} else {
					if (source.charAt(i) != target.charAt(i)) {
						$("#sourceSpan_" + index + "_" + i).css("background-color", "#ff8080");
						wrong = true;
					} else {
						$("#sourceSpan_" + index + "_" + i).css("background-color", "#053c4f");
					}
				}
			}
			
			if (typeof target == "string" && target.length > 0) {
				
				typing.typing[index].status = -1;
				
				if (typing.time[index].startTime == null) {
					typing.time[index].startTime = new Date();
				}
			}
			
			if (target == "") {

				$("[id^=sourceSpan_" + index + "]").css("background-color", "#053c4f");
				
				typing.typing[index].status = 0;
				
				typing.time[index].startTime = null;
				typing.time[index].endTime = null;
				
				typing.typingTemporarySaving[index] = 0;
			}
			
			var check = 0;
			
			if (target.length >= source.length) {
				
				if (wrong) {
					typing.typing[index].status = -1;
				
				} else {
					typing.typing[index].status = 1;
					
					if (typing.time[index].startTime != null && typing.time[index].endTime == null) {
						typing.time[index].endTime = new Date();
					}

					// 타이핑 내역 저장
					if (typing.typingTemporarySaving[index] == 0) {
						
						var resultString = "";
						for (var i=0; i < typing.count; i++) {
							if (typing.typing[i].status == "1") {
								resultString += "1";
							} else {
								resultString += "0";
							}
						}
						
						var timeString = "";

						for (var i=0; i < typing.count; i++) {
							if (timeString.length > 0) {
								timeString += "|";
							}
							
							if (typing.time[i].startTime != null && typing.time[i].endTime != null 
									&& typing.time[i].startTime < typing.time[i].endTime) {
								timeString += typing.time[i].endTime - typing.time[i].startTime;

							} else if (typing.time[i].elapsedTime > 0) {
								timeString += typing.time[i].elapsedTime;

							} else {
								timeString += "0";
							}
						}
						
						var param = {
								userId : $("#loginId").val(),
								phrase : typing.abbreviationEng,
								chapter : $scope.bibleChapters,
								resultString : resultString,
								timeString : timeString
							};
						
			            var req = {
			    			    method: 'POST',
			    			    url: '/rg/putTemporaryTypingStorage.do',
			    			    params: param
			            	};
			            
			            var check = 1;
	
						$http(req).then(function successCallback(response) {
							typing.typingTemporarySaving[index] = 1;
							deferred1.resolve();
						}, function errorCallback(response) {
							alert("에러가 발생하였습니다.");
							deferred1.reject("ERROR");
						});
					
					}
				}
			}
			
			if (check == 0) {
				deferred1.resolve();
			}
			
			//deferred1.reject("ERROR");
			return deferred1.promise;
		};
		
		typing.getContent = function(bibleName, bibleChapters, abbreviationKor, abbreviationEng) {
			
			typing.title = bibleName + " " + bibleChapters + "장";
			
			var param = {
					bibleName : bibleName,
					bibleChapters : bibleChapters,
					abbreviationKor : abbreviationKor,
					abbreviationEng : abbreviationEng
				};
			
            var req = {
    			    method: 'POST',
    			    url: '/rg/getTypingContent.do',
    			    params: param
            	};

			$http(req).then(function successCallback(response) {
        	
				$("#sourceDiv").empty();
				
				$scope.typing = [];
				
				$scope.count = typing.count = response.data.length;
				
				for (var i = 0; i < typing.count; i++) {
					
					$("#sourceDiv").append("<div class='typing1' id='sourceDiv_" + i + "'></div>");
					$("#sourceDiv").append('<input type="hidden" id="sourceHidden_' + i + '" value="' + response.data[i].replace(/"/g, "\"") + '" />');
					
					var input = angular.element('<input class="typing2" id="typing_' + i + '" ng-model="typing[' + i + ']" type="text" my-enter="checkResult(' + i + ')" onfocus="this.oldvalue = this.value;" onkeydown="this.oldvalue = this.value;" ng-keyup="checkKeyUp($event, ' + i + ')" ng-blur="checkTypingBlur(' + i + ')" ng-click="checkTyping(' + i + ')" ng-trim="false" onpaste="return false;" ondrop="return false;" />');
					var compile = $compile(input)($scope);
					$("#sourceDiv").append(compile);
					
					$("#sourceDiv").append("<div style='line-height: 52%;'><br/><br/></div>");
			
					var str = response.data[i];

					for (var j = 0; j < str.length; j++) {
						var c = str.charAt(j);
						$("#sourceDiv_" + i).append("<span id='sourceSpan_" + i + "_" + j + "'>" + c + "</span>");
					}

					$('#typing_' + i).width( (Math.ceil(typing.getTextLength(str) * 9.5) + 48) + "px" );
					$('#typing_' + i).attr('maxlength', str.length + 2);
				}
				
				typing.setClear();
				
				
				typing.typing = new Array(typing.count);
				typing.typingLength = new Array(typing.count);
				typing.time = new Array(typing.count);
				typing.typingTemporarySaving = new Array(typing.count);
				
				for (var i = 0; i < typing.count; i++) {
					typing.typing[i] = { status : 0,  // 0 : 미입력, -1 : 틀림 있음, 1 : 틀림 없음.
										 length : 0 };  // 입력된 글자 수
					typing.time[i] = { startTime : null, endTime : null, elapsedTime : 0 };
					typing.typingTemporarySaving[i] = 0;  // 0 : 임시 저장 안함, 1: 임시 저장함
				}
				
				
				var top = document.getElementById( "typing_1" ).documentOffsetTop() - ( window.innerHeight / 2 );
				window.scrollTo( 138, top + 100);
				
				
				
				
				

				// 임시 저장 내역을 가져온다
				var param = {
						phrase : abbreviationEng,
						chapter : bibleChapters,
						userId : $("#loginId").val()
					};
				
	            var req = {
	    			    method: 'POST',
	    			    url: '/rg/getTemporaryTypingStorage.do',
	    			    params: param
	            	};

				$http(req).then(function successCallback(response) {
					
					//console.log("getTemporaryTypingStorage.do", response);
					
					if (response.data.resultString != null) {
						for (var i=0; i < typing.count; i++) {
							if (response.data.resultString.substr(i, 1) == "1") {
								$('#typing_' + i).val($('#sourceHidden_' + i).val());
								typing.typing[i].status = 1;
								typing.typingTemporarySaving[i] = 1;
							}
						}
						
						//alert(Array(typing.count + 1).join("1") + "\n" + response.data.resultString);
						
						if (Array(typing.count + 1).join("1") == response.data.resultString) {
							$("#preTyped").val("1");
						} else {
							$("#preTyped").val("0");
						}
					}

					if (response.data.timeString != null) {
						var timeArr = response.data.timeString.split("|");
						for (var i=0; i < typing.count; i++) {
							typing.time[i].elapsedTime = Number(timeArr[i]);
						}	
					}
					
				}, function errorCallback(response) {
					alert("에러가 발생하였습니다.");
				});
				
				
				
			}, function errorCallback(response) {
				alert("에러가 발생하였습니다.");
			});

		}


        $scope.checkTypingBlur = function(index) {
            //console.log("enterKeyInputed", typing.enterKeyInputed);
            
            if (!typing.enterKeyInputed) {
                typing.checkTyping(index, null, true);
            }
            typing.enterKeyInputed = false;
        }
        
		/**
		 * 한글포함 문자열 길이를 구한다
		 */
		typing.getTextLength = function (str) {
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

		$scope.bibleName = "";
		$scope.bibleChapters = 1;
		$scope.bibleChaptersList = null;
		
		typing.bibleList = function() {

			$http.get("/rg/getBibleList.do")
			.then(function(response) {
				
				$scope.list = typing.list = response.data;
				//$log.debug(typing.list);
				$scope.bibleName = typing.list[0].bibleName;
				$scope.bibleChaptersList = typing.makeBibleChaptersList(typing.list[0].bibleName, typing.list[0].bibleChapters);
				
				// 시작에서 들어온 경우
				if ($location.path().length >= "/typing/".length
						&& $location.path().substring(0, "/typing/".length) == "/typing/") {
					
					for (var i=0; i < typing.list.length; i++) {
						if (typing.list[i].abbreviationEng == $routeParams['bible']) {
							$scope.bibleName = typing.bibleName = typing.list[i].bibleName;
							typing.bibleChapters = typing.list[i].bibleChapters;
							break;
						}
					}
					
					//$log.debug("chapter", $routeParams['chapter']);
					
					if ($routeParams['chapter'] == null) {
						
						var param = {
								userId : $("#loginId").val(),
								phrase : $routeParams['bible'],
								bibleChapters : typing.bibleChapters
							};
						
			            var req = {
			    			    method: 'POST',
			    			    url: '/rg/getDoingChapter.do',
			    			    params: param
			            	};

						// 시작에서 들어온 경우
			            $http(req).then(function successCallback(response) {
			            	
			            	$log.debug("doingChapter", response.data);
							
			            	doingChapter = response.data == null || response.data == "" ? 1 : response.data;
							$scope.bibleChapters = doingChapter;
							
							typing.bibleSelect();
							typing.startTyping();
							
						}, function errorCallback(response) {
							alert("에러가 발생하였습니다.1");
						});
		            
					} else {
						
						//$log.debug($scope.bibleChaptersList);
						//alert("2");
						doingChapter = Number($routeParams['chapter']);
						$scope.bibleChapters = Number($routeParams['chapter']);

						// 시작에서 들어온 경우
						typing.bibleSelect();
						typing.startTyping();
					}
				}

			});
			
		}
		
		typing.init = function() {

			typing.bibleList();
			
			var param = {
					pageNo : 1,
					loginId : $("#loginId").val()
				};
			
            var req = {
    			    method: 'POST',
    			    url: '/rg/getTypingRecord.do',
    			    params: param
            	};

			$http(req).then(function successCallback(response) {
				
				if (response.data.totalCount < 7) {
					$("#typingMessage").html(" &nbsp; &nbsp; 한 행을 타이핑하시고 엔터를 누르시면 되겠습니다.");
				}
				
			}, function errorCallback(response) {
				alert("에러가 발생하였습니다.2");
			});
			
		}
		
		typing.init();
		
		typing.makeBibleChaptersList = function(phrase, articleCount, bibleChapter) {
			
			var deferred= $q.defer();
			
			var param = {
					phrase : phrase,
					loginId : $("#loginId").val()
				};
			
            var req = {
    			    method: 'POST',
    			    url: '/rg/getRecordCountList.do',
    			    params: param
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
				
				//$scope.bibleChapters = bibleChapter == null ? 1 : bibleChapter;

				// 시작에서 들어온 경우
				if ($location.path().length >= "/typing/".length
						&& $location.path().substring(0, "/typing/".length) == "/typing/") {
					$scope.bibleChapters = doingChapter;
				} else {
					$scope.bibleChapters = bibleChapter == null ? 1 : bibleChapter;
				}
				
			}, function errorCallback(response) {
				alert("에러가 발생하였습니다.");
			});
			
			return deferred.promise;
			
		}

		$scope.bibleSelect = function() {
			$scope.bibleChapters = doingChapter = 1;
			for (var i=0; i < typing.list.length; i++) {
				if (typing.list[i].bibleName == $scope.bibleName) {
					$scope.bibleChaptersList = typing.makeBibleChaptersList(typing.list[i].bibleName, typing.list[i].bibleChapters);
					break;
				}
			}
		}

		typing.bibleSelect = function() {
			for (var i=0; i < typing.list.length; i++) {
				if (typing.list[i].bibleName == $scope.bibleName) {
					$scope.bibleChaptersList = typing.makeBibleChaptersList(typing.list[i].bibleName, typing.list[i].bibleChapters, $scope.bibleChapters);
					break;
				}
			}
		}
		
		$scope.startTyping = function() {
			for (var i=0; i < typing.list.length; i++) {
				if (typing.list[i].bibleName == $scope.bibleName) {
					location.href = "#/typing/" + typing.list[i].abbreviationEng + "/" + $scope.bibleChapters;
					break;
				}
			}
		}
		
		typing.startTyping = function() {
			var abbreviation_kor = "";
			var abbreviation_eng = "";
			for (var i=0; i < typing.list.length; i++) {
				if ($scope.bibleName == typing.list[i].bibleName) {
					abbreviationKor = typing.list[i].abbreviationKor;
					typing.abbreviationEng = abbreviationEng = typing.list[i].abbreviationEng;
					break;
				}
			}
			
			typing.getContent($scope.bibleName, $scope.bibleChapters, abbreviationKor, abbreviationEng);
		}

		$scope.deleteTyping = function() {
			//alert("deleteTyping");
			//$("input.typing2").each(function (index, item) {
				//$(this).val("");
			//});
			//var typing = $scope.typing;
			//console.log($("input.typing2").length);
			//console.log(typing.length);
			//console.log($scope.typing[1].length);
			
			console.log(typing.typing);
			console.log($scope.typing);
			
			for (var i=0; i < typing.typing.length; i++) {
				//if (response.data.resultString.substr(i, 1) == "1") {
					$('#typing_' + i).val("");
					typing.typing[i].status = 0;
					//typing.typing[i].status = 1;
					//typing.typingTemporarySaving[i] = 1;
				//}
			}
			
			//for (var i=0; i < $scope.typing.length; i++) {
				//console.log($scope.typing[i]);
			//}
			
			var param = {
					loginId : $("#loginId").val(),
					phrase : typing.abbreviationEng,
					chapter : $scope.bibleChapters,
					typingLength : typing.typing.length
				};
			
            var req = {
    			    method: 'POST',
    			    url: '/rg/deleteTyping.do',
    			    params: param
            	};

			$http(req).then(function successCallback(response) {
				console.log("deleteTyping", response, $scope.typing, typing.typing);
				$("#preTyped").val(response.data);
			}, function errorCallback(response) {
				alert("에러가 발생하였습니다.");
			});
		}
		
		$scope.checkResult = function(index) {
			
			//alert("preTyped val : " + $("#preTyped").val());
			//return;
			
			if ($("#preTyped").val() == "1") {
				alert("연습 내역을 삭제해주시기 바랍니다.");
				return;
			}
			
            typing.enterKeyInputed = true;
			typing.activeElementIndex = index;
			
			var promise1 = typing.checkTyping(index, null, true);
			promise1.then(function(){
				//alert("1");
				typing.showMessage();
			});
			//출처: https://deafjwhong.tistory.com/27 [데프홍's Story]
		}

		typing.showMessage = function(ev) {

			// Appending dialog to document.body to cover sidenav in docs app
			// Modal dialogs should fully cover application
			// to prevent interaction outside of dialog
			var message = "";
			var result = 0;
			var firstWrong = -1;
			
			for (var i = 0; i < typing.count; i++) {
				if (typing.typing[i].status == -1) {
					if (message == "") {
						message += "" + (i + 1);
					} else {
						message += ", " + (i + 1);
					}
				}
				if (firstWrong == -1 && typing.typing[i].status == -1) {
					firstWrong = i;
				}
				result += typing.typing[i].status;
			}
			
			
			if (result == typing.typing.length) {  // 모두 완료하고 결과를 보여준다.

				var diff = 0;
				
				for (var i=0; i < typing.count; i++) {
					if (typing.time[i].startTime != null && typing.time[i].endTime != null 
							&& typing.time[i].startTime < typing.time[i].endTime) {
						diff += typing.time[i].endTime - typing.time[i].startTime;
					} else if (typing.time[i].elapsedTime > 0) {
						diff += typing.time[i].elapsedTime;
					}
				}

				var msec = diff;
				var hh = Math.floor(msec / 1000 / 60 / 60);
				msec -= hh * 1000 * 60 * 60;
				var mm = Math.floor(msec / 1000 / 60);
				msec -= mm * 1000 * 60;
				var ss = Math.round(msec / 1000);
				msec -= ss * 1000;
				
				var charCount = 0;
				$("[id^=sourceHidden_]").each(function( index ) {
					charCount += $( this ).val().length;
				});
				
				var param = {
						title : typing.title,
						charCount : charCount,
						timeElapsed : Math.ceil(diff / 1000),
						average : Math.round(charCount / (hh * 60 + mm + ss / 60)),
						loginId : $("#loginId").val(),
						phrase : typing.abbreviationEng,
						chapter : $scope.bibleChapters,
						typingLength : typing.typing.length
					};
				
	            var req = {
	    			    method: 'POST',
	    			    url: '/rg/putTypingRecord.do',
	    			    params: param
	            	};

	            
				$http(req).then(function successCallback(response) {
					
					console.log(response);
					
					if (response.data != null && response.data.preTyped == "1") {
						alert("연습 내역을 삭제해주시기 바랍니다.");
					
					} else {
						
						if (typing.time[typing.activeElementIndex].startTime != null 
								&& typing.time[typing.activeElementIndex].endTime == null) {
							typing.time[typing.activeElementIndex].endTime = new Date();
						}
						
						var timeMessage = "시간 : " + (hh > 0 ? hh + "시간" : "") + " " + (mm > 0 ? mm + "분" : "") + " " + (ss > 0 ? ss + "초" : "");
						
						var charMessage = "글자수 : ";
						//var charCount = 0;
						//$("[id^=sourceHidden_]").each(function( index ) {
							//charCount += $( this ).val().length;
						//});
						charMessage += addCommas(charCount) + "자";
						
						var averageMessage = Math.round(charCount / (hh * 60 + mm + ss / 60));
						var averageMessage = "평균 : 분당 " + addCommas(averageMessage) + "타";
						
						var title = "제목 : " + typing.title;
						
						$("#sourceDiv").empty();
						$("#sourceDiv").append("<div>" + title + "</div>");
						$("#sourceDiv").append("<div>" + charMessage + "</div>");
						$("#sourceDiv").append("<div>" + timeMessage + "</div>");
						$("#sourceDiv").append("<div>" + averageMessage + "</div>");
						
						window.scrollTo( 0, 0 );
						
						
						$http.get("/rg/getBibleList.do")
						.then(function(response) {
							$scope.list = typing.list = response.data;
							typing.bibleSelect();
						});
					}
					
				}, function errorCallback(response) {
					alert("에러가 발생하였습니다.");
				});
				
				
			} else {
			
				if (message.length > 0) {
					
					message += " 번째 항목이 맞지 앖습니다.";
	
					$mdDialog.show(
						$mdDialog.alert()
							.parent(angular.element(document.querySelector('#sourceDiv')))
							.clickOutsideToClose(true)
							.title('안맞는 부분이 있습니다.')
							.textContent(message)
							.ariaLabel('알림창')
							.ok('확인')
							.targetEvent(ev)
					).finally(function() {
						if (typing.typing[typing.activeElementIndex].status == -1) {
							$("#typing_" + typing.activeElementIndex).focus();
						} else {
							$("#typing_" + firstWrong).focus();

							var top = document.getElementById( "typing_" + firstWrong ).documentOffsetTop() - ( window.innerHeight / 2 );
							window.scrollTo( 138, top + 100);
						}
					});
					
				} else {  // 다음 타이핑으로 이동
					if (typing.time[typing.activeElementIndex].startTime != null 
							&& typing.time[typing.activeElementIndex].endTime == null) {
						typing.time[typing.activeElementIndex].endTime = new Date();
					}
					
					for (var i = 0; i < typing.count; i++) {
						
						if (typing.typing[i].status != 1) {
							
							$("#typing_" + i).focus();
							
							var top = document.getElementById( "typing_" + i ).documentOffsetTop() - ( window.innerHeight / 2 );
							window.scrollTo( 138, top + 100);
							
							break;
						}
					}
					
				}
			}
		};
		
		Element.prototype.documentOffsetTop = function () {
		    return this.offsetTop + ( this.offsetParent ? this.offsetParent.documentOffsetTop() : 0 );
		};
		
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
		
		$scope.showPrerenderedDialog = function(ev) {
			$mdDialog.show({
				contentElement: '#myDialog',
				parent: angular.element(document.body),
				targetEvent: ev,
				clickOutsideToClose: true
			});
		};

		typing.setClear = function () {
			$("[id^=typing_]").bind("mouseup", function(e) {
				var $input = $(this),
				oldValue = $input.val();
				
				if (oldValue == "") return;
				
				setTimeout(function(){
					var newValue = $input.val();
					if (newValue == "") {
						var id = $(document.activeElement).attr("id");
						var index = Number(id.substring(id.indexOf("_") + 1));
						
						$scope.typing[index] = "";
						$scope.$apply();
					}
				}, 1);
			});
		}
		
		typing.getTypingRecord = function(pageNo) {

			$scope.pageNo = pageNo;
			
			var param = {
					pageNo : pageNo,
					loginId : $("#loginId").val()
				};
			
            var req = {
    			    method: 'POST',
    			    url: '/rg/getTypingRecord.do',
    			    params: param
            	};

			$http(req).then(function successCallback(response) {
				
				$scope.totalCount = response.data.totalCount;
				$scope.list2 = response.data.list;
					
				$scope.pageNo = response.data.pageNo;
				$scope.listLimit = response.data.listLimit;  // 한 페이지 게시글 수
				
				$scope.pageLinkCount = 10; // 페이지 이동 링크 개수
				
				$scope.pageEnd = Math.ceil($scope.totalCount / $scope.listLimit);  // 마지막 페이지 번호
				
				$scope.linkStart = Math.floor(($scope.pageNo - 1) / $scope.pageLinkCount) * $scope.pageLinkCount + 1;
				$scope.linkEnd = ($scope.pageEnd - $scope.linkStart > $scope.pageLinkCount) 
									? $scope.linkStart + $scope.pageLinkCount : $scope.pageEnd;
				
			}, function errorCallback(response) {
				alert("에러가 발생하였습니다.");
			});
		}
		
		if ($location.path() == "/typingRecord") {
			
			typing.getTypingRecord(1);
		
		} else if ($location.path().length >= "/typingRecord/".length 
				&& $location.path().substring(0, "/typingRecord/".length) == "/typingRecord/") {
			
			typing.getTypingRecord(Number($routeParams['pageNo']));

		}

    }
]);

function isHan(char) {
	if (escape(char).length == 6) {
		return true;
	} else {
		return false;
	}
}

appTyping.directive('myEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if (event.which === 13) {
                scope.$apply(function () {
                    scope.$eval(attrs.myEnter);
                });
                event.preventDefault();
            }
        });
    };
});
