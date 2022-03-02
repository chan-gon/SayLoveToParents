<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
		<img src="/productImages/${product.imageVO.fileName }" alt="Denim Jeans">
		<h1>${product.prdtName }</h1>
		<p class="price"><c:out value="${product.prdtPrice }" />원</p>
		<p>등록일: <c:out value="${product.prdtRegDate }" /></p>
		<p>상품상태: <c:out value="${product.prdtCondition }" /></p>
		<p>교환여부: <c:out value="${product.prdtIsTradeable }" /></p>
		<p>배송비: <c:out value="${product.prdtIsDeliveryFree }" /></p>
		<p>거래지역: <c:out value="${product.prdtTradeLoc }" /></p>
		<p><c:out value="${product.prdtInfo }" /></p>
		<p>
			<button>찜하기</button>
			<button>연락하기</button>
		</p>
	</div>
</body>
</html>