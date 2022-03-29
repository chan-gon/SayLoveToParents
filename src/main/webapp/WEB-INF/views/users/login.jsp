<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>중고거래사이트</title>
<link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
<link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">
<link href="/resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">
<link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet">
<link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
</head>
<body>
		<button type="button" class="btn btn-primary md-3" id="signUpBtn">회원가입</button>
		<div class="container">
			<div class="row">
				<div class="col-md-4 col-md-offset-4">
					<div class="login-panel panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">webhook test</h3>
							<c:if test="${not empty errormsg}">
								<font color="red">
									<p>
										<c:out value="${errormsg }" />
									</p>
								</font>
							</c:if>
						</div>
						<div class="panel-body">
							<form role="form" name="FormLogin" action="/loginProcess" method="POST" onsubmit="return checkInput()">
								<sec:csrfInput />
								<fieldset>
									<div class="form-group">
										<input class="form-control" placeholder="아이디" id="userId" name="userId" type="text" autofocus>
									</div>
									<div class="form-group">
										<input class="form-control" placeholder="비밀번호" id="userPwd" name="userPwd" type="password">
									</div>
									<div class="form-group">
										<button class="btn btn-block" id="idInquiry" type="button">아이디찾기</button>
										<button class="btn btn-block" id="pwdInquiry" type="button">비밀번호찾기</button>
									</div>
									<button type="submit" class="btn btn-lg btn-success btn-block">로그인</button>
								</fieldset>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script type="text/javascript">
			function checkInput() {

				if (FormLogin.userId.value == "") {
					alert("아이디 입력하세요.");
					FormLogin.userId.focus();
					return false;
				} else if (FormLogin.userPwd.value == "") {
					alert("비밀번호 입력하세요.");
					FormLogin.userId.focus();
					return false;
				} else {
					return true;
				}
			}

			$(function() {
				// 아이디 찾기
				$('#idInquiry').click(function() {
					window.open("/users/help/id");
				});

				// 비밀번호 찾기
				$('#pwdInquiry').click(function() {
					window.open("/users/help/pwd");
				});
			});

			$('#signUpBtn').css("margin", "10px 0 0 10px");
			$('#signUpBtn').click(function() {
				location.href = "/users/signup";
			});
		</script>

		<%@ include file="../includes/footer.jsp"%>
</body>

</html>
