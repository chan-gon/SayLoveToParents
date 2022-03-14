<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래장터</title>
<link rel="stylesheet" href="/resources/chat/chat.css" />
</head>
<body>
	<input hidden="hidden" id="roomId" name="roomId" value='<c:out value="${roomId}"/>'>
	<input hidden="hidden" id="userName" name="userName" value='<c:out value="${buyer}"/>'>
	<div id="chat-page">
		<div class="chat-container">
			<div class="chat-header">
				<h2>연락하기</h2>
			</div>
			<div class="connecting">연결중...</div>
			<ul id="messageArea">

			</ul>
			<form id="messageForm" name="messageForm" nameForm="messageForm">
				<div class="form-group">
					<div class="input-group clearfix">
						<input type="text" id="message" placeholder="Type a message..." autocomplete="off" class="form-control" />
						<button type="submit" class="primary">전송</button>
					</div>
				</div>
			</form>
		</div>
	</div>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
	<script type="text/javascript">
	
		var usernameForm = document.querySelector('#usernameForm');
		var chatPage = document.querySelector('#chat-page');
		var messageForm = document.querySelector('#messageForm');
		var messageInput = document.querySelector('#message');
		var messageArea = document.querySelector('#messageArea');
		var connectingElement = document.querySelector('.connecting');
		
		var roomId = document.querySelector("#roomId").value;

		var stompClient = null;
		var username = null;

		var colors = [ '#2196F3', '#32c787', '#00BCD4', '#ff5652', '#ffc107',
				'#ff85af', '#FF9800', '#39bbb0' ];

		function connect() {
			username = document.querySelector('#userName').value;
		    if(username) {
		        var socket = new SockJS("/stomp");
		        stompClient = Stomp.over(socket);
		        stompClient.connect({}, onConnected, onError);
		        
		    }
		}

		function onConnected() {
			// Subscribe to the Public Topic
			stompClient.subscribe('/topic/join/'+roomId, onMessageReceived);
			
			// Tell your username to the server
			stompClient.send("/app/join", {}, JSON.stringify({
				roomId : roomId,
				sender : username,
				type : 'JOIN'
			}));
			
			connectingElement.classList.add('hidden');
		}

		function onError(error) {
			connectingElement.textContent = '에러가 발생했습니다. 잠시 후 다시 시도해주세요.';
			connectingElement.style.color = 'red';
		}

		function sendMessage(event) {
			var messageContent = messageInput.value.trim();

			if (messageContent && stompClient) {
				var chatMessage = {
					roomId : roomId,
					sender : username,
					content : messageInput.value,
					type : 'CHAT'
				};

				stompClient.send("/app/message", {}, JSON
						.stringify(chatMessage));
				messageInput.value = '';
			}
			event.preventDefault();
		}

		function onMessageReceived(payload) {
			var message = JSON.parse(payload.body);
			var messageElement = document.createElement('li');

			if (message.type === 'JOIN') {
				messageElement.classList.add('event-message');
				message.content = message.sender + '님 접속중';
			} else if (message.type === 'LEAVE') {
				messageElement.classList.add('event-message');
				message.content = message.sender + '님 퇴장';
			} else {
				messageElement.classList.add('chat-message');

				var avatarElement = document.createElement('i');
				var avatarText = document.createTextNode(message.sender[0]);
				avatarElement.appendChild(avatarText);
				avatarElement.style['background-color'] = getAvatarColor(message.sender);

				messageElement.appendChild(avatarElement);

				var usernameElement = document.createElement('span');
				var usernameText = document.createTextNode(message.sender);
				usernameElement.appendChild(usernameText);
				messageElement.appendChild(usernameElement);
			}

			var textElement = document.createElement('p');
			var messageText = document.createTextNode(message.sender + " : " + message.content);
			textElement.appendChild(messageText);

			messageElement.appendChild(textElement);

			messageArea.appendChild(messageElement);
			messageArea.scrollTop = messageArea.scrollHeight;
		}

		function getAvatarColor(messageSender) {
			var hash = 0;
			for (var i = 0; i < messageSender.length; i++) {
				hash = 31 * hash + messageSender.charCodeAt(i);
			}

			var index = Math.abs(hash % colors.length);
			return colors[index];
		}

		connect();
		messageForm.addEventListener('submit', sendMessage, true)
	</script>
</body>
</html>