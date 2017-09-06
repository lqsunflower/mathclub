/**
 * 文件名：SubjectService.java
 */
package com.mathclub.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.model.Comment;

/**
 * 功能描述：
 *
 */
public class CommentService {

	public static final Comment commDao = new Comment().dao();

	public Ret add(int userId,String nickName, Comment comm) {
		if (comm.getCommentId() == 0) {
			// 如果没有commentId表示是第一条评论
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("subjectId", comm.getSubjectId());
			map.put("pic", comm.getPic());
			map.put("userName", nickName);
			map.put("userId", userId);
			map.put("text", comm.getText());
			map.put("isToSys", comm.getIsToSys());
			map.put("createTime", new Date());
			Record record = new Record().setColumns(map);
			boolean result = Db.save("comment", "commentId",record);
			if (result) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("commentId", record.get("commentId"));
				return Ret.ok("data", data);
			} else {
				return Ret.fail("msg", "添加失败");
			}
		} else {
			// 表示回复评论

		}
		return Ret.ok("msg", "添加成功");
	}
}
