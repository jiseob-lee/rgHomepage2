<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.net.InetAddress" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>IP Address</title>
<script type="text/javascript">
function download() {
	var url = "";
	var idx = [];
	var filename = [];
	
	idx[0] = "505";
	filename[0] = "20201107114712-3fffcf4d-2c4b-4501-9572-a2b251767c6e";
	
	idx[1] = "506";
	filename[1] = "20201107195000-8344df6a-a089-4c94-a4f1-aeabad760741";
	
	//for (var i=0; i < idx.length; i++) {
		//var url = "/fileDownload.do?idx=" + idx[i] + "&filename=" + filename[i];
		//location.href = url;
	//}
	
	url = "/fileDownload.do?idx=" + idx[0] + "&filename=" + filename[0];
	//alert(url);
	//location.href = url;
	downloadURL(url);
	url = "/fileDownload.do?idx=" + idx[1] + "&filename=" + filename[1];
	//alert(url);
	//location.href = url;
	downloadURL(url);
}

var count=0;

var downloadURL = function downloadURL(url){
  var hiddenIFrameID = 'hiddenDownloader' + count++;
  var iframe = document.createElement('iframe');
  iframe.id = hiddenIFrameID;
  iframe.style.display = 'none';
  document.body.appendChild(iframe);
  iframe.src = url;
}
</script>
</head>
<body>

<%
InetAddress IP = InetAddress.getLocalHost();
out.println(IP.toString());
%>

<input type="button" value="download" onclick="download();" />

</body>
</html>