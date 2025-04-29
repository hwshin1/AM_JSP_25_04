<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login 창</title>
</head>
<body>
	<h2>로그인</h2>

	<a href="../home/main">뒤로가기</a>

	<script type="text/javascript">
 		function LoginForm__submit(form) {
 			let loginId = form.loginId.value.trim();
 			let loginPw = form.loginPw.value.trim();
 
 			if (loginId.length == 0) {
 				alert('아이디 쓰기!');
 				form.loginId.focus();
 				return;
 			}
 			if (loginPw.length == 0) {
 				alert('비번 쓰기!');
 				form.loginPw.focus();
 				return;
 			}
 			
 			form.submit();
 		}
 	</script>
 	
	<form onsubmit="LoginForm__submit(this); return false;" action="doLogin" method="POST">
		<div>
			아이디 :<input autocomplete="off" type="text" placeholder="아이디 입력" name="loginId"/>
		</div>
		<div>
			비밀번호 : <input autocomplete="off" type="text" placeholder="비번 입력" name="loginPw"/>
		</div>
		
		<button type="submit">확인</button>
	</form>
</body>
</html>
