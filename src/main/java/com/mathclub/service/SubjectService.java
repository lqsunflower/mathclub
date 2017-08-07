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

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.model.Subject;

/**
 * 功能描述：
 *
 */
public class SubjectService
{

    public static final Subject subjectDao = new Subject().dao();
    
    /**
     * 检查用户是否已点赞或点跪
     * @param paraToInt
     * @param paraToInt2
     * @return
     */
    public boolean checkUserExists(Integer userId, Integer subjectId)
    {
        //添加缓存
        //subjectDao.findByCache(cacheName, key, sql);
        boolean result = false;
        Subject subject = subjectDao.findFirst("select * from support where userId=? and subjectId=?",userId,subjectId);
        if(subject != null){
            result = true;
        }
        return result;
    }

    public boolean support(Integer userId, Integer subjectId,
        Integer type)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("subjectId", subjectId);
        map.put("type", type);
        map.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Record record = new Record().setColumns(map);
        return Db.save("support", "id", record);
    }

	public List<Subject> getSubjectListByName(String name) {
		return subjectDao.find("select s.*, k.keyId from subject s inner join keypoint k on s.keyId=k.keyId where k.name=?", name);
	}

}
