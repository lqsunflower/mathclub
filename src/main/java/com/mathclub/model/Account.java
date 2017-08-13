/**
 * 
 */
package com.mathclub.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * @author Administrator
 *
 */
public class Account extends Model<Account>{

	private static final long serialVersionUID = -3191132139411674149L;
	private int accountId;//用户ID
	private String userName;//用户头像
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
