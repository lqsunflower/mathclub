package com.mathclub.controller;

import com.jfinal.core.ActionKey;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import com.mathclub.model.Account;
import com.mathclub.model.User;
import com.mathclub.service.UploadService;

/**
 * UploadController 上传控制器，接管 ueditor 上传功能
 */
public class UploadController extends BaseController {

	static UploadService srv = UploadService.me;

	User user = new User();
	/**
	 * 接管 ueditor 上传图片服务端
	 *
	 * 1：该 action 与 ueditor.config.js 中的 serverUrl: "/upload/ueditor" 对应
	 *
	 * 2：ueditor 页面加载时会向后端发送 "/xxx?action=config 请求用来获取服务端
	 *    /ueditor-home/jsp/config.json 中的配置，后续的上传将受该配置的影响
	 *
	 * 3：ueditor1_4_3_2-utf8-jsp 版本测试上传图片成功所返回的 json 数据格式如下：
	 *  {
	 *     "state": "SUCCESS",
	 *     "title": "1461249851191086496.png",
	 *     "original": "qr.png",
	 *     "type": ".png",
	 *     "url": "/ueditor/jsp/upload/image/20160421/1461249851191086496.png",
	 *     "size": "58640"
	 *  }
	 *
	 * 4：如果上传出现错误，直接响应如下的 json 即可：
	 *    {"state": "错误信息"}
	 *
	 */
	@ActionKey("/upload:image")
	public void uploadImage() {
		
		/**
		 * uploadType 是通过如代码令 ueditor 在上传图片时通过问号挂参的方式传递过来的自定义参数
		 * ue.ready(function() {
		 *      ue.execCommand('serverparam', {
		 *          'uploadType': 'project'
		 *      });
		 * });
		 */
		/*String uploadType  = getPara("uploadType");
		if (StrKit.isBlank(uploadType)) {
			renderJson("state", "上传类型参数缺失");
			return ;
		}*/
		String uploadType = "image";
		/*if (notLogin()) {
			renderJson("state", "只有登录用户才可以上传文件");
			return ;
		}*/

		UploadFile uploadFile = null;
		try {
			// "upfile" 来自 config.json 中的 imageFieldName 配置项
			uploadFile = getFile("file", UploadService.uploadTempPath, UploadService.imageMaxSize);
			Ret ret = srv.uploadFile(user, uploadType, uploadFile);
			renderJson(ret);
		}
		catch(com.jfinal.upload.ExceededSizeException ex) {
			renderJson(Ret.fail("msg", "上传图片只允许 5M 大小"));
		}
		catch(Exception e) {
			if (uploadFile != null) {
				uploadFile.getFile().delete();
			}
			renderJson(Ret.fail("msg", "上传图片出现未知异常，请告知管理员："));
			LogKit.error(e.getMessage(), e);
		}
	}
	
	//上传视频文件
	@ActionKey("/upload:video")
	public void uploadVideo() {
		String uploadType = "video";
		/*if (notLogin()) {
			renderJson("state", "只有登录用户才可以上传文件");
			return ;
		}*/

		UploadFile uploadFile = null;
		try {
			// "upfile" 来自 config.json 中的 imageFieldName 配置项
			uploadFile = getFile("file", UploadService.uploadTempPath, UploadService.videoMaxSize);
			Ret ret = srv.uploadVideoFile(user, uploadType, uploadFile);
			renderJson(ret);
		}
		catch(com.jfinal.upload.ExceededSizeException ex) {
			renderJson(Ret.fail("msg", "上传视频只允许 20M 大小"));
		}
		catch(Exception e) {
			if (uploadFile != null) {
				uploadFile.getFile().delete();
			}
			renderJson(Ret.fail("msg", "上传视频出现未知异常，请告知管理员："));
			LogKit.error(e.getMessage(), e);
		}
	}

}
