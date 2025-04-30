package com.KoreaIT.java.AM_JSP.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.AM_JSP.dao.ArticleDao;

public class ArticleService {
	private Connection conn;
	
	private ArticleDao articleDao;

	public ArticleService(Connection conn) {
		this.conn = conn;
		
		this.articleDao = new ArticleDao(conn);
	}

	public int getTotalCnt() {
		return articleDao.getTotalCnt();
	}

	public List<Map<String, Object>> getArticleRows(int limitFrom, int itemsInAPage) {
		return articleDao.getArticleRows(limitFrom, itemsInAPage);
	}

	public Map<String, Object> getarticleRow(int id) {
		return articleDao.getArticleRow(id);
	}

	public void getDelete(int id) {
		articleDao.getDelete(id);
	}

	public int getDoWrite(String title, String body, int loginedMemberId) {
    	return articleDao.getDoWrite(title, body, loginedMemberId);
	}

	public void getUpdate(int id, String title, String body) {
    	articleDao.getUpdate(id, title, body);
	}
}
