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
import java.util.Map;

import com.KoreaIT.java.AM_JSP.util.DBUtil;
import com.KoreaIT.java.AM_JSP.util.SecSql;

@WebServlet("/article/detail")
public class ArticleDetailServlet extends HttpServlet {
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
        	
        	int id = Integer.parseInt(request.getParameter("id"));
        	 
        	SecSql sql = SecSql.from("SELECT a.*, m.name");
 			sql.append("FROM article AS a");
 			sql.append("INNER JOIN `member` AS m");
 			sql.append("ON a.memberId = m.id");
 			sql.append("WHERE a.id = ?;", id);
 
 			Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);
        	
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
