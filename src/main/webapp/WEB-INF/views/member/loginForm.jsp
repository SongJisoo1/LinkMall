<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Linkmall :: 로그인</title>
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
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="/js/loginForm.js" defer></script>
<style>
      .my-form {
        padding: 2em;
        color: #fff;
        width: 560px;
        margin-inline: auto;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
      }

      h1 {
        font-weight: bold;
        text-align: center;
        font-size: 2.5em;
      }

      .login-input {
        background-color: inherit;
        color: #fff;
        border: none;
        border-bottom: 1px solid #fff;
        border-radius: 0;
        padding-left: 0;
      }

      .login-input:focus {
        background-color: inherit;
        color: #fff;
        box-shadow: none;
        border-color: #fff;
      }

      .btn {
        border-radius: 0;
        width: 100%;
        font-weight: bold;
      }

      p {
        color: gray;
        text-align: center;
      }

      p a {
        text-decoration: none;
      }

      p a:hover {
        color: #fff;
      }
</style>
<script>
	const msg = "${message}";
	
	if(msg === "VAN_USER") {
		alert("비밀번호 찾기를 이용해주세요");
	} else if (msg === "INCORRECT_ACC") {
		alert("아이디 또는 비밀번호를 확인해주세요");
	}
</script>
</head>
<body>
	
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	
    <div class="my-form bg-dark">
      <h1>로그인</h1>
      <form action="/users/login" method="POST">
        <div class="mb-3 mt-4">
          <label for="id" class="form-label">아이디</label>
          <input
            type="text"
            class="form-control login-input"
            id="id"
            name="loginId"
            aria-describedby="emailHelp"
          />
        </div>
        <div class="mb-3 mt-4">
          <label for="exampleInputPassword1" class="form-label">비밀번호</label>
          <input
            type="password"
            class="form-control login-input"
            id="password"
            name="password"
          />
        </div>
        <button type="submit" id="submitBtn" class="btn btn-light mt-3">로그인</button>
      </form>
      <p class="mt-4">계정이 없으신가요? <a href="/users/register">회원가입</a></p>
      <a href="/users/find-password">비밀번호 찾기</a>
    </div>
</body>
</html>