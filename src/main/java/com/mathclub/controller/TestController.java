package com.mathclub.controller;

import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.core.ActionKey;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.mathclub.kit.StringKit;
import com.mathclub.model.TestSubject;
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
		log.info("test:add req=" + req);
		Map<String, String> param = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (param == null) || StrKit.isBlank(param.get("majorId"))) {
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
		log.info(" test:update req=" + req);
		Map<String, String> param = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || (param == null)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		if (StrKit.isBlank(param.get("name")) || StrKit.isBlank(param.get("subjectIdList"))
				|| StrKit.isBlank(param.get("majorId")) || StrKit.isBlank(param.get("testId"))) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		String name = param.get("name");
		int majorId = Integer.valueOf(param.get("majorId"));
		String subjectIds = param.get("subjectIdList");
		int testId = Integer.valueOf(param.get("testId"));

		renderJson(testService.update(name, majorId, subjectIds, testId));
	}

	/**
	 * 删除小测
	 */
	@ActionKey("/test:delete")
	public void delete() {
		String req = HttpKit.readData(getRequest());
		log.info("delete test req=" + req);
		if (StrKit.isBlank(req)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		Map<String, String> param = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(param.get("testIds"))) {
			renderJson(Ret.fail("msg", "请求参数错误"));
			return;
		}
		String testIds = param.get("testIds");

		renderJson(testService.delete(testIds));
	}

	/**
	 * 小测列表
	 */
	@ActionKey("/test:list")
	public void findAll() {

		String req = HttpKit.readData(getRequest());
		log.info("test list req=" + req);
		Map<String, String> param = StringKit.putParamsInMap(req);
		if (StrKit.isBlank(req) || StrKit.isBlank(param.get("page")) || StrKit.isBlank(param.get("size"))) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		int page = Integer.valueOf(param.get("page"));
		int size = Integer.valueOf(param.get("size"));
		if ((page == 0) || (size == 0)) {
			renderJson(Ret.fail("msg", "请求参数为空"));
			return;
		}
		renderJson(testService.getListByPage(page, size, param));
	}

}
