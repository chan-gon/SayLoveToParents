<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>비밀번호 조회 결과</title>

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
                        <h3 class="panel-title">비밀번호 변경</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" method="post" id="inputForm">
                            <fieldset>
                            <c:if test="${not empty userId}">
                            	<div class="form-group">
                                    <input class="form-control" id="userId" name="userId" value="<c:out value='${userId}' />" readonly="readonly" >
                            	</div>
                            </c:if>
                            	<div class="form-group">
                                    <input class="form-control" placeholder="새로운 비밀번호" id="newPwd" name="newPwd" type="password" autofocus>
                            	</div>
                                <button type="button" id="changePwdBtn" class="btn btn-lg btn-success btn-block">비밀번호 변경</button>
                                <button type="button" id="homeBtn" class="btn btn-lg btn-success btn-block">HOME</button>
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
    		$('#changePwdBtn').click(function() {
    			var userId = $('#userId').val();
    			var newPwd = $('#newPwd').val();
    			$.ajax({
    				type: "post",
    				url: "/users/help/pwd/" + userId,
    			    contentType: 'application/json', 
    				data: JSON.stringify({'userPwd' : newPwd, 'userId' : userId}),
    				success: function(data) {
    					alert(data);
    					location.href = "/";
    				},
    				error: function(xhr, status, error) {
    					alert(error);
    				}
    			});
    		});
    		$('#homeBtn').click(function() {
    			location.href = "/";
    		});
    	});
    
    </script>

<%@ include file="../includes/footer.jsp"%>

</body>

</html>
