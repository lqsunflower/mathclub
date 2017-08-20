/**
 * 文件名：SubjectController.java
 * 创建日期： 2017年8月7日
 * 作者：     richinfo
 * Copyright (c) 2009-2017 邮箱开发室
 * All rights reserved.
 */
package com.mathclub.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.core.ActionKey;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.mathclub.model.Subject;
import com.mathclub.model.SubjectVo;
import com.mathclub.service.SubjectService;

/**
 * 功能描述：
 *
 */
public class SubjectController extends BaseController {

	private static Logger log = Logger.getLogger(SubjectController.class);
	public static final Subject subjectDao = new Subject().dao();
	private SubjectService subjectService = new SubjectService();

	/**
	 * 添加题目
	 */
	@ActionKey("/subject:add")
	public void addSubject() {
		Subject param = getBean(Subject.class, "");
		String name = param.getName();
		int majorId = param.getMajorId();
		String tags = param.getTags();
		log.info("name=" + name + "majorId=" + majorId + "tags=" + tags + "answer=" + param.getAnswer());

		if (StrKit.isBlank(param.getName()) || StrKit.isBlank(param.getAnswer()) || StrKit.isBlank(param.getPic())) {
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
		Subject param = getBean(Subject.class, "");
		String name = param.getName();
		int majorId = param.getMajorId();
		String tags = param.getTags();
		log.info("name=" + name + "majorId=" + majorId + "tags=" + tags + "answer=" + param.getAnswer());

		if (param.getSubjectId() == 0 || StrKit.isBlank(param.getName())
				|| StrKit.isBlank(param.getAnswer()) || StrKit.isBlank(param.getPic())) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		renderJson(subjectService.update(param));
	}

	/**
	 * 根据题目ID获取题目信息
	 */
	// @ActionKey("/subject:findById")
	/*
	 * public void getSubjectInfo() {
	 * log.info("get subject info request subjectId =" +
	 * getParaToInt("subjectId") + " keyId=" + getParaToInt("keyId")); Subject
	 * subject = service.getSubjectInfo(getParaToInt("subjectId"),
	 * getParaToInt("keyId")); renderJson(subject); }
	 */

	/**
	 * 点赞
	 */
	@ActionKey("/subject:likeSubject")
	public void likeSubject() {
		log.info("like subject info request userId=" + getParaToInt("userId") + " subjectId ="
				+ getParaToInt("subjectId") + " type=" + getParaToInt("type"));
		int type = getParaToInt("type");
		if (type == 1 || type == 2) {
			boolean result = subjectService.checkUserExists(getParaToInt("userId"), getParaToInt("subjectId"),
					getParaToInt("type"));
			if (result) {
				renderJson(Ret.fail("summary", "该用户已经点赞或点跪"));
				return;
			}
		}
		boolean res = subjectService.like(getParaToInt("userId"), getParaToInt("subjectId"), getParaToInt("type"));
		if (res) {
			renderJson(Ret.ok());
		} else {
			renderJson(Ret.fail("summary", "数据库操作失败"));
		}

	}

	/**
	 * 搜索根据知识点名字获取题目信息
	 */
	@ActionKey("/subject:searchSubject")
	public void getSubjectListByName() {
		log.info("search subject list request name =" + getPara("name"));
		List<Subject> subjectList = subjectService.getSubjectListByName(getPara("name"));
		if (subjectList != null && subjectList.size() > 0) {
			renderJson(Ret.create("status", "ok").set("subjectList", subjectList));
		} else {
			renderJson(Ret.ok("summary", "没有题目信息"));
		}

	}

	/**
	 * 搜索根据题目ID获取题目信息
	 */
	@ActionKey("/subject:getSubjectInfoBySubjectId")
	public void getSubjectInfo() {
		log.info("get subject info request name =" + getPara("subjectId"));
		SubjectVo subject = subjectService.getSubjectInfo(getParaToInt("subjectId"), getParaToInt("userId"));
		if (subject != null) {
			renderJson(subject);
		} else {
			renderJson("msg", "没有该题目");
		}
	}

	/**
	 * 搜索根据题目ID获取题目信息
	 */
	@ActionKey("/subject:querySubjectInfoByPage")
	public void querySubjectInfoByPage() {
		log.info("page query subject info request name =" + getPara("keyId"));
		Ret ret = subjectService.querySubjectInfo(getParaToInt("userId"), getParaToInt("keyId"), getParaToInt("p", 1));
		renderJson(ret);
	}

	
	
	/**
	 * 根据知识点ID获取题目列表
	 */
	@ActionKey("/subject:list")
	public void getSubjectList() {
		log.info("get subject list request majorId =" + getParaToInt("keyId"));
		Page<Subject> subjectList = subjectService.getSubjectListByKeyId(getParaToInt("keyId"));
		renderJson(subjectList);
	}
	
	/**
	 * 根据题目ID获取题目信息
	 */
	/*@ActionKey("/subject:findById")
	public void getSubjectInfo() {
		log.info("get subject info request subjectId =" + getParaToInt("subjectId") + " keyId=" + getParaToInt("keyId"));
		Subject subject = service.getSubjectInfo(getParaToInt("subjectId"),getParaToInt("keyId"));
		renderJson(subject);
	}*/
}
