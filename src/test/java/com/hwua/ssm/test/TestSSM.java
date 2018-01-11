package com.hwua.ssm.test;

import com.alibaba.fastjson.JSON;
import com.hwua.ssm.mapper.AuthMapper;
import com.hwua.ssm.mapper.RoleMapper;
import com.hwua.ssm.mapper.UserMapper;
import com.hwua.ssm.po.Auth;
import com.hwua.ssm.po.Role;
import com.hwua.ssm.po.User;
import com.hwua.ssm.service.AuthService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSSM {

    @Test
    public void testMD5(){
        //密码加密
        String str = "aaaaaa";
        String s = DigestUtils.md5DigestAsHex(str.getBytes());
        System.out.println(s);
    }

    @Test
    public void queryUser(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ctx.getBean(UserMapper.class);
        User user1 = new User();
        user1.setUserName("1");
        String str = "123456";
        String s = DigestUtils.md5DigestAsHex(str.getBytes());
        user1.setPassword(s);
        User user = mapper.queryUser(user1);
        System.out.println("user = " + user);
    }

    @Test
    public void queryByDbid(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        AuthMapper authMapper = ctx.getBean(AuthMapper.class);
        List<Auth> auths = authMapper.queryByDbid(-1);
        System.out.println(JSON.toJSONString(auths));
    }

    @Test
    public void queryRole(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        RoleMapper roleMapper = ctx.getBean(RoleMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("roleName","1");
//        map.put("roleCode","hr");
//        map.put("valid",1);
        map.put("start",(1-1)*2);
        map.put("rows",2);
        List<Role> roles = roleMapper.query(map);
        System.out.println(JSON.toJSONString(roles));
    }

    @Test
    public void queryByUserAuths(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        AuthMapper authMapper = ctx.getBean(AuthMapper.class);
        List<Auth> auths = authMapper.queryByUserId(27);
        System.out.println(JSON.toJSONString(auths));
    }

    @Test
    public void test(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        AuthService authService = ctx.getBean(AuthService.class);
        List<Auth> auths = authService.queryByUserId(31);
        System.out.println(JSON.toJSONString(auths));
    }

}
