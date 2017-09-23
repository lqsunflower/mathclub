/**
 * 
 */
package com.mathclub.model;

import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

/**
 * 评论
 * 
 * @author Administrator
 *
 */
public class CommentVo {

	private int commentId;// 评论id
	private int userId;// 评论用户id
	private int subjectId;// 评论题目ID
	private String userName;// 用户名字
	private String pic;// 图片地址
	private String text;// 评论内容
	private int isToSys;// 是否发送给管理员
	private int parentId;// 父评论ID
	private List<Record> reply;// 回复的内容
	private Date createTime;// 创建时间

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getIsToSys() {
		return isToSys;
	}

	public void setIsToSys(int isToSys) {
		this.isToSys = isToSys;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public List<Record> getReply() {
		return reply;
	}

	public void setReply(List<Record> reply) {
		this.reply = reply;
	}

}
