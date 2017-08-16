/**
 * 文件名：SubjectService.java
 */
package com.mathclub.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.json.JsonManager;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.mathclub.model.Like;
import com.mathclub.model.Subject;
import com.mathclub.model.SubjectVo;

/**
 * 功能描述：
 *
 */
public class SubjectService {

	public static final Subject subjectDao = new Subject().dao();
	private static final Like likeDao = new Like().dao();
	private static Logger log = Logger.getLogger(SubjectService.class);
	final int pageSize = 1;

	/**
	 * 检查用户是否已点赞或点跪
	 * 
	 * @param integer
	 * @param paraToInt
	 * @param paraToInt2
	 * @return
	 */
	public boolean checkUserExists(int userId, int subjectId, int type) {
		// 添加缓存
		// subjectDao.findByCache(cacheName, key, sql);
		boolean result = false;
		Like like = likeDao.findFirst("select * from subject_like where subjectId=? and userId=? and type=?", subjectId,
				userId, type);
		if (like != null) {
			result = true;
		}
		return result;
	}

	public boolean like(Integer userId, Integer subjectId, Integer type) {
		int result = Db.update("insert into subject_like(userId,subjectId,type,createTime) values(?, ?, ?, ?)", userId,
				subjectId, type, new Date());
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

	public List<Subject> getSubjectListByName(String name) {
		return subjectDao.find(
				"select s.*, k.keyId from subject s inner join keypoint k on s.keyId=k.keyId where k.name=?", name);
	}

	/**
	 * 获取题目信息
	 * 
	 * @param para
	 * @return
	 */
	public SubjectVo getSubjectInfo(int subjectId, int userId) {
		SubjectVo sv = new SubjectVo();
		boolean[] sign = new boolean[2];
		int[] userSign = new int[2];
		Subject subject = subjectDao.findFirst("select * from subject where subjectId=?", subjectId);
		if (subject == null) {
			return null;
		} else {
			sv.setSubjectId(subject.getSubjectId());
			sv.setMajorId(subject.getMajorId());
			sv.setAnswer(subject.getAnswer());
			sv.setAnswerNum(subject.getAnswerNum());
			sv.setApic(subject.getApic());
			sv.setHide(subject.getHide());
			sv.setHint(subject.getHint());
			sv.setKeyId(subject.getKeyId());
			// 根据题目id查询点赞人数和点跪人数，并且查询是否点赞或者点跪
			int likeCount = Db.queryInt("select count(*) from subject_like where subjectId = ? and type = 1",
					subjectId);
			int unlikeCount = Db.queryInt("select count(*) from subject_like where subjectId = ? and type = 2",
					subjectId);
			userSign[0] = likeCount;
			userSign[1] = unlikeCount;
			sv.setUserSign(userSign);
			Like like = likeDao.findFirst("select * from subject_like where userId = ? and subjectId = ? and type = 1",
					userId, subjectId);
			if (like != null) {
				sign[0] = true;
			} else {
				sign[0] = false;
			}
			Like unlike = likeDao.findFirst(
					"select * from subject_like where userId = ? and subjectId = ? and type = 2", userId, subjectId);
			if (unlike != null) {
				sign[1] = true;
			} else {
				sign[1] = false;
			}
			sv.setSign(sign);
			Integer n = Db.queryInt("select * from subject_like where userId = ? and subjectId = ? and type = 3",
					userId, subjectId);
			if (n != null) {
				sv.setFavorite(true);
			} else {
				sv.setFavorite(false);
			}
			return sv;
		}
	}

	/**
	 * 分页查询
	 * @param userId
	 * @param keyId
	 * @param pageNum
	 * @return
	 */
	public Ret querySubjectInfo(int userId, int keyId, int pageNum) {
		Page<Subject> sub = getSubjectPage(keyId, pageNum);
		boolean[] sign = new boolean[2];
		int[] userSign = new int[2];
		List<Subject> list = sub.getList();
		if (list == null || list.size() == 0) {
			return Ret.fail("msg", "该知识点没有题目");
		}
		SubjectVo subVo =JSONObject.parseObject(list.get(0).toJson(), SubjectVo.class);
		System.out.println("subjet0000 " + subVo.getName() + "kkk=" + subVo.getTags());
		// 根据题目id查询点赞人数和点跪人数，并且查询是否点赞或者点跪
		int likeCount = Db.queryInt("select count(*) from subject_like where subjectId = ? and type = 1",
				subVo.getSubjectId());
		int unlikeCount = Db.queryInt("select count(*) from subject_like where subjectId = ? and type = 2",
				subVo.getSubjectId());
		userSign[0] = likeCount;
		userSign[1] = unlikeCount;
		subVo.setUserSign(userSign);
		Like like = likeDao.findFirst("select * from subject_like where userId = ? and subjectId = ? and type = 1",
				userId, subVo.getSubjectId());
		if (like != null) {
			sign[0] = true;
		} else {
			sign[0] = false;
		}
		Like unlike = likeDao.findFirst("select * from subject_like where userId = ? and subjectId = ? and type = 2",
				userId, subVo.getSubjectId());
		if (unlike != null) {
			sign[1] = true;
		} else {
			sign[1] = false;
		}
		subVo.setSign(sign);
		Integer n = Db.queryInt("select * from subject_like where userId = ? and subjectId = ? and type = 3", userId,
				subVo.getSubjectId());
		if (n != null) {
			subVo.setFavorite(true);
		} else {
			subVo.setFavorite(false);
		}
		return Ret.create("state","ok").set("subject",subVo).set("page", 1);
	}

	/**
	 * 分页查询页数
	 */
	private Page<Subject> getSubjectPage(int keyId, int pageNum) {
		String select = "select *";
		String sql = "from subject where keyId = ? order by createTime desc";
		return subjectDao.paginate(pageNum, pageSize, select, sql, keyId);
	}
}
