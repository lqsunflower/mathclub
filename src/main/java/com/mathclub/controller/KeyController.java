package com.mathclub.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.core.ActionKey;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.kit.StringKit;
import com.mathclub.model.KeyPoint;

public class KeyController extends BaseController {

	private static Logger log = Logger.getLogger(KeyController.class);
	private final KeyPoint keyDao = new KeyPoint().dao();

	/**
	 * 添加知识点
	 */
	@ActionKey("/math:addKey")
	public void addKey() {
		String req = HttpKit.readData(getRequest());
		log.info("req=" + req);
		Map<String, String> map2 = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (map2 == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		
		String name = map2.get("name");
		String majorId = map2.get("majorId");
		log.info("name=" + name + "|majorId=" + majorId);
		if (StrKit.isBlank(name) || Integer.valueOf(majorId) == 0) {
			renderJson(Ret.fail("msg", "请求参数错误"));
			return;
		}
		Record record = new Record().set("name", name).set("majorId", majorId);
		boolean res = Db.save("keypoint", "keyId", record);
		if (res) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("keyId", record.get("keyId"));
			renderJson(Ret.ok("data", map).set("msg", "添加知识点成功"));
			return;
		} else {
			renderJson(Ret.fail("msg", "添加知识点失败"));
			return;
		}
	}

	/**
	 * 添加知识点
	 */
	@ActionKey("/math:updateKey")
	public void updateKey() {
		
		String req = HttpKit.readData(getRequest());
		log.info("req=" + req);
		Map<String, String> param = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (param == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		
		String keyId = param.get("keyId");
		String name = param.get("name");
		log.info("name=" + name + "|keyId=" + keyId);
		if (StrKit.isBlank(name) || StrKit.isBlank(keyId)) {
			renderJson(Ret.fail("msg", "请求参数错误"));
			return;
		}
		int result = Db.update("update keypoint set name=? where keyId=? ", name, keyId);
		if (result > 0) {
			renderJson(Ret.ok("msg", "更新知识点成功"));
			return;
		} else {
			renderJson(Ret.fail("msg", "更新知识点失败"));
			return;
		}
	}

	/**
	 * 删除知识点
	 */
	@ActionKey("/math:deleteKey")
	public void deleteKey() {
		String req = HttpKit.readData(getRequest());
		LogKit.info("math:deleteKey req=" + req);
		Map<String, String> param = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (param == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		
		String keyId = param.get("keyId");
		log.info("|keyId=" + keyId);
		if (StrKit.isBlank(keyId)) {
			renderJson(Ret.fail("msg", "请求参数错误"));
			return;
		}
		int result = Db.update("delete from keypoint where keyId=? ", keyId);
		if (result > 0) {
			renderJson(Ret.ok("msg", "删除知识点成功"));
			return;
		} else {
			renderJson(Ret.fail("msg", "删除知识点失败"));
			return;
		}
	}

	/**
	 * 获取知识点列表根据学科id
	 */
	@ActionKey("/math:listKey")
	public void getKeyList() {
		String majorId = getPara("majorId");
		LogKit.info("math:listKey req majorId=" + majorId);
		if (StrKit.isBlank(majorId)) {
			renderJson(Ret.fail("msg", "请求参数错误"));
			return;
		}
		List<KeyPoint> list = keyDao.find("select * from keypoint where majorId = ?", majorId);
		if (list != null && list.size() > 0) {
			renderJson(Ret.ok("data", list));
		} else {
			renderJson(Ret.fail("msg", "没有知识点"));
		}
	}

}
