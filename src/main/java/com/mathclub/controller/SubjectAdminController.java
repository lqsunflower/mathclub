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
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.kit.StringKit;
import com.mathclub.model.Subject;
import com.mathclub.service.SubjectService;

/**
 * 功能描述：
 *
 */
public class SubjectAdminController extends BaseController {

	private static Logger log = Logger.getLogger(SubjectAdminController.class);
	public static final Subject subjectDao = new Subject().dao();
	private SubjectService subjectService = new SubjectService();

	/**
	 * 添加题目
	 */
	@ActionKey("/subject:add")
	public void addSubject() {
		String req = HttpKit.readData(getRequest());
		log.info("subject:add req=" + req);
		Map<String, String> param = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (param == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		String name = param.get("name");
		String majorId = param.get("majorId");
		String pic = param.get("pic");
		String apic = param.get("apic");
		log.info("subject:add name=" + name + "majorId=" + majorId + "pic=" + pic + "apic=" + param.get("apic"));

		if (StrKit.isBlank(name) || StrKit.isBlank(pic) || StrKit.isBlank(apic)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		renderJson(subjectService.addSubject(param));
	}

	/**
	 * 修改题目
	 */
	@ActionKey("/subject:update")
	public void updateSubject() {
		String req = HttpKit.readData(getRequest());
		log.info("req=" + req);
		Map<String, String> param = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (param == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		String name = param.get("name");
		String majorId = param.get("majorId");
		String keyId = param.get("keyId");
		log.info("name=" + name + "majorId=" + majorId + "keyId=" + keyId + "answer=" + param.get("answer"));

		if (StrKit.isBlank(name) || Integer.valueOf(param.get("subjectId")) == 0 || StrKit.isBlank(name)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		renderJson(subjectService.update(param));
	}

	/**
	 * (后台管理)删除题目
	 */
	@ActionKey("/subject:delete")
	public void deleteSubject() {

		String req = HttpKit.readData(getRequest());
		log.info("req=" + req);
		Map<String, String> param = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (param == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		String subjectId = param.get("subjectId");

		log.info("subjectId=" + subjectId);
		if (StrKit.isBlank(subjectId) || Integer.valueOf(subjectId) == 0) {
			renderJson(Ret.fail("msg", "请求参数错误"));
			return;
		}
		renderJson(subjectService.delete(Integer.valueOf(subjectId)));
	}

	/**
	 * (后台管理)根据题目ID获取题目信息
	 */
	@ActionKey("/subject:findById")
	public void getSubjectInfoById() {

		String req = HttpKit.readData(getRequest());
		log.info("subject:findById req=" + req);
		Map<String, String> param = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (param == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		String subjectId = param.get("subjectId");

		log.info("get subject info request subjectId =" + subjectId);
		Subject subject = subjectService.getSubjectInfoById(Integer.valueOf(subjectId));
		if (subject != null) {
			renderJson(Ret.ok("data", subject));
		} else {
			renderJson(Ret.fail("msg", "没有该题目"));
		}
	}

	/**
	 * (后台管理)分页查询题目根据知识点ID 给后台管理页面的接口
	 */
	@ActionKey("/subject:list")
	public void getSubjectListByPage() {
		/*
		 * String req = HttpKit.readData(getRequest());
		 * log.info("subject:list req=" + req);
		 */
		String keyId = getPara("keyId"); 
		String page = getPara("page");
		String size = getPara("size");
		LogKit.info("subject:list page=" + page + "--size=" + size);
		if (StrKit.isBlank(page) || StrKit.isBlank(size)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		
		Page<Record> subjectList = null;
		if (StrKit.notBlank(keyId)) {
			subjectList = subjectService.getSubjectByPage(Integer.valueOf(keyId), getPara("name"),
					Integer.valueOf(page), Integer.valueOf(size));
		} else {
			subjectList = subjectService.getSubjectByPage(0, getPara("name"), Integer.valueOf(page),
					Integer.valueOf(size));
		}
		if (subjectList != null && subjectList.getTotalPage() != 0) {
			renderJson(Ret.ok("data", subjectList));
		} else {
			renderJson(Ret.fail("msg", "没有题目信息"));
		}
	}

}
