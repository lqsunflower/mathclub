package com.mathclub.model;

import com.mathclub.model.base.BaseAccount;

/**
 * Account
 */
public class Account extends BaseAccount<Account> {
	private static final long serialVersionUID = 1L;

	public Account removeSensitiveInfo() {
		remove("password", "salt");
		return this;
	}
}
