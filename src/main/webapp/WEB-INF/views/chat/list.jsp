<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
									<td><c:out value="${list.productVO.prdtName }" /> </td>
									<td><c:out value="${list.productVO.prdtPrice }" /> </td>
									<td><c:out value="${list.productVO.prdtTradeStatus }" /> </td>
									<td><c:out value="${list.seller }" /> </td>
									<td>
									<form method="post">
										<button type="button" id="updateBtn" onclick="updateProduct(this)">연락하기</button>
										<button type="button" id="deleteBtn" onclick="deleteProduct(this)">삭제</button>
									</form>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</section>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript">
		function deleteProduct(e) {
			var result = confirm("해당 상품을 삭제하시겠습니까?");
			if (result) {
				var prdtId = $(e).prev("input").val();
				$.ajax({
					type: "post",
					url: "/products/delete/"+prdtId,
					success: function(data) {
						alert("삭제 완료");
						location.reload();
					},
					error: function(e) {
						alert("에러 발생. 다시 요청해주세요.");
					}
				});
			}
		}
		
		function updateProduct(e) {
			var prdtId = $(e).prev().prev().val();
			location.href = "/products/update/"+prdtId;
		}
	</script>
</body>
</html>