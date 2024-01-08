const deletePost = (productId) => {
    
    if(confirm("삭제하시겠습니까?")) {
        const url = "/products/delete";
        const data = { productId };

        $.ajax({
        	type: "DELETE",
			url: url,
			data: data,
			success: function () {
				alert("삭제완료");
				location.replace("/products");
			}
        });
    } else {
        alert("취소되었습니다.");
    }

}