package com.KoreaIT.java.AM_JSP.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

import com.KoreaIT.java.AM_JSP.service.MemberService;
import com.KoreaIT.java.AM_JSP.util.DBUtil;
import com.KoreaIT.java.AM_JSP.util.SecSql;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MemberController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection conn;
	
	private MemberService memberService;

	public MemberController(HttpServletRequest request, HttpServletResponse response, Connection conn) {
		this.request = request;
		this.response = response;
		this.conn = conn;
		
		this.memberService = new MemberService(conn);
	}

	public void join() throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/member/join.jsp").forward(request, response);
	}

	public void doJoin() throws IOException {
		String loginid = request.getParameter("loginId");
    	String loginpw = request.getParameter("loginPw");
    	String name = request.getParameter("name");
    	
    	boolean isJoinableLoginId = memberService.getCountLoginId(loginid);
    	if (isJoinableLoginId == false) {
    		response.getWriter().append(String.format("<script>alert('%s는 이미 사용중 입니다.'); location.replace('../member/join');</script>", loginid));
    	}
    	
    	memberService.getJoin(loginid, loginpw, name);
    	
    	response.getWriter().append(String.format("<script>alert('%s님 회원가입이 되었습니다.'); location.replace('../home/main');</script>", name));
	}

	public void login() throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/member/login.jsp").forward(request, response);
	}

	public void doLogin() throws IOException {
		String loginid = request.getParameter("loginId");
    	String loginpw = request.getParameter("loginPw");
    	
    	Map<String, Object> memberRow = memberService.getdoLoginId(loginid);
    	
    	System.out.println(memberRow);
    	
    	if (memberRow.isEmpty()) {
    		response.getWriter().append(String.format("<script>alert('%s는 없는 아이디 입니다.'); location.replace('../member/login');</script>", loginid));
    		return;
    	}
    	
    	if (memberRow.get("loginPw").equals(loginpw) == false) {
    		response.getWriter().append(String.format("<script>alert('비밀번호가 일치하지 않습니다.'); location.replace('../member/login');</script>"));
    		return;
    	}
    	
    	HttpSession session = request.getSession();
    	session.setAttribute("loginedMember", memberRow);
    	session.setAttribute("loginedMemberId", memberRow.get("id"));
    	session.setAttribute("loginedMemberLoginId", memberRow.get("loginId"));
    	
    	response.getWriter().append(String.format("<script>alert('로그인 되었습니다.'); location.replace('../home/main');</script>"));
	}

	public void doLogout() throws IOException {
		HttpSession session = request.getSession();
		
    	session.removeAttribute("loginedMember");
    	session.removeAttribute("loginedMemberId");
    	session.removeAttribute("loginedMemberLoginId");
    	
    	response.getWriter().append(String.format("<script>alert('로그아웃 되었습니다.'); location.replace('../home/main');</script>"));
	}
}
