package com.KoreaIT.java.AM_JSP.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.AM_JSP.service.ArticleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ArticleController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection conn;
	
	private ArticleService articleService;

	public ArticleController(HttpServletRequest request, HttpServletResponse response, Connection conn) {
		this.conn = conn;
		this.request = request;
		this.response = response;
		
		this.articleService = new ArticleService(conn);
	}
	
	private boolean isLogined() {
		return request.getSession().getAttribute("loginedMemberId") != null;
	}
	
	private int getLoginedMemberId() {
		return (int) request.getSession().getAttribute("loginedMemberId");
	}
	
	public void showList() throws ServletException, IOException {
		int page = 1;
		
		String pageParam = request.getParameter("page");
    	
    	if (pageParam != null && pageParam.length() != 0 ) {
    		page = Integer.parseInt(pageParam);
    	}
    	
    	int itemsInAPage = 10;
		int limitFrom = (page - 1) * itemsInAPage;

		int totalCnt = articleService.getTotalCnt();
		int totalPage = (int) Math.ceil(totalCnt / (double)itemsInAPage);
    	
    	List<Map<String, Object>> articleRows = articleService.getArticleRows(limitFrom, itemsInAPage);
    	
    	request.setAttribute("page", page);
    	request.setAttribute("articleRows", articleRows);
    	request.setAttribute("totalCnt", totalCnt);
    	request.setAttribute("totalPage", totalPage);
    	
    	request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
	}

	public void showDetail() throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
    	Map<String, Object> articleRow = articleService.getarticleRow(id);
    	
    	request.setAttribute("articleRow", articleRow);
    	request.getRequestDispatcher("/jsp/article/detail.jsp").forward(request, response);
	}

	public void doDelete() throws IOException {
    	if (!isLogined()) {
    		response.getWriter().append(String.format("<script>alert('로그인 하세요.'); location.replace('../article/list');</script>"));
    		return;
    	}
    	
		int id = Integer.parseInt(request.getParameter("id"));
    	
    	Map<String, Object> articleRow = articleService.getarticleRow(id);

		int loginedMemberId = getLoginedMemberId();

		if (loginedMemberId != (int) articleRow.get("memberId")) {
    		response.getWriter().append(String.format("<script>alert('%d번 글에 대한 권한이 없습니다.'); location.replace('list');</script>", id));
    		return;
    	}
		
		articleService.getDelete(id);
		response.getWriter().append(String.format("<script>alert('%d번 글이 삭제되었습니다.'); location.replace('list');</script>", id));
	}

	public void showWrite() throws ServletException, IOException {
		if (!isLogined()) {
    		response.getWriter().append(String.format("<script>alert('로그인 하세요.'); location.replace('../article/list');</script>"));
    		return;
    	}
		
		request.getRequestDispatcher("/jsp/article/write.jsp").forward(request, response);
	}

	public void doWrite() throws IOException {
		if (!isLogined()) {
    		response.getWriter().append(String.format("<script>alert('로그인 하세요.'); location.replace('../article/list');</script>"));
    		return;
    	}
    	
    	String title = request.getParameter("title");
    	String body = request.getParameter("body");
    	int loginedMemberId = getLoginedMemberId();
    	
    	int id = articleService.getDoWrite(title, body, loginedMemberId);
    	response.getWriter().append(String.format("<script>alert('%d번 글이 작성되었습니다.'); location.replace('list');</script>", id));
	}

	public void showModify() throws ServletException, IOException {
		if (!isLogined()) {
    		response.getWriter().append(String.format("<script>alert('로그인 하세요.'); location.replace('../article/list');</script>"));
    		return;
    	}
		
		int id = Integer.parseInt(request.getParameter("id"));
    	
    	Map<String, Object> articleRow = articleService.getarticleRow(id);
    	int loginedMemberId = getLoginedMemberId();
    	
    	if (loginedMemberId != (int) articleRow.get("memberId")) {
    		response.getWriter().append(String.format("<script>alert('%d번 글에 대한 권한이 없습니다.'); location.replace('list');</script>", id));
    		return;
    	}
    	
    	request.setAttribute("articleRow", articleRow);
    	request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response);
	}

	public void doModify() throws IOException {
		if (!isLogined()) {
    		response.getWriter().append(String.format("<script>alert('로그인 하세요.'); location.replace('../article/list');</script>"));
    		return;
    	}
		
		int id = Integer.parseInt(request.getParameter("id"));
    	String title = request.getParameter("title");
    	String body = request.getParameter("body");
    	
    	articleService.getUpdate(id, title, body);
    	response.getWriter().append(String.format("<script>alert('%d번 글이 수정되었습니다.'); location.replace('detail?id=%d');</script>", id, id));
	}
}
