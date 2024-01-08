 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<!-- 경로 세팅  -->
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!-- 댓글 사용자가 아니면 보기만 가능  -->
<c:if test="${empty sessionScope.loginMember || sessionScope.loginMember.memberId != c.member_id }">
	<c:set var="str">readonly</c:set>
</c:if>

<html lang="ko">

<head>
	<meta charset="UTF-8">
	<title>ASK</title>

	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

	<!-- Google Font -->
	<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700|Raleway:400,300,500,700,600' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.css" type="text/css">

    <!-- Theme Stylesheet -->
    <link rel="stylesheet" type="text/css" href="/css/style.css" >
    <link rel="stylesheet" type="text/css" href="/css/responsive.css">
    
    <link type="text/css" rel="stylesheet" href="/css/sty_2.css"/>
    
    <script src="/js/detatil.js" defer></script>
    
    <script>

	function login_check(){
		var content= document.r.content;
		
		if(content.value==""){
			alert("댓글을 입력해 주세요.");
			content.focus();
			return false;
		}
	}
	
	$(function () {
		$(".state").change(function () {
			const value = $("select[name='state']").val();
			const productId = $("input[name='product_id']").val();
			
			const label = $("#state");
			label.removeClass();
			label.attr("class", "badge");
			
			if(value === "0") {
				label.addClass("text-bg-primary");
				label.text("판매중");
			} else if(value === "1") {
				label.addClass("text-bg-success");
				label.text("예약중");
			} else if(value === "2") {
				label.addClass("text-bg-danger");
				label.text("판매완료");
			}
			
			const params = {
					productId: productId,
					state: value,
			}
			
			$.ajax({
				type: "POST",
				url: "/products/edit",
				data: params,
				success: function (response) {
					alert("거래 상태가 수정되었습니다.")
				}
			})
			
		});
	});
	
	function soldProduct(b_member_id) {
		
		const buyer = b_member_id;
		const product = $("input[name='product_id']").val();
		const seller = $("input[name='member_id']").val();
		
		const params = {
				buyerId: buyer,
				productId: product,
				sellerId: seller
		};
		
		$.ajax({
			type: "POST",
			url: `/products/close`,
			data: params,
			
		});
		
		
	}
    	
    </script>

</head>

<style>
.hr1 {
	height: 3px;
	background-color: #000;
}

.Z-I{
	position: relative;
	z-index: 800;
}
</style>
    <!-- Core theme JS-->
<script src="/js/script.js" defer></script>
<script src="/js/detail.js" defer></script>

<body>

<!-- 맨 위 서비스  -->
	<jsp:include page="/WEB-INF/views/common/header.jsp" />

    <!-- 상세메뉴 -->
    
    <section class = "asas">
    	
     	<div class="container2 px-4 px-lg-5 my-5">
     		
     		<hr class="hr2 pad1">
     	
     		<div class="row22 gx-4 gx-lg-5 align-items-center">
     			<div class="col-md-6">
                    <img class="card-img-top mb-5 mb-md-0 s-67 imgg" src="https://project-img-bucket.s3.ap-northeast-2.amazonaws.com/${p.imgs[0].imgName }" alt="..." />
                    </div>
                    
                    <div class="col-md-6"> 
                  
	                   <!-- 작성자만 수정 삭제 가능. -->
	                   <div class="Float_R">               
	                    	<c:if test="${sessionScope.loginMember.memberId eq p.memberId }">
							    <button type="button" class="btn2 btn-dark mT-3" onclick="javascript:location.href='/product/edit?member_id=${p.memberId }'" ${p.state eq 2 ? "disabled" : "" }>수정</button>
								<button type="button" class="btn2 btn-danger mT-3 " onclick="deletePost(${p.productId})" ${p.state eq 2 ? "disabled" : "" }>삭제</button>
							</c:if>
	                    </div>
	                    
	                    <!-- 작성자가 아니면 작성자 이름이 뜬다 -->
                    	<c:if test="${sessionScope.loginMember.memberId ne p.memberId }">
							   <div class="selleR"><a href="#">작성자 ${p.nickname }</a></div>
						</c:if>

                        <!-- 상품명 가격  -->
                        <div class="sellEr rx">
                        	<div class="margiN1 display-6 fw-bolder">${p.title } 
                        		<c:if test="${p.state eq 0 }">
                        			<span id="state" class="badge text-bg-primary">판매중</span>
                        		</c:if>
                        		<c:if test="${p.state eq 1 }">
                        			<span id="state" class="badge text-bg-success">예약중</span>
                        		</c:if>
                        		<c:if test="${p.state eq 2 }">
                        			<span id="state" class="badge text-bg-danger">판매완료</span>
                        		</c:if>
                        	</div>
                        	<span class="margiN1 display-4 fw-bolder"><fmt:formatNumber value="${p.price}" pattern="#,###" /> 원</span>
   						</div>
                        
                        <div>
                        	<ul>
                        		<li> <div class="li1"><i class="fa fa-eye" aria-hidden="true"></i>	  ${p.viewCnt }</div> </li>
                        		<li> <div class="li1"><i class="fa fa-clock-o" aria-hidden="true"></i>	  00분전</div> </li>
                        		<li> <div class="li1"><i class="fa fa-map-marker" aria-hidden="true"></i>	  00구 00동</div> </li>
                        		<li> <div class="li1"><i class="fa fa-check-square-o" aria-hidden="true"></i>	  직거래</div> </li>
                        		<c:if test="${sessionScope.loginMember.memberId == p.memberId }"> 
	                        		<li> 
	                        			<div class="li1"> 
	                        				<select class="state" name="state" ${p.state eq 2 ? "disabled" : "" }>
				                           		<option value="0" ${p.state eq 0 ? "selected" : "" }>판매중</option>
				                           		<option value="1" ${p.state eq 1 ? "selected" : "" }>예약중</option>
				                           		<option value="2" ${p.state eq 2 ? "selected" : "" }>판매완료</option>
	                           				</select>
	                           			</div>
	                           		</li>
                           		</c:if>
                        	</ul>   
                        </div>
                        
                        <!-- 신고하기 -->
                        <div class="containeR Z-I">
                            
							  <div class="modal-btn-box">
								  <button type="button" class="btn2 btn-outline-secondary btn-lg coR Float_R Btn-pop" id="modal-open">
								  <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>  신고하기</button>
							  </div>
							  
							  <div class="popup-wrap" id="popup">
							    <div class="popup">
							    
							      <div class="popup-head">
							          <span class="head-title">
							            신고하기</span>
							      </div>
							      
							      <div class="popup-body">
							        <div class="body-content">
							          <div class="body-titlebox">
							            <h1>신고 사유를 적어주세요.</h1>
							          </div>
							          <div class="body-contentbox">
							            <textarea class="textareA" rows="6" cols="41"></textarea>
							          </div>
	
							        </div>
							      </div>
							      
							      <div class="popup-foot">
							        <span class="pop-btn confirm" id="confirm">신고하기</span>
							        <span class="pop-btn closE" id="closE">취소하기</span>
							      </div>
							      
							    </div>
							</div>
						</div>        
                        
                        <hr class="hrr1 rx">
                        
                        <!-- 상품 내용  -->
                        <p class="lead">
                        ${p.contents }
						</p>

                        <div class="button01">
                        	<c:if test="${p.isLiked eq 1 }"> 
                            	<button type="button" class="btn2 btn-outline-info btn-lg coR" onclick="insertLike(${p.productId})"><i class="fas fa-heart like" aria-hidden="true"></i>  찜하기 취소</button>
                            </c:if>
                            <c:if test="${p.isLiked eq 0 }"> 
                            	<button type="button" class="btn2 btn-outline-info btn-lg coR" onclick="insertLike(${p.productId})"><i class="fa-regular fa-heart like" aria-hidden="true" ></i>  찜하기</button>
                            </c:if>
                        </div>
    
                    </div>
                </div>
                
                <hr class="hr2 pad1">
                
            </div>
        </section>
        
        <!-- 댓글 쓰기 -->
		
		<!-- 로그인 한 사람만 댓글쓰기  -->
<%-- 		<c:if test="${not empty sessionScope.loginId}"> --%>
		
		<section class="py-5 bg-light">
            <div class="container2 px-4 px-lg-5 mt-5">
				
				<!-- 위에 아이콘 -->
				<div class="card mb-2">
					<div class="card-header bg-light fS19 Font-B">
					        <i class="fa fa-pencil-square-o" aria-hidden="true"></i> 댓글 쓰기
					</div>
					
					<!-- 댓글쓰기  -->					
					<form action="${path}/reply/add" method="post" onsubmit="return login_check()" name="r" accept-charset="UTF-8">
						
						<div class="card-body">
							<div class="input-group">
								<c:choose>
									<c:when test="${p.state eq 2 }">
										<textarea name="content" class="form-control textareA" id="floatingTextarea2" 
										placeholder="판매완료된 제품입니다." style="width:1200px; height:120px" readonly></textarea>
									</c:when>
									
									<c:otherwise>
										<textarea name="content" class="form-control textareA" id="floatingTextarea2" 
										placeholder="게시물과 상관없는 댓글을 삭제 될 수 있습니다." style="width:1200px; height:120px"></textarea>
									</c:otherwise>
									
								</c:choose>								
								
								<input type="hidden" name="product_id" value="${p.productId}">
								<!-- 나중에 값 바꿔주기 {p.member_id}-->
								<input type="hidden" name="member_id" value="${sessionScope.loginMember.memberId }">
								<button type="submit" class="btn2 btn-dark mt-3 btn-lg" ${p.state eq 2 ? "disabled" : "" }>등록</button>
								
							</div>
						</div>
						
					</form>
				</div>
				
				<!-- 답글 리스트-->
				
				<hr class="hrr1">
				
				<div class="hrr1">
					<h2 class="Font-B"><i class="fa fa-comments-o" aria-hidden="true">
					</i>  댓글 목록</h2>
				</div>
		
				<c:if test="${empty list }">
					<div class="hrr1">
						<h2 class="Font-B">
						  현재 작성되어 있는 댓글이 없습니다.</h2>
					</div>
				</c:if>
				
				<c:forEach items="${list}" var="c">

					<div class="card mB-2" id="tr1">
					
						<!-- 사용자 번호 -->
						<div class="card-header bg-light fS19 Font-B">
						        <i class="fa fa-user" aria-hidden="true"></i> 
						        
						        <c:if test="${p.memberId == c.member_id }">
						       		판매자
						        </c:if>
						        
						        <c:if test="${p.memberId != c.member_id }">
						        	
						       		작성자 ${c.nickname }
						        </c:if>
						</div>
						
						
						
					<!-- 내용 -->
					<form name = "f1" action="${path }/reply/edit" method="post" accept-charset="UTF-8">
						<div class="card-body rx">
							
							<div class="input-group">
								<textarea name="content" class="form-control textareA" id="floatingTextarea2"
								style="width:1200px; height:120px" ${str}>${c.content}</textarea>
								<input type="hidden" name="comment_id" value="${c.comment_id}">
								<input type="hidden" name="product_id" value="${p.productId}">
								<!-- 나중에 값 바꿔주기 {p.member_id}-->
								<input type="hidden" name="member_id" value="${p.memberId }">
							</div>
							
							<!-- 댓글 쓴 사람만 수정 삭제 가능  -->
							<div class="Float_R">	
								<c:if test="${sessionScope.loginMember.memberId == c.member_id }">					
							    		    						 
							    	<button type="submit" class="btn2 btn-dark mT--3">수정</button>
								    <button type="button" id="delete" class="btn2 btn-danger mT--3" onclick="javascript:location.href='/reply/delete?comment_id=${c.comment_id }'">삭제</button>
								</c:if>
									
								<c:if test="${sessionScope.loginMember.memberId == p.memberId and p.memberId != c.member_id }">               
                                	
                                	<button type="button" class="btn2 btn-dark mT--3 deal-btn" onclick="soldProduct(${c.member_id})" ${p.state eq 2 ? "disabled" : "" }>거래채택</button>
                            	
                           		</c:if>			
								
							</div>
						</div>
					</form>
				
				</div>
				
			</c:forEach>
			
			<!-- 댓글 목록 -->
			
			<div>
			   <nav aria-label="Page navigation example">
			      <ul class="pagination">
			         <li class="page-item"  value="${pager.pre}" id="pre">
			           <a class="page-link" href="${path }/products/test2?product_id=${p.productId}&page=${pager.page-1}" aria-label="Previous">
			             <span aria-hidden="true">&laquo;</span>
			           </a>
			         </li>
			  
			         <c:forEach var="i" begin="${pager.startNum}" end="${pager.lastNum}">
			            <li class="page-item ${pager.page==i? 'active':''}">
			                <a class="page-link" href="${path }/products/test2?product_id=${p.productId}&page=${i}">${i}</a>
			             </li>
			         </c:forEach>
			                    
			         <li class="page-item ${pager.next?'':'disabled'}" id="next">
			             <a class="page-link" href="${path }/products/test2?product_id=${p.productId}&page=${pager.page+1}" aria-label="Next">
			                <span aria-hidden="true">&raquo;</span>
			              </a>
			          </li>
			       </ul>
			    </nav>
			</div>
			
			
			

		</div>
        </section>

<%-- 		</c:if> --%>


        
	<!-- 맨 아래 이용약관및 여러가지들 -->
<%-- 	<%@ include file="../include/footer.jsp" %> --%>


	<!-- jQuery Library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>

	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

	<!-- Script -->
	<script  type="text/javascript" src="/js/script.js"></script>
	
	<!-- Bootstrap core JS-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <script	src="https://kit.fontawesome.com/f870d87297.js"	crossorigin="anonymous"></script>
 	
</body>
</html>