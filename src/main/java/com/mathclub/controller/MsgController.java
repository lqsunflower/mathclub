/**
 * 
 */
package com.mathclub.controller;

import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.jfinal.MsgControllerAdapter;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.in.event.InFollowEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;

/**
 * @author lq
 *
 */
public class MsgController extends MsgControllerAdapter {

	// 关注回复文本内容
	private static final String textMsg = "你好，欢迎关注数村俱乐部！";

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
	
	
	@Override
	protected void processInFollowEvent(InFollowEvent inFollowEvent) {
		if (InFollowEvent.EVENT_INFOLLOW_SUBSCRIBE.equals(inFollowEvent.getEvent())) {
			OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
			outMsg.setContent(textMsg);
			render(outMsg);
		}
		if (InFollowEvent.EVENT_INFOLLOW_UNSUBSCRIBE.equals(inFollowEvent.getEvent())) {
			renderText("success");
		}

	}

	@Override
	protected void processInMenuEvent(InMenuEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void processInTextMsg(InTextMsg arg0) {
		// TODO Auto-generated method stub

	}

}
