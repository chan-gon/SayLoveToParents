<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래장터</title>
<!-- Google font -->
<link href="https://fonts.googleapis.com/css?family=Quicksand:700" rel="stylesheet">

<!-- Font Awesome Icon -->
<link type="text/css" rel="stylesheet" href="/resources/css/font-awesome.min.css" />

<!-- Custom stlylesheet -->
<link type="text/css" rel="stylesheet" href="/resources/css/error500-style.css" />
</head>
<body>
	<div id="notfound">
		<div class="notfound">
			<div class="notfound-bg">
				<div></div>
				<div></div>
				<div></div>
			</div>
			<c:if test="${not empty EXCEPTION_MSG }">
				<h2>${EXCEPTION_MSG }</h2>
			</c:if>
			<h4>잠시 후 다시 시도해주세요.</h4>
			<h4>계속 발생하는 경우 관리자에게 문의해주세요.</h4>
			<a href="/main">MAIN</a>
			<a href="javascript:history.back()">BACK</a>
		</div>
	</div>
</body>
</html>