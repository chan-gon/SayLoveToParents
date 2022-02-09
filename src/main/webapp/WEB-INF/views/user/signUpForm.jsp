<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp"%>

<div id="wrapper">

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
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                    <form id="signUpForm" role="form" method="post">
                                        <div class="form-group input-group">
                                            <input class="form-control" placeholder="아이디" type="text" id="userId" name="userId">
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
                                        <button type="submit" class="btn btn-success">회원가입</button>
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
    <!-- /#wrapper -->

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		// 아이디 중복 체크
		$('#checkUserId').on('click', function() {
			var userId = $('#userId').val();
			if (userId == null || userId == "") {
				alert("아이디 입력하세요.");
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
				alert("이메일 입력하세요.");
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
			
			var isSignable = true;

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
					alert(inputTag.placeholder + " 입력하세요.");
					isSignable = false;
					return;
				}
			});
			
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
			}

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