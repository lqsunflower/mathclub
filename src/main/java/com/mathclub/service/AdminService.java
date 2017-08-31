/**
 * 
 */
package com.mathclub.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.model.Subject;

/**
 * @author Administrator
 *
 */
public class AdminService {

	private static Logger log = Logger.getLogger(AdminService.class);
	public static final Subject subjectDao = new Subject().dao();

	JsonKit.toJson(record);
	

}
