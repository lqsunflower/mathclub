/**
 * 文件名：SubjectService.java
 */
package com.mathclub.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.model.Comment;
import com.mathclub.model.User;

/**
 * 功能描述：
 *
 */
public class CommentService {

	public static final Comment commDao = new Comment().dao();

	public Ret add(int userId, User user, Comment comm) {
		String nickName = user.toRecord().get("nickName");
		String headImgurl = user.toRecord().get("headImgurl");
		if (comm.getCommentId() == 0) {
			// 如果没有commentId表示是第一条评论
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("subjectId", comm.getSubjectId());
			map.put("pic", comm.getPic());
			map.put("userName", nickName);
			map.put("headImgurl", headImgurl);
			map.put("userId", userId);
			map.put("text", comm.getText());
			map.put("isToSys", comm.getIsToSys());
			map.put("parentId", 0);// 父评论ID为0
			map.put("createTime", new Date());
			Record record = new Record().setColumns(map);
			boolean result = Db.save("comment", "commentId", record);
			if (result) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("commentId", record.get("commentId"));
				return Ret.ok("data", data);
			} else {
				return Ret.fail("msg", "添加失败");
			}
		} else {
			// 表示回复评论
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("subjectId", comm.getSubjectId());
			map.put("pic", comm.getPic());
			map.put("userName", nickName);
			map.put("headImgurl", headImgurl);
			map.put("userId", userId);
			map.put("text", comm.getText());
			map.put("isToSys", comm.getIsToSys());
			map.put("parentId", comm.getCommentId());// 父评论ID
			map.put("createTime", new Date());
			Record record = new Record().setColumns(map);
			boolean result = Db.save("comment", "commentId", record);
			if (result) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("commentId", record.get("commentId"));
				return Ret.ok("data", data);
			} else {
				return Ret.fail("msg", "添加失败");
			}
		}
	}

	public void queryInfo() {
		String sql = "select c1.*,c2.commentId as reply_id,c2.text as reply_content,c2.parentId as reply_parent_id from comment c1 INNER JOIN comment c2 on c2.parentId = c1.commentId";
	}
}
