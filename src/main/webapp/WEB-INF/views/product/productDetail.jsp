<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
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

.callout {
	position: fixed;
	bottom: 35px;
	right: 20px;
	margin-left: 20px;
	max-width: 300px;
}

.callout-header {
	text-align: center;
	padding: 25px 15px;
	background: #555;
	font-size: 30px;
	color: white;
}

.callout-container {
	padding: 15px;
	background-color: #ccc;
	color: black
}
</style>
</head>
<body>
	<input type="text" hidden="hidden" id="prdtId" name="prdtId" value="<c:out value='${product.prdtId}'/>">
	<input type="text" hidden="hidden" id="seller" name="seller" value="<c:out value='${product.userVO.userId}'/>">
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
		<div class="right-column">

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
					<c:if test="${not empty product.prdtUpdateDate}">
						/
						<span>수정일: </span>
						<fmt:formatDate value="${product.prdtUpdateDate }" pattern="yyyy-MM-dd" />
					</c:if>
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
							<button type="button" onclick="openMessageBox()">메시지 보내기</button>
						</sec:authorize>
					</div>
				</div>
			</div>

			<div class="product-price">
				<span><c:out value="${product.prdtPrice }" />원</span>
			</div>
		</div>

		<!-- Callout Message Box -->
		<div class="callout" style="display: none">
			<div class="callout-header">메시지 전송</div>
			<div class="callout-container">
				<textarea id="content" name="content" rows="5" cols="30" style="resize: none"></textarea>
			</div>
			<div class="callout-container">
				<button type="button" id="sendBtn" onclick="sendMessage()">전송</button>
				<button type="button" id="closeBtn" onclick="closeMessage()">취소</button>
			</div>
		</div>
		<!-- End of Callout Message Box -->
	</main>

	<script type="text/javascript">
		// Image Slider
		$(document).ready(function() {
			$('.slider').bxSlider();
		});
		// 찜하기
		$(document).on("click", "#likeBtn", function(e) {
			e.preventDefault();
			const prdtId = $("#prdtId").val();
			const likeBtn = $("#likeBtn");
			$.ajax({
				type : "post",
				url : "/products/like/" + prdtId,
				success : function(data) {
					alert("찜하기 완료.");
					likeBtn.text("찜하기 취소");
					likeBtn.attr("id", "unlikeBtn");
					location.reload();
				},
				error : function(e) {
					alert("에러 발생. 다시 요청해주세요.");
				}
			});
		});

		// 찜하기 취소
		$(document).on("click", "#unlikeBtn", function(e) {
			e.preventDefault();
			const prdtId = $("#prdtId").val();
			const unlikeBtn = $("#unlikeBtn");
			$.ajax({
				type : "post",
				url : "/products/unlike/" + prdtId,
				success : function(data) {
					alert("찜하기 취소 완료.");
					unlikeBtn.text("찜하기");
					unlikeBtn.attr("id", "likeBtn");
					location.reload();
				},
				error : function(e) {
					alert("에러 발생. 다시 요청해주세요.");
				}
			});
		});
		
		const prdtId = $("#prdtId").val();
		const seller = $("#seller").val();
		const content = $("#content");
		// 메시지 창 열기
		function openMessageBox() {
			const calloutElement = $(".callout");
			if (calloutElement.css("display") === "none") {
				calloutElement.show();
			} else {
				calloutElement.hide();
				content.val('');
			}
		}
		// 전송
		function sendMessage() {
			const sendData = {
				prdtId : prdtId,
				seller : seller,
				content : content.val(),
			}
			if (content.val() == null || content.val() == "") {
				alert("메시지를 입력하세요.");
				return false;
			}
			const result = confirm("판매자에게 메시지를 보내겠습니까?");
			if (result) {
				$.ajax({
					type : "post",
					url : "/messages",
					data : JSON.stringify(sendData),
					contentType : "application/json; UTF-8",
					success : function(data) {
						alert("메시지 전송 완료.");
						location.reload();
					},
					error : function(e) {
						alert("에러 발생. 다시 요청해주세요.");
					}
				});
			}
		}
		// 취소
		function closeMessage() {
			const calloutElement = $(".callout");
			calloutElement.hide();
			content.val('');
		}
	</script>
</body>
</html>