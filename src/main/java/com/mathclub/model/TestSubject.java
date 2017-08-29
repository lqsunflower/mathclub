/**
 * 
 */
package com.mathclub.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;

/**
 * 小测model
 * 
 * @author Administrator
 *
 */
public class TestSubject extends Model<TestSubject> {

	private static final long serialVersionUID = -699456399056744459L;

	private int id;// 知识点ID
	private int majorId;// 学科id
	private String name;// 名字
	private String subjectIds;// 测试题目包括的题目ID
	private Date createTime;
	
	public int getMajorId() {
		return majorId;
	}

	public void setMajorId(int majorId) {
		this.majorId = majorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubjectIds() {
		return subjectIds;
	}

	public void setSubjectIds(String subjectIds) {
		this.subjectIds = subjectIds;
	}

}
