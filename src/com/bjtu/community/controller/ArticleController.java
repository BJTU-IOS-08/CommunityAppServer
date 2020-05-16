package com.bjtu.community.controller;

import com.bjtu.community.entity.Article;
import com.bjtu.community.entity.ArticleAction;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import java.util.List;

@IocBean
public class ArticleController {
    @Inject
    Dao dao;

    @Ok("json")
    @Fail("http:500")
    @At("/article/list")
    @POST
    public Object getList(
            @Param("phone_number") String phone,
            @Param("psw") String psw) {

        NutMap re = new NutMap();
        try {
            List<Article> a = dao.query(Article.class, Cnd.orderBy());
            if (a != null) {
                re.put("status", 0);
                re.put("msg", "OK");
                re.put("body", a);
            } else {
                re.put("status", 1);
                re.put("msg", "error");
            }
        } catch (Throwable e) {
            re.put("status", 1);
            re.put("msg", "error");
        }
        return re;
    }

    @Ok("json")
    @Fail("http:500")
    @At("/article/detail")
    @POST
    public Object detail(
            @Param("pid") String pid) {

        NutMap re = new NutMap();
        try {
            Article a = dao.fetch(Article.class, Cnd.where("pid", "=", pid));
            if (a != null) {
                re.put("status", 0);
                re.put("msg", "OK");
                re.put("body", a);
            } else {
                re.put("status", 1);
                re.put("msg", "error");
            }
        } catch (Throwable e) {
            re.put("status", 1);
            re.put("msg", "error");
        }
        return re;
    }

    @Ok("json")
    @Fail("http:500")
    @At("/article/action")
    @POST
    public Object modifyProfile(
            @Param("uid") String uid,
            @Param("token") String token,
            @Param("pid") String pid,
            @Param("comment") String comment,
            @Param("type") Integer type) {

        NutMap re = new NutMap();
        try {
            ArticleAction articleAction = new ArticleAction();
            articleAction.setUid(uid);
            articleAction.setComment(comment);
            articleAction.setType(type);
            articleAction.setPid(pid);
            dao.insert(articleAction);
            re.put("status", 0);
            re.put("msg", "OK");
        } catch (Throwable e) {
            re.put("status", 1);
            re.put("msg", "error");
        }
        return re;
    }
}
