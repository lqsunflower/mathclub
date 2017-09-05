/**
 * 
 */
package com.mathclub.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.model.User;

/**
 * @author Administrator
 *
 */
public class UserService {
	public static final User dao = new User().dao();

	public User queryUser(String openId) {
		return dao.findFirst("select * from user where openId=? limit 1", openId);
	}
	
	public User queryUserById(int userId) {
		return dao.findFirst("select * from user where userId=? limit 1", userId);
	}

	public boolean addUser(String openid, String nickname, String headimgurl, String ip) {
		if (StrKit.isBlank(openid)) {
			return false;
		}
		/*User user = new User();
		user.setOpenid(openid);
		user.setNickname(nickname);
		user.setHeadimgurl(headimgurl);
		user.setIp(ip);
		user.setCreateTime(new Date());*/
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("openid", openid);
		map.put("nickname", nickname);
		map.put("headimgurl", headimgurl);
		map.put("ip", ip);
		map.put("createTime", new Date());
		LogKit.info("o=" + openid + "|n=" + nickname + "|h=" + headimgurl);
		LogKit.info("i=" + ip + "caret=" + new Date());
		Record u = new Record().setColumns(map);
		return Db.save("user", "userId", u);
	}
}
