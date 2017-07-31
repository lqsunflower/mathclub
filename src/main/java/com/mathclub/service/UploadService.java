/**
 * 
 */
package com.mathclub.service;

import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import com.mathclub.common.ImageKit;

/**
 * @author Administrator
 *
 */
public class UploadService {

	// 经测试对同一张图片裁切后的图片 jpg为3.28KB，而 png 为 33.7KB，大了近 10 倍
	public static final String extName = ".jpg";
	/**
	 * 上传文件，以及上传后立即缩放后的文件暂存目录
	 */
	public String getTempDir() {
		return "/temp/";
	}

	// 用户上传图像最多只允许 1M大小
	public int getMaxSize() {
		return 5 * 1024 * 1024;
	}

	public Ret upload(UploadFile uf) {
		if (uf == null) {
			return Ret.fail("msg", "上传文件UploadFile对象不能为null");
		}
		try {
			if (ImageKit.notImageExtName(uf.getFileName())) {
				return Ret.fail("msg", "文件类型不正确，只支持图片类型：gif、jpg、jpeg、png、bmp");
			}
			String fileUrl = "/upload" + getTempDir() + System.currentTimeMillis() + extName;
			// String saveFile = PathKit.getWebRootPath() + fileUrl;
			// ImageKit.zoom(500, uf.getFile(), saveFile);
			return Ret.ok("fileUrl", fileUrl);
		} catch (Exception e) {
			return Ret.fail("msg", e.getMessage());
		} finally {
			uf.getFile().delete();
		}
	}
}
