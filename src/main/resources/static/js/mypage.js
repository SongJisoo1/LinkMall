window.onload = () => {
	findAllAlarm();
};

document.querySelector(".alarm").addEventListener("click", function () {
	handleTabClick(this);
	findAllAlarm(1);
})

document.querySelector(".products").addEventListener("click", function () {
	handleTabClick(this);
	findAllProducts(1);
})

document.querySelector(".like-list").addEventListener("click", function () {
	handleTabClick(this);
	findAllLikedList(1);
})

$(document).ready(function () {
	
	let rank = 0;
	
	$("#myModal").on("show.bs.modal", function (event) {
		const dataset = $(event.relatedTarget);
		
		const productId = dataset.data("product");
		const buyerId = dataset.data("buyer");
		const sellerId = dataset.data("seller");
		const alarmId = dataset.data("alarm");
		
		$("#productId").val(productId);
		$("#buyerId").val(buyerId);
		$("#sellerId").val(sellerId);
		$("#alarmId").val(alarmId);
	});
	
	$(".review i").click(function () {
		rank = $(this).index() + 1;
		$(".review i").css("color","#d2cbcb");
		$(".review i:nth-child(-n+" + rank + ")").css("color", "#fae500");	
	});
	
	$("#myModal").on("hidden.bs.modal", function (event) {
		
		for(let i = 0; i <= rank; i++) {
			$(".review i:nth-child(-n+" + rank + ")").css("color", "#d2cbcb");
		}
		
		rank = 0;
		
		$("#productId").val(0);
		$("#buyerId").val(0);
		$("#sellerId").val(0);
		$("#alarmId").val(0);
	});
	
	$(".submit-btn").click(function () {
		
		const currRank = rank;
		const productId = $("#productId").val();
		const buyerId = $("#buyerId").val();
		const sellerId = $("#sellerId").val();
		const alarmId = $("#alarmId").val();
		
		console.log(alarmId);
		
		const data = {
			rank: currRank,
			productId,
			buyerId,
			sellerId,
			alarmId
		};
		
		const url = "/users/review";
		
		const response = callApi(url, "POST", data);
		
		location.replace(location.href);
					
	});
	
})

function handleTabClick(tabNode) {
	
	const allTabNodes = document.querySelectorAll(".nav-link");
	allTabNodes.forEach((node) => {
		node.classList.remove("active");
	});
	
	tabNode.classList.add("active");
}

function insertLike(productId, event) {
	event.stopPropagation();
	
	const url = `/products/${productId}/likes`;
	let prevPage = 0;
		
	const response = callApi(url, "POST");
	
	const tag = document.querySelector(`.like-${productId}`);

	tag.innerText = response.totalLikeCnt;
	
	
	if(tag.classList.contains("bi-heart-fill")) {
		tag.classList.remove("bi-heart-fill");
		tag.classList.add("bi-heart");	
	} else {
		tag.classList.remove("bi-heart");
		tag.classList.add("bi-heart-fill");
		
		if(document.querySelector(".card-container").children.length === 1) {
			prevPage = +document.querySelector(".page-link.on").text;
		}
	}
	
	if(document.querySelector(".nav-link.active").innerText === "찜목록") {
		
		if(document.querySelector)
		findAllLikedList (prevPage);
	}
	
}

function deleteAlarm(alarmId) {
	
	if(confirm("알림을 삭제하시겠습니까?")) {
		
		const url = `/alarms/${alarmId}`;
		
		$.ajax({
			type: "DELETE",
			url: url,
			dataType: "json",
			success: function (response) {
				alert("알람을 삭제했습니다.")
				window.location.reload();
			},
			error: function(error) {
				console.log(error);
			}
		});
		
	} else {
		return;
	}
}

function findAllProducts(page) {
	
	const currentPage = document.querySelector(".page-link.on");
	page = (page) ? page : (currentPage ? parseInt(currentPage.text) : 1);
	
	const memberId = document.querySelector("#memberId").value;
	
	const url = `/users/${memberId}/posts`;
	
	const params = { 
		page: page,
		recordSize: 6,
		pageSize: 5, 
	};
	
	const { list, pagination } = getJson(url, params);
	drawCards(list);
	drawPage("findAllProducts", pagination, page);
	
}

function findAllLikedList(page) {
	
	const currentPage = document.querySelector(".page-link.on");
	page = (page) ? page : (currentPage ? parseInt(currentPage.text) : 1);
	
	const memberId = document.querySelector("#memberId").value;
	
	const url = `/users/${memberId}/like-list`;
	
	const params = { 
		page: page,
		recordSize: 6,
		pageSize: 5, 
	};
	
	const { list, pagination } = getJson(url, params);
	drawCards(list);
	drawPage("findAllLikedList", pagination, page);
}

function findAllAlarm(page) {
	
	const currentPage = document.querySelector(".page-link.on");
	page = (page) ? page : (currentPage ? parseInt(currentPage.text) : 1);
	
	const params = {
		page: page,
		recoredSize: 6,
		pageSize: 5,
	};
	
	const url = "/users/alarm";
	
	const { list, pagination } = getJson(url, params);
	
	drawAlarm(list);
}

function drawAlarm(list) {
	if( !list.length ) {
		document.querySelector(".card-container").innerHTML = '<div class="cm_none"><p>관심목록을 추가해보세요!</p></div>';
		return;
	}
	
	const div = document.querySelector(".card-container");
	div.removeAttribute("class");
	div.setAttribute("class", "wrap");
	
	let alarmList = "";
	
	list.forEach(row => {
		
		let messgae = "";
		
		if(row.alarmType === "COMMENT") {
			message = `
				<div
	              style="
	                border: 1px solid black;
	                padding: 10px;
	              	"
	            	>
	              <span class="badge rounded-pill text-bg-primary">${row.category}</span>
	              <div
	                style="
	                  display: flex;
	                  justify-content: space-between;
	                  align-items: center;
	                	"
	              	>
	                <div style="margin-top: 5px">
	                  <a href=${row.href}&alarm_id=${row.alarmId} class=${row.isRead ? "not-read" : "read" } style="font-size: 13px;">
	                  	 <span style="font-weight: bold;">${row.title}</span> 게시글에 댓글이 달렸습니다.
	                  </a>
	                  <span style="font-size: 8px;">${timeForToday(row.createdAt)}</span>
	                </div>
	                <img src="/img/delete.png" alt="" style="margin-left: 5px" onclick="deleteAlarm(${row.alarmId})" />
	              </div>
	            </div>
	            `;
		} else if(row.alarmType === "SALE") {
			message = `
				<div
	              style="
	                border: 1px solid black;
	                padding: 10px;
	              	"
	            	>
	              <span class="badge rounded-pill text-bg-primary">${row.category}</span>
	              <div
	                style="
	                  display: flex;
	                  justify-content: space-between;
	                  align-items: center;
	                	"
	              	>
	                <div style="margin-top: 5px">
	                  <a href=${row.href}&alarm_id=${row.alarmId} class=${row.isRead ? "not-read" : "read" } style="font-size: 13px;">
	                  	 <span style="font-weight: bold;">${row.title}</span> 게시글의 상태가 판매중으로 변경되었습니다.
	                  </a>
	                  <span style="font-size: 8px;">${timeForToday(row.createdAt)}</span>
	                </div>
	                <img src="/img/delete.png" alt="" style="margin-left: 5px" onclick="deleteAlarm(${row.alarmId})" />
	              </div>
	            </div>
	            `;
	            
		} else if(row.alarmType === "RESERVATION") {
			message = `
				<div
	              style="
	                border: 1px solid black;
	                padding: 10px;
	              	"
	            	>
	              <span class="badge rounded-pill text-bg-primary">${row.category}</span>
	              <div
	                style="
	                  display: flex;
	                  justify-content: space-between;
	                  align-items: center;
	                	"
	              	>
	                <div style="margin-top: 5px">
	                  <a href=${row.href}&alarm_id=${row.alarmId} class=${row.isRead ? "not-read" : "read" } style="font-size: 13px;">
	                  	 <span style="font-weight: bold;">${row.title}</span> 게시글의 상태가 예약중으로 변경되었습니다.
	                  </a>
	                  <span style="font-size: 8px;">${timeForToday(row.createdAt)}</span>
	                </div>
	                <img src="/img/delete.png" alt="" style="margin-left: 5px" onclick="deleteAlarm(${row.alarmId})" />
	              </div>
	            </div>
	            `;
	
		} else if(row.alarmType === "SOLD") {
			message = `
				<div
	              style="
	                border: 1px solid black;
	                padding: 10px;
	              	"
	            	>
	              <span class="badge rounded-pill text-bg-primary">${row.category}</span>
	              <div
	                style="
	                  display: flex;
	                  justify-content: space-between;
	                  align-items: center;
	                	"
	              	>
	                <div style="margin-top: 5px">
	                  <a href=${row.href}&alarm_id=${row.alarmId} class=${row.isRead ? "not-read" : "read" } style="font-size: 13px;">
	                  	 <span style="font-weight: bold;">${row.title}</span> 게시글의 상태가 판매완료로 변경되었습니다.
	                  </a>
	                  <span style="font-size: 8px;">${timeForToday(row.createdAt)}</span>
	                </div>
	                <img src="/img/delete.png" alt="" style="margin-left: 5px" onclick="deleteAlarm(${row.alarmId})" />
	              </div>
	            </div>
	            `;
	
		} else if(row.alarmType === "DEAL") {
			
			console.log(row);
			
			message = `
				<div
	              style="
	                border: 1px solid black;
	                padding: 10px;
	              	"
	            	>
	              <span class="badge rounded-pill text-bg-primary">${row.category}</span>
	              <div
	                style="
	                  display: flex;
	                  justify-content: space-between;
	                  align-items: center;
	                	"
	              	>
	                <div style="margin-top: 5px">
	                  <a data-bs-toggle="modal" data-bs-target="#myModal" href="#" data-alarm="${row.alarmId}" data-buyer="${row.buyerId}" data-seller="${row.sellerId}" data-product=${row.productId} class=${row.isRead ? "not-read" : "read" } style="font-size: 13px;">
	                  	 <span style="font-weight: bold; margin-left: 3px;">${row.title}</span> 거래가 완료되었습니다. 평점을 남겨주세요
	                  </a>
	                  <span style="font-size: 8px;">${timeForToday(row.createdAt)}</span>
	                </div>
	                <img src="/img/delete.png" alt="" style="margin-left: 5px" onclick="deleteAlarm(${row.alarmId})" />
	              </div>
	            </div>
	            `;
		}
		
		alarmList += `
			<div class="col" style="margin-bottom: 10px;">
				${message}
          </div>
		`;
	});
	
	document.querySelector(".wrap").innerHTML = alarmList;
}

function drawCards(list) {
	
	if( !list.length ) {
		document.querySelector(".card-container").innerHTML = '<div class="cm_none"><p>등록한 거래가 없습니다.</p></div>';
	}
	
	const div = document.querySelector(".wrap");
	
	if(div) {
		div.removeAttribute("class");
		div.setAttribute("class", "row row-cols-1 row-cols-md-3 g-4 card-container");	
	}
	
	let cardList = "";
	
	list.forEach(row => {

		cardList += `
			<div class="col">
   				<div class="card" onclick="javascript:location.href='/products/test2?product_id=${row.productId }'">
      				<div class="text-container">
         				<img src=https://project-img-bucket.s3.ap-northeast-2.amazonaws.com/${row?.imgs[0]?.imgName} class="card-img-top" alt="..." />
      				</div>
      				<div class="card-body bg-primary pb-2">
				        <h5 class="card-title1">${row.nickname}</h5>
				        <p class="card-text">${row.title}</p>
				        <hr />
				        <div class="d-flex justify-content-between">
            				<div>
               					<p class="price">${row.price}</p>
           					 </div>
            				 <div>
					            <i class="${row.isLiked ? 'bi bi-heart-fill' : 'bi bi-heart' } like-${row.productId}" onclick="insertLike(${row.productId}, event)" >
					            	${row.likeCnt}
					            </i>
					            <i class="bi bi-eye"> ${row.viewCnt} </i>
           					 </div>
				         </div>
				      </div>
				   </div>
				</div>
			`;
	});
	
	document.querySelector(".card-container").innerHTML = cardList;
}

function drawPage(type, pagination, page) {
	
	if (!pagination || !page) {
		document.querySelector(".pagination").innerHTML = "";
		throw new Error("Missing required parameters");
	}
	
	let html = "";
	
	// 첫, 이전 페이지 버튼 추가
	if(page > 1) {
		html = `
			<li class="page-item">
              <a class="page-link" aria-label="Previous" onclick=${type}(1)>
                <span aria-hidden="true">처음으로</span>
              </a>
            </li>
            <li class="page-item">
              <a class="page-link" aria-label="Previous" onclick=${type}(${page - 1})>
                <span aria-hidden="true">&laquo;</span>
              </a>
            </li>
		`;
	}
	
	// 페이지 번호 추가
	html += `<li class="page-item">`;
	
	for(let i = pagination.startPage; i <= pagination.endPage; i++) {
		html += `
			<li class="page-item">
				<a class="page-link" href="javascript:void(0)" onclick=${type}(${i})>${i}</a>
			</li>
		`;
	}
	
	html += `</li>`;
	
	// 다음 / 끝 페이지 추가
	if(pagination.existNextPage) {
		html += `
			<li class="page-item">
              <a class="page-link" href="javascript:void(0)" onclick=${type}(${page + 1}) aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
              </a>
            </li>
            <li class="page-item">
              <a class="page-link" href="javascript:void(0)" onclick=${type}(${pagination.totalPageCount}) aria-label="Next">
                <span aria-hidden="true">마지막으로</span>
              </a>
            </li>	
		`;
	}
	
	// 페이징 렌더링
	const paging = document.querySelector(".pagination");
	paging.innerHTML = html;
	
	// 사용자가 클릭시 해당되는 a 태그 찾아 활성화 후 클릭 이벤트 제거
	const currentPage = Array.from(paging.querySelectorAll("a")).find(a => (Number(a.text) === page || Number(a.text) === pagination.totalPageCount));
	currentPage.classList.add("on");
	currentPage.removeAttribute("onclick");
}

function like(productId) {
	
	console.log(productId.class);
	
	const memberId = getPathVariable(window.location.href);
	const currentMemeberId = document.querySelector("#memberId").value;
	
	if(memberId !== currentMemeberId) {
		return;
	}
	
	const url = `/products/${productId}/like`;
	
	callApi(url, "PATCH");
}

function getPathVariable(url) {
	const regEx = /\/(\d+)\//;
	const match = url.match(regEx);
	return parseInt(match[1], 10);
}

function timeForToday(value) {
	const today = new Date();
    const timeValue = new Date(value);

    const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
    if (betweenTime < 1) return '방금전';
    if (betweenTime < 60) {
        return `${betweenTime}분전`;
    }

    const betweenTimeHour = Math.floor(betweenTime / 60);
    if (betweenTimeHour < 24) {
        return `${betweenTimeHour}시간전`;
    }

    const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
    if (betweenTimeDay < 365) {
        return `${betweenTimeDay}일전`;
    }

    return `${Math.floor(betweenTimeDay / 365)}년전`;
 }