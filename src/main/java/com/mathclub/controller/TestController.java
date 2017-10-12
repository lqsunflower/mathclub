package com.mathclub.controller;

import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.mathclub.interceptor.AdminAuthInterceptor;
import com.mathclub.kit.StringKit;
import com.mathclub.model.TestSubject;
import com.mathclub.model.User;
import com.mathclub.service.TestService;

/**
 * 功能描述：
 *
 */
@Before(AdminAuthInterceptor.class)
// 添加后台权限拦截
public class TestController extends BaseController
{

    private static Logger log = Logger.getLogger(TestController.class);
    public static final TestSubject testDao = new TestSubject().dao();
    private TestService testService = new TestService();

    /**
     * 添加小测
     */
    @ActionKey("/test:add")
    public void addTestSubject()
    {

        String name = getPara("name");
        int majorId = Integer.valueOf(getPara("majorId"));
        String subjectIds = getPara("subjectIds");

        User user = getLoginUser();
        if (user == null)
        {
            renderJson(Ret.fail("msg", "没有该用户"));
            return;
        }
        log.info("test:add req name=" + name + "|majorId=" + getPara("majorId")
            + "|subjectIds=" + subjectIds);

        if (StrKit.isBlank(name) || StrKit.isBlank(subjectIds)
            || (majorId == 0))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        renderJson(testService.addTestSubject(name, majorId, subjectIds));
    }

    /**
     * 修改小测
     */
    @ActionKey("/test:update")
    public void updateSubject()
    {
        String name = getPara("name");
        String majorId = getPara("majorId");
        String subjectIds = getPara("subjectIds");
        String testId = getPara("testId");
        LogKit.info("test:update req name=" + name + "|majorId=" + majorId
            + "|subjectIds=" + subjectIds + "|testId=" + testId);
        if (StrKit.isBlank(name) || StrKit.isBlank(subjectIds)
            || StrKit.isBlank(majorId) || StrKit.isBlank(testId))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }

        renderJson(testService.update(name, Integer.valueOf(majorId),
            subjectIds, Integer.valueOf(testId)));
    }

    /**
     * 删除小测
     */
    @ActionKey("/test:delete")
    public void delete()
    {
        String  testIds= getPara("testIds");
        log.info("test:delete req testIds=" + testIds);
        if (StrKit.isBlank(testIds))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        renderJson(testService.delete(testIds));
    }

    /**
     * 小测列表
     */
    @Clear
    @ActionKey("/test:list")
    public void findAll()
    {
        String req = HttpKit.readData(getRequest());
        log.info("test list req=" + req);
        Map<String, String> param = StringKit.putParamsInMap(req);
        if (StrKit.isBlank(req) || StrKit.isBlank(param.get("page"))
            || StrKit.isBlank(param.get("size")))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        int page = Integer.valueOf(param.get("page"));
        int size = Integer.valueOf(param.get("size"));
        if ((page == 0) || (size == 0))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        renderJson(testService.getListByPage(page, size, param));
    }

    /**
     * 查找小测
     */
    @Clear
    @ActionKey("/test:findById")
    public void findById()
    {
        if (StrKit.isBlank(getPara("testId")))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        renderJson(testService.findTest(getPara("testId")));
    }

}
