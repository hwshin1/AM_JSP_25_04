package com.KoreaIT.java.AM_JSP.service;

import java.sql.Connection;
import java.util.Map;

import com.KoreaIT.java.AM_JSP.dao.MemberDao;

public class MemberService {
	private Connection conn;
	
	private MemberDao memberDao;

	public MemberService(Connection conn) {
		this.conn = conn;
		this.memberDao = new MemberDao(conn);
	}

	public boolean getCountLoginId(String loginid) {
    	return memberDao.getCountLoginId(loginid);
	}

	public void getJoin(String loginid, String loginpw, String name) {
    	memberDao.getJoin(loginid, loginpw, name);
	}

	public Map<String, Object> getdoLoginId(String loginid) {
    	return memberDao.getDoLoginId(loginid);
	}
}
