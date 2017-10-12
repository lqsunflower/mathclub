/**
 * 文件名：SubjectService.java
 */
package com.mathclub.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.json.FastJson;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.model.KeyPoint;
import com.mathclub.model.Like;
import com.mathclub.model.Subject;
import com.mathclub.model.SubjectVo;

/**
 * 功能描述：
 *
 */
public class SubjectService
{

    private static Logger log = Logger.getLogger(SubjectService.class);
    public static final Subject subjectDao = new Subject().dao();
    private static final Like likeDao = new Like().dao();
    private final KeyPoint keyDao = new KeyPoint().dao();

    public Ret addSubject(Map<String, String> param)
    {
        // 查找知识点名字和学科名字
        KeyPoint key = keyDao.findFirst(
            "select * from keypoint where keyId = ?", param.get("keyId"));
        System.out.println("______________" + key.get("majorName") + "999"
            + key.get("keyName"));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", param.get("name"));
        map.put("majorId", param.get("majorId"));
        map.put("majorName", key.get("majorName"));
        map.put("keyName", key.get("keyName"));
        map.put("keyId", param.get("keyId"));
        map.put("pic", param.get("pic"));
        map.put("apic", param.get("apic"));
        if (StrKit.isBlank(param.get("hide")))
        {
            map.put("hide", 0);
        }
        else
        {
            map.put("hide", param.get("hide"));
        }
        map.put("answer", param.get("answer"));
        map.put("answerNum", param.get("answerNum"));
        map.put("hint", param.get("hint"));
        map.put("author", param.get("author"));
        map.put("video", param.get("video"));
        map.put("tags", param.get("tags"));
        map.put("createTime", new Date());
        map.put("modifyTime", new Date());
        Record record = new Record().setColumns(map);
        boolean result = Db.save("subject", "subjectId", record);
        if (result)
        {
            log.info("succeed to add subject.name=" + param.get("name"));
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("subjectId", record.get("subjectId"));
            return Ret.ok("data", data);
        }
        else
        {
            log.error("failed to add subject.name=" + param.get("name"));
            return Ret.fail("msg", "操作数据库失败");
        }

    }

    public Page<Subject> getSubjectListByKeyId(int keyId)
    {
        return subjectDao.paginate(2, 1, "select *",
            "from subject where keyId=? order by createTime desc", keyId);
    }

    /*
     * public Subject getSubjectInfo(Integer subjectId, Integer keyId) { return
     * subjectDao
     * .findFirst("select * from subject where subjectId=? and keyId=?",
     * subjectId, keyId); }
     */

    /**
     * 检查用户是否已点赞或点跪
     * 
     * @param integer
     * @param paraToInt
     * @param paraToInt2
     * @return
     */
    public boolean checkUserExists(int userId, int subjectId, int type)
    {
        // 添加缓存
        // subjectDao.findByCache(cacheName, key, sql);
        boolean result = false;
        Like like = likeDao
            .findFirst(
                "select * from subject_like where subjectId=? and userId=? and type=?",
                subjectId, userId, type);
        if (like != null)
        {
            result = true;
        }
        return result;
    }

    public int like(int userId, int subjectId, int type)
    {
        return Db
            .update(
                "insert into subject_like(userId,subjectId,type,createTime) values(?, ?, ?, ?)",
                userId, subjectId, type, new Date());
    }

    /*
     * public List<Subject> getSubjectListByName(String name) { return
     * subjectDao .find(
     * "select s.*, k.keyId from subject s inner join keypoint k on s.keyId=k.keyId where k.name=?"
     * , name); }
     */

    /**
     * 获取题目信息
     * 
     * @param para
     * @return
     */
    public SubjectVo getSubjectInfo(int subjectId, int userId)
    {
        SubjectVo sv = new SubjectVo();
        boolean[] userSign = new boolean[2];
        int[] sign = new int[2];
        Subject subject = getSubjectInfoById(subjectId);
        if (subject == null)
        {
            return null;
        }
        else
        {
            sv.setSubjectId(subject.getSubjectId());
            sv.setMajorId(subject.getMajorId());
            sv.setAnswer(subject.getAnswer());
            sv.setAnswerNum(subject.getAnswerNum());
            sv.setApic(subject.getApic());
            sv.setHide(subject.getHide());
            sv.setHint(subject.getHint());
            sv.setKeyId(subject.getKeyId());
            // 根据题目id查询点赞人数和点跪人数，并且查询是否点赞或者点跪
            int likeCount = Db
                .queryInt(
                    "select count(*) from subject_like where subjectId = ? and type = 1",
                    subjectId);
            int unlikeCount = Db
                .queryInt(
                    "select count(*) from subject_like where subjectId = ? and type = 2",
                    subjectId);
            sign[0] = likeCount;
            sign[1] = unlikeCount;
            sv.setUserSign(userSign);
            Like like = likeDao
                .findFirst(
                    "select * from subject_like where userId = ? and subjectId = ? and type = 1",
                    userId, subjectId);
            if (like != null)
            {
                userSign[0] = true;
            }
            else
            {
                userSign[0] = false;
            }
            Like unlike = likeDao
                .findFirst(
                    "select * from subject_like where userId = ? and subjectId = ? and type = 2",
                    userId, subjectId);
            if (unlike != null)
            {
                userSign[1] = true;
            }
            else
            {
                userSign[1] = false;
            }
            sv.setSign(sign);
            Integer n = Db
                .queryInt(
                    "select * from subject_like where userId = ? and subjectId = ? and type = 3",
                    userId, subjectId);
            if (n != null)
            {
                sv.setFavorite(true);
            }
            else
            {
                sv.setFavorite(false);
            }
            return sv;
        }
    }

    /**
     * 分页查询 包括用户点赞情况信息
     * 
     * @param userId
     * @param keyId
     * @param pageNum
     * @return
     */
    public Ret querySubjectInfo(int userId, int keyId, int pageNum, int pageSize)
    {
        Page<Record> page = getSubjectListByPage(keyId, pageNum, pageSize);
        boolean[] userSign = new boolean[2];
        int[] sign = new int[2];

        List<Record> list = page.getList();
        if (list == null || list.size() == 0)
        {
            return Ret.fail("msg", "该知识点没有题目");
        }
        List<SubjectVo> subs = new ArrayList<SubjectVo>();
        for (Record record : list)
        {

            SubjectVo subVo = FastJson.getJson().parse(record.toJson(),
                SubjectVo.class);

            int likeCount = Db
                .queryInt(
                    "select count(*) from subject_like where subjectId = ? and type = 1",
                    subVo.getSubjectId());
            int unlikeCount = Db
                .queryInt(
                    "select count(*) from subject_like where subjectId = ? and type = 2",
                    subVo.getSubjectId());
            sign[0] = likeCount;
            sign[1] = unlikeCount;
            subVo.setUserSign(userSign);
            Like like = likeDao
                .findFirst(
                    "select * from subject_like where userId = ? and subjectId = ? and type = 1",
                    userId, subVo.getSubjectId());
            if (like != null)
            {
                userSign[0] = true;
            }
            else
            {
                userSign[0] = false;
            }
            Like unlike = likeDao
                .findFirst(
                    "select * from subject_like where userId = ? and subjectId = ? and type = 2",
                    userId, subVo.getSubjectId());
            if (unlike != null)
            {
                userSign[1] = true;
            }
            else
            {
                userSign[1] = false;
            }
            subVo.setSign(sign);
            Record n = Db
                .findFirst(
                    "select * from subject_like where userId = ? and subjectId = ? and type = 3",
                    userId, subVo.getSubjectId());
            if (n != null)
            {
                subVo.setFavorite(true);
            }
            else
            {
                subVo.setFavorite(false);
            }
            subs.add(subVo);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", subs);
        map.put("totalPage", page.getTotalPage());
        map.put("totalRow", page.getTotalRow());
        map.put("isFirstPage", page.isFirstPage());
        map.put("isLastPage", page.isLastPage());

        // 根据题目id查询点赞人数和点跪人数，并且查询是否点赞或者点跪
        return Ret.ok("data", map);
    }

    /**
     * 查询题目分页
     * 
     * @param keyId
     * @param pageNum
     * @param pageSize
     * @return
     */
    private Page<Record> getSubjectListByPage(int keyId, int pageNum,
        int pageSize)
    {
        String select = "select s.*,k.name as keyName";
        if (keyId != 0)
        {
            String sql = "from subject s inner join keypoint k on s.keyId=k.keyId where s.keyId = ? and s.hide = 0 order by createTime desc";
            return Db.paginate(pageNum, pageSize, select, sql, keyId);
        }
        return null;
    }

    /**
     * 分页查询全部信息包括知识点和学科名字
     * @param param 
     * @param keyId 
     * @param desc 
     * @param orderPara 
     */
    public Page<Record> getSubjectByPage(int keyId, Map<String, String> param)
    {
        
        String name = param.get("name");
        int pageNum = Integer.valueOf(param.get("page"));
        int pageSize = Integer.valueOf(param.get("size"));
        String orderPara = param.get("order");
        String desc = param.get("desc");
        
        String select = "select s.*,k.name as keyName,m.name as majorName";
        if (keyId != 0 && StrKit.notBlank(name))
        {
            String sql = "from (subject s inner join keypoint k on s.keyId=k.keyId) inner join major m on k.majorId = m.majorId where s.keyId = ? and s.name like ? order by s.createTime desc";
            return Db.paginate(pageNum, pageSize, select, sql, keyId, "%"
                + name + "%");
        }
        else if (keyId != 0)
        {
            String sql = "from (subject s inner join keypoint k on s.keyId=k.keyId) inner join major m on k.majorId = m.majorId where s.keyId = ? order by s.createTime desc";
            return Db.paginate(pageNum, pageSize, select, sql, keyId);
        }
        else if (StrKit.notBlank(name))
        {
            String sql = "from (subject s inner join keypoint k on s.keyId=k.keyId) inner join major m on k.majorId = m.majorId where s.name like ? order by s.createTime desc";
            return Db
                .paginate(pageNum, pageSize, select, sql, "%" + name + "%");
        }
        else
        {
            String sql = "from (subject s inner join keypoint k on s.keyId=k.keyId) inner join major m on k.majorId = m.majorId order by s.createTime desc";
            return Db.paginate(pageNum, pageSize, select, sql);
        }

    }

    public Ret update(Map<String, String> param)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("subjectId", param.get("subjectId"));
        map.put("name", param.get("name"));
        map.put("majorId", param.get("majorId"));
        map.put("keyId", param.get("keyId"));
        map.put("pic", param.get("pic"));
        map.put("apic", param.get("apic"));
        map.put("hide", param.get("hide"));
        map.put("answer", param.get("answer"));
        map.put("answerNum", param.get("answerNum"));
        map.put("hint", param.get("hint"));
        map.put("author", param.get("author"));
        map.put("tags", param.get("tags"));
        map.put("video", param.get("video"));
        map.put("modifyTime", new Date());
        Record record = new Record().setColumns(map);
        boolean ret = Db.update("subject", "subjectId", record);
        if (ret)
        {
            return Ret.ok("msg", "更新题目成功");
        }
        else
        {
            return Ret.fail("msg", "更新题目失败");
        }
    }

    public Ret delete(int subjectId)
    {
        int ret = Db.update("delete from subject where subjectId = ?",
            subjectId);
        if (ret > 0)
        {
            return Ret.ok("msg", "删除题目成功");
        }
        else
        {
            return Ret.fail("msg", "删除题目失败");
        }
    }

    public Subject getSubjectInfoById(int subjectId)
    {
        return subjectDao.findFirst(
            "select * from subject where subjectId=? limit 1", subjectId);
    }

    public List<Subject> searchSubjectListByName(String name)
    {
        return subjectDao.find("select * from subject where name like ?", "%"
            + name + "%");
    }

    /**
     * 收藏
     * @param userId
     * @param majorId
     * @param subjectId
     * @param type
     * @return
     */
    public boolean favorite(int userId, int majorId, int subjectId)
    {
        Record re = Db.findFirst(
            "select * from subject where subjectId=? limit 1", subjectId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("subjectId", subjectId);
        map.put("userId", userId);
        map.put("subjectName", re.get("name"));
        map.put("majorId", majorId);
        map.put("type", 3);
        map.put("createTime", new Date());
        Record record = new Record().setColumns(map);
        return Db.save("subject_like", "id", record);
    }

    /**
     * 取消收藏
     * @param userId
     * @param subjectId
     * @return
     */
    public int deleteFavorite(int userId, int subjectId)
    {
        return Db.update(
            "delete from subject_like where userId = ? and subjectId = ?",
            userId, subjectId);
    }

    /**
     * 获取收藏列表
     * @param userId
     * @param subjectId
     * @param majorId
     * @return
     */
    public Ret listFavorite(int userId, int majorId)
    {
        List<Record> list = Db
            .find(
                "select * from subject_like where userId = ? and majorId = ? and type = 3",
                userId, majorId);
        if (list == null || list.size() == 0)
        {
            return Ret.fail("msg", "没有该信息");
        }
        else
        {
            return Ret.ok("data", list);
        }
    }
}
