package com.mathclub.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mathclub.interceptor.AdminAuthInterceptor;
import com.mathclub.kit.StringKit;
import com.mathclub.model.KeyPoint;
import com.mathclub.model.Major;
import com.mathclub.model.User;

@Before(AdminAuthInterceptor.class)
// 添加后台权限拦截
public class KeyController extends BaseController
{
    private final KeyPoint keyDao = new KeyPoint().dao();
    public static final Major majorDao = new Major().dao();

    /**
     * 添加知识点
     */
    @ActionKey("/math:addKey")
    public void addKey()
    {

        User user = getLoginUser();
        if (user == null)
        {
            renderJson(Ret.fail("msg", "没有该用户"));
            return;
        }

        String name = getPara("name");
        String majorId = getPara("majorId");
        LogKit.info("name=" + name + "|majorId=" + majorId);
        if (StrKit.isBlank(name) || StrKit.isBlank(majorId)
            || Integer.valueOf(majorId) == 0)
        {
            renderJson(Ret.fail("msg", "请求参数错误"));
            return;
        }
        Major major = majorDao.findFirst(
            "select * from major where majorId = ?", majorId);
        if (major == null)
        {
            renderJson(Ret.fail("msg", "没有该学科"));
            return;
        }
        Record record = new Record().set("name", name).set("majorId", majorId)
            .set("majorName", major.get("name"));
        boolean res = Db.save("keypoint", "keyId", record);
        if (res)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("keyId", record.get("keyId"));
            renderJson(Ret.ok("data", map).set("msg", "添加知识点成功"));
            return;
        }
        else
        {
            renderJson(Ret.fail("msg", "添加知识点失败"));
            return;
        }
    }

    /**
     * 添加知识点
     */
    @ActionKey("/math:updateKey")
    public void updateKey()
    {
        String keyId = getPara("keyId");
        String name = getPara("name");
        LogKit.info("math:updateKey req keyId=" + keyId + "|name=" + name);
        if (StrKit.isBlank(keyId) || StrKit.isBlank(name))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        int result = Db.update("update keypoint set name=? where keyId=? ",
            name, keyId);
        if (result > 0)
        {
            renderJson(Ret.ok("msg", "更新知识点成功"));
            return;
        }
        else
        {
            renderJson(Ret.fail("msg", "更新知识点失败"));
            return;
        }
    }

    /**
     * 删除知识点
     */
    @ActionKey("/math:deleteKey")
    public void deleteKey()
    {
        String keyId = getPara("keyId");
        LogKit.info("math:deleteKey req keyId=" + keyId);
        if (StrKit.isBlank(keyId) || (Integer.valueOf(keyId) == 0))
        {
            renderJson(Ret.fail("msg", "请求参数为空"));
            return;
        }
        int result = Db.update("delete from keypoint where keyId=? ", keyId);
        if (result > 0)
        {
            renderJson(Ret.ok("msg", "删除知识点成功"));
            return;
        }
        else
        {
            renderJson(Ret.fail("msg", "删除知识点失败"));
            return;
        }
    }

    /**
     * 获取知识点列表根据学科id
     */
    @ActionKey("/math:listKey")
    public void getKeyList()
    {
        String majorId = getPara("majorId");
        LogKit.info("math:listKey req majorId=" + majorId);
        if (StrKit.isBlank(majorId))
        {
            renderJson(Ret.fail("msg", "请求参数错误"));
            return;
        }
        List<KeyPoint> list = keyDao.find(
            "select * from keypoint where majorId = ?", majorId);
        if (list != null && list.size() > 0)
        {
            renderJson(Ret.ok("data", list));
        }
        else
        {
            renderJson(Ret.fail("msg", "没有知识点"));
        }
    }

}
