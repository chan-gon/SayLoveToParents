<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래장터</title>
<link rel="stylesheet" href="/resources/chat/chat.css">
</head>
<body>
	<input hidden="hidden" id="userId" name="userId" value='<c:out value="${userId}"/>'>
	<input hidden="hidden" id="roomId" name="roomId" value='<c:out value="${roomId}"/>'>
	<div id="wrapper">
		<div id="menu">
			<p class="welcome">
				연락하기 <b></b>
			</p>
			<p class="logout">
				<a id="exit" onclick="disconnect();">채팅방 나가기</a>
			</p>
		</div>

		<div id="chatbox"></div>

		<form name="message" method="post">
			<input type="text" id="msg" class="form-control" aria-label="Recipient's username" aria-describedby="button-addon2">
			<button class="btn btn-outline-secondary" type="button" id="button-send" onclick="messageSend();">전송</button>
		</form>
	</div>

	<div>
		<div class="col-6">
			<div class="input-group mb-3">
				<div class="input-group-append"></div>
			</div>
		</div>
	</div>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.0/sockjs.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript">
		var userId = $("#userId").val();
		var socket = new SockJS("/stomp");
		var client = Stomp.over(socket);
		client.connect({}, function(frame) {
			console.log("소켓 연결 성공 = ", frame);
			$("#chatbox").append(userId + "님이 채팅방에 입장하셨습니다." + "<br>");
			
			client.subscribe("/topic/enter", function(message) {
				console.log("/topic/enter = " + message);
			});
			
			client.subscribe("/topic/message", function(message) {
				console.log("/topic/message = " + message.body);
				$("#chatbox").append(message.body + "<br>");
				
			});
		});

		function disconnect() {
			if (client != null) {
				client.disconnect();
			}
			$("#chatbox").append('Disconnected<br>');
		}

		function messageSend(e) {
			e.preventDefault();
			var message = $("#msg").val();
			var roomId = $("#msg").val();
			var sendData = {"roomId":roomId,"message":message}
			$.ajax({
				type: "post",
				url: "/chat/message",
				data: JSON.stringify(sendData),
				contentType: "application/json; charset=utf-8",
				success: function(data) {
					client.send("/app/message", {}, message);
				},
				error: function(e) {
					alert("에러 발생. 다시 요청해주세요.");
				}
			});
		}
		
	</script>

</body>
</html>