angular.module("fileDownload").controller("downloadCtrl", function($scope, $timeout, $q, downloadService, $log) {
	
	$scope.downloadFileMultiple = function() {
		
		var params = {};
		var loadingText = 'Loading Data';
		var options = ['.', '..', '...'];
	
		var list = null;

		var param = $.param({});
		
        var req = {
			method: 'GET',
			url: "/fileDownloadMultiple.do?" + param
        };
        
		$http(req).then(function successCallback(response) {
			
			$log.debug(response);
			
			return;
			
			list = response;
			
			for (var i = 0; i < list.length; i++) {

				$scope.downloadFileText = loadingText + options[0];

				var promise = downloadService.validateBeforeDownload(params).then(null, function (reason) {
		      
					alert(reason);
		      
					// you can also throw the error
					// throw reason;
					return $q.reject(reason);
				
				}).then(downloadService.downloadFile).then(function() {
						$scope.downloadFileText = 'Loaded';
					}, function() {
						$scope.downloadFileText = 'Failed';
					}, function(i) {
						i = (i + 1) % 3;
						$scope.downloadFileText = loadingText + options[i];
					});
			
					promise.finally(function() {
						$timeout(function() {
							delete $scope.downloadFileText;  
						}, 2000);
					}
				);
			}
			
		}, function errorCallback(response) {
			$log.debug(response);
			alert("error occurred.");
		});

	};
});