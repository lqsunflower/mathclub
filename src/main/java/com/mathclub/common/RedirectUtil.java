/**
* 文件名：RedirectUtil.java
* 创建日期： 2016年9月1日
* 作者：     wangjin
* Copyright (c) 2009-2016 彩讯科技.邮箱开发室
* All rights reserved.
 
* 修改记录：
* 	1.修改时间：2016年9月1日
* 	修改人：wangjin
* 	修改内容：
*/
package com.mathclub.common;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 功能描述：页面重定向方法公共类
 * 
 * @author lq
 *
 */
public class RedirectUtil {
	private static Logger log = Logger.getLogger(RedirectUtil.class);

	/**
	 * 通过相应参数的传递，重定向到对应的页面
	 * 
	 * @param response
	 * @param url
	 * @param msg
	 */
	public static void redirectPages(HttpServletResponse response,String url, String userNumber, String msg) {
		try {
			response.sendRedirect(url);
			
			log.info("redirect to {} page success, userNumber={}|url={}" + url + msg);
		} catch (Exception e) {
			log.error("redirect to {} page failure!, userNumber={}|url={}" + msg + userNumber);
		}
	}
}
