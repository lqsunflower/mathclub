/**
 * 
 */
package com.mathclub.service;

import com.mathclub.model.Session;

/**
 * 获取用户信息根据sessionId
 *
 */
public class SessionService {

	public static Session getUserId(String sessionId) {
		Session user = Session.dao.findFirst("select * from session where id = ?", sessionId);
		return user;
	}

}
