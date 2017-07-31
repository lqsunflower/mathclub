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
	public static final User dao = new User().dao();
	private int keyId;//知识点ID
	private int majorId;//学科id
	private String name;//知识点名字

}
