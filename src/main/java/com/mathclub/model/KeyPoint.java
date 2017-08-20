/**
 * 
 */
package com.mathclub.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 知识点model
 * @author Administrator
 *
 */
public class KeyPoint extends Model<KeyPoint>{

	private static final long serialVersionUID = -699456399056744459L;
	
	private int keyId;//知识点ID
	private int majorId;//学科id
	private String name;//知识点名字
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
