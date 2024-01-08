<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
   href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
   rel="stylesheet"
   integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
   crossorigin="anonymous"
/>
<script
   src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
   integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
   crossorigin="anonymous"
></script>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script
   src="https://kit.fontawesome.com/f870d87297.js"
   crossorigin="anonymous"
></script>
<link href="/css/search.css" rel="stylesheet">
<script src="/js/search.js" defer></script>
</head>
<body>
	
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	
	<section>
	    <div class="container category mt-5">
	      <form class="form" action="/products/search" method="GET">
	      	<div class="row mb-3">
          		<div class="col-md-2" style="padding-right: 0px">
            		<select class="form-select" name="searchType">
		              <option value="title">제품명</option>
		              <option value="seller">판매자명</option>
            		</select>
          		</div>
		        <div class="col-md-6" style="padding-left: 0px">
		           <input type="text" name="keyword" class="form-control" placeholder="검색내용" />
		        </div>
        	</div>
	        <div class="row align-items-center">
	          <div class="col">
	            <select class="form-select" aria-label="" name=category>
	              <option selected>카테고리</option>
	              <option value="0" ${param.category eq 0 ? "selected" : "" }>전체</option>
		          <option value="1" ${param.category eq 1 ? "selected" : "" }>여성의류</option>
		          <option value="2" ${param.category eq 2 ? "selected" : "" }>남성의류</option>
		          <option value="3" ${param.category eq 3 ? "selected" : "" }>패션잡화</option>
		          <option value="4" ${param.category eq 4 ? "selected" : "" }>생활가전</option>
		          <option value="5" ${param.category eq 5 ? "selected" : "" }>가구/인테리어</option>
		          <option value="6" ${param.category eq 6 ? "selected" : "" }>게임/취미</option>
		          <option value="7" ${param.category eq 7 ? "selected" : "" }>도서</option>
		          <option value="8" ${param.category eq 8 ? "selected" : "" }>스포츠</option>
	            </select>
	          </div>
	          <div class="col">
	            <select class="form-select" aria-label="">
	              <option selected>거래방식</option>
	              <option value="직거래" ${param.method eq "직거래" ? "selected" : "" }>직거래</option>
	              <option value="택배거래" ${param.method eq "택배거래" ? "selected" : "" }>택배거래</option>
	            </select>
	          </div>
	        </div>
	        
	        <div class="row mt-3">
	          <div class="col">
	        	<div class="form-floating">
					 <input type="number" class="form-control" id="lowPrice" name="lowPrice" value="${not empty param.lowPrice ? param.lowPrice : 0 }">
					 <label for="lowPrice">최소가격</label>
				</div>
			  </div>
			  <div class="col">
	        	<div class="form-floating">
					 <input type="number" class="form-control" id="highPrice" name="highPrice" value="${not empty param.highPrice ? param.highPrice : 0 }">
					 <label for="floatingInputValue">최대가격</label>
				</div>
			  </div>
	        </div>
	        <div class="mt-3">
	          <button type="submit" class="btn btn-primary">검색</button>
	        </div>
	      </form>
          <div class="collection-list mt-4 row gx-0 gy-3">
          
          	<c:forEach var="p" items="${products.list }">
          
	         	<div class="col-md-6 col-lg-4 col-xl-3 p-2" onclick="detailProduct(${p.productId})">
	              <div class="collection-img position-relative">
	                <img
	                  src="https://project-img-bucket.s3.ap-northeast-2.amazonaws.com/${p.imgs[0].imgName }"
	          		  class="w-100"
	          		  style="height: 300px;"
	                  alt=""
	                />
	                <span
	                  class="position-absolute d-flex align-items-center justify-content-center text-primary fs-4"
	                >
	                 	<c:choose>
	                 		
	                 		<c:when test="${p.isLiked eq 1 }">
	                 			<i class="fas fa-heart like-${p.productId }" onclick="insertLike(${p.productId}, event)"></i>
	                 		</c:when>
	                 		<c:otherwise>
	                 			<i class="fa-regular fa-heart like-${p.productId }" onclick="insertLike(${p.productId}, event)"></i>
	                 		</c:otherwise>
	                 		
	                 	</c:choose> 
	                  	
	                </span>
	              </div>
	              <div class="">
	                <div class="mb-4">
	                  <p class="mt-3 mb-0 fw-bold">${p.title }</p>
	                  <span class="text-capitalize my-1">${p.nickname }</span>
	                  <br>
	                  <span>조회수: ${p.viewCnt }</span>
	                  <span>좋아요: 
	                  	<span id="likeCnt-${p.productId }">${p.likeCnt }</span>
	                  </span>
	                </div>
	                <span class="fw-bold">
	                	<fmt:formatNumber value="${p.price}" pattern="#,###" />원
	                </span>
	              </div>
	            </div>
            
            </c:forEach>
     
          </div>
        </div>
      </div>
    </section>
    <nav class="container mt-3" aria-label="Page navigation example">
      <ul class="pagination d-flex justify-content-center">
        
        <c:set var="pagination" value="${products.pagination }" />
        
        <c:if test="${pagination.existPrevPage }">
        
	        <li class="page-item">
	          	<a class="page-link" href="products?page=1${not empty param.category ? '&category=' += param.category : ''}" aria-label="Previous">
	            	<span aria-hidden="true">&laquo;</span>
	          	</a>
	        </li>
        
        </c:if>
        
        <c:forEach var="page" begin="${pagination.startPage }" end="${pagination.endPage }">
        
        	<li class="page-item">
        		<a class="page-link ${param.page eq page ? 'on' : '' }" href="/products?page=${page }${not empty param.category ? '&category=' += param.category : ''}">${page }</a>
        	</li>
        
        </c:forEach>
        
        <c:if test="${pagination.existNextPage }">
 
	        <li class="page-item">
	          	<a class="page-link" href="/products?page=${pagination.endPage + 1 }${not empty param.category ? '&category=' += param.category : ''}" aria-label="Next">
	            	<span aria-hidden="true">&raquo;</span>
	          	</a>
	        </li>
        	
        </c:if> 
        
      </ul>
    </nav>
</body>
</html>