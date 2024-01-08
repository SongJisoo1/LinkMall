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
<script src="/js/findPwdFrom.js" defer></script>
<script src="/js/utils/ajax.js" defer></script>
<style>
      .my-form {
        padding: 2em;
       /*  color: #fff; */
        width: 560px;

        margin-inline: auto;
      }

      h1 {
        font-weight: bold;
        text-align: center;
        font-size: 1rem;
        margin-bottom: 50px;
      }

      .form-control {
        /* background-color: inherit;
        color: #fff;
        border: none; */
        border-bottom: 1px solid #fff;
        border-radius: 0;
        padding-left: 0;
      }
      
      .form-label {
      	color: black !important;
      }

      /* .form-control:focus {
        background-color: inherit;
        color: #fff;
        box-shadow: none;
        border-color: #fff;
      } */

      .btn[type="submit"] {
        border-radius: 0;
        width: 100%;
        font-weight: bold;
      }

      p {
        color: gray;
        text-align: center;
      }

      p a {
        text-decoration: none;
      }

      p a:hover {
        color: #fff;
      }
      
      .invalid-message {
      	text-align: center;
      	color: red;
      	margin-bottom: 10px;
      }
      
      .invalid-message {
      	color: red;
      }
    </style> 
</head>
<body>
    <div class="my-form">
      <h1>비밀번호 찾기</h1>
      <form>
        <label for="id" class="form-label">아이디</label>
        <div class="input-group mb-3">
          <input type="text" class="form-control" id="id" name="loginId" />
          <button class="btn btn-outline-secondary ms-3" type="button" id="search-id">
            아이디 검색
          </button>
        </div>
        <div class="result">
        </div>
      </form>
    </div>
  </body>
</html>