<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<link
  rel="stylesheet"
  href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css"
/>
<script src="https://kit.fontawesome.com/f870d87297.js" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script src="/js/myPage.js" defer></script>
<script src="/js/utils/ajax.js" defer></script>

<link href="/css/mypage.css" rel="stylesheet"></link>

</head>
<body>
	
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	
 	 <div class="modal" id="myModal">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title">거래 평점을 남겨주세요</h4>
          </div>
          <div class="modal-body">
          	<div class="review">
				<i class="fa-solid fa-star" style="color: #d2cbcb"></i>
				<i class="fa-solid fa-star" style="color: #d2cbcb"></i>
				<i class="fa-solid fa-star" style="color: #d2cbcb"></i>
				<i class="fa-solid fa-star" style="color: #d2cbcb"></i>
				<i class="fa-solid fa-star" style="color: #d2cbcb"></i>
          	</div>
          </div>
          <input type="hidden" id="productId" value="">
          <input type="hidden" id="buyerId" value="">
          <input type="hidden" id="sellerId" value="">
          <input type="hidden" id="alarmId" value="">
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
	        <button type="button" class="btn btn-primary submit-btn">저장하기</button>
	      </div>
        </div>
      </div>
    </div>

    <div class="container">
      <div class="my-profile">
        <img class="my-profile__img" src="https://project-img-bucket.s3.ap-northeast-2.amazonaws.com/${member.profileImg }" alt="" />
        <div class="my-profile__content">
          <div class="my-profile__nick">${member.nickname }</div>
          <div
            class="progress"
            role="progressbar"
            aria-label="Example with label"
            aria-valuenow="${rate }"
            aria-valuemin="0"
            aria-valuemax="5"
          >
            <div class="progress-bar" style="width: ${(rate / 5) * 100}%">${rate }</div>
          </div>
          <input type="hidden" id="memberId" value="${sessionScope.loginMember.memberId }">
        </div>
      </div>
      <div class="my-profile__nav">
        <ul class="nav">
          <li class="nav-item">
            <a class="nav-link alarm" href="javascript:void(0)">알림</a>
          </li>
          <li class="nav-item">
            <a class="nav-link products" href="javascript:void(0)">거래 물품</a>
          </li>
          <li class="nav-item">
            <a class="nav-link like-list" href="javascript:void(0)">찜목록</a>
          </li>
        </ul>
      </div>
      <div class="my-profile__list">
        <div class="row row-cols-1 row-cols-md-3 g-4 card-container"></div>

        <nav
          aria-label="Page navigation example"
          class="d-flex justify-content-center mt-5"
        >
          <ul class="pagination"></ul>
        </nav>
      </div>
     </div>
</body>
</html>