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
		
		$scope.status = '  ';
		$scope.customFullscreen = false;
		
		typing.activeElementIndex = -1;
		
		typing.title = "";
		
		typing.abbreviationEng = "";
		
		/*
		$scope.$watch('expr', function(newVal, oldVal, scope) {
        	if (newVal !== oldVal) {
        		// Let's set up our parseFun with the expression
        		var parseFun = $parse(newVal);
        		// Get the value of the parsed expression
        		$scope.parsedValue = parseFun(scope);
        	}
        });
		*/
		
		//var startTime = null;
		//var endTime = null;

		$scope.$watch('typing', function(newVal, oldVal, scope) {
			//typing.checkTyping();
			//$log.debug(scope);
			//alert("1");
			if (newVal !== oldVal) {
				//console.log(newVal[0].charCodeAt(0));
        		//alert("inputed." + newVal.substring(newVal.length - 1));
        		//if (newVal.substring())
				//var elem = document.activeElement;
				//$log.debug("id", $(document.activeElement).attr("id"));
				var id = $(document.activeElement).attr("id");
				
				if (id != null) {
					
					var index = Number(id.substring(id.indexOf("_") + 1));
					//alert(index);
					typing.activeElementIndex = index;
					
	        		typing.checkTyping(index);
	        		
	        		//if (index == 1) {
	        			//alert("newVal : " + newVal + ",\noldVal :" + oldVal);
	        		//}
	        		
	        		//var newValArray = newVal.split(",");
	        		
	        		//if ($scope.typing[index] == "") {
	        		if ($("#typing_" + index).val() == "") {
	        			//alert(index);
	        			typing.typing[index] = 0;  // 모두 삭제이면 미입력으로 바꿈
	        		}
				}
        		
        		/*
        		var source = $("#source").text();
    			var target = $scope.typing;
    			//alert("source : " + source + "\ntarget : " + target);
    			for (var i=0; i < target.length; i++) {
    				//alert(source.charAt(i) + " : " + target.charAt(i));
    				if (source.charAt(i) != target.charAt(i)) {
    					alert(source.charAt(i) + " : " + target.charAt(i));
    					//source.charAt(i).style.color = "red";
    				}
    			}
    			*/
        	}
        }, true);

		/*
		typing.checkTyping = function() {
			//alert("1");
			
			$("[id^=sourceSpan]").css("background-color", "white");
			
			var source = $("#sourceHidden").val();
			var target = $scope.typing;
			//alert("source : " + source + "\ntarget : " + target);
			
			for (var i=0; i < target.length; i++) {
				//alert(source.charAt(i) + " : " + target.charAt(i));
				if (source.charAt(i) != target.charAt(i)) {
					//alert(source.charAt(i) + " : " + target.charAt(i));
					$("#sourceSpan" + i).css("background-color", "#ff8080");
				} else {
					$("#sourceSpan" + i).css("background-color", "white");
				}
			}
			
		};
		*/

		$scope.checkTyping = typing.checkTyping = function(index) {
			
			//alert("1");
			
			//$log.debug("1");
			
			typing.activeElementIndex = index;
			
			var wrong = false;
			
			$("[id^=sourceSpan_" + index + "]").css("background-color", "#053c4f");
			
			var source = $("#sourceHidden_" + index).val();
			//var target = $scope.typing[index];
			var target = $("#typing_" + index).val();
			//alert("index : " + index + "\nsource : " + source + "\ntarget : " + target);
			
			//$log.debug(source.length);
			
			for (var i=0; i < source.length; i++) {
				
				if (i >= target.length) {
					$("#sourceSpan_" + index + "_" + i).css("background-color", "#053c4f");
				
				} else {
					
					if (source.charAt(i) != target.charAt(i)) {
						//alert(source.charAt(i) + " : " + target.charAt(i));
						//$log.debug(source.charAt(i) + " : " + target.charAt(i));
						$("#sourceSpan_" + index + "_" + i).css("background-color", "#ff8080");
						wrong = true;
					} else {
						//$log.debug(source.charAt(i) + " - " + target.charAt(i));
						$("#sourceSpan_" + index + "_" + i).css("background-color", "#053c4f");
					}
				}
			}
			
			//$log.debug(typeof target);
			
			if (typeof target == "string" && target.length > 0) {
				
				typing.typing[index] = -1;
				
				if (typing.time[index].startTime == null) {
					typing.time[index].startTime = new Date();
					//startTime = new Date();
				}
			}
			
			if (target == "") {

				$("[id^=sourceSpan_" + index + "]").css("background-color", "#053c4f");
				
				typing.typing[index] = 0;
				
				typing.time[index].startTime = null;
				typing.time[index].endTime = null;
				
				typing.typingTemporarySaving[index] = 0;
			}
			
			if (target.length == source.length) {
				
				//$log.debug("2");
				
				if (wrong) {
					//$log.debug("3");
					typing.typing[index] = -1;
				
				} else {
					//$log.debug("4");
					typing.typing[index] = 1;
					
					if (typing.time[index].startTime != null && typing.time[index].endTime == null) {
						typing.time[index].endTime = new Date();
						//alert(typing.time[index].endTime);
					}

					// 타이핑 내역 저장
					if (typing.typingTemporarySaving[index] == 0) {
						// typing.getContent($scope.bibleName, $scope.bibleChapters, abbreviationKor, abbreviationEng);
						
						var resultString = "";
						for (var i=0; i < typing.count; i++) {
							if (typing.typing[i] == "1") {
								resultString += "1";
							} else {
								resultString += "0";
							}
						}
						
						var timeString = "";
						//alert("typing.time.length : " + typing.time.length);
						for (var i=0; i < typing.count; i++) {
							if (timeString.length > 0) {
								timeString += "|";
							}
							
							if (typing.time[i].startTime != null && typing.time[i].endTime != null 
									&& typing.time[i].startTime < typing.time[i].endTime) {
								//alert("endTime : " + typing.time[i].endTime + "\nstartTime : " + typing.time[i].startTime + "\n" + (typing.time[i].endtime - typing.time[i].startTime));
								timeString += typing.time[i].endTime - typing.time[i].startTime;

							} else if (typing.time[i].elapsedTime > 0) {
								timeString += typing.time[i].elapsedTime;

							} else {
								timeString += "0";
							}
						}
						//alert(timeString);
						
						var param = {
								phrase : typing.abbreviationEng,
								chapter : $scope.bibleChapters,
								resultString : resultString,
								timeString : timeString,
								userId : $("#loginId").val()
							};
						//$log.debug(param);
						
			            var req = {
			    			    method: 'POST',
			    			    url: '/rg/putTemporaryTypingStorage.do',
			    			    params: param
			            	};
	
						$http(req).then(function successCallback(response) {
							typing.typingTemporarySaving[index] = 1;
						}, function errorCallback(response) {
							alert("에러가 발생하였습니다.");
						});
					
					}
				}
			}
		};
		
		typing.getContent = function(bibleName, bibleChapters, abbreviationKor, abbreviationEng) {
			
			typing.title = bibleName + " " + bibleChapters + "장";
			
			var param = {
					bibleName : bibleName,
					bibleChapters : bibleChapters,
					abbreviationKor : abbreviationKor,
					abbreviationEng : abbreviationEng
				};
			//$log.debug(param);
			
            var req = {
    			    method: 'POST',
    			    url: '/rg/getTypingContent.do',
    			    params: param
            	};

			$http(req).then(function successCallback(response) {
        	
				//$log.debug(response.data);
				
				$("#sourceDiv").empty();
				
				$scope.typing = [];
				
				$scope.count = typing.count = response.data.length;
				
				for (var i = 0; i < typing.count; i++) {
					
					//alert(typeof response.data[i]);
				
					$("#sourceDiv").append("<div class='typing1' id='sourceDiv_" + i + "'></div>");
					$("#sourceDiv").append('<input type="hidden" id="sourceHidden_' + i + '" value="' + response.data[i].replace(/"/g, "\"") + '" />');
					//$("#sourceDiv").append('<input id="typing_' + i + '" ng-model="typing" type="text" ng-click="checkTyping(' + i + ')" style="font-size: 25pt;" />');
					
					var input = angular.element('<input class="typing2" id="typing_' + i + '" ng-model="typing[' + i + ']" type="text" my-enter="checkResult(' + i + ')" ng-blur="checkTyping(' + i + ')" ng-trim="false" onpaste="return false;" ondrop="return false;" />');
					var compile = $compile(input)($scope);
					$("#sourceDiv").append(compile);
					
					$("#sourceDiv").append("<div style='line-height: 52%;'><br/><br/></div>");
			
					var str = response.data[i];

					for (var j = 0; j < str.length; j++) {
						var c = str.charAt(j);
						$("#sourceDiv_" + i).append("<span id='sourceSpan_" + i + "_" + j + "'>" + c + "</span>");
					}

					//$('#typing_' + i).attr('size', typing.getTextLength(str) + 2);
					$('#typing_' + i).width( (Math.ceil(typing.getTextLength(str) * 14.4) + 48) + "px" );
					$('#typing_' + i).attr('maxlength', str.length);
				}
				
				typing.setClear();
				
				
				typing.typing = new Array(typing.count);
				typing.time = new Array(typing.count);
				typing.typingTemporarySaving = new Array(typing.count);
				
				for (var i = 0; i < typing.count; i++) {
					typing.typing[i] = 0;  // 0 : 미입력, -1 : 틀림 있음, 1 : 틀림 없음.
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
					
					//$log.debug("getTemporaryTypingStorage", response.data);
					if (response.data.resultString != null) {
						for (var i=0; i < typing.count; i++) {
							if (response.data.resultString.substr(i, 1) == "1") {
								$('#typing_' + i).val($('#sourceHidden_' + i).val());
								typing.typing[i] = 1;
								typing.typingTemporarySaving[i] = 1;
							}
						}
					}

					if (response.data.timeString != null) {
						var timeArr = response.data.timeString.split("|");
						//$log.debug(typing.time);
						for (var i=0; i < typing.count; i++) {
							typing.time[i].elapsedTime = Number(timeArr[i]);
						}
					}
					//$log.debug("typing.time", typing.time);
					
				}, function errorCallback(response) {
					alert("에러가 발생하였습니다.");
				});
				
				
				
			}, function errorCallback(response) {
				alert("에러가 발생하였습니다.");
			});

		}


		/**
		 * 한글포함 문자열 길이를 구한다
		 */
		typing.getTextLength = function (str) {
		    var len = 0;
		    for (var i = 0; i < str.length; i++) {
		        if (escape(str.charAt(i)).length == 6) {
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
		
		typing.init = function() {

			$http.get("/rg/getBibleList.do")
			.then(function(response) {
				//$log.debug(response.data);
				$scope.list = typing.list = response.data;

				$scope.bibleName = typing.list[0].bibleName;
		
				$scope.bibleChaptersList = typing.makeBibleChaptersList(typing.list[0].bibleName, typing.list[0].bibleChapters);
				
				//$log.debug("scope.bibleChaptersList", $scope.bibleChaptersList);
				//$scope.bibleChapters = 1;
			});
			
			/*
			var str = $("#sourceHidden").val();
			for (var i=0; i < str.length; i++) {
				var c = str.charAt(i);
				$("#sourceDiv").append("<span id='sourceSpan" + i + "'>" + c + "</span>");
			}
			
			$('#typing').attr('size', str.length * 2);
			$('#typing').attr('maxlength', str.length);
			*/
			

			var param = {
					pageNo : 1,
					loginId : $("#loginId").val()
				};

			//$log.debug(param);
			
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
				alert("에러가 발생하였습니다.");
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
				
				//$log.debug(response.data);
				
				var chapterList = new Array();
				
				for (var i=1; i <= articleCount; i++) {
					//$log.debug(i, response.data[phrase + " " + i + "장"]);
					var cnt = typeof response.data[phrase + " " + i + "장"] == "undefined" ? 0 : response.data[phrase + " " + i + "장"];
					chapterList.push({ index : i, 
						count : cnt, 
						string : i + "장" + (cnt > 0 ? " (" + addCommas(cnt) + ")" : "")
					});
				}
				
				//return chapterList;
				
				deferred.resolve(chapterList);
				
				//$scope.bibleChaptersList = chapterList;
					
				$scope.bibleChapters = bibleChapter == null ? 1 : bibleChapter;
				
			}, function errorCallback(response) {
				alert("에러가 발생하였습니다.");
			});
			
			return deferred.promise;
			
		}

		$scope.bibleSelect = function() {
			for (var i=0; i < typing.list.length; i++) {
				if (typing.list[i].bibleName == $scope.bibleName) {
					//$scope.bibleChapters = typing.list[i].bibleChapters;
					
					//typing.makeBibleChaptersList(typing.list[i].bibleName, typing.list[i].bibleChapters);
					
					$scope.bibleChaptersList = typing.makeBibleChaptersList(typing.list[i].bibleName, typing.list[i].bibleChapters);
					//$scope.bibleChapters = 1;
					
					/*
					var promise1 = new Promise(function(resolve, reject) {
						  
						//setTimeout(function() {
						  //  resolve('foo');
						//  }, 300);
						$scope.bibleChaptersList = typing.makeBibleChaptersList(typing.list[i].bibleName, typing.list[i].bibleChapters);	  
						resolve($scope.bibleChaptersList);  
						});

						promise1.then(function(value) {
							alert($scope.bibleChapters);
							$scope.bibleChapters = 1;
						});
						*/
					break;
				}
			}
		}

		typing.bibleSelect = function() {
			for (var i=0; i < typing.list.length; i++) {
				if (typing.list[i].bibleName == $scope.bibleName) {
					//$scope.bibleChapters = typing.list[i].bibleChapters;
					//typing.makeBibleChaptersList(typing.list[i].bibleName, typing.list[i].bibleChapters, $scope.bibleChapters);
					
					$scope.bibleChaptersList = typing.makeBibleChaptersList(typing.list[i].bibleName, typing.list[i].bibleChapters, $scope.bibleChapters);
					//$scope.bibleChapters = 1;
					break;
				}
			}
		}
		
		$scope.startTyping = function() {
			//alert($scope.bibleName + " " + $scope.bibleChapters + "장");
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

		$scope.checkResult = function(index) {
			//alert(index);
			typing.activeElementIndex = index;
			typing.showMessage();
		}

		typing.showMessage = function(ev) {
			// Appending dialog to document.body to cover sidenav in docs app
			// Modal dialogs should fully cover application
			// to prevent interaction outside of dialog
			var message = "";
			var result = 0;
			var firstWrong = -1;
			
			for (var i = 0; i < typing.count; i++) {
				if (typing.typing[i] == -1) {
					if (message == "") {
						message += "" + (i + 1);
					} else {
						message += ", " + (i + 1);
					}
				}
				if (firstWrong == -1 && typing.typing[i] == -1) {
					firstWrong = i;
				}
				result += typing.typing[i];
			}
			
			
			if (result == typing.typing.length) {  // 모두 완료하고 결과를 보여준다.
				
				//alert("완료.");
				
				//$log.debug("완료");
				
				if (typing.time[typing.activeElementIndex].startTime != null 
						&& typing.time[typing.activeElementIndex].endTime == null) {
					typing.time[typing.activeElementIndex].endTime = new Date();
				}

				//if (endTime < startTime) {
					//endTime.setDate(endTime.getDate() + 1);
				//}
				
				//$log.debug("typing.time", typing.time);
				
				var diff = 0;
				
				for (var i=0; i < typing.count; i++) {
					if (typing.time[i].startTime != null && typing.time[i].endTime != null 
							&& typing.time[i].startTime < typing.time[i].endTime) {
						diff += typing.time[i].endTime - typing.time[i].startTime;
					} else if (typing.time[i].elapsedTime > 0) {
						diff += typing.time[i].elapsedTime;
					}
					//$log.debug(i, typing.time[i].endTime - typing.time[i].startTime);
				}

				//$log.debug("diff", diff);
				
				//var diff = endTime - startTime;
				
				var msec = diff;
				var hh = Math.floor(msec / 1000 / 60 / 60);
				msec -= hh * 1000 * 60 * 60;
				var mm = Math.floor(msec / 1000 / 60);
				msec -= mm * 1000 * 60;
				var ss = Math.round(msec / 1000);
				msec -= ss * 1000;
				
				//alert(hh + ":" + mm + ":" + ss);
				
				var timeMessage = "시간 : " + (hh > 0 ? hh + "시간" : "") + " " + (mm > 0 ? mm + "분" : "") + " " + (ss > 0 ? ss + "초" : "");
				
				var charMessage = "글자수 : ";
				var charCount = 0;
				$("[id^=sourceHidden_]").each(function( index ) {
					charCount += $( this ).val().length;
				});
				charMessage += addCommas(charCount) + "자";
				
				var averageMessage = Math.round(charCount / (hh * 60 + mm + ss / 60));
				var averageMessage = "평균 : 분당 " + addCommas(averageMessage) + "타";

				//$.each( $("[id^=sourceHidden_]"), function( key, value ) {
					//alert( key + ": " + value );
				//});
				
				var title = "제목 : " + typing.title;
				
				$("#sourceDiv").empty();
				$("#sourceDiv").append("<div>" + title + "</div>");
				$("#sourceDiv").append("<div>" + charMessage + "</div>");
				$("#sourceDiv").append("<div>" + timeMessage + "</div>");
				$("#sourceDiv").append("<div>" + averageMessage + "</div>");
				
				window.scrollTo( 0, 0 );
				
				var param = {
						title : typing.title,
						charCount : charCount,
						timeElapsed : Math.ceil(diff / 1000),
						average : Math.round(charCount / (hh * 60 + mm + ss / 60)),
						loginId : $("#loginId").val(),
						phrase : typing.abbreviationEng,
						chapter : $scope.bibleChapters
					};
				
				//-1545246801
				//1188 / 

				//$log.debug(param);
				
	            var req = {
	    			    method: 'POST',
	    			    url: '/rg/putTypingRecord.do',
	    			    params: param
	            	};

	            
				$http(req).then(function successCallback(response) {
					
					typing.bibleSelect($scope.bibleChapters);
					
				}, function errorCallback(response) {
					alert("에러가 발생하였습니다.");
				});
				
				
			} else {
			
				if (message.length > 0) {
					
					message += " 번째 항목이 맞지 앖습니다.";
					
					//$log.debug(message);
					
					//alert(typing.activeElementIndex);
	
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
						//$("#typing_" + firstWrong).focus();
						if (typing.typing[typing.activeElementIndex] == -1) {
							$("#typing_" + typing.activeElementIndex).focus();
						} else {
							//$log.debug(typing.typing);
							//alert("틀린 것.");
							$("#typing_" + firstWrong).focus();
							//window.scrollBy(document.getElementById("#typing_" + firstWrong).scrollTop, 0);
							//window.scrollBy(0, -100);

							var top = document.getElementById( "typing_" + firstWrong ).documentOffsetTop() - ( window.innerHeight / 2 );
							window.scrollTo( 138, top + 100);
						}
					});
					
					//$("#typing_" + typing.activeElementIndex).focus();
					
					
					/*
					var promise1 = $q(function(resolve, reject) {
	
						$mdDialog.show(
							$mdDialog.alert()
								.parent(angular.element(document.querySelector('#sourceDiv')))
								.clickOutsideToClose(true)
								.title('안맞는 부분이 있습니다.')
								.textContent(message)
								.ariaLabel('알림창')
								.ok('확인')
								.targetEvent(ev)
						);
						
					});
	
					promise1.then(function(value) {
						$("#typing_" + typing.activeElementIndex).focus();
					});
					*/
					
				} else {  // 다음 타이핑으로 이동
					//$log.debug(typing.typing);
					//alert("다음");
					//$log.debug("다음");
					//endtime = new Date();
					
					if (typing.time[typing.activeElementIndex].startTime != null 
							&& typing.time[typing.activeElementIndex].endTime == null) {
						typing.time[typing.activeElementIndex].endTime = new Date();
					}
					
					//startTime = null;
					
					for (var i = 0; i < typing.count; i++) {
						
						if (typing.typing[i] != 1) {
							
							$("#typing_" + i).focus();
							
							var top = document.getElementById( "typing_" + i ).documentOffsetTop() - ( window.innerHeight / 2 );
							window.scrollTo( 138, top + 100);
							
							break;
						}
					}
					
				}
			}
			
			//alert(typing.typing.length);
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

			//$log.debug(param);
			
            var req = {
    			    method: 'POST',
    			    url: '/rg/getTypingRecord.do',
    			    params: param
            	};

			$http(req).then(function successCallback(response) {
				
				//$log.debug(response.data);
				
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
		
		//$log.debug("location.path", $location.path());
		
		if ($location.path() == "/typingRecord") {
			
			typing.getTypingRecord(1);
		
		} else if ($location.path().length >= "/typingRecord/".length 
				&& $location.path().substring(0, "/typingRecord/".length) == "/typingRecord/") {
			
			typing.getTypingRecord(Number($routeParams['pageNo']));

		} else if ($location.path().length >= "/typing//".length
				&& $location.path().substring(0, "/typing/".length) == "/typing/") {
			;
		}

    }
]);

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
