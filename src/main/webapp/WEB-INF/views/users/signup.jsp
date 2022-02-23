<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp"%>


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
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                    <form id="signUpForm" role="form" method="post">
                                    <input type="hidden" id="idCheckResult" value="0">
                                    <input type="hidden" id="emailCheckResult" value="0">
                                        <div class="form-group input-group">
                                            <input class="form-control" placeholder="아이디" type="text" id="userId" name="userId" autofocus>
                                            <span class="input-group-btn">
                                                <button class="btn btn-default" type="button" id="checkUserId"><i class="fa fa-search"></i>
                                                </button>
                                            </span>
                                        </div>
                                        <div class="form-group">
                                            <input class="form-control" placeholder="비밀번호" type="password" id="userPwd" name="userPwd">
                                        </div>
                                        <div class="form-group">
                                            <input class="form-control" placeholder="이름" type="text" id="userName" name="userName">
                                        </div>
                                        <div class="form-group input-group">
                                            <input class="form-control" placeholder="이메일" type="text" id="userEmail" name="userEmail">
                                            <span class="input-group-btn">
                                                <button class="btn btn-default" type="button" id="checkUserEmail"><i class="fa fa-search"></i>
                                                </button>
                                            </span>
                                        </div>
                                        <div class="form-group">
                                            <input class="form-control" placeholder="연락처" type="text" id="userPhone" name="userPhone">
                                        </div>
                                        <div class="form-group input-group">
                                            <input class="form-control" placeholder="주소" type="text" id="userAddr" name="userAddr">
                                            <span class="input-group-btn">
                                                <button class="btn btn-default" type="button" id="findAddr"><i class="fa fa-search"></i>
                                                </button>
                                            </span>
                                        </div>
                                        <button type="button" id="submitBtn" class="btn btn-success">회원가입</button>
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
        <!-- /#page-wrapper -->

    <!-- /#wrapper -->

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">

	// 아이디/이메일 중복체크 실행 여부 확인값 초기화
	validCheckInit();
	
	function validCheckInit() {
		if (document.getElementById("idCheckResult").value == "1") {
			document.getElementById("idCheckResult").value = "0";
		}
		if (document.getElementById("emailCheckResult").value == "1") {
			document.getElementById("emailCheckResult").value = "0";
		}
	}
	
	$(function() {
		
		$("#submitBtn").click(function(){
			var isSignable = true;
			
				// 폼 입력값 빈칸 및 유효성 체크
				var userId = document.querySelector('#userId');
				var userPwd = document.querySelector('#userPwd');
				var userName = document.querySelector('#userName');
				var userEmail = document.querySelector('#userEmail');
				var userPhone = document.querySelector('#userPhone');
				var userAddr = document.querySelector('#userAddr');
				
				// 빈칸 체크
				var elementArr = [
					userId,
					userPwd,
					userName,
					userEmail,
					userPhone,
					userAddr
				];
				
				elementArr.forEach(function(inputTag) {
					if (inputTag.value == null || inputTag.value == "") {
						alert(inputTag.placeholder + " 입력하세요.");
						isSignable = false;
						return false;
					}
				});
				
				// 이메일 체크
				var emailCheck = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
				if (!emailCheck.test(userEmail.value)) {
					alert("이메일 주소는 @가 포함되어야 합니다.");
					userEmail.focus();
					isSignable = false;
					return false;
				}
				
				// 전화번호 체크
				var phoneCheck = /^[0-9]{3}[0-9]{4}[0-9]{4}$/;
				if (!phoneCheck.test(userPhone.value)) {
					alert("전화번호는 숫자만 가능합니다.");
					userPhone.focus();
					isSignable = false;
					return false;
				}
				
				if ($('#idCheckResult').val() == "0") {
					alert("아이디 중복체크를 반드시 해주세요.");
					isSignable = false;
					return false;
				}
				
				if ($('#emailCheckResult').val() == "0") {
					alert("이메일 중복체크를 반드시 해주세요.");
					isSignable = false;
					return false;
				}
				
				isSignable = true;
				
				// 회원가입
				if (isSignable == true) {
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
						url: "/users",
						data: JSON.stringify(formData),
						contentType: "application/json; charset=utf-8",
						headers: {
							Accept: "text/html; charset=utf-8"
						},
						success: function(data) {
							alert(data);
							location.href="/";
						},
						error: function(e) {
							alert(e.responseText);
						}
					});
					
				}
			
		});
		
		// 아이디 중복 체크
		$('#checkUserId').on('click', function() {
			var userId = $('#userId').val();
			if (userId == null || userId == "") {
				alert("아이디 입력하세요.");
				return;
			}
			$.ajax({
				type: "get",
				url: "/users/signup/id",
				data: {"userId":userId},
				headers: {
					Accept: "text/html; charset=utf-8"
				},
				success: function(data) {
						// Http Status 200에 대해서는 success로 이동
						alert(data);
						$('#idCheckResult').val("1");
				},
				error: function(e) {
					// Controller 설정에 따라 Http Status 409는 여기로
					alert(e.responseText);
				}
			});
		});
		
		// 이메일 중복 체크
		$('#checkUserEmail').on('click', function() {
			var userEmail = $('#userEmail').val();
			if (userEmail == null || userEmail == "") {
				alert("이메일 입력하세요.");
				return;
			}
			var emailCheck = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
			if (!emailCheck.test(userEmail)) {
				alert("이메일 주소는 @가 포함되어야 합니다.");
				return;
			}
			$.ajax({
				type: "get",
				url: "/users/signup/email",
				data: {"userEmail":userEmail},
				headers: {
					Accept: "text/html; charset=utf-8"
				},
				success: function(data) {
					alert(data);
					$('#emailCheckResult').val("1");
				},
				error: function(e) {
					alert(e.responseText);
				}
			});
		});
		
		// 주소찾기
		$('#findAddr').on('click', function() {
		    new daum.Postcode({
		        oncomplete: function(data) {
		        	document.getElementById('userAddr').value = data.address;
		        }
		    }).open();
		})
		
	});
	
</script>

<%@ include file="../includes/footer.jsp"%>