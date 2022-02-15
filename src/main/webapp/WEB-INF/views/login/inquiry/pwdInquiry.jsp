<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>비밀번호찾기</title>

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="/resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

</head>

<body>

    <div id="wrapper">

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">비밀번호찾기</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" method="get" id="inputForm">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="아이디" id="userId" name="userId" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="이메일" id="userEmail" name="userEmail" type="text">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="인증번호 입력" id="certNum" name="certNum" type="text" disabled="disabled">
                                </div>
                                <div class="form-group text-center">
                                	<button type="button" id="sendEmailBtn" class="btn btn-primary">인증번호 전송</button>
                                	<button type="button" id="checkCertNum" class="btn btn-primary" disabled="disabled">인증번호 확인</button>
                                </div>
                                <button type="button" id="changePwdBtn" class="btn btn-lg btn-success btn-block" disabled="disabled">비밀번호 변경</button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript">
    
    	$(function() {
    		
    		// 인증코드 담는 변수
    		var code = "";
    		
    		$('#sendEmailBtn').click(function() {
	    		var isSubmittable = true;
	    		var userId = $('#userId').val();
		    	var userEmail = $('#userEmail').val();
	    		
    			if (userId == "" || userId == null) {
    				alert("아이디를 입력하세요.");
    				isSubmittable = false;
    				return false;
    			}
    			if (userEmail == "" || userEmail == null) {
    				alert("이메일을 입력하세요.");
    				isSubmittable = false;
    				return false;
    			}
    			
    			isSubmittable = true;
    			
    			if (isSubmittable == true) {
    				$.ajax({
    					type: "get",
    					url: "/users/send-cert-email?userId=" + userId + "&userEmail=" + userEmail,
    					success: function(data) {
    						alert("인증번호 전송 완료. 인증번호 입력 후 인증을 누르세요.");
    						code = data;
    						$('#sendEmailBtn').attr("disabled", true);
    						$('#certNum').attr("disabled", false);
    						$('#checkCertNum').attr("disabled", false);
    					},
    					error: function(data, textStatus, xhr) {
    						alert(data.responseText);
    					}
    					
    				});
    			}
    		});
    		
    		$('#checkCertNum').click(function() {
    			var certNum = $('#certNum').val();
    			if (certNum == "" || certNum == null) {
    				alert("인증번호를 입력하세요.");
    				return false;
    			}
    			if (certNum != code) {
    				alert("인증번호를 잘못 입력했습니다. 다시 입력해주세요.");
    				$('#certNum').focus();
    				return false;
    			}
    			if (certNum == code) {
    				alert("인증번호가 일치합니다. 버튼을 눌러 비밀번호를 확인하세요.");
    				$('#changePwdBtn').attr("disabled", false);
    				$('#certNum').attr("disabled", true);
					$('#checkCertNum').attr("disabled", true);
    			}
    		});
    		
    		$('#changePwdBtn').click(function() {
    			location.href = "/users/pwd";
    		});
    	});
    
    </script>

<%@ include file="../../includes/footer.jsp"%>

</body>

</html>
