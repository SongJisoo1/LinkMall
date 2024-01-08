$(function () {
    $("#search-id").on("click", function () {
        const loginId = $("#id").val();

        if (loginId === "") {
            alert("아이디를 입력하세요");
            return;
        }

        $(".result").empty();

        const param = { id: loginId };

        $.ajax({
            type: "GET",
            url: "/users/help",
            data: param,
            dataType: "json",
            success: function (response) {
                const div = $(".result");

                div.append(`
                    <label for="email" class="form-label">이메일</label>
                    <div class="input-group mb-3">
                        <input type="email" class="form-control" id="email" name="email" />
                        <button class="btn btn-outline-secondary ms-3" type="button" id="sendCode">
                            인증번호 보내기
                        </button>
                    </div>
                    <label for="confirm-code" class="form-label">인증번호</label>
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" id="confirm-code" name="confirmCode" />
                        <span id="timer"></span>
                        <input type="hidden" name="validateCode" value="false" />
                        <button class="btn btn-outline-secondary ms-3" type="button" id="emailValidate">
                            인증하기
                        </button>
                    </div>`
                );
            },
            error: function (request, status, error) {
                console.log(error);
                const div = $(".result");
                div.append("<p class='invalid-message'>존재하지 않는 아이디입니다.</p>");
            }
        });
    });

    $(document).on("click", "#sendCode", function () {

        const loginId = $("#id").val();
        const email = $("#email").val();

        const param = { id: loginId, email };

        $.ajax({
            type: "POST",
            url: "/mail/verified",
            data: param,
            dataType: "json",
            success: function (response) {

                if (!response) {
                    alert("가입한 이메일을 확인해주세요");

                }
            },
            error: function (error) {

            }
        });
    });

    $(document).on("click", "#emailValidate", function () {
        const code = $("#confirm-code").val();
        const email = $("#email").val();

        const url = "/mail/check-code?email=" + email + "&code=" + code + "&type=re";

        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                if (result) {
                    alert("새 비밀번호가 발급되었습니다. 메일을 확인해주세요");
                    
                } else {
                    alert("이메일 인증에 실패했습니다.")
                }
            }
        })
    });
});
