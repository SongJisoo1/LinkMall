<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/css/header.css" rel="stylesheet"></link>

<title>Insert title here</title>

</head>
<body>
	<nav class="nvb">
      <div style="color: white;">
      	<a href="/products" class="home-link">LinkMall</a>
      </div>
      <form class="d-flex" role="search">
        <input class="form-control me-2" style="width: 350px" type="search" placeholder="��ǰ���� �Է����ּ���" aria-label="Search">
      </form>
      
      <c:choose>
      	<c:when test="${not empty sessionScope.loginMember }">
      		<img
	        src="https://project-img-bucket.s3.ap-northeast-2.amazonaws.com/${sessionScope.loginMember.profileImg }"
	        class="user-pic"
	        alt=""
	        onclick="toggleMenu()"
	      />
	    </c:when>
	    <c:otherwise>
	    	<p class="login-btn" style="color: white">
        		<a href="/users/login">�α���/ȸ������</a>
      		</p>
	    </c:otherwise>
      </c:choose>
          
      <div class="sub-menu-wrap" id="subMenu">
        <div class="sub-menu">
          <div class="user-info">
            <img src="https://project-img-bucket.s3.ap-northeast-2.amazonaws.com/${sessionScope.loginMember.profileImg }" alt="������ �̹���" />
            <h3>${sessionScope.loginMember.nickname }</h3>
          </div>
          <div>
            <hr />

            <a href="/users/mypage" class="sub-menu-link">
              <img src="/images/images/profile.png" alt="" />
              <p>����������</p>
              <span>></span>
            </a>

<!--             <a href="" class="sub-menu-link">
              <img src="/images/images/setting.png" alt="" />
              <p>Ȱ���α�</p>
              <span>></span>
            </a> -->

            <a href="/users/edit-info" class="sub-menu-link">
              <img src="/images/images/profile.png" alt="" />
              <p>������ ����</p>
              <span>></span>
            </a>
            
            <a href="/products/add" class="sub-menu-link">
              <img src="/images/images/profile.png" alt="" />
              <p>��ǰ ���</p>
              <span>></span>
            </a>

            <a href="javascript:void(0)" id="logoutBtn" class="sub-menu-link">
              <img src="/images/images/profile.png" alt="" />
              <p>�α׾ƿ�</p>
              <span>></span>
            </a>
          </div>
        </div>
      </div>
    </nav>
    
    <script>
      
	const input = document.querySelector("input");
	
	input.addEventListener("keyup", function (event) {
		if(event.keyCode === 13) {
			const title = event.target.value;
			window.location.href = `/products/search?title=\${title}`;
		}
	})

      let subMenu = document.querySelector("#subMenu");

      function toggleMenu() {
        console.log(subMenu);
        subMenu.classList.toggle("open-menu");
        
      let logoutBtn = document.querySelector("#logoutBtn");
      
      logoutBtn.addEventListener("click", function () {
    	 $.ajax({
    		 type: "POST",
    		 url: "/users/logout",
    		 success: function () {
    			 alert("�α׾ƿ� �Ǿ����ϴ�.");
    			 window.location.href = "/";
    		 }
    	 }) 
      });
      }
    </script>
    
</body>
</html>