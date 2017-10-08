/**
 * 文件名：SubjectService.java
 */
package com.mathclub.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.json.FastJson;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.model.Comment;
import com.mathclub.model.CommentVo;
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
		String sql = "select c1.*,c2.commentId as reply_id,c2.text as reply_content,c2.parentId as reply_parent_id "
				+ "from comment c1 INNER JOIN comment c2 on c2.parentId = c1.commentId";
	}

	public Ret getCommentList(User user, String subjectId, int page, int size) {
		String sa = "select c2.*" + "from comment c1 INNER JOIN comment c2 on c2.parentId = c1.commentId "
				+ "where c2.parentId = ?";

		String select = "select *";
		String sql = "from comment where subjectId = ? and parentId = 0 order by createTime desc";
		Page<Record> comments = Db.paginate(page, size, select, sql, subjectId);

		List<Record> list = comments.getList();
		if (list == null || list.size() == 0) {
			return Ret.fail("msg", "没有评论信息");
		}
		List<CommentVo> coms = new ArrayList<CommentVo>();
		for (Record record : list) {
			CommentVo comVo = FastJson.getJson().parse(record.toJson(), CommentVo.class);
			List<Record> replys = Db.find(sa, comVo.getCommentId());
			comVo.setReply(replys);
			coms.add(comVo);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", coms);
		map.put("totalPage", comments.getTotalPage());
		map.put("totalRow", comments.getTotalRow());
		map.put("isFirstPage", comments.isFirstPage());
		map.put("isLastPage", comments.isLastPage());
		return Ret.ok("data", map);
	}

	public Ret queryMessage(User user, String type, int page, int size) {
		String select = "select *";
		String sql = "from comment where isToSys = ? and parentId = 0 order by createTime desc";
		Page<Record> message = Db.paginate(page, size, select, sql, type);
		List<Record> list = message.getList();
		if (list == null || list.size() == 0) {
			return Ret.fail("msg", "没有评论信息");
		} else {
			return Ret.ok("data", message);
		}

	}

	public Ret delete(User user, int commnetId) {
		Db.update("delete from comment where commentId = ?", commnetId);
		Db.update("delete from comment where parentId = ?", commnetId);
		return Ret.ok().create("msg", "删除成功");
	}

	public Page<Record> find(User user, String subjectId, String userName, String text, Integer pageNum,
			Integer pageSize) {
		String select = "select *";
		LogKit.info("subjentidd=" + subjectId + "dsd=" + userName + "text=" + text);
		if (StrKit.notBlank(subjectId)) {
			if (StrKit.notBlank(userName) && StrKit.notBlank(text)) {
				String sql = "from comment where subjectId = ? and userName = ? and text like ? order by createTime desc";
				return Db.paginate(pageNum, pageSize, select, sql, subjectId, userName, "%" + text + "%");
			} else if (StrKit.notBlank(userName)) {
				String sql = "from comment where subjectId = ? and userName = ? order by createTime desc";
				return Db.paginate(pageNum, pageSize, select, sql, subjectId, userName);
			} else if (StrKit.notBlank(text)) {
				String sql = "from comment where subjectId = ? and text like ?  order by createTime desc";
				return Db.paginate(pageNum, pageSize, select, sql, subjectId, "%" + text + "%");
			} else {
				String sql = "from comment where subjectId = ? order by createTime desc";
				return Db.paginate(pageNum, pageSize, select, sql, subjectId);
			}
		}
		if (StrKit.notBlank(userName)) {
			if (StrKit.notBlank(text)) {
				String sql = "from comment where userName = ? and text like ?  order by createTime desc";
				return Db.paginate(pageNum, pageSize, select, sql, userName, "%" + text + "%");
			} else {
				String sql = "from comment where userName = ? order by createTime desc";
				return Db.paginate(pageNum, pageSize, select, sql, userName);
			}
		} else if (StrKit.notBlank(text)) {
			String sql = "from comment where text like ? order by createTime desc";
			return Db.paginate(pageNum, pageSize, select, sql, "%" + text + "%");
		} else {
			String sql = "from comment order by createTime desc";
			return Db.paginate(pageNum, pageSize, select, sql);
		}
	}
}
