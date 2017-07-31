/**
 * 
 */
package com.mathclub.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * @author Administrator
 * 学科bean
 */
public class Major extends Model<Major>{

	private static final long serialVersionUID = -699456399056744459L;
	public static final User dao = new User().dao();
	private int majorId;//学科id
	private String name;//学科名字
}
