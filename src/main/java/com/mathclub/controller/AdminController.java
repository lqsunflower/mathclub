/**
 * 
 */
package com.mathclub.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.core.ActionKey;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.model.Major;

/**
 * @author Administrator
 *
 */
public class AdminController extends BaseController {
	private static Logger log = Logger.getLogger(AdminController.class);
	public static final Major dao = new Major().dao();

	// 添加学科
	@ActionKey("/addMajor")
	public void addMajor() {
		String name = getPara("name");
		log.info("name=" + name);
		Record record = new Record().set("name", name);
		Db.save("major", "id", record);
		renderJson("ok");
	}

	/**
	 * 添加知识点
	 */
	@ActionKey("/addKey")
	public void addKey() {
		String name = getPara("name");
		String majorId = getPara("majorId");
		log.info("name=" + name);
		log.info("majorId=" + majorId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("majorId", majorId);
		map.put("name", name);
		Record record = new Record().setColumns(map);
		Db.save("keyPoint", "keyId", record);
		renderJson("ok");
	}
}
