/**
 *  getJson 데이터 조회(SELECT) 
 *  서버에서 내려오는 모든 데이터의 dataType은 json으로 고정
 * 	요청 url과 parm만 유동적으로 처리 해서 사용
 */

function getJson(uri, param) {
	let json = {};
	
	$.ajax({
		url: uri,
		type: "GET",
		dataType: "json",
		data: param,
		async: false,
		success: function (response) {
			json = response;
		},
		error: function (request, status, error) {
			console.log(error);
		}
	});
	
	return json;
}

/** 
 * 조회를 제외한 나머지 API를 호출ㅎ
 */

function callApi(uri, method, params) {
	 
	 let json = {};
	 
	 $.ajax({
		 url: uri,
		 type: method,
		 contentType: "application/json",
		 dataType: "json",
		 data: (params) ? JSON.stringify(params) : {},
		 async: false,
		 success: function (response) {
			 console.log(response);
			 json = response;
		 },
		 error: function (request, status, error) {
			 console.log(error);
		 }
	 });
	 
	 return json;
}
