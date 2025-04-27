package com.KoreaIT.java.AM_JSP.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.AM_JSP.util.DBUtil;

@WebServlet("/article/detail")
public class ArticleDetailServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append("실행");
		
		// DB 연결
 		try {
 			// cj 붙이기 최신은 cj 붙여야 한다.
 			Class.forName("com.mysql.cj.jdbc.Driver");
 		} catch (ClassNotFoundException e) {
 			System.out.println("클래스 x");
 			e.printStackTrace();
 		}
		
		String user = "root";
		String password= "1234";
		// serverTimezone 넣기 안넣으면 작동 안할수도 있음
		String url = "jdbc:mysql://localhost:3306/AM_JSP_25_04?serverTimezone=Asia/Seoul&useUnicode=true&characterEncoding=utf8";
		
		Connection conn = null;
		
        try {
        	conn = DriverManager.getConnection(url, user, password);
        	response.getWriter().append("연결 성공!");
        	
        	DBUtil dbUtil = new DBUtil(request, response);
        	
        	int id = Integer.parseInt(request.getParameter("id"));
        	 
 			String sql = String.format("SELECT * FROM article WHERE id = %d;", id);
 
 			Map<String, Object> articleRow = dbUtil.selectRow(conn, sql);
        	
        	request.setAttribute("articleRow", articleRow);
        	request.getRequestDispatcher("/jsp/article/detail.jsp").forward(request, response);
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
