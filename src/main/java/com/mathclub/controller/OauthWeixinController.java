/**
 * 
 */
package com.mathclub.controller;

import org.apache.log4j.Logger;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.SnsApi;
import com.mathclub.kit.IpKit;
import com.mathclub.login.LoginController;
import com.mathclub.login.LoginService;
import com.mathclub.model.SnsAccessToken;
import com.mathclub.model.User;
import com.mathclub.service.OauthWeixinService;
import com.mathclub.service.UserService;

/**
 * 微信授权类
 * 
 * @author Administrator
 *
 */
public class OauthWeixinController extends Controller {

	private static Logger log = Logger.getLogger(OauthWeixinController.class);
	OauthWeixinService oanthservice = new OauthWeixinService();
	UserService userService = new UserService();
	LoginController login = new LoginController();

	@ActionKey("/oauth")
	public void processOauthRequest() {
		/** 获取授权code */
		String code = getPara("code");
		/** 最终目标地址 */
		String toUrl = getPara("toUrl");
		log.info("toUrl=" + toUrl);
		/** 通过code获取openId值 */
		SnsAccessToken snsAccessToken = oanthservice.getOauthInfo(code);
		String openId = snsAccessToken.getOpenid();
		String accessToken = snsAccessToken.getAccessToken();
		ApiResult userInfo = SnsApi.getUserInfo(accessToken, openId);
		String nickName = userInfo.getStr("nickname");
		String headImgurl = userInfo.getStr("headimgurl");
		User user = null;
		String ip = IpKit.getRealIp(getRequest());
		// 根据openId去查询用户信息
		user = userService.queryUser(openId);
		if (user != null) {
			LogKit.info("user is exist.userId=" + user.toJson());
			LogKit.info("userNickname=" + user.toRecord().getStr("nickName"));
		} else {
			// 第一次登陆添加用户
			boolean result = userService.addUser(openId, nickName, headImgurl, ip);
			LogKit.info("regist user openid=" + openId + "|result=" + result);
		}

		Ret ret = login.doLogin(openId, ip);// 自动登录
		if (ret.isOk()) {
			String sessionId = ret.getStr(LoginService.sessionIdName);
			int maxAgeInSeconds = ret.getInt("maxAgeInSeconds");
			setCookie(LoginService.sessionIdName, sessionId, maxAgeInSeconds, true);
			setAttr(LoginService.loginUserCacheName, ret.get(LoginService.loginUserCacheName));
		}
		
		if (toUrl.equals("1")) {
			StringBuilder sb = new StringBuilder();
			sb.append(PropKit.get("url"));
			sb.append("?o=").append(openId).append("&n=").append(nickName);
			sb.append("&h=").append(headImgurl);
			redirect(sb.toString());
			//redirect301(sb.toString());
		}

	}
}
