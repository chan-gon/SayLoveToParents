<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>

	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">
				상품 등록
			</h1>
		</div>
	</div>
	
	<div class="container">
		
		<form name="newProduct" action="./processAddProduct.jsp" class="form-horizontal" method="post" 
		enctype="multipart/form-data">
		
			<div class="form-group row">
				<label class="col-sm-2">상품 이미지</label>
				<div>
					<input type="file" id="productImage" name="productImage" class="form-control">
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">제목</label>
				<div class="com-sm-3">
					<input type="text" id="productName" name="productName" class="form-control">
					<span><span id="nameCnt"></span>/40</span>
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">카테고리</label>
				 <div class="com-sm-3">
					<select class="form-control" id="productCategory" name="productCategory">
					  <option>여성의류</option>
					  <option>남성의류</option>
					  <option>신발</option>
					  <option>가발</option>
					  <option>시계/쥬얼리</option>
					  <option>시계/쥬얼리</option>
					  <option>패션 액세서리</option>
					  <option>디지털/가전</option>
					  <option>스포츠/레저</option>
					  <option>차량/오토바이</option>
					  <option>스타굿즈</option>
					  <option>키덜트</option>
					  <option>음반/악기</option>
					  <option>도서/티켓/문구</option>
					  <option>뷰티/미용</option>
					  <option>가구/인테리어</option>
					  <option>생활/가공식품</option>
					  <option>반려동물용품</option>
					  <option>기타</option>
					</select>
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">가격</label>
				<div class="com-sm-3">
					<input type="text" id="productPrice" name="productPrice" class="form-control">
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">상태</label>
				<div class="com-sm-5">
					<input type="radio" id="productCondition" name="productCondition" value="OLD"> 중고상품
					<input type="radio" id="productCondition" name="productCondition" value="NEW"> 새상품
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">교환</label>
				<div class="com-sm-5">
					<input type="radio" id="isTradeable" name="isTradeable" value="YES"> 교환불가
					<input type="radio" id="isTradeable" name="isTradeable" value="NO"> 교환가능
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">가격</label>
				<div class="com-sm-3">
					<input type="text" id="productPrice" name="productPrice" class="form-control">
					<input type="checkbox" id="isDeliveryFree" name="isDeliveryFree" >배송비 포함
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2">설명</label>
				<div class="com-sm-3">
					<textarea rows="5" id="productInfo" name="productInfo" class="form-control" style="resize:none"></textarea>
				</div>
			</div>
			
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="button" class="btn btn-primary" value="등록" onclick="CheckAddProduct()">
				</div>
			</div>
			
		</form>
	</div>
	
	<script type="text/javascript">
	
	</script>

<%@ include file="../includes/footer.jsp"%>