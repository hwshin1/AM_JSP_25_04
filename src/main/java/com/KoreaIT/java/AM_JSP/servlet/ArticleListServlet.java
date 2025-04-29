package com.KoreaIT.java.AM_JSP.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.AM_JSP.util.DBUtil;
import com.KoreaIT.java.AM_JSP.util.SecSql;

@WebServlet("/article/list")
public class ArticleListServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		// DB 연결
 		try {
 			// cj 붙이기 최신은 cj 붙여야 한다.
 			Class.forName("com.mysql.cj.jdbc.Driver");
 		} catch (ClassNotFoundException e) {
 			System.out.println("클래스 x");
 			e.printStackTrace();
 		}
		
		String user = "root";
		String password= "";
		// serverTimezone 넣기 안넣으면 작동 안할수도 있음
		String url = "jdbc:mysql://localhost:3306/AM_JSP_25_04?serverTimezone=Asia/Seoul&useUnicode=true&characterEncoding=utf8";
		
		Connection conn = null;
		
        try {
        	conn = DriverManager.getConnection(url, user, password);
        	response.getWriter().append("연결 성공!");
        	
        	int page = 1;
        	
        	if (request.getParameter("page") != null && request.getParameter("page").length() != 0 ) {
        		page = Integer.parseInt(request.getParameter("page"));
        	}
        	
        	int itemsInAPage = 10;
 			int limitFrom = (page - 1) * itemsInAPage;
 
 			SecSql sql = SecSql.from("SELECT COUNT(*)");
 			sql.append("FROM article;");
 
 			int totalCnt = DBUtil.selectRowIntValue(conn, sql);
 			int totalPage = (int) Math.ceil(totalCnt / (double)itemsInAPage);
        	
        	sql = SecSql.from("SELECT *");
        	sql.append("FROM article");
        	sql.append("ORDER BY id DESC");
        	sql.append("LIMIT ?, ?;", limitFrom, itemsInAPage);
        	
        	List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);
        	
        	HttpSession session = request.getSession();
        	 
 			boolean isLogined = false;
 			int loginedMemberId = -1;
 			Map<String, Object> loginedMember = null;
 
 			if (session.getAttribute("loginedMemberId") != null) {
 				isLogined = true;
 				loginedMemberId = (int) session.getAttribute("loginedMemberId");
 				loginedMember = (Map<String, Object>) session.getAttribute("loginedMember");
 			}
 
 			request.setAttribute("isLogined", isLogined);
 			request.setAttribute("loginedMemberId", loginedMemberId);
 			request.setAttribute("loginedMember", loginedMember);
        	
        	request.setAttribute("page", page);
        	request.setAttribute("articleRows", articleRows);
        	request.setAttribute("totalCnt", totalCnt);
        	request.setAttribute("totalPage", totalPage);
        	
        	request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
        } catch (SQLException e) {
            System.out.println("에러 1 : " + e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
}
