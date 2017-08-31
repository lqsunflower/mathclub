package com.mathclub.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.core.ActionKey;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.mathclub.kit.StringKit;
import com.mathclub.model.Subject;
import com.mathclub.service.SubjectService;

/**
 * 功能描述：
 *
 */
public class UserController extends BaseController {
	private static Logger log = Logger.getLogger(UserController.class);
	private SubjectService subjectService = new SubjectService();

	/**
	 * 分页获取题目信息(包括用户的信息，点赞和点跪情况)
	 */
	@ActionKey("/subject:querySubjectInfoByPage")
	public void querySubjectInfoByPage() {
		log.info("page query subject info request name =" + getPara("keyId"));
		Ret ret = subjectService.querySubjectInfo(getParaToInt("userId"), getParaToInt("keyId"), getParaToInt("p", 1),
				getParaToInt("size"));
		renderJson(ret);
	}

	/**
	 * 点赞
	 */
	@ActionKey("/user:like")
	public void likeSubject() {
		String req = HttpKit.readData(getRequest());
		log.info("req=" + req);
		Map<String, String> param = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (param == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		int subjectId = Integer.valueOf(param.get("subjectId"));
		int userId = Integer.valueOf(param.get("userId"));
		int type = Integer.valueOf(param.get("type"));

		if (type == 1 || type == 2) {
			boolean result = subjectService.checkUserExists(userId, subjectId, type);
			if (result) {
				renderJson(Ret.fail("msg", "该用户已经点赞或点跪"));
				return;
			}
		}
		boolean res = subjectService.like(userId, subjectId, type);
		if (res) {
			renderJson(Ret.ok());
		} else {
			renderJson(Ret.fail("msg", "数据库操作失败"));
		}

	}

	/**
	 * 用户搜索根据题目ID获取题目信息
	 */
	// @ActionKey("/user:searchByName")
	/*
	 * public void searchSubjectInfo() {
	 * log.info("get subject info request name =" + getPara("name")); String
	 * sessionId = getCookie(LoginService.sessionIdName);
	 * if(StrKit.isBlank(sessionId)){ renderJson(Ret.fail("msg","没有用户信息"));
	 * return; } SubjectVo subject =
	 * subjectService.getSubjectInfo(getParaToInt("subjectId"),
	 * getParaToInt("userId")); if (subject != null) { renderJson(subject); }
	 * else { renderJson("msg", "没有该题目"); } }
	 */

	/**
	 * 用户搜索根据题目名字匹配获取题目信息
	 */
	@ActionKey("/user:searchByName")
	public void searchSubjectListByName() {
		
		String req = HttpKit.readData(getRequest());
		log.info("req=" + req);
		Map<String, String> param = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (param == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		String name = param.get("name");

		log.info("search subject list request name =" + name);
		List<Subject> subjectList = subjectService.searchSubjectListByName(name);
		if (subjectList != null && subjectList.size() > 0) {
			renderJson(Ret.create("status", "ok").set("data", subjectList));
		} else {
			renderJson(Ret.fail("msg", "没有题目信息"));
		}
	}

}
