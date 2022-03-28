<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래장터</title>
<style>
body {
	margin: 0 auto;
	max-width: 800px;
	padding: 0 20px;
}

.container {
	border: 2px solid #dedede;
	background-color: #f1f1f1;
	border-radius: 5px;
	padding: 10px;
	margin: 10px 0;
}

#area {
	overflow: scroll;
	height: 700px;
	background-color: #ffff;
}

.darker {
	border-color: #ccc;
	background-color: #ddd;
}

.container::after {
	content: "";
	clear: both;
	display: table;
}

.time-right {
	float: right;
	color: #aaa;
}

.time-left {
	float: left;
	color: #999;
}

input {
	width: 740px;
	height: 35px;
}

button {
	height: 40px;
	float: right;
}
</style>
</head>
<body>
<body>
	<h2>보낸 메시지 : ${prdtName}</h2>
	<div class="container" id="area">
		<input hidden="hidden" id="prdtId" name="prdtId" value='<c:out value="${prdtId}"/>'>
		<input hidden="hidden" id="buyer" name="buyer" value='<c:out value="${buyer}"/>'>
		<input hidden="hidden" id="seller" name="seller" value='<c:out value="${seller}"/>'>
		<c:forEach var="list" items="${sentMessages}">
			<div class="container">
				<c:choose>
					<c:when test="${list.type eq 'SELLER'}">
						<p>
							<b>${list.seller} : </b>${list.content}
						</p>
					</c:when>
					<c:otherwise>
						<p>
							<b>${list.buyer} : </b>${list.content}
						</p>
					</c:otherwise>
				</c:choose>
			</div>
		</c:forEach>
	</div>
	<input type="text" id="content" name="content" placeholder="메시지를 입력하세요." onkeypress="if(window.event.keyCode==13){sendMessage()}">
	<button type="button" onclick="sendMessage()">전송</button>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script type="text/javascript">
		function sendMessage() {

			const prdtId = $("#prdtId").val();
			const buyer = $("#buyer").val();
			const seller = $("#seller").val();
			const content = $("#content").val();

			const sendData = {
				prdtId : prdtId,
				buyer : buyer,
				seller : seller,
				content : content,
				type : "BUYER"
			}
			$.ajax({
				type : "post",
				url : "/messages/response",
				data : JSON.stringify(sendData),
				contentType : "application/json; UTF-8",
				success : function(data) {
					location.reload();
				},
				error : function(e) {
					alert("에러 발생. 다시 요청해주세요.");
				}
			});
		}
	</script>
</body>
</html>