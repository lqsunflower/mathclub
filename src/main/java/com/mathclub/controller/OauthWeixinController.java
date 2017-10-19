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
import com.jfinal.weixin.sdk.api.ApiConfig;
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

    /**
     * 如果要支持多公众账号，只需要在此返回各个公众号对应的  ApiConfig 对象即可
     * 可以通过在请求 url 中挂参数来动态从数据库中获取 ApiConfig 属性值
     */
    public ApiConfig getApiConfig() {
        ApiConfig ac = new ApiConfig();
        
        // 配置微信 API 相关常量
        ac.setToken(PropKit.get("token"));
        ac.setAppId(PropKit.get("appId"));
        ac.setAppSecret(PropKit.get("appSecret"));
        
        /**
         *  是否对消息进行加密，对应于微信平台的消息加解密方式：
         *  1：true进行加密且必须配置 encodingAesKey
         *  2：false采用明文模式，同时也支持混合模式
         */
        ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
        ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
        return ac;
    }
    
    
    
    @ActionKey("/oauth")
    public void index()
    {
        //ApiConfigKit.setThreadLocalAppId(PropKit.get("appId"));
        //ApiConfigKit.putApiConfig(GetApiConfigUtil.getApiConfig());
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
        long userId = 0;
        String ip = IpKit.getRealIp(getRequest());
        // 根据openId去查询用户信息
        user = userService.queryUser(openId);
        if (user != null)
        {
            userId = user.toRecord().getInt("userId");
            log.info("user is exist.userId=" + user.toJson());
        }
        else
        {
            // 第一次登陆添加用户
            long result = userService.addUser(openId, nickName, headImgurl, ip);
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
                session.delete(); // 被动式删除过期数据，此外还需要定时线程来主动清除过期数据
            }
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
        sb.append("?u=").append(userId);
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
