/**
 * 
 */
package com.mathclub.controller;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;
import com.mathclub.common.ImageKit;
import com.mathclub.common.Ret;

/**
 * @author Administrator
 *
 */
public class UploadController extends Controller{

	Ret res = new Ret();
	/**
	 * 上传图像到临时目录，发回路径供 jcrop 裁切
	 */
	public Ret uploadFile() {
		UploadFile file = getFile("fileName");
		String fileName = file.getFileName();
		if (file == null) {
			res.setCode("-1");
			res.setSummary("上传文件UploadFile对象不能为null");
			renderJson(res);
		}

		try {
			if (ImageKit.notImageExtName(fileName)) {
				res.setCode("-1");
				res.setSummary("文件类型不正确，只支持图片类型：gif、jpg、jpeg、png、bmp");
				renderJson(res);
			}
			
			String imageUrl = "/upload/image" +System.currentTimeMillis() + file;
			String saveFile = PathKit.getWebRootPath() + imageUrl;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("code", "S_OK");
			map.put("imageUrl", saveFile);
			renderJson(map);
		}
		catch (Exception e) {
			return Ret.fail("msg", e.getMessage());
		} finally {
			((Object) file).delete();
		}
	}
}
