package com.hwua.ssm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hwua.ssm.mapper.AuthMapper;
import com.hwua.ssm.po.Auth;
import com.hwua.ssm.po.User;
import com.hwua.ssm.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping("/toauth")
    public String main(){
        return "auth";
    }

    @RequestMapping(value = "/getAllAuth",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getAuth(){
        List<Auth> auths = authService.queryAllAuth();
        return JSON.toJSONString(auths);
    }

    @RequestMapping(value = "/addOrUpdate",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String update(Auth auth){
        System.out.println("auth.getDbid() = " + auth.getDbid());
        int i = 0;
        if(auth.getDbid()==null){
            i = authService.doInsert(auth);
        }else{
            i = authService.doUpdate(auth);
        }
        JSONObject json = new JSONObject();
        if(i==1){
            json.put("msg",true);
        }else{
            json.put("msg",false);
        }
        return json.toJSONString();
    }

    @RequestMapping(value = "/getAuthValid" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getAuthValid(Integer roleId){
        List<Map<String, Object>> authValid = authService.getAuthValid(roleId);
        System.out.println(JSON.toJSONString(authValid));
        return JSON.toJSONString(authValid);
    }

    @RequestMapping(value = "/getUserAuths", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getUserAuths(HttpSession session){
        User user = (User) session.getAttribute("user");
        System.out.println("user = " + user);
        return null;
    }

    @RequestMapping(value = "/queryUserAuths", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String queryUserAuths(HttpSession session){
        Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
        List<Auth> queryUserAuth = authService.queryUserAuth((Integer) user.get("success"));
        System.out.println("queryUserAuth = " + queryUserAuth);
        JSONObject json = new JSONObject();
        for(int i=0;i<=queryUserAuth.size()-1;i++){
            Auth auth = queryUserAuth.get(i);
            System.out.println(auth.getDbid());
            if(auth.getDbid()==15){
                json.put("msg",true);
                break;
            }else{
                json.put("msg",false);
            }
        }
        System.out.println("json = " + json);
        return json.toJSONString();
    }

}
