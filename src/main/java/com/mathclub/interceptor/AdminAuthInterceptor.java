package com.mathclub.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.mathclub.admin.LoginService;
import com.mathclub.kit.IpKit;
import com.mathclub.model.Account;

import java.util.HashSet;
import java.util.Set;

/**
 * 后台权限管理拦截器
 * 
 * 暂时做成最简单的判断当前用户是否是管理员账号，后续改成完善的 基于用户、角色、权限的权限管理系统，并且实现角色、权限完全动态化配置
 */
public class AdminAuthInterceptor implements Interceptor {

	private static Set<String> adminAccountSet = initAdmin();

	private static Set<String> initAdmin() {
		Set<String> ret = new HashSet<String>();
		String admin = PropKit.get("admin"); // 从配置文件中读取管理员账号，多个账号用逗号分隔
		String[] adminArray = admin.split(",");
		for (String a : adminArray) {
			ret.add(a.trim());
		}
		return ret;
	}

	public static boolean isAdmin(Account loginAccount) {
		return loginAccount != null && adminAccountSet.contains(loginAccount.getUserName());
	}

	public void intercept(Invocation inv) {
		Account loginAccount = null;
		Controller c = inv.getController();
		String sessionId = c.getCookie(LoginService.sessionIdName);
		if (sessionId != null) {
			loginAccount = LoginService.me.getLoginAccountWithSessionId(sessionId);
			if (loginAccount == null) {
				String loginIp = IpKit.getRealIp(c.getRequest());
				loginAccount = LoginService.me.loginWithSessionId(sessionId, loginIp);
			}
			if (isAdmin(loginAccount)) {
				inv.invoke();
			} else {
				inv.getController().renderJson(Ret.fail("msg", "没有管理员权限"));
			}
		} else {
			inv.getController().renderJson(Ret.fail("msg", "没有管理员权限"));
		}
	}
}
