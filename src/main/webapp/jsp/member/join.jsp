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
	
	<!-- 입력을 잘 했는지 체크 -->
	<script type="text/javascript">
	</script>
	
	<form action="doJoin" method="POST">
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
