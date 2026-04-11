<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular-route.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.7/angular-cookies.js"></script>

<!-- Angular Material requires Angular.js Libraries -->
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.7/angular-animate.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.7/angular-aria.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.7/angular-messages.min.js"></script>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.7/angular-material.min.js"></script>

<script type="text/javascript" src="/assets/js/tinymce/tinymce.min.js"></script>
<script type="text/javascript" src="/assets/js/angular-ui-tinymce/tinymce.min.js"></script>

<!--
<script type="text/javascript" src="/assets/js/tinymce-angular-demo/bower_components/tinymce/tinymce.js"></script>
<script type="text/javascript" src="/assets/js/tinymce-angular-demo/bower_components/angular-ui-tinymce/src/tinymce.js"></script>
-->

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/FileAPI.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/ng-file-upload-all.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/ng-file-upload-shim.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/danialfarid-angular-file-upload/12.2.13/ng-file-upload.js"></script>

<script type="text/javascript" src="/src/jisblee.me/1.attachment/controllers/AttachmentController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/2.board/controllers/BoardController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/2.board/controllers/ManageBoardController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/3.user/controllers/UserController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/4.test/controllers/TestController.js"></script>
<script type="text/javascript" src="/src/jisblee.me/0.common/controllers/ProgressCircularController.js"></script>

<script type="text/javascript" src="/src/jisblee.me/rg.js"></script>

<script type="application/javascript">

var serverFileList = new Array();
var downFileList = new Array();
		
<c:forEach var="item" items="${serverFileList}">
	serverFileList.push("${item}");
</c:forEach>

<c:forEach var="item" items="${downFileList}">
	downFileList.push("${item}");
</c:forEach>


alert("file count : " + serverFileList.length);

//for (var i=0; i<serverFileList.length; i++) {
	//alert(serverFileList[i] + " : " + downFileList[i]);
//}

if (serverFileList != null && serverFileList.length > 0) {
	downloadFileMultiple(serverFileList.length, 0);
}

//$.fileDownload()
//"/assets/images/1.png"

function downloadFileMultiple(total, i) {

	if (total > i) {
		
		//alert(serverFileList[i] + " : " + downFileList[i]);
		
		//downloadFileMultiple(total, i + 1);
		
		var param = $.param({
			idx: serverFileList[i],
			filename: downFileList[i]
		});
		
		var url = "https://jisblee.me/fileDownload.do" + "?" + param;

		url = "https://jisblee.me/fileDownload.do?idx=276&filename=20180512163530-ac9d24f2-f6ac-4145-9b41-f918dbcdcaa5";
		
		//location.href = url;
		
		//alert("1");
		
		
		/*
        var deferred = $q.defer();
        $http({
            method: 'GET',
            url: url
            //params: getParams,
            //data: postParams
        }).
         success(function (data, status, headers, config) {
             deferred.resolve(data)
         }).
         error(function (data, status)
         {
             debugger;
             deferred.reject(data);
         });
        */

		
		
		//$.when( location.href = url ).done(function() {
			//alert("1");
		//});
		
		//$.fileDownload(url)
			//.done(function() { alert("success"); })
			//.fail(function() { alert("fail"); });
		
		/*
		$.ajax({
			  url: url
			}).done(function() {
				downloadFileMultiple(total, i + 1);
			});
		*/
		
		
		/*
		$.get( url, function() {
			  //alert( "success : " + url);
			})
			.done(function() {
				//alert( "second success" );
			})
			.fail(function() {
				alert( "error" );
			})
			.always(function() {
				//alert( "finished" );
			});
		*/
		
	}
}


var url = "https://jisblee.me/fileDownload.do?idx=276&filename=20180512163530-ac9d24f2-f6ac-4145-9b41-f918dbcdcaa5";

//angular.injector(['ng', 'rg.Attachment']).get("dataService").doHttpRequest('GET', url);
//angular.injector(['ng', 'rg.Attachment']).get("batchLog").log2();
//dataService.test();

angular.injector(['ng', 'rg.Attachment']).get("MyService").GetName();

angular.injector(['ng', 'rg.Attachment']).get("MyService").doHttpRequest('GET', url);

</script>
