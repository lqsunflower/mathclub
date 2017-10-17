/**
 * 
 */
package com.mathclub.controller;

import com.jfinal.plugin.activerecord.Db;
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

	@Override
	protected void processInFollowEvent(InFollowEvent inFollowEvent) {
		if (InFollowEvent.EVENT_INFOLLOW_SUBSCRIBE.equals(inFollowEvent.getEvent())) {
			OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
			outMsg.setContent(textMsg);
			render(outMsg);
		}
		if (InFollowEvent.EVENT_INFOLLOW_UNSUBSCRIBE.equals(inFollowEvent.getEvent())) {
		    Db.update("delete from user where openId = ?",inFollowEvent.getFromUserName());//取消关注删除用户表内容
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
