package com.mathclub.controller;

import java.util.List;

import com.jfinal.core.ActionKey;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.mathclub.model.Subject;
import com.mathclub.model.User;
import com.mathclub.service.SubjectService;

/**
 * 功能描述：
 *
 */
public class UserController extends BaseController
{
    private SubjectService subjectService = new SubjectService();

    /**
     * 分页获取题目信息(包括用户的信息，点赞和点跪情况)
     */
    @ActionKey("/subject:querySubjectInfoByPage")
    public void querySubjectInfoByPage()
    {
        User user = getLoginUser();
        if (user == null)
        {
            LogKit.error("subject:querySubjectInfoByPage  user is null");
            renderJson(Ret.fail("msg", "没有该用户"));
            return;
        }
        LogKit.info("subject:querySubjectInfoByPage request name ="
            + getPara("keyId"));
        
        Ret ret = subjectService.querySubjectInfo(user.get("userId"),
            getParaToInt("keyId"), getParaToInt("page", 1),
            getParaToInt("size"),getPara("order"),getPara("desc"));
        renderJson(ret);
    }

    /**
     * 点赞
     */
    @ActionKey("/user:like")
    public void likeSubject()
    {
        User user = getLoginUser();
        if (user == null)
        {
            LogKit.error("user:like  user is null");
            renderJson(Ret.fail("msg", "没有该用户"));
            return;
        }

        if (StrKit.isBlank(getPara("subjectId"))
            || StrKit.isBlank(getPara("type")))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        int subjectId = Integer.valueOf(getPara("subjectId"));
        int type = Integer.valueOf(getPara("type"));

        if (type == 1 || type == 2)
        {
            boolean result = subjectService.checkUserExists(user.get("userId"),
                subjectId, type);
            if (result)
            {
                renderJson(Ret.fail("msg", "该用户已经点赞或点跪"));
                return;
            }
        }
        else
        {
            renderJson(Ret.fail("msg", "类型错误"));
            return;
        }
        int res = subjectService.like(user.get("userId"), subjectId, type);
        if (res > 0)
        {
            renderJson(Ret.ok("msg", "点赞成功"));
        }
        else
        {
            renderJson(Ret.fail("msg", "数据库操作失败"));
        }

    }

    /**
     * 收藏
     */
    @ActionKey("/user:favorite")
    public void favorite()
    {
        User user = getLoginUser();
        if (user == null)
        {
            LogKit.error("user:favorite user is null");
            renderJson(Ret.fail("msg", "没有该用户"));
            return;
        }
        LogKit.info("user:favorite req=" + getPara("subjectId") + "|majorId="
            + getPara("majorId"));
        String subjectId = getPara("subjectId");
        String majorId = getPara("majorId");
        if (StrKit.isBlank(subjectId) || (StrKit.isBlank(majorId)))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        boolean res = subjectService.favorite(user.get("userId"),
            Integer.valueOf(majorId), Integer.valueOf(subjectId));
        if (res)
        {
            renderJson(Ret.ok("msg", "成功"));
        }
        else
        {
            renderJson(Ret.fail("msg", "数据库操作失败"));
        }

    }

    /**
     * 取消收藏
     */
    @ActionKey("/user:cancelFavorite")
    public void cancelFavorite()
    {
        User user = getLoginUser();
        if (user == null)
        {
            LogKit.error("user:cancelFavorite user is null");
            renderJson(Ret.fail("msg", "没有该用户"));
            return;
        }

        LogKit.info("user:cancelFavorite req=" + getPara("subjectId"));
        String subjectId = getPara("subjectId");
        if (StrKit.isBlank(subjectId) || (Integer.valueOf(subjectId) == 0))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        int res = subjectService.deleteFavorite(user.get("userId"),
            Integer.valueOf(subjectId));
        if (res > 0)
        {
            renderJson(Ret.ok("msg", "成功"));
        }
        else
        {
            renderJson(Ret.fail("msg", "数据库操作失败"));
        }

    }

    /**
     * 获取收藏列表
     */
    @ActionKey("/user:favoriteList")
    public void list()
    {
        User user = getLoginUser();
        if (user == null)
        {
            LogKit.error("user:favoriteList user is null");
            renderJson(Ret.fail("msg", "没有该用户"));
            return;
        }
        LogKit.info("user:favoriteList req=" + getPara("subjectId"));
        Integer majorId = getParaToInt("majorId");
        if ((majorId == null) || (majorId == 0))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        renderJson(subjectService.listFavorite(user.get("userId"), majorId));
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
    public void searchSubjectListByName()
    {
        User user = getLoginUser();
        if (user == null)
        {
            LogKit.error("user:searchByName user is null");
            renderJson(Ret.fail("msg", "没有该用户"));
            return;
        }
        String name = getPara("name");
        LogKit.info("user:searchByName req =" + name);
        if (StrKit.isBlank(name))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        List<Subject> subjectList = subjectService
            .searchSubjectListByName(name);
        if (subjectList != null && subjectList.size() > 0)
        {
            renderJson(Ret.ok("data", subjectList));
        }
        else
        {
            renderJson(Ret.fail("msg", "没有题目信息"));
        }
    }

}
