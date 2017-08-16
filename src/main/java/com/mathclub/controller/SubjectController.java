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
import com.mathclub.model.Subject;
import com.mathclub.model.SubjectVo;
import com.mathclub.service.AdminService;
import com.mathclub.service.SubjectService;

/**
 * 功能描述：
 *
 */
public class SubjectController extends BaseController
{

    private static Logger log = Logger.getLogger(SubjectController.class);
    public static final Subject subjectDao = new Subject().dao();
    private AdminService service = new AdminService();
    private SubjectService subjectService = new SubjectService();

    /**
     * 根据题目ID获取题目信息
     *//*
    @ActionKey("/subject:findById")
    public void getSubjectInfo()
    {
        log.info("get subject info request subjectId ="
            + getParaToInt("subjectId") + " keyId=" + getParaToInt("keyId"));
        Subject subject = service.getSubjectInfo(getParaToInt("subjectId"),
            getParaToInt("keyId"));
        renderJson(subject);
    }*/

    /**
     * 点赞
     */
    @ActionKey("/subject:likeSubject")
    public void likeSubject()
    {
        log.info("like subject info request userId="
            + getParaToInt("userId") + " subjectId ="
            + getParaToInt("subjectId") + " type=" + getParaToInt("type"));
        int type = getParaToInt("type");
        if (type == 1 || type == 2)
        {
            boolean result = subjectService.checkUserExists(
                getParaToInt("userId"), getParaToInt("subjectId"),getParaToInt("type"));
            if (result)
            {
                renderJson(Ret.fail("summary", "该用户已经点赞或点跪"));
                return;
            }
        }
        boolean res = subjectService.like(getParaToInt("userId"),
            getParaToInt("subjectId"), getParaToInt("type"));
        if (res)
        {
            renderJson(Ret.ok());
        }
        else
        {
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
		if(subjectList != null){
		    renderJson(subjectList);
		}else{
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
        if(subject != null){
            renderJson(subject);
        }else{
            renderJson("msg", "没有该题目");
        }
    }

    /**
     * 搜索根据题目ID获取题目信息
     */
    @ActionKey("/subject:querySubjectInfoByPage")
    public void querySubjectInfoByPage() {
        log.info("page query subject info request name =" + getPara("keyId"));
        Ret ret = subjectService.querySubjectInfo(getParaToInt("userId"),getParaToInt("keyId"),getParaToInt("p", 1));
        renderJson(ret);
    }
    
}