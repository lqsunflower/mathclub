package com.mathclub.login;

import com.jfinal.aop.Clear;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;

/**
 * 登录控制器
 */
public class LoginController extends Controller {

	static final LoginService srv = LoginService.me;
	
	/**
	 * 显示登录界面
	 */
	public void index() {
		keepPara("returnUrl");  // 保持住 returnUrl 这个参数，以便在登录成功后跳转到该参数指向的页面
		render("index.html");
	}

	/**
	 * 登录
	 */
	//@Before(LoginValidator.class)
	public Ret doLogin(String openId,String loginIp) {
		return srv.login(openId, false, loginIp);
	}

	/**
	 * 退出登录
	 */
	@Clear
	@ActionKey("/logout")
	public void logout() {
		srv.logout(getCookie(LoginService.sessionIdName));
		removeCookie(LoginService.sessionIdName);
		redirect("/");
	}

	public void captcha() {
		renderCaptcha();
	}
	

}

