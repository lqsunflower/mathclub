/**
 * 
 */
package com.mathclub.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.core.ActionKey;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.model.Major;
import com.mathclub.model.Subject;
import com.mathclub.service.AdminService;

/**
 * @author Administrator
 *
 */
public class AdminController extends BaseController {
	private static Logger log = Logger.getLogger(AdminController.class);
	public static final Major dao = new Major().dao();
	public static final Subject subjectDao = new Subject().dao();
	private AdminService service = new AdminService();

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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("majorId", majorId);
		map.put("name", name);
		Record record = new Record().setColumns(map);
		Db.save("keyPoint", "keyId", record);
		renderJson("ok");
	}

	/**
	 * 添加题目
	 */
	@ActionKey("/subject:addSubject")
	public void addSubject() {
		Subject param = getBean(Subject.class, "");
		String name = param.getName();
		int majorId = param.getMajorId();
		String tags = param.getTags();
		log.info("name=" + name + "majorId=" + majorId + "tags=" + tags + "answer=" + param.getAnswer());

		if (StrKit.isBlank(param.getName()) || StrKit.isBlank(param.getAnswer()) || StrKit.isBlank(param.getPic())) {
			renderJson(Ret.fail("summary", "请求参数为空"));
			return;
		}
		Ret ret = service.addSubject(param);
		renderJson(ret);
	}

	/**
	 * 根据知识点ID获取题目列表
	 */
	@ActionKey("/subject:list")
	public void getSubjectList() {
		log.info("get subject list request majorId =" + getParaToInt("keyId"));
		Page<Subject> subjectList = service.getSubjectListByKeyId(getParaToInt("keyId"));
		renderJson(subjectList);
	}
	
	/**
	 * 根据题目ID获取题目信息
	 */
	@ActionKey("/subject:findById")
	public void getSubjectInfo() {
		log.info("get subject info request subjectId =" + getParaToInt("subjectId") + " keyId=" + getParaToInt("keyId"));
		Subject subject = service.getSubjectInfo(getParaToInt("subjectId"),getParaToInt("keyId"));
		renderJson(subject);
	}
	
}
