package com.KoreaIT.java.AM_JSP.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.AM_JSP.util.DBUtil;
import com.KoreaIT.java.AM_JSP.util.SecSql;

public class ArticleDao {
	private Connection conn;
	private SecSql sql;

	public ArticleDao(Connection conn) {
		this.conn = conn;
	}

	public int getTotalCnt() {
		sql = SecSql.from("SELECT COUNT(*)");
		sql.append("FROM article;");
		
		return DBUtil.selectRowIntValue(conn, sql);
	}

	public List<Map<String, Object>> getArticleRows(int limitFrom, int itemsInAPage) {
		sql = SecSql.from("SELECT a.*, m.name");
		sql.append("FROM article AS a");
		sql.append("INNER JOIN `member` AS m");
		sql.append("ON a.memberId = m.id");
    	sql.append("ORDER BY id DESC");
    	sql.append("LIMIT ?, ?;", limitFrom, itemsInAPage);
    	
		return DBUtil.selectRows(conn, sql);
	}

	public Map<String, Object> getArticleRow(int id) {
		sql = SecSql.from("SELECT a.*, m.name");
		sql.append("FROM article AS a");
		sql.append("INNER JOIN `member` AS m");
		sql.append("ON a.memberId = m.id");
		sql.append("WHERE a.id = ?;", id);

		return DBUtil.selectRow(conn, sql);
	}

	public void getDelete(int id) {
		sql = SecSql.from("DELETE");
		sql.append("FROM article");
		sql.append("WHERE id = ?;", id);
		
		DBUtil.delete(conn, sql);
	}

	public int getDoWrite(String title, String body, int loginedMemberId) {
		sql = SecSql.from("INSERT INTO article");
    	sql.append("SET regDate = NOW(),");
    	sql.append("updateDate = NOW(),");
    	sql.append("memberId = ?,", loginedMemberId);
    	sql.append("title = ?,", title);
    	sql.append("`body` = ?;", body);
    	
    	return DBUtil.insert(conn, sql);
	}

	public void getUpdate(int id, String title, String body) {
		sql = SecSql.from("UPDATE article");
    	sql.append("SET updateDate = NOW(),");
    	sql.append("title = ?,", title);
    	sql.append("`body` = ?", body);
    	sql.append("WHERE id = ?;", id);
    	
    	DBUtil.update(conn, sql);
	}
}
