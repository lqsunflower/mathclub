/**
* 文件名：SubjectVo.java
* 创建日期： 2017年8月8日
* 作者：     richinfo
* Copyright (c) 2009-2017 邮箱开发室
* All rights reserved.
*/
package com.mathclub.model;

import java.sql.Timestamp;

/**
 * 功能描述：
 *
 */
public class SubjectVo
{
    private int subjectId;//题目id
    private int keyId;//所属知识点id
    private int majorId;//所属学科id
    private String name;//题目名称
    private String pic;//题目图片地址
    private String apic;//答案图片地址
    private String hide;//是否隐藏
    private String answer;//选择题答案
    private int answerNum;//选择题数量
    private String hint;//提示
    private String author;//题目作者
    private String tags;//标签列表
    private Timestamp createTime;//创建时间
    private boolean[] sign;//[是否点赞,是否点跪]
    private int[] userSign;//[点赞人数,是否点跪]
    private boolean isFavorite;//是否收藏
    
    public int getSubjectId()
    {
        return subjectId;
    }
    public void setSubjectId(int subjectId)
    {
        this.subjectId = subjectId;
    }
    public int getKeyId()
    {
        return keyId;
    }
    public void setKeyId(int keyId)
    {
        this.keyId = keyId;
    }
    public int getMajorId()
    {
        return majorId;
    }
    public void setMajorId(int majorId)
    {
        this.majorId = majorId;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getPic()
    {
        return pic;
    }
    public void setPic(String pic)
    {
        this.pic = pic;
    }
    public String getApic()
    {
        return apic;
    }
    public void setApic(String apic)
    {
        this.apic = apic;
    }
    public String getHide()
    {
        return hide;
    }
    public void setHide(String hide)
    {
        this.hide = hide;
    }
    public String getAnswer()
    {
        return answer;
    }
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }
    public int getAnswerNum()
    {
        return answerNum;
    }
    public void setAnswerNum(int answerNum)
    {
        this.answerNum = answerNum;
    }
    public String getHint()
    {
        return hint;
    }
    public void setHint(String hint)
    {
        this.hint = hint;
    }
    public String getAuthor()
    {
        return author;
    }
    public void setAuthor(String author)
    {
        this.author = author;
    }
    public String getTags()
    {
        return tags;
    }
    public void setTags(String tags)
    {
        this.tags = tags;
    }
    public Timestamp getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }
	public boolean[] getSign() {
		return sign;
	}
	public void setSign(boolean[] sign) {
		this.sign = sign;
	}
	public int[] getUserSign() {
		return userSign;
	}
	public void setUserSign(int[] userSign) {
		this.userSign = userSign;
	}
	public boolean isFavorite() {
		return isFavorite;
	}
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
}
