<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	<h2>회원가입</h2>
	
	<a href="../home/main">뒤로 가기</a>
	
	<script type="text/javascript">
 		function JoinForm__submit(form) {
 			console.log('form.loginId.value : ' + form.loginId.value);
 			console.log('form.loginId.value.trim() : '
 					+ form.loginId.value.trim());
 			console.log('form.loginPw.value : ' + form.loginPw.value);
 			console.log('form.loginPwCon.value : '
 					+ form.loginPwCon.value);
 			console.log('form.name.value : ' + form.name.value);
 
 			let loginId = form.loginId.value.trim();
 			let loginPw = form.loginPw.value.trim();
 			let loginPwCon = form.loginPwCon.value.trim();
 
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
 
 			if (loginPwCon.length == 0) {
 				alert('비번 확인 쓰기!');
 				form.loginPwCon.focus();
 				return;
 			}
 
 			if (loginPw != loginPwCon) {
 				alert('비번이 맞지 않아요.');
 				return;
 			}
 
 			if (form.name.value.trim().length == 0) {
 				alert('이름 쓰기!');
 				form.name.focus();
 				return;
 			}
 
 			form.submit();
 		}
 	</script>
	
	<form onsubmit="JoinForm__submit(this); return false;" action="doJoin" method="POST">
		<div>
			아이디 : <input autocomplete="off" type="text" placeholder="아이디 입력" name="loginId"/>
		</div>
		<div>
			비밀번호 : <input autocomplete="off" type="text" placeholder="비밀번호 입력" name="loginPw"/>
		</div>
		<div>
			비밀번호 확인 : <input autocomplete="off" type="text" placeholder="비밀번호 확인" name="loginPwCon"/>
		</div>
		<div>
			닉네임 : <input autocomplete="off" type="text" placeholder="닉네임 입력" name="name"/>
		</div>
		
		<button type="submit">가입</button>
	</form>
</body>
</html>
