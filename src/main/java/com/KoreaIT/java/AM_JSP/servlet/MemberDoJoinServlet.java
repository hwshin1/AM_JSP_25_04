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

import com.KoreaIT.java.AM_JSP.util.DBUtil;
import com.KoreaIT.java.AM_JSP.util.SecSql;

@WebServlet("/member/doJoin")
public class MemberDoJoinServlet extends HttpServlet {
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
        	
        	String loginid = request.getParameter("loginId");
        	String loginpw = request.getParameter("loginPw");
        	String name = request.getParameter("name");
        	String loginpwconfirm = request.getParameter("loginPwCon");
        	
        	SecSql sql = SecSql.from("SELECT *");
        	sql.append("FROM `member`");
        	sql.append("WHERE loginId = ?,", loginid);
        	
        	
        	
        	sql = SecSql.from("INSERT INTO `member`");
        	sql.append("SET regDate = NOW(),");
        	sql.append("loginId = ?,", loginid);
        	sql.append("loginPw = ?,", loginpw);
        	sql.append("`name` = ?;", name);
        	
        	DBUtil.insert(conn, sql);
        	
        	response.getWriter().append(String.format("<script>alert('%s님 회원가입이 되었습니다.'); location.replace('../home/main');</script>", name));
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
