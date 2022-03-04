<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래사이트</title>
<link href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,700' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/resources/js/product/productDetail/css/style.css">
</head>
<body>
	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-6 text-center mb-4">
					<h2 class="heading-section">내상점</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<h3 class="h5 mb-4 text-center">등록된 상품</h3>
					<div class="table-wrap">
						<table class="table">
							<thead class="thead-primary">
								<tr>
									<th>사진</th>
									<th>이름</th>
									<th>가격</th>
									<th>등록일</th>
									<th>상태</th>
									<th>삭제</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="product" items="${products }">
								<tr class="alert" role="alert">
									<td>
										<div class="img" style="background-image: url(/productImages/${product.imageVO.fileName });"></div>
									</td>
									<td>
										<div class="email">
											<c:out value="${product.prdtName }" />
										</div>
									</td>
									<td><c:out value="${product.prdtPrice }" /> </td>
									<td><fmt:formatDate value="${product.prdtRegDate }" pattern="yyyy-MM-dd" /></td>
									<td>
										<c:out value="${product.prdtTradeStatus }" />
									</td>
									<td>
										<button type="button" id="deleteBtn">삭제</button>
										<button type="button" id="updateBtn">수정</button>
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

	<script src="/resources/js/product/productDetail/js/jquery.min.js"></script>
	<script src="/resources/js/product/productDetail/js/popper.js"></script>
	<script src="/resources/js/product/productDetail/js/bootstrap.min.js"></script>
	<script src="/resources/js/product/productDetail/js/main.js"></script>
	<script type="text/javascript">
		$("#deleteBtn").on("click", function() {
			console.log('click')
		});
		
		$("#updateBtn").on("click", function() {
			console.log('click')
		});
	</script>
</body>
</html>