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
	<form id="newProductForm" name="newProductForm" action="./processAddProduct.jsp" class="form-horizontal" method="post" enctype="multipart/form-data">
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
			<label class="col-sm-2" for="prdtName">제목</label>
			<div class="com-sm-3">
				<input type="text" id="prdtName" name="prdtName" class="form-control"><span><span id="nameCnt">
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2" for="prdtCategory">카테고리</label>
			<div class="com-sm-3">
				<select class="form-control" id="prdtCategory" name="prdtCategory">
					<option>여성의류</option>
					<option>남성의류</option>
					<option>신발</option>
					<option>가방</option>
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
					<option>유아동/출산</option>
				</select>
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2" for="prdtTradeLoc">거래지역</label>
			<div class="com-sm-3">
				<input type="text" id="prdtTradeLoc" name="prdtTradeLoc" class="form-control">
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2" for="prdtCondition">상태</label>
			<div class="com-sm-5">
				<input type="radio" id="prdtCondition" name="prdtCondition" value="중고" checked="checked"> 중고상품
				<input type="radio" id="prdtCondition" name="prdtCondition" value="새상품"> 새상품
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2" for="prdtIsTradeable">교환</label>
			<div class="com-sm-5">
				<input type="radio" id="prdtIsTradeable" name="prdtIsTradeable" value="교환불가" checked="checked"> 교환불가 
				<input type="radio" id="prdtIsTradeable" name="prdtIsTradeable" value="교환가능"> 교환가능
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2" for="prdtPrice">가격</label>
			<div class="com-sm-3">
				<input type="text" id="prdtPrice" name="prdtPrice" class="form-control" onkeyup="convertNum(this);"> 
				<input type="radio" id="prdtIsDeliveryFree" name="prdtIsDeliveryFree" value="배송비포함" checked="checked"> 배송비포함 
				<input type="radio" id="prdtIsDeliveryFree" name="prdtIsDeliveryFree" value="배송비별도"> 배송비별도
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2" for="prdtInfo">설명</label>
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

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/product/image-load.js"></script>
<script type="text/javascript">

var regex = new RegExp("(.*?)\(exe|sh|zip|alz|webp|svg)$");
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
	var flag = false;
	
	if (isImageNull() == false || isTextNull() == false || isTextAreaNull() == false) {
		flag = false;
	} else {
		flag = true;
	}
	console.log("all null checks passed? = " + flag);
	
	if (flag) {
		let inputData1 = {
				"prdtName" : $("#prdtName").val(),
				"prdtCategory" : $("#prdtCategory").val(),
				"prdtTradeLoc" : $("#prdtTradeLoc").val(),
				"prdtCondition" : $("#prdtCondition").val(),
				"prdtIsTradeable" : $("#prdtIsTradeable").val(),
				"prdtPrice" : $("#prdtPrice").val(),
				"prdtInfo" : $("#prdtInfo").val(),
				"prdtIsDeliveryFree" : $("#prdtIsDeliveryFree").val()
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
				alert("상품 등록 완료.");
				location.href = "/main";
			},
			error: function(e) {
				alert("에러 발생. 다시 요청해주세요.");
			}
		});
	}
});

function isImageNull() {
	var inputFile = $("#att_zone")[0].childNodes.length;
	if (inputFile == 0) {
		alert("상품 사진을 등록해주세요.");
		return false;
	} else {
		return true;
	}
}

function isTextNull() {
	var txtElements = $("#newProductForm input[type=text]");
	for (var i=0; i<txtElements.length; i++) {
		if ($(txtElements[i]).val() == "" || $(txtElements[i]).val() == null) {
			var elmtId = $(txtElements[i]).attr("id");
			var labelTxt = $("label[for='" + elmtId +"']").text();
			alert(labelTxt + " 입력하세요.");
			return false;
		}
	}
	return true;
}

function isTextAreaNull() {
	var info = $("#prdtInfo")[0].value.length;
	if (info === 0) {
		alert("상품 정보를 입력해주세요.");
		return false;
	} else {
		return true;
	}
}

// 주소찾기
$('#prdtTradeLoc').on('click', function() {
    new daum.Postcode({
        oncomplete: function(data) {
        	document.getElementById('prdtTradeLoc').value = data.address;
        }
    }).open();
})

// 가격 단위 변환
function convertNum(obj) {
	obj.value = comma(uncomma(obj.value));
}
function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}
function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}
</script>

<%@ include file="../includes/footer.jsp"%>