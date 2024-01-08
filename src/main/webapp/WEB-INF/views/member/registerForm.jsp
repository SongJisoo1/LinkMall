<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Linkmall :: 회원가입</title>
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
<script src="/js/register.js" defer></script>
<link href="/css/register.css" rel="stylesheet"></link>
      
</head>
<body>
	
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	
    <div class="my-form bg-dark">
      <h1>회원가입</h1>
      <form action="/users/register" method="POST">
        <label for="loginId" class="form-label">아이디</label>
        <div class="input-group mb-3 regi-input" id="id-input-wrap">
          <input
            type="text"
            class="form-control regi-input"
            id="loginId"
            name="loginId"
            aria-describedby="loginId"
          />
          <input type="hidden" name="idDuplicatedCheckVal" value="false" />
          <button id="idChekcBtn" class="btn btn-outline-secondary ms-3" type="button">
            중복확인
          </button>
        </div>
        <label for="nickname" class="form-label">닉네임</label>
        <div class="input-group mb-3">
          <input type="text" class="form-control regi-input" id="nickname" name="nickname" />
          <input type="hidden" name="nickDuplicatedCheckVal" value="false" />
          <button id="nickCheckBtn" class="btn btn-outline-secondary ms-3" type="button">
            중복확인
          </button>
        </div>
        <div class="mb-3 mt-4" id="pwd-input-wrap">
          <label for="password" class="form-label">비밀번호</label>
          <input
            type="password"
            class="form-control regi-input"
            id="password"
            name="password"
          />
        </div>
        <div class="mb-3 mt-4">
          <label for="confirmPwd" class="form-label">비밀번호 확인</label>
          <input type="password" class="form-control regi-input" id="confirmPwd" />
        </div>
        <label for="email" class="form-label">이메일</label>
        <div class="input-group mb-3">
          <input type="email" class="form-control regi-input" id="email" name="email" />
          <button class="btn btn-outline-secondary ms-3" type="button" id="sendCode">
            인증번호 보내기
          </button>
        </div>
        <label for="confirm-code" class="form-label">인증번호</label>
        <div class="input-group mb-3">
          <input type="text" class="form-control regi-input" id="confirm-code" name="confirmCode" />
          <span id="timer"></span>
           <input type="hidden" name="validateCode" value="false" />
          <button class="btn btn-outline-secondary ms-3" type="button" id="emailValidate">
            인증하기
          </button>
        </div>
        <label for="postAddr" class="form-label">우편번호</label>
        <div class="input-group mb-3">
          <input type="password" name="postAddress" class="form-control regi-input" id="postAddr" />
          <button class="btn btn-outline-secondary ms-3" id="findAddr" type="button">
            우편번호 찾기
          </button>
        </div>
        <div class="d-flex justify-content-between">
          <div class="mb-3 mt-4 flex-grow-1">
            <label for="loadAddr" class="form-label">도로명 주소</label>
            <input type="text" name="roadAddress" class="form-control regi-input" id="loadAddr" />
          </div>
          <div class="mb-3 mt-4 ms-2 flex-grow-1">
            <label for="detailAddr" class="form-label">상세주소</label>
            <input type="password" name="detailAddress" class="form-control regi-input" id="detailAddr" />
          </div>
        </div>
        <button type="submit" class="btn btn-light mt-3 mb-3">회원가입</button>
      </form>
    </div>
  </body>
</html>