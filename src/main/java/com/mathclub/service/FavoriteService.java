package com.mathclub.service;

import java.util.List;

import com.jfinal.kit.Ret;
import com.mathclub.model.Favorite;

@SuppressWarnings("rawtypes")
public class FavoriteService {

    public static final FavoriteService me = new FavoriteService();
    final Favorite dao = new Favorite().dao();

    /**
     * 收藏
     * @param myId  收藏的用户 id，即当前登录用户
     * @param refTypeTable 被收藏的表名
     * @param refId 被收藏的表名中的相应的 id 值
     * @param isAdd true 为收藏，false 为取消收藏，null 需要判断是否已被收藏
     */
    /*public Ret favorite(int myId, String refTypeTable, int refId, Boolean isAdd) {
        Favorite.checkRefTypeTable(refTypeTable);
        if (isAdd != null) {
            if (isAdd) {
                return save(myId, refTypeTable, refId);
            } else {
                return delete(myId, refTypeTable, refId);
            }
        } else {
            return favorite(myId, refTypeTable, refId);
        }
    }

    private Ret favorite(int myId, String refTypeTable, int refId) {
        String sql = "select accountId from favorite where accountId=? and refType=? and refId=? limit 1";
        if (Db.queryInt(sql, myId, Favorite.getRefType(refTypeTable), refId) != null) {
            return delete(myId, refTypeTable, refId);
        } else {
            return save(myId, refTypeTable, refId);
        }
    }

    // 获取被收藏资源的创建者
    private Integer getUserIdOfRef(String refTypeTable, int refId) {
        return Db.queryInt("select accountId from " + refTypeTable + " where id=? limit 1", refId);
    }

    *//**
     * 收藏
     *//*
    private Ret save(final int myId, final String refTypeTable, final int refId) {
        final Integer userId = getUserIdOfRef(refTypeTable, refId);
        if (userId == null) {
            return Ret.fail("msg", "未找到资源，可能已经被删除");
        }
        if (myId == userId) {
            return Ret.fail("msg", "不能收藏自己的文章");
        }
        if (Db.queryInt("select accountId from favorite where accountId=? and refType=? and refId=? limit 1",
                myId, Favorite.getRefType(refTypeTable), refId) != null) {
            return Ret.fail("msg", "已经收藏过该文章");
        }

        boolean isOk = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int n = Db.update("insert into favorite(accountId, refType, refId, createAt) values(?, ?, ?, ?)",
                        myId, Favorite.getRefType(refTypeTable), refId, new Date());

                if (n > 0) {
                    n = Db.update("update " + refTypeTable + " set favoriteCount=favoriteCount+1 where id=? limit 1", refId);
                }
                return n > 0;
            }
        });

        return isOk ? Ret.ok() : Ret.fail("msg", "收藏失败");
    }

    *//**
     * 取消收藏
     *//*
    private Ret delete(final int myId, final String refTypeTable, final int refId) {
        boolean isOk = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int n = Db.update("delete from favorite where accountId=? and refType=? and refId=? limit 1", myId, Favorite.getRefType(refTypeTable), refId);
                if (n > 0) {
                    n = Db.update("update " + refTypeTable + " set favoriteCount=favoriteCount-1 where id=? and favoriteCount>0 limit 1", refId);
                }
                return n > 0;
            }
        });
        return isOk ? Ret.ok() : Ret.fail("msg", "取消收藏失败");
    }

    *//**
     * 对 refType + refId 指向的资源，是否已收藏
     *//*
    public boolean isFavorite(int accountId, int refType, int refId) {
        String sql = "select accountId from favorite where accountId=? and refType=? and refId=? limit 1";
        return Db.queryInt(sql, accountId, refType, refId) != null;
    }

    *//**
     * 设置 article detail 页面的收藏状态
     *//*
    public void setFavoriteStatus(Account loginAccount, String refTypeTable, Model refObj, Ret ret) {
        if (loginAccount != null) {
            boolean isFavorite = isFavorite(loginAccount.getId(), Favorite.getRefType(refTypeTable), refObj.getInt("id"));
            ret.set("isFavoriteActive", isFavorite ? "active" : "");
            ret.set("isFavoriteAdd", isFavorite ? "false" : "true");
        } else {
            ret.set("isFavoriteActive", "");
            ret.set("isFavoriteAdd", "true");
        }
        int favoriteCount = refObj.getInt("favoriteCount");
        ret.set("favoriteCount", favoriteCount > 0 ? favoriteCount : "");
    }

    *//**
     * 删除 project 时，要删除相关的收藏
     *//*
    public void deleteByProjectDeleted(int projectId) {
        deleteByRefDeleted(Favorite.REF_TYPE_PROJECT, projectId);
    }

    *//**
     * 删除 share 时，要删除相关的收藏
     *//*
    public void deleteByShareDeleted(int shareId) {
        deleteByRefDeleted(Favorite.REF_TYPE_SHARE, shareId);
    }

    *//**
     * 删除 feedback 时，要删除相关的收藏
     *//*
    public void deleteByFeedbackDeleted(int feedbackId) {
        deleteByRefDeleted(Favorite.REF_TYPE_FEEDBACK, feedbackId);
    }

    *//**
     * 删除被引用的资源时，要删除相关的收藏
     *//*
    private void deleteByRefDeleted(int refType, int refId) {
        Db.update("delete from favorite where refType=? and refId=?", refType, refId);
    }

    *//**
     * 通过 favoriteId 删除 favorite 记录，需要检查当前记录的创建者是不是删除者本人
     *//*
    public void deleteByFavoriteId(int accountId, int favoriteId) {
        Favorite f = dao.findById(favoriteId);
        if (f != null) {
            delete(accountId, Favorite.getRefTable(f.getRefType()), f.getRefId());
        }
    }

    *//**
     * 获取当前用户的收藏列表
     *//*
    public List<Favorite> findAll(int userId) {
        List<Favorite> list = dao.find("select * from favorite where userId=? order by id desc", userId);
        loadRef(list);
        return list;
    }

    private void loadRef(List<Favorite> list) {
        for (Favorite f : list) {
            Model m ;
            if (f.getRefType() == Favorite.REF_TYPE_PROJECT) {
                m = ProjectService.me.findById(f.getRefId());
            } else if (f.getRefType() == Favorite.REF_TYPE_SHARE) {
                m = ShareService.me.findById(f.getRefId());
            } else if (f.getRefType() == Favorite.REF_TYPE_FEEDBACK) {
                m = FeedbackService.me.findById(f.getRefId());
            } else {
                throw new RuntimeException("refType 不正确：" + f.getRefType());
            }
            if (m != null) {
                f.put("title", m.getStr("title"));
            }
        }
    }*/
}
