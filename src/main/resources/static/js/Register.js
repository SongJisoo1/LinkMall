let timer = null;
let isRunning = false;

$(function() {
	
	/* 주소검색 */
	$("#findAddr").on("click", function () {
		new daum.Postcode({
			oncomplete: function(data) {
		            
		    let addr = "";
		    let extraAddr = "";
		        	
		    if(data.userSelectedType === "R") {
		       	addr = data.roadAddress;
		    } else {	       			
		       	addr = data.jibunAddress;
		    }
					
		       	
		    $("#postAddr").val(data.zonecode);
		    $("#loadAddr").val(addr);
		    $("#detailAddr").focus();
		   }
		}).open();
	});
	
	/* 아이디 중복 체크*/
	$("#idChekcBtn").on("click", function () {
		const id = $("#loginId").val();
		const url = "/user/check-id?id=" +id;
		
		if(!inputValueValidator(id)) {
			alert("아이디를 입력하세요");
			return;
		}
			
			
		$.ajax({
			type: "GET",
			url: url,
			dataType: "json",
			contentType: "application/json",
			success: function (result) {
				if(result) {
					const idCheckResult = document.querySelector("input[name='idDuplicatedCheckVal']");
					idCheckResult.value = true;
					alert("사용 가능한 아이디 입니다.")
				} else {
					alert("사용 불가능한 아이디 입니다.");
				}
			}
		});
		
	});
	
	/* 닉네임 중복 체크 */
	$("#nickCheckBtn").on("click", function () {
		const nickname = $("#nickname").val();
		const url = "/user/check-nick?nickname="+nickname;
			
		if(!inputValueValidator(nickname)) {
			alert("닉네임을 입력하세요");
			nickname.focus();
			return;
		}
			
		$.ajax({
			type: "GET",
			url: url,
			dataType: "json",
			contentType: "application/json",
			success: function (result) {
				if(result) {
					const nickCheckResult = document.querySelector("input[name='nickDuplicatedCheckVal']");
					nickCheckResult.value = true;
					alert("사용 가능한 닉네임 입니다.");
				} else {
					alert("사용 불가능한 닉네임입니다.");
				}
			} 
		});	
				
	});
	
	$("#sendCode").on("click", function () {
		
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
		
		const url = "/mail/check-code?email=" + email + "&code=" + code + "&type=code";
		
		$.ajax({
			type: "GET",
			url: url,
			dataType: "json",
			contentType: "application/json",
			success: function (result) {
				if(result) {
					alert("이메일 인증에 성공했습니다.");
					const validate = document.querySelector("input[name='validateCode']");
					validate.value = true;
				} else {
					alert("이메일 인증에 실패했습니다.")
				}
			}
		}); 
	
	});

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

/* 인풋값이 "" 인지 확인 */
const inputValueValidator = (input) => {
	if(input === "") {
		return false;
	}
	return true;
}

const submitFromValidator = (input) => {
	
	const idCheckResult = document.querySelector("input[name='idDuplicatedCheckVal']").value;
	const nickCheckResult = document.querySelector("input[name='nickDuplicatedCheckVal']").value;
	
	const id = document.querySelector("#loginId").value;
	const password = document.querySelector("#password").value;
	const confirmPassword = document.querySelector("#confirmPwd").value;	
	const email = document.querySelector("#email").value;
	const emailValidate = document.querySelector("input[name='validateCode']").value;
	
	const idRegExp = /^(?=.*[0-9])(?=.*[A-Za-z]).{4,30}$/;
	const pwdRegExp = /^(?=.*[A-Za-z])(?=.*\d).{8,}$/;

	
	if(idCheckResult === "false") {
		alert("아이디 중복체크를 완료하세요");
		return false;
	}
	
	if(!idRegExp.test(id)) {
		const inputDiv = document.querySelector("#id-input-wrap");
		const idMessage = "아이디는 영문, 숫자조합 4자 이상 30자 이하입니다.";

		const divElement = document.createElement("div");
		divElement.setAttribute("class", "invalid-message")
		divElement.innerHTML = idMessage;

		inputDiv.after(divElement);
		
		return false;
	}

	if(nickCheckResult === "false") {
		e.preventDefault();
		alert("닉네임 중복체크를 완료하세요");
		return false;
	}
	
	if(!inputValueValidator(email)) {
		alert("이메일을 입력하세요");
		return false;
	}
	
	if(!emailValidate) {
		alert("이메일 인증을 완료해주세요");
		return false;
	}
	
	if(!(inputValueValidator(password) && inputValueValidator(confirmPassword))) {
		alert("비밀번호를 입력하세요");
		return false;
	}
	
	if(password !== confirmPassword) {
		alert("비밀번호가 일치하지 않습니다.");
		return false;
	}
	
	if(!pwdRegExp.test(password)) {
		const inputDiv = document.querySelector("#pwd-input-wrap");
		const idMessage = "비밀번호는 영문, 숫자조합 8자 이상입니다.";

		const divElement = document.createElement("div");
		divElement.setAttribute("class", "invalid-message")
		divElement.innerHTML = idMessage;

		inputDiv.after(divElement);
		
		return false;
	}
	
	return true;
}

const addAlertHtml = (type, div) => {
	const idMessage = "아이디는 영문, 숫자조합 4자 이상 30자 이하입니다.";
	let html = "";
	
	if(type === "id") {
		html = `<div>${message}</div>`
	}
	
	div.appendChild(html);
}

const removeMessage = (inputBox) => {
	const invalidMessage = document.querySelector(".invalid-message");
	
	if(invalidMessage !== null) {
		invalidMessage.remove();
	}
	
}

const submitBtn = document.querySelector("button[type='submit']");
const formControls = document.querySelectorAll(".form-control");


const submitForm = (e) => {
	if(!submitFromValidator()) {
		e.preventDefault();
	}
}

submitBtn.addEventListener("click", submitForm);

/* 유효성 검사 실패 메세지가 있는지 확인해서 있으면 삭제 */
formControls.forEach((elem) => {
	elem.addEventListener("input", (e) => {
		if(document.querySelector(".invalid-message")) {
			removeMessage(e.target);
		}
	})
});