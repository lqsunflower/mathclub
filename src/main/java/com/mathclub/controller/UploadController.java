/**
 * 
 */
package com.mathclub.controller;

import com.jfinal.core.ActionKey;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import com.mathclub.service.UploadService;

/**
 * @author Administrator
 * 上传返回路径
 *
 */
public class UploadController extends BaseController {

	private UploadService uploadService = new UploadService();

	/**
	 * 上传图像到临时目录，发回路径
	 */
	@ActionKey("/uploadFile")
	public void uploadFile() {
		UploadFile file = null;
		file = getFile("file", uploadService.getTempDir(), uploadService.getMaxSize());
		if (file == null) {
			renderJson(Ret.fail("msg", "请先选择上传文件"));
			return;
		}
		Ret ret = uploadService.upload(file);
		if (ret.isOk()) {   // 上传成功则将文件 url 径暂存起来，供下个环节进行裁切
			setSessionAttr("fileUrl", ret.get("fileUrl"));
		}
		renderJson(ret);
	}
}
