/**
 * 
 */
package com.mathclub.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;

/**
 * 知识点实体
 * @author Administrator
 *
 */
public class Subject extends Model<Subject> {

	private static final long serialVersionUID = 4489464881060983346L;
	private int subjectId;//题目id
	private int keyId;//所属知识点id
	private int majorId;//所属学科id
	private String name;//题目名称
	private String pic;//题目图片地址
	private String apic;//答案图片地址
	private int hide;//是否隐藏
	private String answer;//选择题答案
	private int answerNum;//选择题数量
	private String hint;//提示
	private String author;//题目作者
	private String tags;//标签列表
	private String video;//列表
	private Date createTime;//创建时间
	private Date modifyTime;//修改时间
	
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getApic() {
		return apic;
	}
	public void setApic(String apic) {
		this.apic = apic;
	}
	public int getHide() {
		return hide;
	}
	public void setHide(int hide) {
		this.hide = hide;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getAnswerNum() {
		return answerNum;
	}
	public void setAnswerNum(int answerNum) {
		this.answerNum = answerNum;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public int getKeyId() {
		return keyId;
	}
	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}
	public int getMajorId() {
		return majorId;
	}
	public void setMajorId(int majorId) {
		this.majorId = majorId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
