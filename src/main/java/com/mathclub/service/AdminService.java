/**
 * 
 */
package com.mathclub.service;

import java.text.SimpleDateFormat;
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

	public static final Subject subjectDao = new Subject().dao();
	private static Logger log = Logger.getLogger(AdminService.class);

	public Ret addSubject(Subject param) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", param.getName());
		map.put("majorId", param.getMajorId());
		map.put("pic", param.getPic());
		map.put("apic", param.getApic());
		map.put("hide", param.getHide());
		map.put("answer", param.getAnswer());
		map.put("answerNum", param.getAnswerNum());
		map.put("hint", param.getHint());
		map.put("author", param.getAuthor());
		map.put("tags", param.getTags());
		map.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		Record record = new Record().setColumns(map);
		boolean result = Db.save("subject", "subjectId", record);
		if (result) {
			log.info("succeed to add subject.name=" + param.getName());
			return Ret.ok();
		} else {
			log.error("failed to add subject.name=" + param.getName());
			return Ret.fail("summary", "操作数据库失败");
		}

	}

	public Page<Subject> getSubjectListByKeyId(int keyId) {
		return subjectDao.paginate(1, 10, "select *", "from subject where keyId=? order by createTime desc", keyId);
	}

	public Subject getSubjectInfo(Integer subjectId, Integer keyId) {
		return subjectDao.findFirst("select * from subject where subjectId=? and keyId=?", subjectId, keyId);
	}
	

}
