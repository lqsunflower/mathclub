/**
 * 文件名：SubjectService.java
 */
package com.mathclub.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.model.Comment;

/**
 * 功能描述：
 *
 */
public class CommentService {

	private static Logger log = Logger.getLogger(CommentService.class);
	public static final Comment commDao = new Comment().dao();
	

	public Ret add(int userId, Map<String, String> param) {
		if(StrKit.isBlank(param.get("commentId"))){
			//如果没有commentId表示是第一条评论
			Map<String, Object> map = new HashMap<String, Object>();
			Record record = new Record().setColumns(map);
			//Db.save(tableName, record)
		}
		return null;
	}
}
