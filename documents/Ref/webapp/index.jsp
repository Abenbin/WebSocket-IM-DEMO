<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<base href="${ctx}/">
<title>主页</title>
<meta charset="UTF-8">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
	var username = "liuwb";
	var ws;
	var target = "ws://localhost:8080/ieen-socket/chat?username=" + username;

	$(document).ready(function() {
		if ('WebSocket' in window) {
			ws = new WebSocket(target);
		} else if ('MozWebSocket' in window) {
			ws = new MozWebSocket(target);
		} else {
			console.log('WebSocket is not supported by this browser!');
			return;
		}
		ws.onmessage = function(event) {
			console.info(event);
		}
	});
</script>
</head>
<body>
	<div id="container"
		style="border: 1px solid black; width: 300px; height: 300px; float: left;">
		<div id="content" style="height: 200px;"></div>
		<div style="border-top: 1px solid black; width: 300px; height: 100px;">
			<input id="msg" />
			<button id="subsend">发送</button>
		</div>
	</div>
	<script type="text/javascript">
		window.onload = function() {
			$('#subsend').on('click', function() {
				var msgValue = $('#msg').val();
				console.log('msgValue :' + msgValue);
				if (msgValue != '') {
					ws.send(msgValue);
					$('#msg').val('');
				}
			});
		}
	</script>
</body>