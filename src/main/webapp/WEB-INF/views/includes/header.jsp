<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>중고거래사이트</title>

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

	<style>
	div.gallery {
		margin: 5px;
		border: 1px solid #ccc;
		float: left;
		width: 210px;
	}
	
	div.gallery:hover {
		border: 1px solid #777;
	}
	
	div.gallery img {
		width: 208px;
		height: 220px;
	}
	
	div.desc {
		padding: 15px 0;
		text-align: center;
	}
	</style>

</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/main">중고거래사이트</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                    
	                    <sec:authorize access="isAuthenticated()">
	                        <li><a href="/products/shop"><i class="fa fa-gear fa-fw"></i>내상점</a>
	                        </li>
	                        <li><a href="/products/new"><i class="fa fa-gear fa-fw"></i>판매하기</a>
	                        </li>
	                        <li><a href="/users/profile"><i class="fa fa-user fa-fw"></i>회원정보 수정</a>
	                        </li>
	                    </sec:authorize>
                        
                        <sec:authorize access="isAnonymous()">
	                        <li><a href="/users/signup"><i class="fa fa-user-plus fa-fw"></i>회원가입</a>
	                        </li>
                        </sec:authorize>
                        
                        <li class="divider"></li>
                        
                        <!-- 사용자가 Anonymous 상태인 경우 출력 -->
                        <sec:authorize access="isAnonymous()">
	                        <li><a href="/users/login"><i class="fa fa-sign-in fa-fw"></i>로그인</a>
	                        </li>
                        </sec:authorize>
                        
                        <!-- End of 사용자가 인증 되었을 때 출력 -->
                        
                        <sec:authorize access="isAuthenticated()">
	                        <li><a href="#" onclick="document.getElementById('logout-form').submit();"><i class="fa fa-sign-in fa-fw"></i>로그아웃</a>
	                        </li>
                        </sec:authorize>
					</ul>
					
					<!-- 로그아웃 폼 -->
						<form id="logout-form" action='<c:url value='/logout'/>' method="post">
							<sec:csrfInput/>
						</form>
					<!-- End of 로그아웃 폼 -->
					
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->
            
        </nav>
        
       <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>