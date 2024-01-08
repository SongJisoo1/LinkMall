<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LinkMall :: add product</title>
<link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
      crossorigin="anonymous"
    />
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
      crossorigin="anonymous"
    ></script>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<link href="/css/productRegister.css" rel="stylesheet"></link>
</head>
<style>

      
</style>
<script>
	$(document).ready(function() {
		$("#registerBtn").on("click", function() {
			const title = $("#title").val();
			const price = $("#price").val();
			const contents = $("#contents").val();
			const category = $("#select :selected").val();
			
			console.log(category);
			
			const formData = new FormData();
			const inputFiles = $("#formFileMultiple");
			const files = inputFiles[0].files;
			
			for(let i = 0; i < files.length; i++) {
				formData.append("imgs", files[i]);
			}
			
			formData.append("title", title);
			formData.append("price", price);
			formData.append("contents", contents);
			formData.append("category", category);
			
			$.ajax({
				type: "POST",
				url: "/products",
				data: formData,
				processData: false,
				contentType: false,
				enctype: "multipart/form-data",
				async: false,
				success: function (result) {
					window.location.href="/products";
				}
			})
		})
	})
</script>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	
	<div class="my-form mt-5">
		<h1>제품등록</h1>
		<form>
		  <div class="mb-3">
		    <label for="title" class="form-label">상품 이름</label>
		    <input type="text" class="form-control" id="title" name="title">
		  </div>
		  <div class="mb-3">
		    <label for="price" class="form-label">상품 가격</label>
		    <input type="text" class="form-control" id="price" name="price">
		  </div>
		  <div class="mb-3">
		  	<label for="description" class="form-label">제품설명</label>
		  	<textarea class="form-control" id="contents" rows="3" name="contents"></textarea>
		  </div>
		   <div class="select-container mb-3">
	        <select id="select" class="form-select form-select-sm" aria-label="Small select example">
	          <option selected>카테고리</option>
	          <option value="1">여성의류</option>
	          <option value="2">남성의류</option>
	          <option value="3">패션잡화</option>
	          <option value="4">생활가전</option>
	          <option value="5">가구/인테리어</option>
	          <option value="6">게임/취미</option>
	          <option value="7">도서</option>
	          <option value="8">스포츠</option>
	          <option value="9">나눔</option>
	        </select>
	      </div>
	      
	      <div class="mb-3">
	  		<label for="formFileMultiple" class="form-label">상품이미지</label>
	  		<input class="form-control" type="file" id="formFileMultiple" multiple>
		  </div>
		  <button type="button" class="btn btn-primary" id="registerBtn">상품등록</button>
		</form>
	</div>
</body>
</html>