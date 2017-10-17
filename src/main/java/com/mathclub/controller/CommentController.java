/**
 * 文件名：CommentController.java
 * 创建日期： 2017年8月7日
 */
package com.mathclub.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.json.FastJson;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.mathclub.interceptor.AdminAuthInterceptor;
import com.mathclub.model.Comment;
import com.mathclub.model.User;
import com.mathclub.service.CommentService;

/**
 * 功能描述：评论
 *
 */
public class CommentController extends BaseController
{
    private CommentService commentService = new CommentService();

    /**
     * 添加回复
     */
    @ActionKey("/message:add")
    public void addComment()
    {
        User user = getLoginUser();
        if (user == null)
        {
            renderJson(Ret.fail("msg", "没有该用户"));
            return;
        }
        String req = HttpKit.readData(getRequest());
        LogKit.info("message add req=" + req);
        Comment comm = FastJson.getJson().parse(req, Comment.class);
        if (comm.getSubjectId() == 0 || StrKit.isBlank(comm.getText()))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        renderJson(commentService.add(user.get("userId"), user, comm));
    }

    /**
     * 查询评论列表
     */
    @ActionKey("/message:commentList")
    public void findCommentList()
    {
        User user = getLoginUser();
        if (user == null)
        {
            renderJson(Ret.fail("msg", "没有该用户"));
            return;
        }
        
        String subjectId = getPara("subjectId");
        String page = getPara("page");
        String size = getPara("size");
        LogKit.info("message:commentList request page=" + page + "--size=" + size);
        if (StrKit.isBlank(page) || StrKit.isBlank(size))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        renderJson(commentService.getCommentList(user, subjectId,
            Integer.valueOf(page), Integer.valueOf(size)));
    }

    /**
     * 查询向管理员发送消息的(后台接口)
     */
    @Before(AdminAuthInterceptor.class)
    @ActionKey("/message:queryMessage")
    public void queryMessage()
    {
        String sessionId = getCookie("token");
        String page = getPara("page");
        String size = getPara("size");
        String subjectName = getPara("subjectName");
        String userName = getPara("userName");
        String text = getPara("text");
        LogKit.info("message:queryMessage request sessionId=" + sessionId
            + "-page=" + page + "--size=" + size);
        if (StrKit.isBlank(page) || StrKit.isBlank(size))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        if (getPara("type") != null)
        {
            renderJson(commentService.queryMessage(getPara("type"),
                Integer.valueOf(page), Integer.valueOf(size)));
        }
        else
        {
            renderJson(commentService.adminQuery(subjectName, userName, text,
                Integer.valueOf(page), Integer.valueOf(size)));
        }

    }

    /**
     * 删除评论
     */
    @ActionKey("/message:delete")
    public void deleteMessage()
    {
        User user = getLoginUser();
        if (user == null)
        {
            renderJson(Ret.fail("msg", "没有该用户"));
            return;
        }
        
        String commentId = getPara("commentId");
        
        if (StrKit.isBlank(commentId))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        renderJson(commentService.delete(user, Integer.valueOf(commentId)));
    }
    
    
    /**
     * 批量删除评论
     */
    @Before(AdminAuthInterceptor.class)
    @ActionKey("/message:batchDelete")
    public void batchDelete()
    {
        String commentIds = getPara("commentIds");
        if (StrKit.isBlank(commentIds))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        renderJson(commentService.batchDelete(commentIds));
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
     *//**
     * (后台管理)分页查询题目根据知识点ID 给后台管理页面的接口
     */
    /*
     * @ActionKey("/subject:list") public void getSubjectListByPage() { String
     * req = HttpKit.readData(getRequest()); log.info("req=" + req); Map<String,
     * String> param = StringKit.putParamsInMap(req); if (StrKit.isBlank(req) ||
     * (param == null)) { renderJson(Ret.fail("msg", "请求参数为空")); return; }
     * String keyId = param.get("keyId"); String page = param.get("page");
     * String size = param.get("size"); Page<Subject> subjectList =
     * subjectService.getSubjectByPage(Integer.valueOf(keyId),
     * Integer.valueOf(page), Integer.valueOf(size)); if (subjectList != null &&
     * subjectList.getTotalPage() != 0) { renderJson(Ret.ok("list",
     * subjectList)); } else { renderJson(Ret.ok("msg", "没有题目信息")); } }
     */

}
