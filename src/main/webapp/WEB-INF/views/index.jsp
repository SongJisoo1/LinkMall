<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    
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
    
    <script src="/js/utils/ajax.js" defer></script>
    
    <script src="/js/main.js" defer></script>
    
    <link href="/css/main.css" rel="stylesheet"></link>
    
    <title>LinkMall :: products</title>
    
    <script>
    	const detailProduct = (productId) => {
    		window.location.href = "/products/test2?product_id=" + productId;
    	}
    </script>
  </head>
  <body>
		
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
		
	<section id="collection" class="py-5">
	   <div class="container">
	      <div class="title text-center">
	         <!-- <h2 class="position-relative d-inline-block">New Collection</h2> -->
	      </div>
		
        <div class="row g-0">
          <div class="d-flex flex-wrap justify-content-center mt-5">
            <button type="button" class="custom-button btn m-1 text-dark">
              <i class="fa-solid fa-border-all"></i>
              <a href="/products?page=1">전체</a>
            </button>
            <button type="button" class="custom-button btn m-2 text-dark">
            	<i class="fa-solid fa-person-dress"></i>
            	<a href="/products?page=${not empty param.page ? param.page : 1 }&category=1">여성의류</a>
            </button>
            <button type="button" class="custom-button btn m-2 text-dark">
            	<i class="fa-solid fa-person"></i>
            	<a href="/products?page=${not empty param.page ? param.page : 1 }&category=2">남성의류</a>
            </button>
            <button type="button" class="custom-button btn m-2 text-dark">
            	<i class="fa-solid fa-ring"></i>
            	<a href="/products?page=${not empty param.page ? param.page : 1 }&category=3">패션잡화</a>
            </button>
            <button type="button" class="custom-button btn m-2 text-dark">
            	<i class="fa-solid fa-computer"></i>
            	<a href="/products?page=${not empty param.page ? param.page : 1 }&category=4">생활가전</a>
            </button>
            <button type="button" class="custom-button btn m-2 text-dark">
            	<i class="fa-solid fa-couch"></i>
            	<a href="/products?page=${not empty param.page ? param.page : 1 }&category=5">가구/인테리어</a>
            </button>
            <button type="button" class="custom-button btn m-2 text-dark">
            	<i class="fa-solid fa-gamepad"></i>
            	<a href="/products?page=${not empty param.page ? param.page : 1 }&category=6">게임/취미</a>
            </button>
            <button type="button" class="custom-button btn m-2 text-dark">
            	<i class="fa-solid fa-book"></i>
            	<a href="/products?page=${not empty param.page ? param.page : 1 }&category=7">도서</a>
            </button>
            <button type="button" class="custom-button btn m-2 text-dark">
            	<i class="fa-solid fa-baseball-bat-ball"></i>
            	<a href="/products?page=${not empty param.page ? param.page : 1 }&category=8">스포츠</a>
            </button>
          </div>
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
    <nav class="container" aria-label="Page navigation example">
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
    <script>
      // $(".filter-button-group").on("click", "button", function () {
      //   resetFilterBtns();
      //   $(this).addClass("active-filter-btn");
      // });
      // const filterBnts = $(".filter-button-group").find("button");
      // function resetFilterBtns() {
      //   filterBnts.forEach(function () {
      //     $(this).removeClass("active-filter-btn");
      //   });
      // }

      let subMenu = document.querySelector("#subMenu");

      function toggleMenu() {
        console.log(subMenu);
        subMenu.classList.toggle("open-menu");
      }
    </script>
  </body>
</html>
