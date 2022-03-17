<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래사이트</title>
<link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style>
#backBtn {
	margin: 20px auto auto 20px;
}

button {
	float: right;
	margin-left: auto;
	margin-right: 0;
}
</style>
</head>
<body>
<a href="javascript:history.back()" id="backBtn" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">BACK</a>

	<div class="container">
		<h2>내가 받은 메시지</h2>
		<c:forEach var="list" items="${receivedMessages }">
			<ul class="list-group">
				<li class="list-group-item"><b>문의 상품: </b>${list.productVO.prdtName} <b>\ 보낸 사람: </b>${list.buyer }
				<button type="button" onclick="location.href='/messages/received?seller=${list.seller}&buyer=${list.buyer}&prdtId=${list.prdtId}&prdtName=${list.productVO.prdtName}'">답장 보내기</button></li>
			</ul>
		</c:forEach>
	</div>
	
	<div class="container">
		<h2>내가 보낸 메시지</h2>
		<c:forEach var="list" items="${sentMessages }">
			<ul class="list-group">
				<li class="list-group-item"><b>문의 상품: </b>${list.productVO.prdtName}
				<button type="button" onclick="location.href='/messages/sent?buyer=${list.buyer}&seller=${list.seller}&prdtId=${list.prdtId}&prdtName=${list.productVO.prdtName}'">답장 확인</button></li>
			</ul>
		</c:forEach>
	</div>
</body>
</html>