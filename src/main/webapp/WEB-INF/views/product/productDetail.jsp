<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래사이트</title>
<!-- Bootstrap Core CSS -->
<link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>
<style>
.card {
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
	max-width: 300px;
	margin: auto;
	text-align: left;
	font-family: arial;
}

.price {
	color: grey;
	font-size: 22px;
}

.card button {
	border: none;
	outline: 0;
	padding: 12px;
	color: white;
	background-color: #000;
	text-align: center;
	cursor: pointer;
	width: 100%;
	font-size: 18px;
}

.card button:hover {
	opacity: 0.7;
}

img {
	width: 210px;
	height: 180px;
	display: block;
	margin: auto;
}

p {
	margin: 0 0 0 5px;
	padding: 10px 0 0 0;
}
</style>
</head>
<body>
	<div class="card">

		<div class="slider">
			<c:forEach var="image" items="${images }">
				<div>
					<img src="/productImages/${image.fileName }">
				</div>
			</c:forEach>
		</div>
		<hr>
		
		<h1>${product.prdtName }</h1>
		<p class="price"><c:out value="${product.prdtPrice }" />원</p>
		<p>등록일: <fmt:formatDate value="${product.prdtRegDate }" pattern="yyyy-MM-dd" /></p>
		<p>찜한개수: <c:out value="${product.prdtLikeCnt }" /></p>
		<p>상품상태: <c:out value="${product.prdtCondition }" /></p>
		<p>교환여부: <c:out value="${product.prdtIsTradeable }" /></p>
		<p>배송비: <c:out value="${product.prdtIsDeliveryFree }" /></p>
		<p>거래지역: <c:out value="${product.prdtTradeLoc }" /></p>
		<p>상품정보</p>
		<textarea rows="5" id="prdtInfo" name="prdtInfo" class="form-control" readonly="readonly" style="resize: none; !important:text-align: left;">
			<c:out value="${product.prdtInfo }" />
		</textarea>
		<br>
		<form method="post">
		<p>
			<input type="text" hidden="hidden" id="prdtId" name="prdtId" value="<c:out value='${product.prdtId}'/>" >
			<sec:authorize access="isAuthenticated()" >
				<c:if test="${product.prdtLikeCnt == 0 }">
					<button type="button" id="likeBtn">찜하기</button>
				</c:if>
				<c:if test="${product.prdtLikeCnt > 0 }">
					<button type="button" id="unlikeBtn">찜하기 취소</button>
				</c:if>
				<button type="button">연락하기</button>
			</sec:authorize>
		</p>
		</form>
	</div>

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
				alert(data);
				likeBtn.text("찜하기 취소");
				likeBtn.attr("id", "unlikeBtn");
			},
			error: function(e) {
				alert(e.responseText);
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
				alert(data);
				unlikeBtn.text("찜하기");
				unlikeBtn.attr("id", "likeBtn");
			},
			error: function(e) {
				alert(e.responseText);
			}
		});
	});
</script>
</body>
</html>