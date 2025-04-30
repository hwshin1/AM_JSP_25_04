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
import java.util.Map;

import com.KoreaIT.java.AM_JSP.util.DBUtil;
import com.KoreaIT.java.AM_JSP.util.SecSql;

@WebServlet("/s/member/doLogout")
public class MemberDoLogoutServlet extends HttpServlet {
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
        	
        	HttpSession session = request.getSession();
        	session.removeAttribute("loginedMember");
        	session.removeAttribute("loginedMemberId");
        	session.removeAttribute("loginedMemberLoginId");
        	
        	response.getWriter().append(String.format("<script>alert('로그아웃 되었습니다.'); location.replace('../home/main');</script>"));
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
