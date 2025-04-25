package com.KoreaIT.java.AM_JSP;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/article/list")
public class ArticleListServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append("실행");
		
		// DB 연결
 		try {
 			Class.forName("com.mysql.cj.jdbc.Driver");
 		} catch (ClassNotFoundException e) {
 			System.out.println("클래스 x");
 			e.printStackTrace();
 		}
		
		String user = "root";
		String password= "1234";
		String url = "jdbc:mysql://localhost:3306/JDBC?serverTimezone=Asia/Seoul&useUnicode=true&characterEncoding=utf8";
		
		Connection conn = null;
		
        try {
        	conn = DriverManager.getConnection(url, user, password);
        	response.getWriter().append("연결 성공!");
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