<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp" %>
<!-- sidebar -->
<%@ include file="../includes/sidebar.jsp"%>
<!-- End of sidebar -->
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">판매중인 상품</h1>	
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<!-- /.panel-heading -->
				<div class="panel-body">

					<!-- 판매 상품 리스트 -->
					<c:forEach var="product" items="${products }">
						<div class="gallery">
							<a target="_blank" href="#"> <img src='${imagePath }${product.imageVO.fileName }' alt="Cinque Terre" width="600" height="400">
							</a>
							<div class="desc"><c:out value="${product.prdtName }" /> </div>
						</div>
					</c:forEach>
					<!-- End of 판매 상품 리스트 -->
					
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
            <!-- /.row -->
            <!-- /.row -->
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

<%@ include file="../includes/footer.jsp" %>