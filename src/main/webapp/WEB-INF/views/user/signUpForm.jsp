<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp"%>

<div id="wrapper">
	<!-- Navigation -->

	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">회원가입</h1>
			</div>
			<!-- /.col-lg-12 -->
		</div>
		<!-- /.row -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">Basic Form Elements</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<form id="signUpForm" role="form" class="form-inline"
									method="post">
									<div class="form-inline">
										<label>아이디</label>
									</div>
									<input class="form-control" placeholder="아이디" type="text"
										id="userId" name="userId">
									<button class="btn btn-primary" id="checkUserId" type="button">중복확인</button>
									<div class="form-inline m-3">
										<label>비밀번호</label>
									</div>
									<input class="form-control" placeholder="비밀번호"
										type="password" id="userPwd" name="userPwd">
									<div class="form-inline">
										<label>이름</label>
									</div>
									<input class="form-control" placeholder="이름" type="text"
										id="userName" name="userName">
									<div class="form-inline">
										<label>이메일</label>
									</div>
									<input class="form-control" placeholder="이메일" type="text"
										id="userEmail" name="userEmail">
									<button class="btn btn-primary" id="checkUserEmail"
										type="button">중복확인</button>
									<div class="form-inline">
										<label>연락처</label>
									</div>
									<input class="form-control" placeholder="연락처" type="text" id="userPhone"
										name="userPhone">
									<div class="form-inline">
										<label>주소</label>
									</div>
									<input class="form-control" placeholder="주소" type="text" id="userAddr"
										name="userAddr">
									<button class="btn btn-primary" id="checkUserId" type="button">주소찾기</button>
									<div class="form-inline text-right">
										<button type="submit" class="btn btn-success">가입</button>
									</div>
								</form>
							</div>
							<!-- /.col-lg-6 (nested) -->
							<!-- /.col-lg-6 (nested) -->
						</div>
						<!-- /.row (nested) -->
					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
			</div>
			<!-- /.col-lg-12 -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /#page-wrapper -->
</div>

<script type="text/javascript">
	$(document).ready(function() {
		
		// 아이디 중복 체크
		$('#checkUserId').on('click', function() {
			var userId = $('#userId').val();
			if (userId == null || userId == "") {
				alert("아이디 입력칸이 비워져 있습니다.");
				return;
			}
			$.ajax({
				type: "get",
				url: "/users/signup-id-check",
				data: {"userId" : userId},
				dataType: "json", // 요청 후 응답받는 타입을 json으로 설정해야 XMLDocument 타입으로 되돌아오지 않는다.
				contentType: "application/json; charset=utf-8",
				success: function(data) {
					if (data == true) {
						alert("이미 사용중인 아이디.");
						return;
					} else {
						alert("사용할 수 있는 아이디.");
					}
				},
				error: function(e) {
					alert(e);
				}
			});
		});
		
		// 이메일 중복 체크
		$('#checkUserEmail').on('click', function() {
			var userEmail = $('#userEmail').val();
			if (userEmail == null || userEmail == "") {
				alert("이메일 입력칸이 비워져 있습니다.");
				return;
			}
			$.ajax({
				type: "get",
				url: "/users/email-check",
				data: {"userEmail" : userEmail},
				dataType: "json", // 요청 후 응답받는 타입을 json으로 설정해야 XMLDocument 타입으로 되돌아오지 않는다.
				contentType: "application/json; charset=utf-8",
				success: function(data) {
					if (data == true) {
						alert("이미 사용중인 이메일.");
						return;
					} else {
						alert("사용할 수 있는 이메일.");
					}
				},
				error: function(e) {
					alert(e);
				}
			});
		});
		
		// 회원가입
		$('#signUpForm').on('submit', function(e) {
			e.preventDefault();

			var elementArr = [
				userId = document.querySelector('#userId'), 
				userPwd = document.querySelector('#userPwd'),
				userName = document.querySelector('#userName'),
				userEmail = document.querySelector('#userEmail'),
				userPhone = document.querySelector('#userPhone'),
				userAddr = document.querySelector('#userAddr')
				];
			
			elementArr.forEach(function(inputTag) {
				if (inputTag.value == null || inputTag.value == "") {
					alert(inputTag.placeholder + " 입력칸이 비워져 있습니다.");
					inputTag.focus();
					return;
				}
			});
			
			var formData = {
					userId : $('#userId').val(),
					userPwd : $('#userPwd').val(),
					userName : $('#userName').val(),
					userEmail : $('#userEmail').val(),
					userPhone : $('#userPhone').val(),
					userAddr : $('#userAddr').val()
					}
			
			$.ajax({
				type: "post",
				url: "/users/signup",
				data: JSON.stringify(formData),
				contentType: "application/json; charset=utf-8",
				success: function(data) {
					alert("회원가입 완료");
					location.href = data;
				},
				error: function(e) {
					alert(e);
				}
			});

		});

	});
</script>

<%@ include file="../includes/footer.jsp"%>