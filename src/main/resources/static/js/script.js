$(function(){
	  $("#confirm").click(function(){
	      modalClose();
	      //컨펌 이벤트 처리
	  });
	  $("#modal-open").click(function(){        
		  $("#popup").css('display','flex').hide().fadeIn();
	  });
	  $("#close").click(function(){
	      modalClose();
	  });
	  function modalClose(){
	    $("#popup").fadeOut();
	  }
});

(function ($) {
	"use strict";

    jQuery(document).ready(function ($) {
        
        $(".thumb-image").find('img').bind("click", function() {
            var src = $(this).attr("src");
            // Check the beginning of the src attribute  
            var state = (src.indexOf("bw_") === 0) ? 'bw' : 'clr';
            // Modify the src attribute based upon the state var we just set
            (state === 'bw') ? src = src.replace('bw_', 'clr_') : src = src.replace('clr_', 'bw_');
            // Apply the new src attribute value  
            $(this).attr("src", src);

            // This is just for demo visibility
            $('.thumb-main-image img').attr("src", src);
            
            $('.thumb-image li.active').removeClass('active');
            
            $(this).parent().parent().addClass('active');
            
            

          return false;
        });
        
        var spins = document.getElementsByClassName("qt-area");
        for (var i = 0, len = spins.length; i < len; i++) {
            var spin = spins[i],
                span = spin.getElementsByTagName("i"),
                input = spin.getElementsByTagName("input")[0];

            input.onchange = function() { input.value = +input.value || 0; };
            span[0].onclick = function() { input.value = Math.max(0, input.value - 1); };
            span[1].onclick = function() { input.value -= -1; };
        }



    });


    jQuery(window).load(function(){

        
    });

}(jQuery));

 function insertLike(productId) {
		
	const url = `/products/${productId}/likes`;
	
	$.ajax({
		 url: url,
		 type: "POST",
		 dataType: "json",
		 success: function (response) {
			 
			 	const tag = document.querySelector(".like");	
	
				if(tag.classList.contains("fas")) {
						tag.classList.remove("fas");
						tag.classList.add("fa-regular");	
				} else {
						tag.classList.remove("fa-regular");
						tag.classList.add("fas");
				}
				
		 },
		 error: function (request, status, error) {
			alert("로그인 후 사용해주세요");
			window.location.href = "/users/login";
		 }
	 });
}