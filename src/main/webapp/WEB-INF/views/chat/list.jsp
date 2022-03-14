<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래사이트</title>
<link href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,700' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/resources/product/shop/shop.css">
<link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style>
#backBtn {
	margin: 20px auto auto 20px;
}
#no-chat {
	color: #ff0000;
	text-align: center;
}
</style>
</head>
<body>
	<a href="javascript:history.back()" id="backBtn" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">BACK</a>
	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-6 text-center mb-4">
					<h2 class="heading-section">연락 내역</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<h3 class="h5 mb-4 text-center">채팅 리스트</h3>
					<c:choose>
						<c:when test="${not empty chatList }">
							<div class="table-wrap">
								<table class="table">
									<thead class="thead-primary">
										<tr>
											<th>상품</th>
											<th>가격</th>
											<th>판매여부</th>
											<th>판매자</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="list" items="${chatList }">
											<tr class="alert" role="alert">
												<td><c:out value="${list.productVO.prdtName }" /></td>
												<td><c:out value="${list.productVO.prdtPrice }" /></td>
												<td><c:out value="${list.productVO.prdtTradeStatus }" /></td>
												<td><c:out value="${list.seller }" /></td>
												<td>
													<form method="post">
														<input hidden="hidden" id="roomId" name="roomId" value='<c:out value="${list.roomId }" />'>
														<button type="button" id="updateBtn" onclick="chatRoomBtn()">연락하기</button>
														<button type="button" id="deleteBtn" onclick="deleteChatBtn()">삭제</button>
													</form>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:when>
						<c:otherwise>
							<h1 id="no-chat">현재 진행중인 채팅이 없습니다.</h1>
						</c:otherwise>
					</c:choose>

				</div>
			</div>
		</div>
	</section>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript">
		var roomId = $("#roomId").val();
		function chatRoomBtn() {
			location.href = "/chat/room?roomId=" + roomId;
		}
		function deleteChatBtn() {
			var result = confirm("해당 채팅을 삭제하시겠습니까?");
			if (result) {
				$.ajax({
					type : "post",
					url : "/chat/delete",
					data : {
						roomId : roomId
					},
					success : function(data) {
						alert("삭제 완료.");
						location.reload();
					},
					error : function(e) {
						alert("에러 발생. 다시 요청해주세요.");
					}
				});
			}
		}
	</script>
</body>
</html>