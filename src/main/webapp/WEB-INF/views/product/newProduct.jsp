<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

	<form name="newProduct" action="./processAddProduct.jsp" class="form-horizontal" method="post" enctype="multipart/form-data">

		<div class="form-group row">
			<div id='image_preview'>
				<h5>
					<b>상품이미지</b>
				</h5>
				<label class="input-file-button" for="prdtImage">업로드</label>
				<input type='file' id="prdtImage" name="prdtImage" style="display:none" multiple='multiple' />
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
				<input type="text" id="prdtPrice" name="prdtPrice" class="form-control"> <input type="checkbox" id="prdtIsDeliveryFree" name="prdtIsDeliveryFree">배송비 포함
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
				<input type="button" class="btn btn-primary pull-right" value="등록" onclick="CheckAddProduct()">
			</div>
		</div>

	</form>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">

	/* 
	att_zone : 이미지들이 들어갈 위치 id
	btn : file tag id 
	*/
	( 
	  imageView = function imageView(att_zone, btn){

	    var attZone = document.getElementById(att_zone);
	    var btnAtt = document.getElementById(btn)
	    var sel_files = [];
	    
	    // 이미지와 체크 박스를 감싸고 있는 div 속성
	    var div_style = 'display:inline-block;position:relative;'
	                  + 'width:150px;height:120px;margin:5px;border:1px solid #00f;z-index:1';
	    // 미리보기 이미지 속성
	    var img_style = 'width:100%;height:100%;z-index:none';
	    // 이미지안에 표시되는 체크박스의 속성
	    var chk_style = 'width:30px;height:30px;position:absolute;font-size:24px;'
	                  + 'right:0px;bottom:0px;z-index:999;background-color:rgba(255,255,255,0.1);color:#f00';
	  
	    btnAtt.onchange = function(e){
	      var files = e.target.files;
	      var fileArr = Array.prototype.slice.call(files)
	      for(f of fileArr){
	        imageLoader(f);
	      }
	    }  
	  
	    // 탐색기에서 드래그앤 드롭 사용
	    attZone.addEventListener('dragenter', function(e){
	      e.preventDefault();
	      e.stopPropagation();
	    }, false)
	    
	    attZone.addEventListener('dragover', function(e){
	      e.preventDefault();
	      e.stopPropagation();
	      
	    }, false)
	  
	    attZone.addEventListener('drop', function(e){
	      var files = {};
	      e.preventDefault();
	      e.stopPropagation();
	      var dt = e.dataTransfer;
	      files = dt.files;
	      if (files.length > 6) {
	    	  alert("이미지는 최대 6개까지 업로드 가능합니다.");
	    	  files.length = 0;
	    	  sel_files.length = 0;
	    	  console.log(files.length);
	    	  console.log(sel_files.length);
	      }
	      for(f of files){
	        imageLoader(f);
	      }
	      
	    }, false)
	    
	    /* 첨부된 이미지를 배열에 넣고 미리보기 */
	    imageLoader = function(file){
	      sel_files.push(file);
	      var reader = new FileReader();
	      reader.onload = function(ee){
	        let img = document.createElement('img')
	        img.setAttribute('style', img_style)
	        img.src = ee.target.result;
	        attZone.appendChild(makeDiv(img, file));
	      }
	      
	      reader.readAsDataURL(file);
	    }
	    
	    /* 첨부된 파일이 있는 경우 checkbox와 함께 attZone에 추가할 div를 만들어 반환 */
	    makeDiv = function(img, file){
	      var div = document.createElement('div')
	      div.setAttribute('style', div_style)
	      
	      var btn = document.createElement('input')
	      btn.setAttribute('type', 'button')
	      btn.setAttribute('value', 'x')
	      btn.setAttribute('delFile', file.name);
	      btn.setAttribute('style', chk_style);
	      btn.onclick = function(ev){
	        var ele = ev.srcElement;
	        var delFile = ele.getAttribute('delFile');
	        for(var i=0 ;i<sel_files.length; i++){
	          if(delFile== sel_files[i].name){
	            sel_files.splice(i, 1);      
	          }
	        }
	        
	        dt = new DataTransfer();
	        for(f in sel_files) {
	          var file = sel_files[f];
	          dt.items.add(file);
	        }
	        btnAtt.files = dt.files;
	        var p = ele.parentNode;
	        attZone.removeChild(p)
	      }
	      div.appendChild(img)
	      div.appendChild(btn)
	      return div
	    }
	  }
	)('att_zone', 'prdtImage')
	
	</script>

<%@ include file="../includes/footer.jsp"%>