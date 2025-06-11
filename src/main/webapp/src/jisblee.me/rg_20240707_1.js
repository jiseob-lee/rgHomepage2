var myModule = angular.module('rg',
    [
        'ngRoute',
        'ngCookies',
        'rg.Attachment',
        'rg.Board',
        'rg.ManageBoard',
        'rg.User',
        'rg.Typing',
        'rg.TypingIntro',
        'rg.Statistics',
        'rg.GoAccess',
        'rg.progressCircular',
        'rg.Test',
        'ngMaterial',
        'ngMessages',
        'pascalprecht.translate',
        'rg.S3',
        'rg.Jido',
        'rg.LoginLog',
        'rg.DownHistory'
    ]);

myModule.controller('RgCtrl', ['$scope', '$routeParams', '$location', '$http', '$log', '$translate',
	function ($scope, $routeParams, $location, $http, $log, $translate) {
   		
		var rg = this;

		//alert($translate.proposedLanguage());
		
		
		rg.manageBoardList = null;
		
		/*
    	// 게시판 목록을 구한다.
        $http.get("/getManageBoardList.do?openYn=1")
		.then(function(response) {
			rg.manageBoardList = response.data;
			$scope.manageBoardList = response.data;
			$log.debug("getManageBoardList 2", $scope.manageBoardList);
		});
        */
		
		//alert(window.location.hash);
		var url = "/rg/getLocale.do";
		//alert("search : " + location.search + "\nlanguage : " + $scope.language);
		if (location.search) {
			url += "?lang=" + location.search.substring(location.search.indexOf("=") + 1);
		}
		
		$http.get(url)
		.then(function(response) {
			
			$log.debug(response.data);
			
			//$scope.language = response.data.locale;

			$translate.use(response.data.locale);
	        //$location.search('lang', $scope.language);
			$scope.language = response.data.locale;
		});
		
		
        if (window.location.pathname.length >= 3 && window.location.pathname.substring(0, 3) == "/rg") {
        	$scope.urlPath = "rg";
        } else {
        	$scope.urlPath = "";
        	
        	//alert("hash : " + window.location.hash);
        	//alert("href : " + window.location.href);
        	//$http.get("/logHash.do" + "?" + "href=" + window.location.href);	
        }
    
	
		$http.get("/getLoginUserInfo.do")
		.then(function(response) {

			//$log.debug(response);
	
			if ($scope.urlPath == "rg") {
				if (response.data.loginId == null || response.data.loginId == "") {
					alert("세션이 종료되었습니다.");
					//location.href = "/login.do";
					location.href = "/";
					return;
				}
			}

			$scope.loginId = response.data.loginId;
			$scope.loginUserName =response.data.loginUserName;
			
			rg.loginId = response.data.loginId;
			rg.loginUserName =response.data.loginUserName;
		});
		
		$scope.$on('$routeChangeStart', function($event, next, current) {
			
			//alert("at rg : " + location.href);
			
			//$log.debug("event", $event);
			
			if ($scope.urlPath == "rg") {
			
				//alert("routeChangeStart");
	
				$http.get("/rg/getLoginUserInfo.do")
				.then(function(response) {
					if (response.data.loginId == null || response.data.loginId == "") {
						alert("세션이 종료되었습니다.");
						//location.href = "/login.do";
						location.href = "/";
						return;
					}
				});
			} else {
				
				//$log.debug("next", next);
				//$log.debug("current", current);
				
				//alert("next : " + next + "\ncurrent : " + current + "\nlocation.path : " + $location.path());
				
				//alert("href : " + encodeURIComponent(window.location.href));
				//$http.get("/logHash.do" + "?" + "hash=" + encodeURIComponent($location.path()));
				;
			}
		});
		
		
		$scope.greaterThanZero = function() {
			return function(item) {
				return item["articleCount"] > 0;
			}
		}
		
		/*
		$scope.changeLanguage = rg.changeLanguage = function() {
	        location.href = "/rg/?lang=" + $scope.language + "#" + $location.$$path;
	        $translate.use($scope.language);
	        //$location.search('lang', $scope.language);
		}
		*/

	}
]);


myModule.config(function($routeProvider, $locationProvider, $httpProvider, $translateProvider) {

	$locationProvider.hashPrefix('');
	
	//console.log("routeProvider", $routeProvider);
	//console.log("locationProvider", $locationProvider);

	//console.log(translations_ko);
	
	/*
	$http.get('https://jisblee.me/assets/json/message-common_en.json').success(function(response) {
		translations_en = response.data;
		$translateProvider
			.translations('en', translations_en)
    });
	*/

	//$translateProvider.translations('ko', translations_ko);
	//$translateProvider.translations('en', translations_en);
	//$translateProvider.preferredLanguage('ko');
    
	$translateProvider
    .useStaticFilesLoader({
        prefix: '/assets/json/message-common_',
        suffix: '.json'
    })
    .preferredLanguage('ko')
    .useLocalStorage()
    .useSanitizeValueStrategy('escape')
    .useMissingTranslationHandlerLog();
    
	/*
	var translations = {
			  HEADLINE: 'What an awesome module!',
			  PARAGRAPH: 'Srsly!',
			  NAMESPACE: {
			    PARAGRAPH: 'And it comes with awesome features!'
			  },
			  board: {
				  no: "no."
			  }

			};
	*/
	
	//$translateProvider.useUrlLoader('/rest/messageBundle');
    //$translateProvider.useStorage('UrlLanguageStorage');
    //$translateProvider.preferredLanguage('ko');
    //$translateProvider.fallbackLanguage('ko');
    
	if (window.location.pathname.length >= 3 && window.location.pathname.substring(0, 3) == "/rg") {
		//alert("rg path.");
	    $routeProvider
	        
	        .when('/getDownHistory', {
	            templateUrl: '/src/jisblee.me/14.downhistory/templates/1.downHistory.jsp',
	            controller: 'DownHistoryCtrl',
	            controllerAs: 'downhistory',
	            requiresLogin: true
	        })
	        
	        .when('/getDownHistory/:pageNo', {
	            templateUrl: '/src/jisblee.me/14.downhistory/templates/1.downHistory.jsp',
	            controller: 'DownHistoryCtrl',
	            controllerAs: 'downhistory',
	            requiresLogin: true
	        })
	        
	        .when('/getLoginLog', {
	            templateUrl: '/src/jisblee.me/13.loginlog/templates/1.loginLog.jsp',
	            controller: 'LoginLogCtrl',
	            controllerAs: 'loginlog',
	            requiresLogin: true
	        })
	        
	        .when('/getLoginLog/:pageNo', {
	            templateUrl: '/src/jisblee.me/13.loginlog/templates/1.loginLog.jsp',
	            controller: 'LoginLogCtrl',
	            controllerAs: 'loginlog',
	            requiresLogin: true
	        })
	        
	    	.when('/textLength', {
	            templateUrl: '/src/jisblee.me/11.textLength/templates/TextLength.html',
	            controller: '',
	            controllerAs: '',
	            requiresLogin: false
	        })
	        
	    	.when('/juso', {
	            templateUrl: '/src/jisblee.me/10.juso/templates/sampleTest.html',
	            controller: 'JidoCtrl',
	            controllerAs: '',
	            requiresLogin: false
	        })
	        
	    	.when('/attachment/:boardNo/:pageNo', {
	            templateUrl: '/src/jisblee.me/1.attachment/templates/1.attachment.jsp',
	            controller: 'AttachmentCtrl',
	            controllerAs: 'attachment',
	            requiresLogin: true
	        })
	        
	        .when('/attachment/:pageNo', {
	            templateUrl: '/src/jisblee.me/1.attachment/templates/1.attachment.jsp',
	            controller: 'AttachmentCtrl',
	            controllerAs: 'attachment',
	            requiresLogin: true
	        })
	        
	        .when('/manageBoard', {
	            templateUrl: '/src/jisblee.me/2.board/templates/1.manageBoard.jsp',
	            controller: 'ManageBoardCtrl',
	            controllerAs: 'manageBoard',
	            requiresLogin: true
	        })
	        
	        .when('/user', {
	            templateUrl: '/src/jisblee.me/3.user/templates/1.user.jsp',
	            controller: 'UserCtrl',
	            controllerAs: 'user',
	            requiresLogin: true
	        })
	        
	        .when('/board/list/:boardNo/:pageNo', {
	            templateUrl: '/src/jisblee.me/2.board/templates/2.boardList.jsp',
	            controller: 'BoardCtrl',
	            controllerAs: 'board',
	            requiresLogin: true
	        })
	        
	        .when('/board/list/:boardNo/:pageNo/:openYn', {
	            templateUrl: '/src/jisblee.me/2.board/templates/2.boardList.jsp',
	            controller: 'BoardCtrl',
	            controllerAs: 'board',
	            requiresLogin: true
	        })
	        
	        .when('/board/list/:boardNo/:pageNo/:searchKind/:searchValue', {
	            templateUrl: '/src/jisblee.me/2.board/templates/2.boardList.jsp',
	            controller: 'BoardCtrl',
	            controllerAs: 'board',
	            requiresLogin: true
	        })
	        
	        .when('/board/list/:boardNo/:pageNo/:openYn/:searchKind/:searchValue', {
	            templateUrl: '/src/jisblee.me/2.board/templates/2.boardList.jsp',
	            controller: 'BoardCtrl',
	            controllerAs: 'board',
	            requiresLogin: true
	        })

			.when('/board/view/:boardNo/:pageNo/:articleNo', {
			    templateUrl: '/src/jisblee.me/2.board/templates/3.boardView.jsp',
			    controller: 'BoardCtrl',
			    controllerAs: 'board',
			    requiresLogin: false
			})

			.when('/board/view/:boardNo/:pageNo/:articleNo/:openYn', {
			    templateUrl: '/src/jisblee.me/2.board/templates/3.boardView.jsp',
			    controller: 'BoardCtrl',
			    controllerAs: 'board',
			    requiresLogin: false
			})

			.when('/board/view/:boardNo/:pageNo/:articleNo/:searchKind/:searchValue', {
			    templateUrl: '/src/jisblee.me/2.board/templates/3.boardView.jsp',
			    controller: 'BoardCtrl',
			    controllerAs: 'board',
			    requiresLogin: false
			})

			.when('/board/view/:boardNo/:pageNo/:articleNo/:openYn/:searchKind/:searchValue', {
			    templateUrl: '/src/jisblee.me/2.board/templates/3.boardView.jsp',
			    controller: 'BoardCtrl',
			    controllerAs: 'board',
			    requiresLogin: false
			})

	        .when('/board/edit/:boardNo/:pageNo/:articleNo', {
	            templateUrl: '/src/jisblee.me/2.board/templates/4.boardEdit.jsp',
	            controller: 'BoardCtrl',
	            controllerAs: 'board',
	            requiresLogin: true
	        })
	        
	        .when('/board/edit/:boardNo/:pageNo/:articleNo/:openYn', {
	            templateUrl: '/src/jisblee.me/2.board/templates/4.boardEdit.jsp',
	            controller: 'BoardCtrl',
	            controllerAs: 'board',
	            requiresLogin: true
	        })
	        
	        .when('/board/input/:boardNo/:pageNo', {
	            templateUrl: '/src/jisblee.me/2.board/templates/4.boardEdit.jsp',
	            controller: 'BoardCtrl',
	            controllerAs: 'board',
	            requiresLogin: true
	        })
	        
	        .when('/test', {
	            templateUrl: '/src/jisblee.me/8.test/templates/1.test.jsp',
	            controller: 'TestCtrl',
	            controllerAs: 'test',
	            requiresLogin: true
	        })
	        
	        .when('/typing', {
	            templateUrl: '/src/jisblee.me/7.typing/templates/1.typing.jsp',
	            controller: 'TypingCtrl',
	            controllerAs: 'typing',
	            requiresLogin: true
	        })
	        
	        .when('/typing/:bible', {
	            templateUrl: '/src/jisblee.me/7.typing/templates/1.typing_20200420.jsp',
	            controller: 'TypingCtrl',
	            controllerAs: 'typing',
	            requiresLogin: true
	        })
	        
	        .when('/typing/:bible/:chapter', {
	            templateUrl: '/src/jisblee.me/7.typing/templates/1.typing_20200420.jsp',
	            controller: 'TypingCtrl',
	            controllerAs: 'typing',
	            requiresLogin: true
	        })
	        
	        .when('/typingIntro1', {
	            templateUrl: '/src/jisblee.me/7.typing/templates/3.typingIntro1_20200329.jsp',
	            controller: 'TypingIntroCtrl',
	            controllerAs: 'typingIntro',
	            requiresLogin: true
	        })
	        
	        .when('/typingIntro2', {
	            templateUrl: '/src/jisblee.me/7.typing/templates/3.typingIntro2_20200329.jsp',
	            controller: 'TypingIntroCtrl',
	            controllerAs: 'typingIntro',
	            requiresLogin: true
	        })
	        
	        .when('/typingRecord', {
	            templateUrl: '/src/jisblee.me/7.typing/templates/2.typingRecord.jsp',
	            controller: 'TypingCtrl',
	            controllerAs: 'typing',
	            requiresLogin: true
	        })
	        
	        .when('/typingRecord/:pageNo', {
	            templateUrl: '/src/jisblee.me/7.typing/templates/2.typingRecord.jsp',
	            controller: 'TypingCtrl',
	            controllerAs: 'typing',
	            requiresLogin: true
	        })
	        
	        .when('/getIpCount', {
	            templateUrl: '/src/jisblee.me/5.statistics/templates/1.ipCount.jsp',
	            controller: 'StatisticsCtrl',
	            controllerAs: 'statistics',
	            requiresLogin: true
	        })
	        
	        .when('/getIpCount/:pageNo', {
	            templateUrl: '/src/jisblee.me/5.statistics/templates/1.ipCount.jsp',
	            controller: 'StatisticsCtrl',
	            controllerAs: 'statistics',
	            requiresLogin: true
	        })
	        
	        .when('/getStatisticsArticleSubjects', {
	            templateUrl: '/src/jisblee.me/5.statistics/templates/2.statisticsArticleSubjects.jsp',
	            controller: 'StatisticsCtrl',
	            controllerAs: 'statistics',
	            requiresLogin: true
	        })
	        
	        .when('/getStatisticsLogs', {
	            templateUrl: '/src/jisblee.me/5.statistics/templates/3.statisticsLogs.jsp',
	            controller: 'StatisticsCtrl',
	            controllerAs: 'statistics',
	            requiresLogin: true
	        })

			.when('/goaccess', {
			    templateUrl: '/src/jisblee.me/6.goaccess/templates/1.inputDates.jsp',
			    controller: 'GoAccessCtrl',
			    controllerAs: 'access',
			    requiresLogin: true
			})
			
			.when('/Check_the_latitude_and_longitude', {
			    templateUrl: '/src/jisblee.me/12.Check_the_latitude_and_longitude/templates/Check_the_latitude_and_longitude.html',
			    controller: 'GoAccessCtrl',
			    controllerAs: 'access',
			    requiresLogin: true
			})
			
	        .when('/s3', {
	            templateUrl: '/src/jisblee.me/9.s3/templates/1.s3.jsp',
	            controller: 'S3Ctrl',
	            controllerAs: 's3',
	            requiresLogin: true
	        })
	        
	        //.otherwise({redirectTo: '/staff/login.do'})
	        ;
    
	} else {
		//alert("no rg path.");
		$routeProvider
	
	        .when('/board/list/:boardNo/:pageNo', {
	            templateUrl: '/src/jisblee.me/2.board/templates/2.boardList.jsp',
	            controller: 'BoardCtrl',
	            controllerAs: 'board',
	            requiresLogin: false
	        })
	        
	        .when('/board/list/:boardNo/:pageNo/:openYn', {
	            templateUrl: '/src/jisblee.me/2.board/templates/2.boardList.jsp',
	            controller: 'BoardCtrl',
	            controllerAs: 'board',
	            requiresLogin: false
	        })
	        
	        .when('/board/view/:boardNo/:pageNo/:articleNo', {
	            templateUrl: '/src/jisblee.me/2.board/templates/3.boardView.jsp',
	            controller: 'BoardCtrl',
	            controllerAs: 'board',
	            requiresLogin: false
	        })
	        
	        .otherwise({
	            templateUrl: '/src/jisblee.me/2.board/templates/2.boardList.jsp',
	            controller: 'BoardCtrl',
	            controllerAs: 'board',
	            requiresLogin: false
	        })
	        ;
	}

	
    //initialize get if not there
    if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};    
    }    

    // Answer edited to include suggestions from comments
    // because previous version of code introduced browser-related errors

    //disable IE ajax request caching
    $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
    // extra
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
});


myModule.directive('menuLink', ['$location', '$window', function(location, window) {
	  return {
	    restrict: 'CA',
	    replace: false,
	    transclude: false,
	    link: function(scope, elem, attrs) {
	    		
	    		//console.log("location", location);
	    		
	    		elem.on('click', function() {
	    			//alert(location.path());

	                scope.$apply(function() {
	                	if ("#" + location.path() == attrs.href) {
	                		//alert("2");
	                		window.location.reload();
	                	}
	                });
	    		});
	    		
		    	/*
		        if (parseInt(scope.index) == 0) {
		            angular.element(attrs.options).css({'background-image':'url('+ scope.item.src +')'});
		        }
	
		        elem.bind('click', function() {
		            var src = elem.find('img').attr('src');
	
		            // call your SmoothZoom here
		            angular.element(attrs.options).css({'background-image':'url('+ scope.item.src +')'});
		        });
		        */
	    	}
	}
}]);


//myModule.value('Menus', []);
//myModule.value('Menu_InfoContact', []);
//myModule.value('Menu_HUMANBASE', []);

myModule.factory('UrlLanguageStorage', ['$location', '$http', 
	function($location, $http) {
	    return {
	        put: function (name, value) {},
	        get: function (name) {
	
	        	/*
	    		$http.get("/rg/getLocale.do")
	    		.then(function(response) {
	    			
	    			$log.debug(response.data);
	    			
	    			return response.data.locale;
	
	    			//$translate.use($scope.language);
	    	        //$location.search('lang', $scope.language);
	    		});
	    		*/
	    		
	            //return $location.search()['lang']
	    		return "en";
	        }
	    };
	}
]);