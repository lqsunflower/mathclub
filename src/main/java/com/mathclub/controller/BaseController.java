package com.mathclub.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.mathclub.login.LoginService;
import com.mathclub.model.User;

/**
 * 基础控制器，方便获取登录信息
 *
 * 注意： 需要 LoginSessionInterceptor 配合，该拦截器使用 setAttr("loginAccount", ...)
 * 事先注入了登录账户 否则即便已经登录，该控制器也会认为没有登录
 *
 */
public class BaseController extends Controller
{
    private User loginUser = null;

    public User getLoginUser()
    {
        if (loginUser == null)
        {
            String sessionId = getCookie(LoginService.sessionIdName);
            LogKit.info("base controller sessionId=——————" + sessionId);
            if (StrKit.isBlank(sessionId))
            {
                return null;
            }
            loginUser = LoginService.me.getLoginAccountWithSessionId(sessionId);
            // 缓存失效,从数据库中查找用户
            if (loginUser == null)
            {
                loginUser = LoginService.me.loginWithSessionId(sessionId, null);
            }
        }
        return loginUser;
    }

    public boolean isLogin()
    {
        return getLoginUser() != null;
    }

    public boolean notLogin()
    {
        return !isLogin();
    }

    /**
     * 获取登录账户id 确保在 FrontAuthInterceptor 之下使用，或者 isLogin() 为 true 时使用
     * 也即确定已经是在登录后才可调用
     */
    public int getLoginAccountId()
    {
        return getLoginUser().getUserId();
    }

}
