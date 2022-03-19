<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<div class="navbar-default sidebar" role="navigation">
	<div class="sidebar-nav navbar-collapse">
		<form action="/main" method="get" id="searchForm">
		<ul class="nav" id="side-menu">
			<li class="sidebar-search">
				<div class="input-group custom-search-form">
					<input type="text" class="form-control" id="keyword" name="keyword" placeholder="상품명 입력" onkeypress="if(window.event.keyCode==13){searchProduct()}"> 
					<span class="input-group-btn">
						<button class="btn btn-default" type="button" name="keywordBtn">
							<i class="fa fa-search"></i>
						</button>
					</span>
				</div>
			</li>
			<li><a id="category">여성의류</a></li>
			<li><a id="category">남성의류</a></li>
			<li><a id="category">신발</a></li>
			<li><a id="category">가방</a></li>
			<li><a id="category">시계</a></li>
			<li><a id="category">패션액세서리</a></li>
			<li><a id="category">디지털</a></li>
			<li><a id="category">스포츠</a></li>
			<li><a id="category">차량</a></li>
			<li><a id="category">스타굿즈</a></li>
			<li><a id="category">키덜트</a></li>
			<li><a id="category">음반</a></li>
			<li><a id="category">도서</a></li>
			<li><a id="category">뷰티</a></li>
			<li><a id="category">가구</a></li>
			<li><a id="category">생활</a></li>
			<li><a id="category">반려동물용품</a></li>
			<li><a id="category">유아동</a></li>
		</ul>
		<input type="hidden" name="pageNum" value='<c:out value="${pageMaker.cri.pageNum }" />'> 
		<input type="hidden" name="amount" value='<c:out value="${pageMaker.cri.amount }" />'>
		<input type="hidden" name="category" value='<c:out value="${pageMaker.cri.category }" />'>
		</form>
	</div>
</div>
