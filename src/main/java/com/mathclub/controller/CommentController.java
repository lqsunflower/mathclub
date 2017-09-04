/**
 * 文件名：CommentController.java
 * 创建日期： 2017年8月7日
 */
package com.mathclub.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.jfinal.core.ActionKey;
import com.jfinal.json.FastJson;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.mathclub.kit.StringKit;
import com.mathclub.model.Comment;
import com.mathclub.model.Session;
import com.mathclub.model.Subject;
import com.mathclub.model.User;
import com.mathclub.service.CommentService;
import com.mathclub.service.SubjectService;

/**
 * 功能描述：评论
 *
 */
public class CommentController extends BaseController {

	private static Logger log = Logger.getLogger(CommentController.class);

	private CommentService commentService = new CommentService();

	/**
	 * 添加回复
	 */
	@ActionKey("/message:add")
	public void addComment() {
		String req = HttpKit.readData(getRequest());
		log.info("message add req=" + req);
		Comment comm = FastJson.getJson().parse(req, Comment.class);
		String sessionId = getHeader("token");
		LogKit.info("sessionId=" + sessionId);
		if (comm.getSubjectId() == 0 || StrKit.isBlank(comm.getText()) || StrKit.isBlank(sessionId)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		int userId = 0;
		Session user = Session.dao.findFirst("select * from session where id = ?", sessionId);
		if (user != null) {
			userId = user.getUserId();
			LogKit.info("userId======" + userId);
		} else {
			renderJson(Ret.fail("msg", "没有该用户"));
			return;
		}
		renderJson(commentService.add(userId, comm));
	}

	@Test
	public void a() {
		// String a = "{\"userId\":234,\"nickName\":\"liqiu\"}";
		String a = "{\"nickName\":\"liqiu\"}";
		User user = FastJson.getJson().parse(a, User.class);
		// System.out.println("kkkk====" + user.getUserId());
		System.out.println("kkkk====" + user.getNickName());
		System.out.println("kkkk====" + user.getUserId());
	}

	/**
	 * 修改题目
	 */
	/*
	 * @ActionKey("/subject:update") public void updateSubject() { String req =
	 * HttpKit.readData(getRequest()); log.info("req=" + req); Map<String,
	 * String> param = StringKit.putParamsInMap(req); if (StrKit.isBlank(req) ||
	 * (param == null)) { renderJson(Ret.fail("msg", "请求参数为空")); return; }
	 * String name = param.get("name"); String majorId = param.get("majorId");
	 * String tags = param.get("tags"); log.info("name=" + name + "majorId=" +
	 * majorId + "tags=" + tags + "answer=" + param.get("answer"));
	 * 
	 * if (Integer.valueOf(param.get("subjectId")) == 0 || StrKit.isBlank(name)
	 * || StrKit.isBlank(param.get("answer"))) { renderJson(Ret.fail("msg",
	 * "请求参数为空")); return; } renderJson(subjectService.update(param)); }
	 * 
	 *//**
		 * (后台管理)删除题目
		 */
	/*
	 * @ActionKey("/subject:delete") public void deleteSubject() {
	 * 
	 * String req = HttpKit.readData(getRequest()); log.info("req=" + req);
	 * Map<String, String> param = StringKit.putParamsInMap(req); if
	 * (StrKit.isBlank(req) || (param == null)) { renderJson(Ret.fail("msg",
	 * "请求参数为空")); return; } String subjectId = param.get("subjectId");
	 * 
	 * log.info("subjectId=" + subjectId); if (StrKit.isBlank(subjectId) ||
	 * Integer.valueOf(subjectId) == 0) { renderJson(Ret.fail("msg", "请求参数错误"));
	 * return; } renderJson(subjectService.delete(getParaToInt("subjectId"))); }
	 * 
	 *//**
		 * (后台管理)根据题目ID获取题目信息
		 */
	/*
	 * @ActionKey("/subject:findById") public void getSubjectInfoById() {
	 * 
	 * String req = HttpKit.readData(getRequest()); log.info("req=" + req);
	 * Map<String, String> param = StringKit.putParamsInMap(req); if
	 * (StrKit.isBlank(req) || (param == null)) { renderJson(Ret.fail("msg",
	 * "请求参数为空")); return; } String subjectId = param.get("subjectId");
	 * 
	 * log.info("get subject info request subjectId =" + subjectId); Subject
	 * subject = subjectService.getSubjectInfoById(Integer.valueOf(subjectId));
	 * if (subject != null) { renderJson(Ret.ok("sub", subject)); } else {
	 * renderJson(Ret.fail("msg", "没有该题目")); } }
	 * 
	 *//**
		 * (后台管理)分页查询题目根据知识点ID 给后台管理页面的接口
		 *//*
		 * @ActionKey("/subject:list") public void getSubjectListByPage() {
		 * String req = HttpKit.readData(getRequest()); log.info("req=" + req);
		 * Map<String, String> param = StringKit.putParamsInMap(req); if
		 * (StrKit.isBlank(req) || (param == null)) { renderJson(Ret.fail("msg",
		 * "请求参数为空")); return; } String keyId = param.get("keyId"); String page
		 * = param.get("page"); String size = param.get("size"); Page<Subject>
		 * subjectList = subjectService.getSubjectByPage(Integer.valueOf(keyId),
		 * Integer.valueOf(page), Integer.valueOf(size)); if (subjectList !=
		 * null && subjectList.getTotalPage() != 0) { renderJson(Ret.ok("list",
		 * subjectList)); } else { renderJson(Ret.ok("msg", "没有题目信息")); } }
		 */

}
