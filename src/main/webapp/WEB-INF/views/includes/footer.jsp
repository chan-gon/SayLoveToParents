<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- Bootstrap Core JavaScript -->
    <script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="/resources/vendor/metisMenu/metisMenu.min.js"></script>
    <!-- DataTables JavaScript -->
    <script src="/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="/resources/vendor/datatables-responsive/dataTables.responsive.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="/resources/dist/js/sb-admin-2.js"></script>

    <script type="text/javascript">
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
        
        // 페이징
        var actionForm = $("#actionform");
        $(".paginate_button a").on("click", function (e) {
            e.preventDefault(); // a 태그를 클릭해도 페이지 이동이 없도록 처리
            actionForm.find("input[name='pageNum']").val($(this).attr("href"));
            actionForm.submit();
        });
        
        // 상품 검색
        var searchForm = $("#searchForm");
        $("#searchForm button").on("click", function(e) {
        	e.preventDefault();
        	
        	/*
        	input 태그에 값이 있는지 여부 확인
        	replace()를 통해 스페이스바 방지
        	*/
        	if (!searchForm.find("input[name='keyword']").val().replace(/\s/g, "")) {
        		alert("상품명을 입력하세요.");
    			return false;
        	}
        	// keyword만 쿼리 스트링에 포함하기 위해 category 값을 담는 input 태그 비활성화
        	searchForm.find("input[name='category']").prop('disabled',true);
        	searchForm.find("input[name='pageNum']").val("1");
        	searchForm.submit();
        });
        
        var lists = document.querySelectorAll('#category');
        for (var i = 0; i<lists.length; i++) {
        	lists[i].addEventListener("click", function(e) {
        		e.preventDefault();
        		// 클릭한 값을 input 태그의 value 속성에 담기
        		searchForm.find("input[name='category']").val(this.innerHTML);
        		// category만 쿼리 스트링에 포함하기 위해 keyword 값을 담는 input 태그 비활성화
        		searchForm.find("input[name='keyword']").prop('disabled',true);
        		searchForm.submit();
        	});
        }
    });
    </script>

</body>
</html>