package com.bjtu.community.controller;

import com.bjtu.community.entity.User;
import com.bjtu.community.entity.VerifyCode;
import com.bjtu.community.utils.Message;
import com.bjtu.community.utils.MyUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

@IocBean
public class UserController {
    @Inject
    Dao dao;

    @Ok("json")
    @Fail("http:500")
    @At("/user/login")
    @POST
    public Object login(
            @Param("phone_number") String phone,
            @Param("psw") String psw) {

        NutMap re = new NutMap();
        try {
            User u = dao.fetch(User.class, Cnd.where("phone_number", "=", phone).and("psw", "=", psw));
            if (u != null) {
                u.setToken(MyUtils.getRandomKey());
                dao.update(u);
                re.put("status", 0);
                re.put("msg", "OK");
                re.put("uid", u.getUserId());
                re.put("token", u.getToken());
            } else {
                re.put("status", 1);
                re.put("msg", "用户名或密码错误");
            }
        } catch (Throwable e) {
            re.put("status", 1);
            re.put("msg", "用户名或密码错误");
        }
        return re;
    }

    @Ok("json")
    @Fail("http:500")
    @At("/user/profile")
    @POST
    public Object getProfile(
            @Param("uid") String uid,
            @Param("token") String token) {

        NutMap re = new NutMap();
        try {
            User u = dao.fetch(User.class, Cnd.where("user_id", "=", uid).and("token", "=", token));
            if (u != null) {
                u.setToken(MyUtils.getRandomKey());
                dao.update(u);
                re.put("status", 0);
                re.put("msg", "OK");
                re.put("user", u);
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
    @At("/user/modify_profile")
    @POST
    public Object modifyProfile(
            @Param("uid") String uid,
            @Param("token") String token) {

        NutMap re = new NutMap();
        try {
            User u = dao.fetch(User.class, Cnd.where("user_id", "=", uid).and("token", "=", token));
            if (u != null) {
                u.setToken(MyUtils.getRandomKey());
                dao.update(u);
                re.put("status", 0);
                re.put("msg", "OK");
                re.put("user", u);
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
    @At("/user/verify")
    @POST
    public Object modifyProfile(
            @Param("phone") String phone) {

        NutMap re = new NutMap();
        try {
            VerifyCode code = new VerifyCode();
            code.setPhoneNumber(phone);
            code.setCode(new Message().mSsendMessage(phone));
            dao.insert(code);
            re.put("status", 0);
            re.put("msg", "OK");
        } catch (Throwable e) {
            re.put("status", 1);
            re.put("msg", "error");
        }
        return re;
    }
}
