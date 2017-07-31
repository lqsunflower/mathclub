/**
 * 
 */
package com.mathclub.service;

import org.apache.log4j.Logger;

import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;
import com.mathclub.model.SnsAccessToken;


/**
 * @author Administrator
 *
 */
public class OauthWeixinService {

	private static Logger log = Logger.getLogger(OauthWeixinService.class);
	
	public SnsAccessToken getOauthInfo(String code)
    {
        String appId = PropKit.get("appId");
        String appSecret = PropKit.get("appSecret");
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=").append(appId);
        sb.append("&secret=").append(appSecret).append("&code=").append(code).append("&grant_type=authorization_code");
        String url = sb.toString();
        String json = HttpKit.get(url);
        log.info("result="+ json);
        return new SnsAccessToken(json);
    }
	
}
