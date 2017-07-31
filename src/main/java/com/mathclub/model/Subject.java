/**
 * 
 */
package com.mathclub.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 知识点实体
 * @author Administrator
 *
 */
public class Subject extends Model<Subject> {

	public static final User dao = new User().dao();
	
	private static final long serialVersionUID = 4489464881060983346L;
	private int id;//题目id
	private String name;//题目名称
	private String pic;//题目图片地址
	private String apic;//答案图片地址
	private String hide;//是否隐藏
	private String answer;//选择题答案
	private int answerNum;//选择题数量
	private String hint;//提示
	private String author;//题目作者
	private String tags;//标签列表
	private String keyIds;//所属知识点id列表
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getHide() {
		return hide;
	}
	public void setHide(String hide) {
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
	public String getKeyIds() {
		return keyIds;
	}
	public void setKeyIds(String keyIds) {
		this.keyIds = keyIds;
	}
	
}
