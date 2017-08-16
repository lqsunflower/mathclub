/**
 * 
 */
package com.mathclub.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 用户
 * @author Administrator
 *
 */
public class User extends Model<User>{

	public static final User dao = new User().dao();
	
	private static final long serialVersionUID = 9147757333807425162L;
	private int userId;//用户ID
	private String openid;//用户微信唯一标识
	private String nickname;//用户微信昵称
	private String headimgurl;//用户头像
	private String ip;//用户ip
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
