/**
 * 文件名：SubjectService.java
 */
package com.mathclub.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.model.TestSubject;

/**
 * 功能描述：
 *
 */
public class TestService {

	private static Logger log = Logger.getLogger(TestService.class);
	public static final TestSubject testDao = new TestSubject().dao();

	final int pageSize = 1;

	public Ret addTestSubject(String name, int majorId, String subjectIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("majorId", majorId);
		map.put("subjectIds", subjectIds);
		map.put("createTime", new Date());
		map.put("modifyTime", new Date());
		Record record = new Record().setColumns(map);
		boolean result = Db.save("test", "id", record);
		if (result) {
			log.info("succeed to add test subject.name=" + name);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", record.get("id"));
			return Ret.ok("data", data);
		} else {
			log.error("failed to add subject.name=" + name);
			return Ret.fail("msg", "操作数据库失败");
		}

	}

	public Ret update(String name, int majorId, String subjectIds, int testId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("majorId", majorId);
		map.put("subjectIds", subjectIds);
		map.put("id", testId);
		map.put("modifyTime", new Date());
		Record record = new Record().setColumns(map);
		boolean ret = Db.update("test", "id", record);
		if (ret) {
			return Ret.ok("msg", "更新成功");
		} else {
			return Ret.fail("msg", "更新失败");
		}
	}

	public Ret delete(String testIds) {
		String[] para = testIds.split(",");

		for (int i = 0; i < para.length; i++) {
			Db.update("delete from test where id = ?", para[i]);
		}
		/*
		 * int ret = 0; if (ret > 0) { return Ret.ok("msg", "删除成功"); } else {
		 * return Ret.fail("msg", "删除失败"); }
		 */
		return Ret.ok();
	}

	public Ret getListByPage(int page, int rows, String majorId) {
		Page<TestSubject> list = testDao.paginate(page, rows, "select *",
				"from test where majorId=? order by createTime desc", majorId);
		if (list != null && list.getTotalRow() > 0) {
			return Ret.ok("data", list);
		} else {
			return Ret.fail("msg", "没有题目信息");
		}
	}

}
