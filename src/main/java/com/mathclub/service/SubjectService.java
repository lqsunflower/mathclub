/**
 * 文件名：SubjectService.java
 * 创建日期： 2017年8月7日
 * 作者：     richinfo
 * Copyright (c) 2009-2017 邮箱开发室
 * All rights reserved.
 */
package com.mathclub.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.model.Like;
import com.mathclub.model.Subject;
import com.mathclub.model.SubjectVo;

/**
 * 功能描述：
 *
 */
public class SubjectService
{

    public static final Subject subjectDao = new Subject().dao();
    private static final Like likeDao = new Like().dao();
    private static Logger log = Logger.getLogger(SubjectService.class);
    /**
     * 检查用户是否已点赞或点跪
     * @param paraToInt
     * @param paraToInt2
     * @return
     */
    public boolean checkUserExists(Integer userId, Integer subjectId)
    {
        // 添加缓存
        // subjectDao.findByCache(cacheName, key, sql);
        boolean result = false;
        Like like = likeDao.findFirst(
            "select * from like where userId=? and subjectId=?", userId,
            subjectId);
        if (like != null)
        {
            result = true;
        }
        return result;
    }

    public boolean support(Integer userId, Integer subjectId, Integer type)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("subjectId", subjectId);
        map.put("type", type);
        map.put("createTime",
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Record record = new Record().setColumns(map);
        return Db.save("like", "id", record);
        /*int result = Db.update("insert into like(userId,subjectId,type,createTime) values(?, ?, ?, ?)",userId,subjectId,type,new Date());
        if(result == 1){
            return true;
        }else{
            return false;
        }*/
    }

    public List<Subject> getSubjectListByName(String name)
    {
        return subjectDao
            .find(
                "select s.*, k.keyId from subject s inner join keypoint k on s.keyId=k.keyId where k.name=?",
                name);
    }

    /**
     * 获取题目信息
     * @param para
     * @return
     */
    public SubjectVo getSubjectInfo(String subjectId, String userId)
    {
        SubjectVo sv = new SubjectVo();
        String[] isLike = new String[3];
        Subject subject = subjectDao.findFirst(
            "select * from subject where subjectId=?", subjectId);
        if (subject == null)
        {
            return null;
        }
        else
        {
            sv.setSubjectId(subject.getSubjectId());
            sv.setMajorId(subject.getMajorId());
            sv.setAnswer(subject.getAnswer());
            sv.setAnswerNum(subject.getAnswerNum());
            sv.setApic(subject.getApic());
            sv.setHide(subject.getHide());
            sv.setHint(subject.getHint());
            sv.setKeyId(subject.getKeyId());
            // 根据题目id查询点赞人数和点跪人数，并且查询是否点赞或者点跪
            int likeCount = Db.queryInt(
                "select count(*) from like where subjectId = ? and type = 1",
                subjectId);
            int unlikeCount = Db.queryInt(
                "select count(*) from like where subjectId = ? and type = 2",
                subjectId);
            sv.setLikeCount(likeCount);
            sv.setUnlikeCount(unlikeCount);
            Like like = likeDao
                .findFirst(
                    "select * from like where userId = ? and subjectId = ? and type = 1",
                    userId, subjectId);
            if (like != null)
            {
                isLike[0] = "true";
            }
            else
            {
                isLike[0] = "false";
            }
            Like unlike = likeDao
                .findFirst(
                    "select * from like where userId = ? and subjectId = ? and type = 2",
                    userId, subjectId);
            if (unlike != null)
            {
                isLike[1] = "true";
            }
            else
            {
                isLike[1] = "false";
            }
            sv.setIsLike(isLike);
            log.info("succeed to query subject info id= " + subjectId);
            return sv;
        }
    }

}
