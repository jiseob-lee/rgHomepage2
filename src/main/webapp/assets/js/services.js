angular.module("pascalprecht.translate").factory('UrlLanguageStorage', ['$location', '$http', 
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