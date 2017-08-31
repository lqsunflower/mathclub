/**
 * 文件名：SubjectController.java
 * 创建日期： 2017年8月7日
 * 作者：     richinfo
 * Copyright (c) 2009-2017 邮箱开发室
 * All rights reserved.
 */
package com.mathclub.controller;

import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.core.ActionKey;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.mathclub.kit.StringKit;
import com.mathclub.model.Subject;
import com.mathclub.model.TestSubject;
import com.mathclub.service.SubjectService;
import com.mathclub.service.TestService;

/**
 * 功能描述：
 *
 */
public class TestController extends BaseController {

	private static Logger log = Logger.getLogger(TestController.class);
	public static final TestSubject testDao = new TestSubject().dao();
	private TestService testService = new TestService();

	/**
	 * 添加小测
	 */
	@ActionKey("/test:add")
	public void addTestSubject() {
		
		String req = HttpKit.readData(getRequest());
		log.info("req=" + req);
		Map<String, String> param = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (param == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		String name = param.get("name");
		int majorId = Integer.valueOf(param.get("majorId"));
		String subjectIds = param.get("subjectIdList");
		
		log.info("name=" + name + "majorId=" + majorId + "subjectIds=" + subjectIds);

		if (StrKit.isBlank(name) || StrKit.isBlank(subjectIds) || (majorId == 0)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		renderJson(testService.addTestSubject(name, majorId, subjectIds));
	}

	/**
	 * 修改小测
	 */

	@ActionKey("/test:update")
	public void updateSubject() {
		
		String req = HttpKit.readData(getRequest());
		log.info("req=" + req);
		Map<String, String> param = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (param == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		String name = param.get("name");
		int majorId = Integer.valueOf(param.get("majorId"));
		String subjectIds = param.get("subjectIdList");
		int testId = Integer.valueOf(param.get("testId"));
		
		log.info("name=" + name + "majorId=" + majorId + "subjectIds=" + subjectIds + "id=" + testId);

		if (StrKit.isBlank(name) || StrKit.isBlank(subjectIds) || (majorId == 0) || (testId == 0)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		renderJson(testService.update(name, majorId, subjectIds, testId));
	}

	/**
	 * (后台管理)删除题目
	 */
	// @ActionKey("/subject:delete")
	public void deleteSubject() {
		String req = HttpKit.readData(getRequest());
		log.info("req=" + req);
		Map<String, String> param = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (param == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		int subjectId = Integer.valueOf(param.get("subjectId"));
		
		log.info("subjectId=" + subjectId);
		if (subjectId == 0) {
			renderJson(Ret.fail("msg", "请求参数错误"));
			return;
		}
		renderJson(testService.delete(getParaToInt("subjectId")));
	}

	/**
	 * (后台管理)根据题目ID获取题目信息
	 */
	// @ActionKey("/subject:findById")
	public void getSubjectInfoById() {
		log.info("get subject info request subjectId =" + getParaToInt("subjectId"));
		Subject subject = testService.getSubjectInfoById(getParaToInt("subjectId"));
		if (subject != null) {
			renderJson(Ret.ok("sub", subject));
		} else {
			renderJson(Ret.fail("msg", "没有该题目"));
		}
	}

	/**
	 * (后台管理)分页查询题目根据知识点ID 给后台管理页面的接口
	 */
	// @ActionKey("/subject:list")
	public void getSubjectListByPage() {
		log.info("get subject list request majorId =" + getParaToInt("keyId"));
		Page<Subject> subjectList = testService.getSubjectByPage(getParaToInt("keyId"), getParaToInt("p"),
				getParaToInt("s"));
		if (subjectList != null && subjectList.getTotalPage() != 0) {
			renderJson(Ret.ok("list", subjectList));
		} else {
			renderJson(Ret.fail("msg", "没有题目信息"));
		}
	}

}
