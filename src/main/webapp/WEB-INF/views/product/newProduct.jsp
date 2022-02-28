<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../includes/header.jsp"%>

<style>
#att_zone {
	width: 660px;
	min-height: 150px;
	padding: 10px;
	border: 1px dotted #00f;
}

#att_zone:empty:before {
	content: attr(data-placeholder);
	color: #999;
	font-size: .9em;
}

.input-file-button{
  padding: 6px 25px;
  background-color:#FF6600;
  border-radius: 4px;
  color: white;
  cursor: pointer;
}
</style>

<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">상품 등록</h1>
	</div>
</div>

<div class="container">

	<form id="newProduct" name="newProduct" action="./processAddProduct.jsp" class="form-horizontal" method="post" enctype="multipart/form-data">
	<input type="text" hidden="hidden" id="userId" name="userId" value='<c:out value="${users.userId }" />' >
		<div class="form-group row">
			<div id='image_preview'>
				<h5>
					<b>상품이미지</b>
				</h5>
				<label class="input-file-button" for="fileName">업로드</label>
				<input type='file' id="fileName" name="fileName" style="display:none" multiple='multiple' />
				<div id='att_zone' data-placeholder='파일 선택 버튼을 클릭하거나 파일을 끌어다 놓으세요.'></div>
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2">제목</label>
			<div class="com-sm-3">
				<input type="text" id="prdtName" name="prdtName" class="form-control"> <span><span id="nameCnt"></span>/40</span>
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2">카테고리</label>
			<div class="com-sm-3">
				<select class="form-control" id="prdtCategory" name="prdtCategory">
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
			<label class="col-sm-2">거래지역</label>
			<div class="com-sm-3">
				<input type="text" id="prdtTradeLoc" name="prdtTradeLoc" class="form-control">
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2">상태</label>
			<div class="com-sm-5">
				<input type="radio" id="prdtCondition" name="prdtCondition" value="OLD"> 중고상품 <input type="radio" id="prdtCondition" name="prdtCondition" value="NEW"> 새상품
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2">교환</label>
			<div class="com-sm-5">
				<input type="radio" id="prdtIsTradeable" name="prdtIsTradeable" value="YES"> 교환불가 <input type="radio" id="prdtIsTradeable" name="prdtIsTradeable" value="NO"> 교환가능
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2">가격</label>
			<div class="com-sm-3">
				<input type="text" id="prdtPrice" name="prdtPrice" class="form-control"> 
				<input type="checkbox" id="prdtIsDeliveryFree" name="prdtIsDeliveryFree">배송비 포함
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2">설명</label>
			<div class="com-sm-3">
				<textarea rows="5" id="prdtInfo" name="prdtInfo" class="form-control" style="resize: none"></textarea>
			</div>
		</div>

		<div class="form-group row">
			<div class="col-sm-offset-2 col-sm-10">
				<input type="button" id="uploadBtn" class="btn btn-primary pull-right" value="등록">
			</div>
		</div>

	</form>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/product/image-load.js"></script>
<script type="text/javascript">

	var regex = new RegExp("(.*?)\(exe|sh|zip|alz)$");
	var maxSize = 5242880;
	
	function checkExtension(fileName, fileSize) {
		if (fileSize >= maxSize) {
			alert("파일 사이즈 초과");
			return false;
		}
		
		if (regex.test(fileName)) {
			alert("해당 종류의 파일은 업로드 불가능합니다.");
			return false;
		}
		return true;
	}
	
	$("#uploadBtn").on("click", function(e) {
		
		let inputData1 = {
				"prdtName" : $("#prdtName").val(),
				"prdtCategory" : $("#prdtCategory").val(),
				"prdtTradeLoc" : $("#prdtTradeLoc").val(),
				"prdtCondition" : $("#prdtCondition").val(),
				"prdtIsTradeable" : $("#prdtIsTradeable").val(),
				"prdtPrice" : $("#prdtPrice").val(),
				"prdtInfo" : $("#prdtInfo").val(),
				"prdtIsDeliveryFree" : $("#prdtIsDeliveryFree").val(),
		}
		
		let inputData2 = {
				"userId" : $("#userId").val()
		}
		
		var formData = new FormData();
		var inputFile = $("input[name='fileName']");
		var files = inputFile[0].files;
		
		for(var i = 0; i < files.length; i++) {
			if (!checkExtension(files[i].name, files[i].size)) {
				return false;
			} 
			formData.append("productImage", files[i]);
		}	
		
		formData.append("product", new Blob([JSON.stringify(inputData1)], {type: "application/json"}));
		formData.append("user", new Blob([JSON.stringify(inputData2)], {type: "application/json"}));
		
		$.ajax({
			type: "post",
			url: "/products/new",
			processData: false,
			contentType: false,
			data: formData,
			headers: {
				Accept: "text/html; charset=utf-8"
			},
			success: function(data) {
				alert(data);
				location.href = "/";
			},
			error: function(e) {
				alert(e.responseText);
			}
		});
	});
	
</script>

<%@ include file="../includes/footer.jsp"%>