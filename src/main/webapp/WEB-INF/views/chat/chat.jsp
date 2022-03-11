<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/chat/chat.css">
</head>
<body>
	<input hidden="hidden" id="userid" name="userid" value='<c:out value="${userid }"/>'>
	<div id="wrapper">
		<div id="menu">
			<p class="welcome">
				연락하기 <b></b>
			</p>
			<p class="logout">
				<a id="exit">채팅방 나가기</a>
			</p>
		</div>

		<div id="chatbox"></div>

		<form name="message" action="">
			<input type="text" id="msg" class="form-control" aria-label="Recipient's username" aria-describedby="button-addon2">
			<button class="btn btn-outline-secondary" type="button" id="button-send">전송</button>
		</form>
	</div>

	<div>
		<div class="col-6">
			<div class="input-group mb-3">
				<div class="input-group-append">
					
				</div>
			</div>
		</div>
	</div>

	<script src="/resources/dist/sockjs.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript">
		//전송 버튼 누르는 이벤트
		$("#button-send").on("click", function(e) {
			sendMessage();
			$('#msg').val('')
		});

		var sock = new WebSocket('ws://localhost:8081/websocket');
		sock.open = function() {
			console.log('open test');
			sock.send('test message');
		}
		sock.onmessage = onMessage;
		sock.onclose = onClose;
		sock.onopen = onOpen;

		function sendMessage() {
			sock.send($("#msg").val());
		}
		//서버에서 메시지를 받았을 때
		function onMessage(msg) {

			var data = msg.data;
			var sessionId = null; //데이터를 보낸 사람
			var message = null;

			var arr = data.split(":");

			for (var i = 0; i < arr.length; i++) {
				console.log('arr[' + i + ']: ' + arr[i]);
			}

			var cur_session = $('#userid').val(); //현재 세션에 로그인 한 사람
			console.log("cur_session : " + cur_session);

			sessionId = arr[0];
			message = arr[1];

			//로그인 한 클라이언트와 타 클라이언트를 분류하기 위함
			if (sessionId == cur_session) {

				var str = "<div class='col-6'>";
				str += "<div class='alert alert-secondary'>";
				str += "<b>" + sessionId + " : " + message + "</b>";
				str += "</div></div>";

				$("#chatbox").append(str);
			} else {

				var str = "<div class='col-6'>";
				str += "<div class='alert alert-warning'>";
				str += "<b>" + sessionId + " : " + message + "</b>";
				str += "</div></div>";

				$("#chatbox").append(str);
			}

		}
		//채팅창에서 나갔을 때
		function onClose(evt) {
			var user = $('#userid').val();
			var str = user + " 님이 퇴장하셨습니다.";
			$("#chatbox").append(str);
		}
		//채팅창에 들어왔을 때
		function onOpen(evt) {
			var user = $('#userid').val();
			var str = user + "님이 입장하셨습니다.";
			$("#chatbox").append(str);
		}
		// 채팅방 나가기
		$("#exit").on("click", function() {
			var user = $('#userid').val();
			var str = user + "님이 퇴장하셨습니다.";
			$("#chatbox").append(str);
			sock.close();
			history.back();
		});
	</script>
</body>
</html>