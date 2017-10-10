package com.mathclub.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.mathclub.kit.IpKit;
import com.mathclub.login.LoginService;
import com.mathclub.model.Session;
import com.mathclub.model.User;
import com.mathclub.service.SessionService;

/**
 * 基础控制器，方便获取登录信息
 *
 * 注意：
 * 需要 LoginSessionInterceptor 配合，该拦截器使用
 * setAttr("loginAccount", ...) 事先注入了登录账户
 * 否则即便已经登录，该控制器也会认为没有登录
 *
 */
public class BaseController extends Controller {

	private User loginUser = null;

	public User getLoginAccount() {
		if (loginUser == null) {
		    String sessionId = getCookie(LoginService.loginUserCacheName);
			//loginUser = getAttr(LoginService.loginUserCacheName);
			/*if (sessionId != null) {
	            loginAccount = LoginService.me.getLoginAccountWithSessionId(sessionId);
	            if (loginAccount == null) {
	                String loginIp = IpKit.getRealIp(c.getRequest());
	                loginAccount = LoginService.me.loginWithSessionId(sessionId, loginIp);
	            }*/
		
		      Session session = SessionService.getUserId(sessionId);
		      int userId = 0;
		      if (session != null)
		      {
		          userId = session.getUserId();
		      }
		      else
		      {
		          renderJson(Ret.fail("msg", "没有该用户"));
		      }
		}
		return loginUser;
	}

	public boolean isLogin() {
		return getLoginAccount() != null;
	}

	public boolean notLogin() {
		return !isLogin();
	}

	/**
	 * 获取登录账户id
	 * 确保在 FrontAuthInterceptor 之下使用，或者 isLogin() 为 true 时使用
	 * 也即确定已经是在登录后才可调用
	 */
	public int getLoginAccountId() {
		return getLoginAccount().getUserId();
	}
	
}


