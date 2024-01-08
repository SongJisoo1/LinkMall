function insertLike(productId, event) {
	event.stopPropagation();
	
	const url = `/products/${productId}/likes`;
	
	$.ajax({
		 url: url,
		 type: "POST",
		 dataType: "json",
		 success: function (response) {
			 
			 	const tag = document.querySelector(`.like-${productId}`);	
	
				if(tag.classList.contains("fas")) {
						tag.classList.remove("fas");
						tag.classList.add("fa-regular");	
				} else {
						tag.classList.remove("fa-regular");
						tag.classList.add("fas");
				}
								
				const likeCnt = document.querySelector(`#likeCnt-${productId }`);
								
				likeCnt.innerText = response.totalLikeCnt;
		 },
		 error: function (request, status, error) {
			alert("로그인 후 사용해주세요");
			window.location.href = "/users/login";
		 }
	 });

}