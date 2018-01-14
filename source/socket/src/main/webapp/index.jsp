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
	var target = "ws://localhost:8080/socket/chat";

	$(document).ready(
			function() {
				if ('WebSocket' in window) {
					ws = new WebSocket(target);
				} else if ('MozWebSocket' in window) {
					ws = new MozWebSocket(target);
				} else {
					console.log('WebSocket is not supported by this browser!');
					return;
				}
				ws.onmessage = function(event) {
					var respMsg = JSON.parse(event.data);
					$('#content').append(respMsg.msgDate);
					if (undefined == respMsg.toUserId || '' == respMsg.toUserId
							|| '*' == respMsg.toUserId) {
						// 群聊显示
						if (undefined == respMsg.fromUserId
								|| '' == respMsg.fromUserId
								|| '*' == respMsg.fromUserId) {
							// 系统提示 
							$('#content').append('  ' + respMsg.content);
							$('#content').append('<div></div>');
						} else {
							$('#content').append(respMsg.fromUserId + '说：');
							$('#content').append('<div></div>');
							$('#content').append('  ' + respMsg.content);
							$('#content').append('<div></div>');
						}
					} else {
						$('#content').append(respMsg.fromUserId + '对你说：');
						$('#content').append('<div></div>');
						$('#content').append('  ' + respMsg.content);
						$('#content').append('<div></div>');
					}
					
					$('#rightcontent').empty();
					console.log(respMsg.users);
					if (undefined != respMsg.users) {
						respMsg.users.forEach(function(value, index, array) {
							$('#rightcontent').append(value);
							$('#rightcontent').append('<div></div>');
						});
					}
				}
			});
</script>
</head>
<body>
	<h3>欢迎${sessionScope.loginUser}进入聊天室！</h3>
	<input id="loginUser" type="hidden" value="${sessionScope.loginUser}">
	<div style="border-top: 1px solid black; width: 300px; height: 100px;">
		<input id="login" />
		<button id="sublogin">登录</button>
	</div>
	<div id="container"
		style="border: 1px solid black; width: 300px; height: 300px; float: left;">
		<div id="content" style="height: 200px;"></div>
		<div style="border-top: 1px solid black; width: 300px; height: 100px;">
			<input id="msg" />
			<button id="subsend">发送</button>
		</div>
	</div>
	<div id="rightcontainer"
		style="border: 1px solid black; width: 300px; height: 300px; float: right;">
		<div id="rightcontent" style="height: 500px;"></div>
	</div>
	<script type="text/javascript">
		window.onload = function() {
			$('#sublogin').on('click', function() {
				var msgValue = $('#login').val();
				$.post("login", {
					'loginUser' : msgValue
				}, function(data, status) {
					window.location.href = "index.jsp";
				});
			});

			$('#subsend').on('click', function() {
				var msgValue = $('#msg').val();
				if (msgValue != '') {
					var sendMsg = {};
					sendMsg.toUserId = '*';
					sendMsg.content = msgValue;
					ws.send(JSON.stringify(sendMsg));
					$('#msg').val('');
					$('#content').append(new Date().toLocaleString());
					$('#content').append('我说：');
					$('#content').append('<div></div>');
					$('#content').append('  ' + msgValue);
					$('#content').append('<div></div>');
				}
			});
		}
	</script>
</body>