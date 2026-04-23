var appTest = angular.module('rg.Test', ['ngMaterial']);

appTest.controller('TestCtrl', ['$scope', '$parse', '$log', '$http', '$compile', '$mdDialog', '$q', '$location', '$routeParams',
	
    function ($scope, $parse, $log, $http, $compile, $mdDialog, $q, $location, $routeParams) {

		var test = this;
		
		test.list = null;
		$scope.count = test.count = 0;
		
		$scope.typing = [];
		
		test.typing = [];
		test.time = [];
		
		$scope.status = '  ';
		$scope.customFullscreen = false;
		
		test.activeElementIndex = -1;
		
		test.title = "";
		
		test.abbreviationEng = "";
		
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
			//test.checkTyping();
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
					test.activeElementIndex = index;
					
	        		test.checkTyping(index);
	        		
	        		//if (index == 1) {
	        			//alert("newVal : " + newVal + ",\noldVal :" + oldVal);
	        		//}
	        		
	        		//var newValArray = newVal.split(",");
	        		
	        		if ($scope.typing[index] == "") {
	        			//alert(index);
	        			test.typing[index] = 0;  // 모두 삭제이면 미입력으로 바꿈
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
		test.checkTyping = function() {
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

		$scope.checkTyping = test.checkTyping = function(index) {
			//alert("1");
			
			test.activeElementIndex = index;
			
			var wrong = false;
			
			$("[id^=sourceSpan_" + index + "]").css("background-color", "#fafafa");
			
			var source = $("#sourceHidden_" + index).val();
			//var target = $scope.typing[index];
			var target = $("#typing_" + index).val();
			//alert("index : " + index + "\nsource : " + source + "\ntarget : " + target);
			
			for (var i=0; i < target.length; i++) {
				//alert(source.charAt(i) + " : " + target.charAt(i));
				if (source.charAt(i) != target.charAt(i)) {
					//alert(source.charAt(i) + " : " + target.charAt(i));
					$("#sourceSpan_" + index + "_" + i).css("background-color", "#ff8080");
					wrong = true;
				} else {
					$("#sourceSpan_" + index + "_" + i).css("background-color", "#fafafa");
				}
			}
			
			if (target.length > 0) {
				test.typing[index] = -1;
				
				if (test.time[index].startTime == null) {
					test.time[index].startTime = new Date();
					//startTime = new Date();
				}
			}
			
			if (target.length == 0) {
				test.time[index].startTime = null;
			}
			
			if (target.length == source.length) {
				
				if (wrong) {
					test.typing[index] = -1;
				
				} else {
					test.typing[index] = 1;
					
					if (test.time[index].endTime == null) {
						test.time[index].endTime = new Date();
					}
				}

				// 타이핑 내역 저장
				// test.getContent($scope.bibleName, $scope.bibleChapters, abbreviationKor, abbreviationEng);
				var param = {
						bibleChapters : $scope.bibleChapters,
						abbreviationEng : test.abbreviationEng,
						result : wrong ? "0" : "1",
						totalChapters : test.list.length
					};
				//$log.debug(param);
				
	            var req = {
	    			    method: 'POST',
	    			    url: '/rg/temporaryStoreTyping.do',
	    			    params: param
	            	};

				$http(req).then(function successCallback(response) {
					;
				}, function errorCallback(response) {
					alert("에러가 발생하였습니다.");
				});
			}
		};
		
		test.getContent = function(bibleName, bibleChapters, abbreviationKor, abbreviationEng) {
			
			test.title = bibleName + " " + bibleChapters + "장";
			
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
				
				$scope.count = test.count = response.data.length;
				
				for (var i = 0; i < response.data.length; i++) {
					
					//alert(typeof response.data[i]);
				
					$("#sourceDiv").append("<div class='typing1' id='sourceDiv_" + i + "'></div>");
					$("#sourceDiv").append('<input type="hidden" id="sourceHidden_' + i + '" value="' + response.data[i].replace(/"/g, "\"") + '" />');
					//$("#sourceDiv").append('<input id="typing_' + i + '" ng-model="typing" type="text" ng-click="checkTyping(' + i + ')" style="font-size: 25pt;" />');
					
					var input = angular.element('<input class="typing2" id="typing_' + i + '" ng-model="typing[' + i + ']" type="text" my-enter="checkResult(' + i + ')" ng-blur="checkTyping(' + i + ')" />');
					var compile = $compile(input)($scope);
					$("#sourceDiv").append(compile);
					
					$("#sourceDiv").append("<div style='line-height: 42%;'><br/><br/></div>");
			
					var str = response.data[i];

					for (var j = 0; j < str.length; j++) {
						var c = str.charAt(j);
						$("#sourceDiv_" + i).append("<span id='sourceSpan_" + i + "_" + j + "'>" + c + "</span>");
					}

					$('#typing_' + i).attr('size', test.getTextLength(str) + 5);
					$('#typing_' + i).attr('maxlength', str.length);
				}
				
				test.setClear();
				
				test.typing = new Array($("[id^=typing_]").length);
				for (var i = 0; i < test.typing.length; i++) {
					test.typing[i] = 0;  // 0 : 미입력, -1 : 틀림 있음, 1 : 클림 없음.
				}
				
				test.time = new Array($("[id^=typing_]").length);
				for (var i=0; i < test.time.length; i++) {
					test.time[i] = {startTime:null, endTime:null};
				}
				//test.time[0].startTime = 0;
				//$log.debug(test.time[0].startTime);
				
				var top = document.getElementById( "typing_1" ).documentOffsetTop() - ( window.innerHeight / 2 );
				window.scrollTo( 138, top );
				
			}, function errorCallback(response) {
				alert("에러가 발생하였습니다.");
			});
		}


		/**
		 * 한글포함 문자열 길이를 구한다
		 */
		test.getTextLength = function (str) {
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
		
		test.init = function() {

			$http.get("/rg/getBibleList.do")
			.then(function(response) {
				//$log.debug(response.data);
				$scope.list = test.list = response.data;

				$scope.bibleName = test.list[0].bibleName;
		
				$scope.bibleChaptersList = test.makeBibleChaptersList(test.list[0].bibleChapters);
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
    			    url: '/rg/getTypingHistory.do',
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
		
		test.init();
		
		test.makeBibleChaptersList = function(index) {
			var chapterList = new Array();
			for (var i=1; i <= index; i++) {
				chapterList.push(i);
			}
			return chapterList;
		}
		
		$scope.bibleSelect = function() {
			for (var i=0; i < test.list.length; i++) {
				if (test.list[i].bibleName == $scope.bibleName) {
					//$scope.bibleChapters = test.list[i].bibleChapters;
					$scope.bibleChaptersList = test.makeBibleChaptersList(test.list[i].bibleChapters);
					$scope.bibleChapters = 1;
					break;
				}
			}
		}
		
		$scope.startTyping = function() {
			//alert($scope.bibleName + " " + $scope.bibleChapters + "장");
			var abbreviation_kor = "";
			var abbreviation_eng = "";
			for (var i=0; i < test.list.length; i++) {
				if ($scope.bibleName == test.list[i].bibleName) {
					abbreviationKor = test.list[i].abbreviationKor;
					test.abbreviationEng = abbreviationEng = test.list[i].abbreviationEng;
					break;
				}
			}
			
			test.getContent($scope.bibleName, $scope.bibleChapters, abbreviationKor, abbreviationEng);
		}

		$scope.checkResult = function(index) {
			//alert(index);
			test.activeElementIndex = index;
			test.showAlert();
		}

		test.showAlert = function(ev) {
			// Appending dialog to document.body to cover sidenav in docs app
			// Modal dialogs should fully cover application
			// to prevent interaction outside of dialog
			var message = "";
			var result = 0;
			var firstWrong = -1;
			
			for (var i = 0; i < test.typing.length; i++) {
				if (test.typing[i] == -1) {
					if (message == "") {
						message += "" + (i + 1);
					} else {
						message += ", " + (i + 1);
					}
				}
				if (firstWrong == -1 && test.typing[i] == -1) {
					firstWrong = i;
				}
				result += test.typing[i];
			}
			
			
			if (result == test.typing.length) {  // 모두 완료하고 결과를 보여준자.
				//alert("완료.");
				
				if (test.time[test.activeElementIndex].endTime == null) {
					test.time[test.activeElementIndex].endTime = new Date();
				}

				//if (endTime < startTime) {
					//endTime.setDate(endTime.getDate() + 1);
				//}
				
				//$log.debug("test.time", test.time);
				
				var diff = 0;
				
				for (var i=0; i < test.time.length; i++) {
					diff += test.time[i].endTime - test.time[i].startTime;
					//$log.debug(i, test.time[i].endTime - test.time[i].startTime);
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
				
				var title = "제목 : " + test.title;
				
				$("#sourceDiv").empty();
				$("#sourceDiv").append("<div>" + title + "</div>");
				$("#sourceDiv").append("<div>" + charMessage + "</div>");
				$("#sourceDiv").append("<div>" + timeMessage + "</div>");
				$("#sourceDiv").append("<div>" + averageMessage + "</div>");
				
				window.scrollTo( 0, 0 );
				
				var param = {
						title : test.title,
						charCount : charCount,
						timeElasped : Math.ceil(diff / 1000),
						average : Math.round(charCount / (hh * 60 + mm + ss / 60)),
						loginId : $("#loginId").val()
					};
				
				//-1545246801
				//1188 / 

				//$log.debug(param);
				
	            var req = {
	    			    method: 'POST',
	    			    url: '/rg/putTypingHistory.do',
	    			    params: param
	            	};

	            
				$http(req).then(function successCallback(response) {
	        		;
				}, function errorCallback(response) {
					alert("에러가 발생하였습니다.");
				});
				
				
			} else {
			
				if (message.length > 0) {
					
					message += " 번째 항목이 맞지 앖습니다.";
					
					//alert(test.activeElementIndex);
	
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
						if (test.typing[test.activeElementIndex] == -1) {
							$("#typing_" + test.activeElementIndex).focus();
						} else {
							//$log.debug(test.typing);
							//alert("틀린 것.");
							$("#typing_" + firstWrong).focus();
							//window.scrollBy(document.getElementById("#typing_" + firstWrong).scrollTop, 0);
							//window.scrollBy(0, -100);

							var top = document.getElementById( "typing_" + firstWrong ).documentOffsetTop() - ( window.innerHeight / 2 );
							window.scrollTo( 138, top );
						}
					});
					
					//$("#typing_" + test.activeElementIndex).focus();
					
					
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
						$("#typing_" + test.activeElementIndex).focus();
					});
					*/
					
				} else {  // 다음 타이핑으로 이동
					//$log.debug(test.typing);
					//alert("다음");
					//endtime = new Date();
					
					if (test.time[test.activeElementIndex].endTime == null) {
						test.time[test.activeElementIndex].endTime = new Date();
					}
					
					//startTime = null;
					
					for (var i = 0; i < test.typing.length; i++) {
						
						if (test.typing[i] != 1) {
							
							$("#typing_" + i).focus();
							
							var top = document.getElementById( "typing_" + i ).documentOffsetTop() - ( window.innerHeight / 2 );
							window.scrollTo( 138, top );
							
							break;
						}
					}
					
				}
			}
			
			//alert(test.typing.length);
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

		test.setClear = function () {
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
		
		test.getTypingHistory = function(pageNo) {

			$scope.pageNo = pageNo;
			
			var param = {
					pageNo : pageNo,
					loginId : $("#loginId").val()
				};

			//$log.debug(param);
			
            var req = {
    			    method: 'POST',
    			    url: '/rg/getTypingHistory.do',
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
		
		if ($location.path() == "/typingHistory") {
			test.getTypingHistory(1);
		} else if ($location.path().length >= "/typingHistory/".length 
				&& $location.path().substring(0, "/typingHistory/".length) == "/typingHistory/") {
			test.getTypingHistory(Number($routeParams['pageNo']));
		}

    }
]);

appTest.directive('myEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.myEnter);
                });

                event.preventDefault();
            }
        });
    };
});
