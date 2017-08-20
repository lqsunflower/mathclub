package com.mathclub.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.core.ActionKey;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
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
		String name = getPara("name");
		log.info("name=" + name);
		if (StrKit.isBlank(name)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		Record record = new Record().set("name", name);
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
		String name = getPara("name");
		String majorId = getPara("majorId");
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
		String majorId = getPara("majorId");
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
			renderJson(Ret.ok("majors", list));
		} else {
			renderJson(Ret.ok("msg", "没有学科"));
		}
	}

}
