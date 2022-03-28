<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<style>
#backBtn {
	margin: 20px auto auto 20px;
}
</style>
</head>
<body>
<a href="javascript:history.back()" id="backBtn" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">BACK</a>
		<div class="container">
			<div class="row">
				<div class="col-md-4 col-md-offset-4">
					<div class="login-panel panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">회원 탈퇴</h3>
						</div>
						<div class="panel-body">
							<form role="form" name="FormLogin">
								<fieldset>
									<div class="form-group">
										<input class="form-control" placeholder="아이디" id="userId" name="userId" type="text" autofocus>
									</div>
									<div class="form-group">
										<input class="form-control" placeholder="이메일" id="userEmail" name="userEmail" type="text">
									</div>
									<button type="button" id="deleteUserBtn" class="btn btn-lg btn-success btn-block">탈퇴</button>
								</fieldset>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script type="text/javascript">
		
			const userId = $("#userId");
			const userEmail = $("#userEmail");
			
			$("#deleteUserBtn").on("click", function() {
				if (userId.val() == "") {
					alert("아이디 입력하세요.");
					userId.focus();
					return false;
				}
				if (userEmail.val() == "") {
					alert("비밀번호 입력하세요.");
					userEmail.focus();
					return false;
				}
				
				const result = confirm("사이트를 탈퇴하시겠습니까?");
				if (result) {
					const formData = {userId : userId.val(), userEmail : userEmail.val()};
					$.ajax({
						type: "delete",
						url: "/users",
						data: JSON.stringify(formData),
						contentType: "application/json; UTF-8",
						success: function(data) {
							alert("탈퇴 성공.");
							location.href = "/";
						},
						error: function(e) {
							alert("올바른 아이디/이메일을 입력해주세요.");
						}
					});
				}
			});
		</script>

		<%@ include file="../includes/footer.jsp"%>
</body>
</html>
