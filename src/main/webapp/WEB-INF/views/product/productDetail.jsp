<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래사이트</title>
<link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="/resources/product/productDetail/productDetail.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>
<style>
#backBtn {
	margin: 20px auto auto 20px;
}
</style>
</head>
<body>
	<input type="text" hidden="hidden" id="prdtId" name="prdtId" value="<c:out value='${product.prdtId}'/>" >
	<input type="text" hidden="hidden" id="userName" name="userName" value="<c:out value='${userName}'/>" >
	<a href="javascript:history.back()" id="backBtn" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">BACK</a>
	<main class="container">
		<div class="left-column">
			<div class="slider">
				<c:forEach var="image" items="${images }">
					<div>
						<img src="/productImages/${image.fileName }">
					</div>
				</c:forEach>
			</div>
		</div>
		<!-- Right Column -->
		<div class="right-column">

			<!-- Product Description -->
			<div class="product-description">
				<h1>
					<c:out value="${product.prdtName }" />
				</h1>
				<p>
					<c:out value="${product.prdtInfo }" />
				</p>
			</div>

			<div class="product-configuration">
				<div class="product-color">
					<span>등록일: </span>
					<fmt:formatDate value="${product.prdtRegDate }" pattern="yyyy-MM-dd" />
					/
					<span>수정일: </span>
					<fmt:formatDate value="${product.prdtUpdateDate }" pattern="yyyy-MM-dd" />
				</div>
				<div class="product-color">
					<span>찜한개수: </span>
					<c:out value="${product.prdtLikeCnt }" />
				</div>
				<div class="product-color">
					<span>상품상태: </span>
					<c:out value="${product.prdtCondition }" />
				</div>
				<div class="product-color">
					<span>교환여부: </span>
					<c:out value="${product.prdtIsTradeable }" />
				</div>
				<div class="product-color">
					<span>배송비: </span>
					<c:out value="${product.prdtIsDeliveryFree }" />
				</div>
				<div class="product-color">
					<span>거래지역: </span>
					<c:out value="${product.prdtTradeLoc }" />
				</div>

				<div class="cable-config">
					<div class="cable-choose">
						<sec:authorize access="isAuthenticated()">
							<c:if test="${empty productLike }">
								<button type="button" id="likeBtn">찜하기</button>
							</c:if>
							<c:if test="${not empty productLike }">
								<button type="button" id="unlikeBtn">찜하기 취소</button>
							</c:if>
								<button type="button" id="chatBtn">연락하기</button>
						</sec:authorize>
					</div>
				</div>
			</div>

			<!-- Product Pricing -->
			<div class="product-price">
				<span><c:out value="${product.prdtPrice }" />원</span>
			</div>
		</div>
	</main>

	<script type="text/javascript">
	// Image Slider
	$(document).ready(function() {
		$('.slider').bxSlider();
	});
	$(document).on("click", "#likeBtn", function(e) {
		e.preventDefault();
		var prdtId = $("#prdtId").val();
		var likeBtn = $("#likeBtn");
		$.ajax({
			type: "post",
			url: "/products/like/"+prdtId,
			success: function(data){
				alert("찜하기 완료.");
				likeBtn.text("찜하기 취소");
				likeBtn.attr("id", "unlikeBtn");
				location.reload();
			},
			error: function(e) {
				alert("에러 발생. 다시 요청해주세요.");
			}
		});
	});
	$(document).on("click", "#unlikeBtn", function(e) {
		e.preventDefault();
		var prdtId = $("#prdtId").val();
		var unlikeBtn = $("#unlikeBtn");
		$.ajax({
			type: "post",
			url: "/products/unlike/"+prdtId,
			success: function(data){
				alert("찜하기 취소 완료.");
				unlikeBtn.text("찜하기");
				unlikeBtn.attr("id", "likeBtn");
				location.reload();
			},
			error: function(e) {
				alert("에러 발생. 다시 요청해주세요.");
			}
		});
	});
	
	$("#chatBtn").on("click", function(e){
		var prdtId = $("#prdtId").val();
		var userName = $("#userName").val();
		location.href = "/chat/room?userName="+userName+"&prdtId="+prdtId;
		
	});
	
	
	/* $("#chatBtn").on("click", function(e) {
		var prdtId = $("#prdtId").val();
		var result = confirm("연락하시겠습니까");
		if (result) {
			$.ajax({
				type: "post",
				url: "/chat/room",
				data: {"prdtId":prdtId},
				success: function(data) {
					alert("연락 페이지로 이동합니다.");
					location.href = "/chat/room?prdtId="+prdtId;
				},
				error: function(e) {
					alert("기존 연락 페이지로 이동합니다.");
				}
			});
		}
	}); */
	
	</script>
</body>
</html>