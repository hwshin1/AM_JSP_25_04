package com.KoreaIT.java.AM_JSP.dao;

import java.sql.Connection;
import java.util.Map;

import com.KoreaIT.java.AM_JSP.util.DBUtil;
import com.KoreaIT.java.AM_JSP.util.SecSql;

public class MemberDao {
	private Connection conn;
	private SecSql sql;

	public MemberDao(Connection conn) {
		this.conn = conn;
	}
	
	public boolean getCountLoginId(String loginid) {
		sql = SecSql.from("SELECT COUNT(*) AS cnt");
    	sql.append("FROM `member`");
    	sql.append("WHERE loginId = ?;", loginid);
    	
    	return DBUtil.selectRowIntValue(conn, sql) == 0;
	}

	public void getJoin(String loginid, String loginpw, String name) {
		sql = SecSql.from("INSERT INTO `member`");
    	sql.append("SET regDate = NOW(),");
    	sql.append("loginId = ?,", loginid);
    	sql.append("loginPw = ?,", loginpw);
    	sql.append("`name` = ?;", name);
    	
    	DBUtil.insert(conn, sql);
	}

	public Map<String, Object> getDoLoginId(String loginid) {
		sql = SecSql.from("SELECT *");
    	sql.append("FROM `member`");
    	sql.append("WHERE loginId = ?;", loginid);
    	
    	return DBUtil.selectRow(conn, sql);
	}
}
