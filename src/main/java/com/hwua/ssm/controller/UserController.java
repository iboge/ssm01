package com.hwua.ssm.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hwua.ssm.po.Auth;
import com.hwua.ssm.po.User;
import com.hwua.ssm.service.AuthService;
import com.hwua.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @RequestMapping("/touser")
    public String main(){
        return "user";
    }

    @RequestMapping(value = "/getUser",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getUser(String userName,String realName,Integer valid,Integer page,Integer rows){
        Map<String,Object> map = new HashMap<>();
        map.put("userName", StringUtils.isEmpty(userName) ? null : userName);
        map.put("realName", StringUtils.isEmpty(realName) ? null : realName);
        map.put("valid",valid);
        map.put("start",(page -  1) * rows);
        map.put("rows",rows);
        Map<String, Object> query = userService.query(map);
        return JSON.toJSONString(query);
    }

    @RequestMapping(value = "/insertUser",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String insertUser(User user){
        String password = user.getPassword();
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(s);
        int i = 0;
        if(user.getDbid()!=null){
            i = userService.doUpdate(user);
        }else {
            i = userService.doInsert(user);
        }
        JSONObject json = new JSONObject();
        if(i==1){
            json.put("msg",true);
        }else{
            json.put("msg",false);
        }
        return json.toJSONString();
    }

    @RequestMapping(value = "/grantRole" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String grantRole(Integer userId,Integer[] userIds){
        System.out.println("userId = [" + userId + "], userIds = [" + Arrays.toString(userIds) + "]");
        int i = userService.grantRole(userId,userIds);
        JSONObject json = new JSONObject();
        if(i>0){
            json.put("msg",true);
        }else{
            json.put("msg",false);
        }
        return JSON.toJSONString(json);
    }

    @RequestMapping(value = "/login" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String queryUser(String userName, String password, HttpSession session){
        System.out.println("username = [" + userName + "], password = [" + password + "]");
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        User user = new User();
        user.setUserName(userName);
        user.setPassword(s);
        Map<String, Object> login = userService.login(user);
        JSONObject json = new JSONObject();
        if(login.containsKey("error")){
            json.put("error",login. get("error"));
        }else{
            json.put("success",login.get("success"));
            List<Auth> auths = authService.queryByUserId((Integer) login.get("success"));
            List<Auth> allAuths = authService.queryUserAuth((Integer) login.get("success"));
            session.setAttribute("auths",auths);
            session.setAttribute("allAuths",allAuths);
            session.setAttribute("user",login);
        }
        return JSON.toJSONString(json);
    }

    @RequestMapping(value = "/updatePassword" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String updatePassword(Integer userId, String password){
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        User user = new User();
        user.setPassword(s);
        user.setDbid(userId);
        int i = userService.updatepwd(user);
        JSONObject json = new JSONObject();
        if(i==1){
            json.put("msg",true);
        }else{
            json.put("msg",false);
        }
        System.out.println(JSON.toJSONString(json));
        return JSON.toJSONString(json);
    }
    
    @RequestMapping("/end")
    public String end(HttpSession session){
        session.invalidate();
        return "redirect:/main";
    }

}
