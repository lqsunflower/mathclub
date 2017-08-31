package com.mathclub.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.core.ActionKey;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.kit.StringKit;
import com.mathclub.model.Major;

/**
 * @author lq
 *
 */
public class MajorController extends BaseController {
	private static Logger log = Logger.getLogger(MajorController.class);

	public static final Major majorDao = new Major().dao();

	// 添加学科
	@ActionKey("/math:addMajor")
	public void addMajor() {
		String req = HttpKit.readData(getRequest());
		log.info("req=" + req);
		Map<String, String> map = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (map == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}

		Record record = new Record().set("name", map.get("name"));
		boolean res = Db.save("major", "majorId", record);
		if (res) {
			renderJson(Ret.ok("msg", "添加学科成功").set("majorId", record.get("majorId")));
			return;
		} else {
			renderJson(Ret.fail("msg", "添加学科失败"));
			return;
		}

	}

	// 修改学科
	@ActionKey("/math:updateMajor")
	public void updateMajor() {
		String req = HttpKit.readData(getRequest());
		log.info("req=" + req);
		Map<String, String> map = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (map == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		
		String name = map.get("name");
		String majorId = map.get("majorId");
		log.info("name=" + name + "|majorId=" + majorId);
		if (StrKit.isBlank(name) || StrKit.isBlank(majorId)) {
			renderJson(Ret.fail("msg", "请求参数错误"));
			return;
		}
		int result = Db.update("update major set name=? where majorId=? ", name, majorId);
		if (result > 0) {
			renderJson(Ret.ok("msg", "更新学科成功"));
			return;
		} else {
			renderJson(Ret.fail("msg", "更新学科失败"));
			return;
		}
	}

	// 删除学科
	@ActionKey("/math:deleteMajor")
	public void deleteMajor() {
		String req = HttpKit.readData(getRequest());
		log.info("req=" + req);
		Map<String, String> map = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (map == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		
		String majorId = map.get("majorId");
		log.info("|majorId=" + majorId);
		if (StrKit.isBlank(majorId)) {
			renderJson(Ret.fail("msg", "请求参数错误"));
			return;
		}
		int result = Db.update("delete from major where majorId=?", majorId);
		if (result > 0) {
			renderJson(Ret.ok("msg", "删除学科成功"));
			return;
		} else {
			renderJson(Ret.fail("msg", "删除学科失败"));
			return;
		}
	}

	/**
	 * 获取学科列表
	 */
	@ActionKey("/math:listMajor")
	public void getKeyList() {
		List<Major> list = majorDao.find("select * from major");
		if (list != null && list.size() > 0) {
			renderJson(Ret.ok("data", list));
		} else {
			renderJson(Ret.fail("msg", "没有学科"));
		}
	}

}
