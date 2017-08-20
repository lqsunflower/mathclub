package com.mathclub.kit;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jfinal.kit.StrKit;

/**
 * ImageKit 图片高保真缩放与裁剪，不依赖于任何第三方库
 */
public class ImageKit {

	private final static String[] imgExts = new String[]{"jpg", "jpeg", "png", "bmp"};

	public static String getExtName(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index != -1 && (index + 1) < fileName.length()) {
			return fileName.substring(index + 1);
		} else {
			return null;
		}
	}

	/**
	 * 通过文件扩展名，判断是否为支持的图像文件，支持则返回 true，否则返回 false
	 */
	public static boolean isImageExtName(String fileName) {
		if (StrKit.isBlank(fileName)) {
			return false;
		}
		fileName = fileName.trim().toLowerCase();
		String ext = getExtName(fileName);
		if (ext != null) {
			for (String s : imgExts) {
				if (s.equals(ext)) {
					return true;
				}
			}
		}
		return false;
	}

	public static final boolean notImageExtName(String fileName) {
		return ! isImageExtName(fileName);
	}

	public static BufferedImage loadImageFile(String sourceImageFileName) {
		if (notImageExtName(sourceImageFileName)) {
			throw new IllegalArgumentException("只支持如下几种类型的图像文件：jpg、jpeg、png、bmp");
		}

		File sourceImageFile = new File(sourceImageFileName);
		if (!sourceImageFile.exists() || !sourceImageFile.isFile()) {
			throw new IllegalArgumentException("文件不存在");
		}

		try {
			return ImageIO.read(sourceImageFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对图片进行剪裁，只保存选中的区域
	 * @param sourceImageFile 原图
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 */
	public static BufferedImage crop(String sourceImageFile, int left, int top, int width, int height) {
		if (notImageExtName(sourceImageFile)) {
			throw new IllegalArgumentException("只支持如下几种类型的图像文件：jpg、jpeg、png、bmp");
		}

		try {
			BufferedImage bi = ImageIO.read(new File(sourceImageFile));
			width = Math.min(width, bi.getWidth());
			height = Math.min(height, bi.getHeight());
			if(width <= 0) width = bi.getWidth();
			if(height <= 0) height = bi.getHeight();

			left = Math.min(Math.max(0, left), bi.getWidth() - width);
			top = Math.min(Math.max(0, top), bi.getHeight() - height);

			BufferedImage subImage = bi.getSubimage(left, top, width, height);
			return subImage;	// return ImageIO.write(bi, "jpeg", fileDest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void save(BufferedImage bi, String outputImageFile) {
		FileOutputStream newImage = null;
		try {
			// ImageIO.write(bi, "jpg", new File(outputImageFile));
			ImageIO.write(bi, getExtName(outputImageFile), new File(outputImageFile));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (newImage != null) {
				try {
					newImage.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 高保真缩放
	 */
	public static BufferedImage resize(BufferedImage bi, int toWidth, int toHeight) {
		Graphics g = null;
		try {
			// 从 BufferedImage 对象中获取一个经过缩放的 image
			Image scaledImage = bi.getScaledInstance(toWidth, toHeight, Image.SCALE_SMOOTH);
			// 创建 BufferedImage 对象，存放缩放过的 image
			BufferedImage ret = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
			g = ret.getGraphics();
			g.drawImage(scaledImage, 0, 0, null);
			return ret;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (g != null) {
				g.dispose();
			}
		}
	}

}


