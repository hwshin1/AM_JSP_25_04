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

import com.KoreaIT.java.AM_JSP.controller.ArticleController;
import com.KoreaIT.java.AM_JSP.controller.HomeController;
import com.KoreaIT.java.AM_JSP.util.DBUtil;
import com.KoreaIT.java.AM_JSP.util.SecSql;

@WebServlet("/s/*")
public class DispatcherServlet extends HttpServlet {
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
    		
    		String requestUri = request.getRequestURI();
    		
    		System.out.println(requestUri);
    		
    		String[] reqUriBits = requestUri.split("/");
    		
    		if (reqUriBits.length < 5) {
    			response.getWriter().append(String.format("<script>alert('올바른 요청이 아닙니다.'); location.replace('../home/main');</script>"));
    			return;
    		}
        	
    		String controllerName = reqUriBits[3];
    		String actionMethodName = reqUriBits[4];
    		
    		if (controllerName.equals("home")) {
    			HomeController homeController = new HomeController(request, response);
    			homeController.showMain();
    		} else if (controllerName.equals("article")) {
    			ArticleController articleController = new ArticleController(request, response, conn);
    			
    			switch (actionMethodName) {
					case "list": {
						articleController.showList();
						break;
					}
					case "detail": {
						articleController.showDetail();
						break;
					}
					case "doDelete": {
						articleController.doDelete();
						break;
					}
					case "write": {
						articleController.showWrite();
						break;
					}
					case "doWrite": {
						articleController.doWrite();
						break;
					}
					case "modify": {
						articleController.showModify();
						break;
					}
					case "doModify": {
						articleController.doModify();
						break;
					}
    			}
    		}
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
