const validateInput = (value) => {
	if(value === "") {
		return false;
	}
	return true;
}

const submitForm = (e) => {
	
	const id = document.querySelector("#id").value;
	const password = document.querySelector("#password").value;

	if(!validateInput(id)) {
		e.preventDefault();
		alert("아이디를 입력하세요");
		return;
	}
}

document.querySelector("#submitBtn").addEventListener("click", submitForm);