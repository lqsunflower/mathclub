/**
 * 
 */
package com.mathclub.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.model.User;

/**
 * @author Administrator
 *
 */
public class UserService {
	public static final User dao = new User().dao();

	public User queryUser(String openid) {
		return dao.findFirst("select * from user where openid=?", openid);
	}

	
	public boolean addUser(String openid, String nickname, String headimgurl) {
		User user = new User();
		user.setOpenid(openid);
		user.setNickname(nickname);
		user.setHeadimgurl(headimgurl);
		Record record = new Record().setColumns(user);
		return Db.save("user", record);
	}
}
