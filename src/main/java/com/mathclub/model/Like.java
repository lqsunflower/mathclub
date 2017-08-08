/**
* 文件名：Like.java
* 创建日期： 2017年8月8日
* 作者：     richinfo
* Copyright (c) 2009-2017 邮箱开发室
* All rights reserved.
*/
package com.mathclub.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;

/**
 * 功能描述：点赞实体
 *
 */
public class Like extends Model<Like>
{

    private static final long serialVersionUID = -1786518225610940000L;
    private int id;//id
    private int subjectId;//题目id
    private int userId;//用户id
    private int type;//类型 1:点赞 2：点跪
    private Date createTime;//创建时间
    
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public int getSubjectId()
    {
        return subjectId;
    }
    public void setSubjectId(int subjectId)
    {
        this.subjectId = subjectId;
    }
    public int getUserId()
    {
        return userId;
    }
    public void setUserId(int userId)
    {
        this.userId = userId;
    }
    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    public Date getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
   
}
