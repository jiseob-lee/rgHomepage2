
appAttachment.factory("downloadService", ['$interval', '$timeout', '$q', '$cookies', '$http', '$log',
	function($interval, $timeout, $q, $cookies, $http, $log) {
	
	var iframe = null;
	
	var generateIframeDownload = function(i) {

		var param = $.param({
			idx: listIdxs[i],
			filename: listFilenames[i]
		});
		
		var url = "/fileDownload.do" + "?" + param;

		//alert(url);
		
		iframe = document.createElement('iframe');
		
		//$cookieStore.put(config[1], 'true');
		
		setCookie(listFilenames[i], 'true');
		
		iframe.src = url;
		//iframe.style.display = "none";
		
		document.body.appendChild(iframe);  
	}

	var manageIframeProgress = function(config) {
		
		var defer = $q.defer();
      
		// notify that the download is in progress every half a second / do this for a maximum of 50 intervals 
		var promise = $interval(function () {
			//alert("cookie check : " + getCookie(config[1]) + ", " + config[1]);
			//if (!$cookieStore.get(config[1])) {
			if (!getCookie(config[1])) {
				$interval.cancel(promise);
			}
		}, 5000, 50000);
      
		promise.then(defer.reject, defer.resolve, defer.notify);
      
		promise.finally(function () {
			//$cookieStore.remove(config[1]);
			alert("cookie remove : " + config[1]);
			document.body.removeChild(iframe);
		});
	}

	var listIdxs = null;
	var listFilenames = null;

	return {

		validateBeforeDownload: function (config) {

			var defer = $q.defer();
      
			// notify that the download is in progress every half a second
			$interval(function (i) {
				defer.notify(i);
			}, 500);
    
			// mock response from server - this would typicaly be a $http request and response
			$timeout(function () {
				// in case of error: 
				// defer.reject("this file can not be dowloaded");
				defer.resolve(config);
			}, 2000);
  
			return defer.promise;
		},

		downloadFile: function (config) {

			/*
			generateIframeDownload(config);
			
			var promise = manageIframeProgress(config);
  
			// mock response from server - this would be automaticlly triggered by the file download compeletion
			$timeout(function() {
				//$cookieStore.remove(config[1]);
			}, 3000);

			return promise;
			*/

    		var param = $.param({});
    		
            var req = {
    			method: 'GET',
    			url: "/fileDownloadMultiple.do?" + param
            };
            
    		$http(req).then(function successCallback(response) {
    			
    			listIdxs = response.data.idxs;
    			listFilenames = response.data.filenames;
    			
    			if (listIdxs != null && listIdxs.length > 0) {
    				alert("start");
    				downloadFileMultiple(listIdxs.length, 0);
    			}
    			
    		}, function errorCallback(response) {
    			$log.debug(response);
    			alert("error occurred.");
    		});

		}
	}

	function downloadFileMultiple(total, i) {
		
		generateIframeDownload(i);
		
		var promise = $interval(function() {
			
			$.get( "/getSession.do?sessionName=" + listFilenames[i], function( data ) {
				
				$log.debug(data);
				
				if ( !data.session ) {
				
					if (iframe) {
						document.body.removeChild(iframe);
					}

					$interval.cancel(promise);
					
					if (total > i + 1) {

						//alert("total : " + total + "\ni : " + i);
						
						downloadFileMultiple(total, i + 1);
					}
					
				}
			});
						
		}, 2000);
		
		
		
		//$interval(, 2000);
			
	}

	function checkSession() {
		
		$.get( "/getSession.do?sessionName=" + listFilenames[i], function( data ) {
			
			$log.debug(data);
			
			if ( !data.session ) {
			
				if (iframe) {
					//document.body.removeChild(iframe);
				}

				$interval.cancel();
				
				if (total > i + 1) {
				
					downloadFileMultiple(total, i + 1);
				}
				
			}
		});
					
	}
	
	function setCookie(cname, cvalue, exdays) {
	    //var d = new Date();
	    //d.setTime(d.getTime() + (exdays*24*60*60*1000));
	    //var expires = "expires="+ d.toUTCString();
	    //document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
	    //document.cookie = cname + "=" + cvalue + ";path=/";
	}
	
	function getCookie(cname) {
	    var name = cname + "=";
	    var decodedCookie = decodeURIComponent(document.cookie);
	    var ca = decodedCookie.split(';');
	    for(var i = 0; i <ca.length; i++) {
	        var c = ca[i];
	        while (c.charAt(0) == ' ') {
	            c = c.substring(1);
	        }
	        if (c.indexOf(name) == 0) {
	            return c.substring(name.length, c.length);
	        }
	    }
	    return "";
	}
}]);