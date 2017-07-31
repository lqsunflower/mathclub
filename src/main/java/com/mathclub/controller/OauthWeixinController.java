/**
 * 
 */
package com.mathclub.controller;

import org.apache.log4j.Logger;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.SnsApi;
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
	String url = "http://112.74.44.117:8000/index.html#/searchall";

	@ActionKey("/oauth")
	public void processOauthRequest() {
		/** 获取授权code */
		String code = getPara("code");
		/** 最终目标地址 */
		String toUrl = getPara("toUrl");
		log.info("toUrl=" + toUrl);
		/** 通过code获取openId值 */
		SnsAccessToken snsAccessToken = oanthservice.getOauthInfo(code);
		String openid = snsAccessToken.getOpenid();
		String accessToken = snsAccessToken.getAccessToken();
		ApiResult userInfo = SnsApi.getUserInfo(accessToken, openid);
		String nickname = userInfo.getStr("nickname");
		String headimgurl = userInfo.getStr("headimgurl");
		User user = null;

		// 根据openId去查询用户信息
		user = userService.queryUser(openid);
		if (StrKit.notNull(user)) {
			LogKit.info("user is exist.userId=" + user.getUserId());
			LogKit.info("userNickname=" + user.getNickname());
		} else {
			// 第一次登陆添加用户
			boolean result = userService.addUser(openid, nickname, headimgurl);
			if (result) {
				user = userService.queryUser(openid);
			}
		}

		if (toUrl.equals("1")) {
			StringBuilder sb = new StringBuilder();
			sb.append(url).append("?userId=").append(user.getUserId());
			sb.append("&openid=").append(openid).append("&nickname=").append(nickname);
			sb.append("&headimgurl=").append(headimgurl);
			redirect301(sb.toString());
		}
	
	}
}
