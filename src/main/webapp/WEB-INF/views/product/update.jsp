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

#backBtn {
	margin: 0 10px 0 0;
}
</style>

<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">상품 수정</h1>
	</div>
</div>

<div class="container">
	<form id="newProductForm" name="newProductForm" action="./processAddProduct.jsp" class="form-horizontal" method="post" enctype="multipart/form-data">
		<input hidden="hidden" id="prdtId" name="prdtId" value="<c:out value="${product.prdtId }" />">
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
				<input type="text" id="prdtName" name="prdtName" class="form-control" value="<c:out value="${product.prdtName }" />">
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2" for="prdtCategory">카테고리</label>
			<input hidden="hidden" id="categoryValue" name="categoryValue" value="<c:out value="${product.prdtCategory }" />">
			<div class="com-sm-3">
				<select class="form-control" id="prdtCategory" name="prdtCategory">
					<option value="여성의류">여성의류</option>
					<option value="남성의류">남성의류</option>
					<option value="신발">신발</option>
					<option value="가방">가방</option>
					<option value="쥬얼리">쥬얼리</option>
					<option value="패션액세서리">패션액세서리</option>
					<option value="디지털">디지털</option>
					<option value="스포츠">스포츠</option>
					<option value="차량">차량</option>
					<option value="스타굿즈">스타굿즈</option>
					<option value="키덜트">키덜트</option>
					<option value="음반/악기">음반</option>
					<option value="도서">도서</option>
					<option value="뷰티">뷰티</option>
					<option value="가구">가구</option>
					<option value="생활">생활</option>
					<option value="반려동물용품">반려동물용품</option>
					<option value="유아동">유아동</option>
				</select>
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2" for="prdtTradeLoc">거래지역</label>
			<div class="com-sm-3">
				<input type="text" id="prdtTradeLoc" name="prdtTradeLoc" class="form-control" value="<c:out value="${product.prdtTradeLoc }" />" >
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2" for="prdtCondition">상태</label>
			<input hidden="hidden" id="conditionValue" name="conditionValue" value="<c:out value="${product.prdtCondition }" />">
			<div class="com-sm-5" id="conditionDiv">
				<input type="radio" id="prdtCondition" name="prdtCondition" value="중고상품"> 중고상품
				<input type="radio" id="prdtCondition" name="prdtCondition" value="새상품"> 새상품
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2" for="prdtIsTradeable">교환</label>
			<input hidden="hidden" id="tradeableValue" name="tradeableValue" value="<c:out value="${product.prdtIsTradeable }" />">
			<div class="com-sm-5" id="tradeableDiv">
				<input type="radio" id="prdtIsTradeable" name="prdtIsTradeable" value="교환불가"> 교환불가 
				<input type="radio" id="prdtIsTradeable" name="prdtIsTradeable" value="교환가능"> 교환가능
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2" for="prdtPrice">가격</label>
			<input hidden="hidden" id="deliveryValue" name="deliveryValue" value="<c:out value="${product.prdtIsDeliveryFree }" />">
			<div class="com-sm-3" id="deliveryFreeDiv">
				<input type="text" id="prdtPrice" name="prdtPrice" class="form-control" onkeyup="convertNum(this);" value="<c:out value="${product.prdtPrice }" />"> 
				<input type="radio" id="prdtIsDeliveryFree" name="prdtIsDeliveryFree" value="배송비포함"> 배송비포함 
				<input type="radio" id="prdtIsDeliveryFree" name="prdtIsDeliveryFree" value="배송비별도"> 배송비별도
			</div>
		</div>

		<div class="form-group row">
			<label class="col-sm-2" for="prdtInfo">설명</label>
			<div class="com-sm-3">
				<textarea rows="5" id="prdtInfo" name="prdtInfo" class="form-control" style="resize: none">${product.prdtInfo }</textarea>
			</div>
		</div>

		<div class="form-group row">
			<div class="col-sm-offset-2 col-sm-10">
				<input type="button" id="uploadBtn" class="btn btn-primary pull-right" value="수정">
				<input type="button" id="backBtn" class="btn btn-primary pull-right" value="뒤로가기" onclick="location.href='javascript:history.back()'">
			</div>
		</div>
	</form>
</div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/product/image-load.js"></script>
<script type="text/javascript">

// 가져온 상품의 카테고리/상태/교환여부/배송비포함여부 세팅 함수
setSelectValue();
setConditionValue();
setTradeableValue();
setDeliveryFreeValue();

// 카테고리 세팅
function setSelectValue() {
	var categoryVal = $("#categoryValue").val();
	$("#prdtCategory option[value=" + categoryVal + "]").attr('selected','selected')
}

// 상태 세팅
function setConditionValue() {
	var conditionVal = $("#conditionValue").val();
	document.querySelectorAll('#prdtCondition').forEach((e) => {
		if (e.value == conditionVal) {
			e.checked = true;
		}
	})
}

// 교환여부 세팅
function setTradeableValue() {
	var tradeableVal = $("#tradeableValue").val();
	document.querySelectorAll('#prdtIsTradeable').forEach((e) => {
		if (e.value == tradeableVal) {
			e.checked = true;
		}
	})
}

// 배송비포함여부 세팅
function setDeliveryFreeValue() {
	var deliveryFreeVal = $("#deliveryValue").val();
	document.querySelectorAll('#prdtIsDeliveryFree').forEach((e) => {
		if (e.value == deliveryFreeVal) {
			e.checked = true;
		}
	})
}


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
	
	if (isTextNull() == false || isTextAreaNull() == false || isPrdtNameIsLong() == false) {
		flag = false;
	} else {
		flag = true;
	}
	console.log("all null checks passed? = " + flag);
	
	if (flag) {
		let inputData1 = {
				"prdtId" : $("#prdtId").val(),
				"prdtName" : $("#prdtName").val(),
				"prdtCategory" : $("#prdtCategory").val(),
				"prdtTradeLoc" : $("#prdtTradeLoc").val(),
				"prdtCondition" : $("input[name=prdtCondition]:checked").val(),
				"prdtIsTradeable" : $("input[name=prdtIsTradeable]:checked").val(),
				"prdtPrice" : $("#prdtPrice").val(),
				"prdtInfo" : $("#prdtInfo").val(),
				"prdtIsDeliveryFree" : $("input[name=prdtIsDeliveryFree]:checked").val()
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
		
		var prdtId = $("#prdtId").val();
		$.ajax({
			type: "post",
			url: "/products/update/"+prdtId,
			processData: false,
			contentType: false,
			data: formData,
			headers: {
				Accept: "text/html; charset=utf-8"
			},
			success: function(data) {
				alert("상품 수정 완료.");
				history.back();
			},
			error: function(e) {
				alert("에러 발생. 다시 요청해주세요.");
			}
		});
	}
});

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

function isPrdtNameIsLong() {
	var prdtNameCnt = $('#prdtName').val().length;
	if (prdtNameCnt > 20) {
		alert("상품명은 20글자 이내로 작성해주세요.");
		$('#prdtName').focus();
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