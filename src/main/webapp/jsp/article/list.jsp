<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
List<Map<String, Object>> articleRows = (List<Map<String, Object>>)request.getAttribute("articleRows");

int cPage = (int) request.getAttribute("page");
int totalCnt = (int) request.getAttribute("totalCnt");
int totalPage = (int) request.getAttribute("totalPage");

boolean isLogined = (boolean) request.getAttribute("isLogined");
int loginedMemberId = (int) request.getAttribute("loginedMemberId");
Map<String, Object> loginedMember = (Map<String, Object>) request.getAttribute("loginedMember");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
<style>
table>thead>tr>th, table>tbody>tr>td {
 	padding: 5px;
}
</style>
</head>
<body>
	<h2>게시글 목록</h2>

	<a href="../home/main">메인으로 이동</a>
	
	<%@ include file="../part/top_bar.jspf" %>
	
	<div>
		총 게시글 갯수 :
		<%=totalCnt %>
	</div>

	<table style="border-collapse: collapse; border-color: green;"
		border="1px">
		<thead>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>수정날짜</th>
				<th>제목</th>
				<th>내용</th>
				<th>수정</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Map<String, Object> articleRow : articleRows) {
			%>
			<tr style="text-align: center;">
				<td><%=articleRow.get("id")%>번</td>
				<td><%=articleRow.get("regDate")%></td>
				<td><%=articleRow.get("updateDate")%></td>
				<td><a href="detail?id=<%=articleRow.get("id")%>"><%=articleRow.get("title")%></a></td>
				<td><%=articleRow.get("body")%></td>
				<td><a href="modify?id=<%=articleRow.get("id")%>">수정</a></td>
				<td><a
					onclick="if ( confirm('정말 삭제하시겠습니까?') == false ) { return false; }"
					href="doDelete?id=<%=articleRow.get("id")%>">삭제</a></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
	
	<style type="text/css">
	.page {
		font-size: 1.4rem;
	}
	
	.page > a {
		color: black;
		text-decoration: none;
	}
	
	.page > a.cPage {
		color: red;
		text-decoration: underline;
	}
	</style>
	
	<div class="page">
		<%
		for (int i = 1; i <= totalPage; i++) {
		%>
		<a class="<%=cPage == i ? "cPage" : ""%>" href="list?page=<%=i%>"><%=i%></a>
		<%
		}
		%>
	</div>
</body>
</html>
