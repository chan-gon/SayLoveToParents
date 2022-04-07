<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
					<h2 class="heading-section">내상점</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<h3 class="h5 mb-4 text-center">판매중인 상품</h3>
					<div class="table-wrap">
						<c:choose>
							<c:when test="${empty products }">
								<h2 style="text-align: center">판매중인 상품이 없습니다.</h2>
							</c:when>
							<c:otherwise>
								<table class="table">
									<thead class="thead-primary">
										<tr>
											<th>사진</th>
											<th>이름</th>
											<th>가격</th>
											<th>등록일</th>
											<th>상태</th>
											<th>삭제</th>
											<th>수정</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="product" items="${products }">
											<tr class="alert" role="alert">
												<td><input hidden="hidden" type="text" id="prdtId" name="prdtId" value="<c:out value="${product.prdtId}"/>">
													<div class="img" style="background-image: url(${s3URL}${product.imageVO.fileName}); cursor:pointer;" title="클릭하면 상품으로 이동합니다." onclick="showProduct(this)"></div></td>
												<td>
													<div class="email">
														<c:out value="${product.prdtName }" />
													</div>
												</td>
												<td><c:out value="${product.prdtPrice }" /></td>
												<td><fmt:formatDate value="${product.prdtRegDate }" pattern="yyyy-MM-dd" /></td>
												<td><c:out value="${product.prdtTradeStatus }" /></td>
												<td><input hidden="hidden" type="text" id="prdtId" name="prdtId" value="<c:out value="${product.prdtId}"/>">
													<button type="button" id="deleteBtn" onclick="deleteProduct(this)">삭제</button></td>
												<td><input hidden="hidden" type="text" id="prdtId" name="prdtId" value="<c:out value="${product.prdtId}"/>">
													<button type="button" id="updateBtn" onclick="updateProduct(this)">수정</button></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>

		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h3 class="h5 mb-4 text-center">찜한 상품</h3>
					<div class="table-wrap">
						<c:choose>
							<c:when test="${empty likeProductList}">
								<h2 style="text-align: center">찜한 상품이 없습니다.</h2>
							</c:when>
							<c:otherwise>
								<table class="table">
									<thead class="thead-primary">
										<tr>
											<th>사진</th>
											<th>이름</th>
											<th>가격</th>
											<th>찜한 날짜</th>
											<th>상태</th>
											<th>찜하기 취소</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="product" items="${likeProductList}">
											<tr class="alert" role="alert">
												<td><input hidden="hidden" type="text" id="prdtId" name="prdtId" value="<c:out value="${product.prdtId}"/>">
													<div class="img" style="background-image: url(${s3URL}${product.imageVO.fileName}); cursor:pointer;" title="클릭하면 상품으로 이동합니다." onclick="showProduct(this)"></div></td>
												<td>
													<div class="email">
														<c:out value="${product.prdtName }" />
													</div>
												</td>
												<td><c:out value="${product.prdtPrice }" /></td>
												<td><fmt:formatDate value="${product.productLikeVO.createDate }" pattern="yyyy-MM-dd" /></td>
												<td><c:out value="${product.prdtTradeStatus }" /></td>
												<td>
													<form method="post">
														<input hidden="hidden" type="text" id="prdtId" name="prdtId" value="<c:out value="${product.prdtId }" />">
														<button type="button" id="deleteBtn" onclick="unlike(this)">찜하기 취소</button>
													</form>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</section>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript">
		// 상품 상세 페이지 이동
		function showProduct(e) {
			var prdtId = $(e).prev("input").val();
			location.href = "/products/" + prdtId;
		}

		// 상품 삭제
		function deleteProduct(e) {
			var result = confirm("해당 상품을 삭제하시겠습니까?");
			if (result) {
				var prdtId = $(e).prev("input").val();
				$.ajax({
					type : "post",
					url : "/products/delete/" + prdtId,
					success : function(data) {
						alert("삭제 완료");
						location.reload();
					},
					error : function(e) {
						alert("에러 발생. 다시 요청해주세요.");
					}
				});
			}
		}

		// 상품 수정
		function updateProduct(e) {
			var prdtId = $(e).prev("input").val();
			location.href = "/products/update/" + prdtId;
		}

		// 찜하기 취소
		function unlike(e) {
			var prdtId = $(e).prev("input").val();
			const result = confirm("찜하기 취소하시겠습니까?");
			if (result) {
				$.ajax({
					type : "post",
					url : "/products/unlike/" + prdtId,
					success : function(data) {
						alert("찜하기 취소 완료.");
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