/**
 * 
 */
package com.mathclub.model;

import java.util.Date;

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
	private String openId;//用户微信唯一标识
	private String nickName;//用户微信昵称
	private String headImgurl;//用户头像
	private String ip;//用户ip
	private Date createTime;//创建时间
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadImgurl() {
		return headImgurl;
	}
	public void setHeadImgurl(String headImgurl) {
		this.headImgurl = headImgurl;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
