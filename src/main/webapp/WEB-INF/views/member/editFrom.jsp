<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
	<link href="/css/editForm.css" rel="stylesheet"></link>
<script>

	let timer = null;
	let isRunning = false;

	$(function () {
		const oldEmail = $("#email").val();
		
		$("#email").change(function () {
			const newEmail = $("#email").val();
				
			if(oldEmail === newEmail) {
				return;
			} 
			
			alert("이메일이 변경되었습니다. \n새로운 이메일을 인증해주세요");
			$("#sendCode").attr("disabled", false);
			$("#email").after(`<input type="hidden" name="validateCode" value="false" />`);
			
		});
		
		$("#sendCode").on("click", function () {
			
			alert("send");
			
			const email = $("#email").val();
			let display = $("#timer");
			let leftSec = 120;
			
			if(!inputValueValidator(email)) {
				alert("이메일을 입력하세요");
				return;
			}
			
			if(isRunning) {
				clearInterval(timer);
				display.html("");
				startTimer(leftSec, display);
			} else {
				startTimer(leftSec, display);
			}
			
			const url = "/mail/send-code?email=" + email; 
			
			$.ajax({
				type: "POST",
				url: url,
				dataType: "json",
				contentType: "application/json",
				success: function(result) {
					$("#emailValidate").attr("disabled", false);
					alert("인증번호가 발송되었습니다.");
				}
					
			});
		});
		
		$("#emailValidate").on("click", function () {
			const code = $("#confirm-code").val();
			const email = $("#email").val();
			
			let display = $("#timer");
			
			const url = "/mail/check-code?email=" + email + "&code=" + code;
			
			$.ajax({
				type: "GET",
				url: url,
				dataType: "json",
				contentType: "application/json",
				success: function (result) {
					if(result) {
						alert("이메일 인증에 성공했습니다.");
						const validate = document.querySelector("input[name='validateCode']");
						$("#emailValidate").attr("disabled", true);
						display.remove();
					} else {
						alert("이메일 인증에 실패했습니다.")
					}
				}
			}); 
		
		});
		
		$("#editBtn").on("click", function() {
			const memberId = $("#memberId").val();
			const loginId = $("#loginId").val();
			const nickname = $("#nickname").val();
			const email = $("#email").val();
			const postAddress = $("#postAddr").val();
			const roadAddress = $("#roadAddr").val();
			const detailAddress = $("#detailAddr").val();
			
			const formData = new FormData();
			const inputFiles = $("#formFile");
			const files = inputFiles[0].files;
			
			for(let i = 0; i < files.length; i++) {
				formData.append("imgs", files[i]);
			}
			
			formData.append("memberId", memberId);
			formData.append("loginId", loginId);
			formData.append("nickname", nickname);
			formData.append("email", email);
			formData.append("postAddress", postAddress);
			formData.append("roadAddress", roadAddress);
			formData.append("detailAddress", detailAddress)
			
			$.ajax({
				type: "POST",
				url: "/users/edit-info",
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
				
	});
	
	const startTimer = (count, display) => {
		let minutes = 0;
		let seconds = 0;
		
		timer = setInterval(function () {
			minutes = parseInt(count / 60, 10);
			seconds = parseInt(count % 60, 10);
			
			minutes = minutes < 10 ? "0" + minutes : minutes;
			seconds = seconds < 10 ? "0" + seconds : seconds;
			
			display.html(minutes + ":" + seconds);
			
			if(--count < 0) {
				clearInterval(timer);
				alert("인증시간이 초과되었습니다.");
				display.html("시간초과");
				$("#emailValidate").attr("disabled", true);
				isRunning = false
			}
		}, 1000);
		
		isRunning = true;
	}
	
	const inputValueValidator = (input) => {
		if(input === "") {
			return false;
		}
		return true;
	}

</script>    
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<div class="my-form bg-dark">
      <h1>회원정보 수정</h1>
      <form>
      	<input type="hidden" id="memberId" value="${member.memberId }">
        <label for="loginId" class="form-label">아이디</label>
        <div class="input-group mb-3" id="id-input-wrap">
          <input
            type="text"
            class="form-control"
            id="loginId"
            name="loginId"
            aria-describedby="loginId"
            value="${member.loginId }"
            readonly
          />
        </div>
        <label for="nickname" class="form-label">닉네임</label>
        <div class="input-group mb-3">
          <input type="text" class="form-control input" id="nickname" name="nickname" value="${member.nickname }" />
          <input type="hidden" name="nickDuplicatedCheckVal" value="false" />
          <button id="nickCheckBtn" class="btn btn-outline-secondary ms-3" type="button">
            중복확인
          </button>
        </div>
        <label for="email" class="form-label">이메일</label>
        <div class="input-group mb-3">
          <input type="email" class="form-control input" id="email" name="email" value="${member.email }" />
          <button class="btn btn-outline-secondary ms-3" type="button" id="sendCode" disabled>
            인증번호 보내기
          </button>
        </div>
        <label for="confirm-code" class="form-label">인증번호</label>
        <div class="input-group mb-3">
          <input type="text" class="form-control input" id="confirm-code" name="confirmCode" />
          <span id="timer"></span>
           
          <button class="btn btn-outline-secondary ms-3" type="button" id="emailValidate">
            인증하기
          </button>
        </div>
        <label for="postAddr" class="form-label">우편번호</label>
        <div class="input-group mb-3">
          <input type="text" name="postAddress" class="form-control input" id="postAddr" value="${member.postAddress }"/>
          <button class="btn btn-outline-secondary ms-3" id="findAddr" type="button">
            우편번호 찾기
          </button>
        </div>
        <div class="d-flex justify-content-between">
          <div class="mb-3 mt-4 flex-grow-1">
            <label for="loadAddr" class="form-label">도로명 주소</label>
            <input type="text" name="roadAddress" class="form-control input" id="loadAddr" value="${member.roadAddress }" />
          </div>
          <div class="mb-3 mt-4 ms-2 flex-grow-1">
            <label for="detailAddr" class="form-label">상세주소</label>
            <input type="password" name="detailAddress" class="form-control input" id="detailAddr" value="${member.detailAddress }" />
          </div>
        </div>
        <div class="mb-3">
  			<label for="formFile" class="form-label">프로필 이미지</label>
  			<input class="form-control" type="file" id="formFile">
		</div>
        <button type="button" class="btn btn-light mt-3 mb-3" id="editBtn">수정하기</button>
      </form>
    </div>
</body>
</html>