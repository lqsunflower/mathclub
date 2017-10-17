/**
 * 文件名：GetApiConfigUtil.java
 * 创建日期： 2016年9月1日
 * 作者：     wangjin
 * Copyright (c) 2009-2016 彩讯科技.邮箱开发室
 * All rights reserved.
 
 * 修改记录：
 * 	1.修改时间：2016年9月1日
 * 	修改人：wangjin
 * 	修改内容：
 */
package com.mathclub.kit;

import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;

public class GetApiConfigUtil
{
    public static ApiConfig getApiConfig()
    {
        ApiConfig ac = new ApiConfig();

        // 配置微信 API 相关常量
        ac.setToken(PropKit.get("token"));
        ac.setAppId(PropKit.get("appId"));
        ac.setAppSecret(PropKit.get("appSecret"));

        /**
         * 是否对消息进行加密，对应于微信平台的消息加解密方式： 1：true进行加密且必须配置 encodingAesKey
         * 2：false采用明文模式，同时也支持混合模式
         */
        ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
        ac.setEncodingAesKey(PropKit.get("encodingAesKey",
            "setting it in config file"));
        return ac;
    }
}
