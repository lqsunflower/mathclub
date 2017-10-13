/**
 * 
 */
package com.mathclub.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.SnsApi;
import com.mathclub.kit.IpKit;
import com.mathclub.login.LoginController;
import com.mathclub.login.LoginService;
import com.mathclub.model.Session;
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
public class OauthWeixinController extends Controller
{

    private static Logger log = Logger.getLogger(OauthWeixinController.class);
    OauthWeixinService oanthservice = new OauthWeixinService();
    private UserService userService = new UserService();
    LoginController login = new LoginController();
    private final User userDao = new User().dao();

    @ActionKey("/oauth")
    public void processOauthRequest()
    {
        ApiConfigKit.setThreadLocalAppId(PropKit.get("appId"));
        // ApiConfigKit.putApiConfig(GetApiConfigUtil.getApiConfig());
        // ApiConfigKit.setThreadLocalApiConfig(GetApiConfigUtil.getApiConfig());
        /** 获取授权code */
        String code = getPara("code");
        /** 最终目标地址 */
        String toUrl = getPara("toUrl");
        /** 通过code获取openId值 */
        SnsAccessToken snsAccessToken = oanthservice.getOauthInfo(code);
        String openId = snsAccessToken.getOpenid();
        String accessToken = snsAccessToken.getAccessToken();
        ApiResult userInfo = SnsApi.getUserInfo(accessToken, openId);
        String nickName = userInfo.getStr("nickname");
        String headImgurl = userInfo.getStr("headimgurl");
        User user = null;
        int userId = 0;
        String ip = IpKit.getRealIp(getRequest());
        // 根据openId去查询用户信息
        user = userService.queryUser(openId);
        if (user != null)
        {
            userId = user.toRecord().getInt("userId");
            LogKit.info("user is exist.userId=" + user.toJson());
            LogKit.info("userNickname=" + user.toRecord().getStr("nickName"));
        }
        else
        {
            // 第一次登陆添加用户
            int result = userService.addUser(openId, nickName, headImgurl, ip);
            if (result > 0)
            {
                userId = result;
            }
            LogKit.info("regist user result=" + result + "|result=" + result);
        }

        // 需要检查是否session表里有sessionId是否过期
        Session session = Session.dao.findFirst("select * from session where userId = ?", userId);
        String sessionId = null;
        if (session != null && session.notExpired())
        { // session 存在且没过期
            sessionId = session.get("id");
            //setAttr(LoginService.loginUserCacheName, session.get("id"));
        }
        else
        {
            if (session != null && session.isExpired())
            { // session 已过期
                LogKit.info("删除过期的session");
                session.delete(); // 被动式删除过期数据，此外还需要定时线程来主动清除过期数据
            }
            LogKit.info("新注册kkkkkkkkkkkkkkk");
            Ret ret = login.doLogin(openId, ip);// 自动登录
            if (ret.isOk())
            {
                sessionId = ret.getStr(LoginService.sessionIdName);
               /* setAttr(LoginService.loginUserCacheName,
                    ret.get(LoginService.loginUserCacheName));*/
            }

        }
        String name = null;
        try
        {
            name = URLEncoder.encode(nickName, "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            LogKit.error("encode nickname is error." + nickName);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(PropKit.get("url"));
        sb.append("?u=").append(user.toRecord().getStr("userId"));
        sb.append("&n=").append(name);
        sb.append("&h=").append(headImgurl);
        sb.append("&sessionId=").append(sessionId);
        if (toUrl.equals("50"))
        {
            // 50表示高等数学
            sb.append("&type=subject").append("&name=").append("gaoshu");
            LogKit.info("redirect url=" + sb.toString());
            redirect(sb.toString());
            return;
        }
        else if (toUrl.equals("51"))
        {
            // 51表示线性代数
            sb.append("&type=subject").append("&name=").append("xiandai");
            LogKit.info("redirect url=" + sb.toString());
            redirect(sb.toString());
            return;
        }
        else if (toUrl.equals("52"))
        {
            // 52表示上次浏览
            sb.append("&type=history");
            LogKit.info("redirect url=" + sb.toString());
            redirect(sb.toString());
            return;
        }
        else if (toUrl.equals("53"))
        {
            // 53表示搜索
            sb.append("&type=search");
            LogKit.info("redirect url=" + sb.toString());
            redirect(sb.toString());
            return;
        }
        else if (toUrl.equals("54"))
        {
            // 54：个人信息
            sb.append("&type=user");
            LogKit.info("redirect url=" + sb.toString());
            redirect(sb.toString());
            return;
        }
        else if (toUrl.equals("55"))
        {
            // 55：直播
            sb.append("&type=live");
            LogKit.info("redirect url=" + sb.toString());
            redirect(sb.toString());
            return;
        }
    }
}
