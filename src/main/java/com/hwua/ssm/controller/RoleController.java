package com.hwua.ssm.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hwua.ssm.po.Auth;
import com.hwua.ssm.po.Role;
import com.hwua.ssm.service.AuthService;
import com.hwua.ssm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @RequestMapping("/torole")
    public String main(){
        return "role";
    }

    @RequestMapping(value = "/getRole",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getRole(String roleName,String roleCode,String valid,Integer page,Integer rows){
        System.out.println("roleName = [" + roleName + "], roleCode = [" + roleCode + "], valid = [" + valid + "], page = [" + page + "], rows = [" + rows + "]");
        Map<String,Object> map = new HashMap<>();
        map.put("roleName", StringUtils.isEmpty(roleName) ? null : roleName);
        map.put("roleCode", StringUtils.isEmpty(roleCode) ? null : roleCode);
        map.put("valid",valid);
        map.put("start",(page -  1) * rows);
        map.put("rows",rows);
        Map<String, Object> query = roleService.query(map);
        return JSON.toJSONString(query);
    }

    @RequestMapping(value = "/insertRole",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String insertUser(Role role){
        int i = 0;
        if(role.getDbid()!=null){
            i = roleService.doUpdate(role);
        }else {
            i = roleService.doInsert(role);
        }
        JSONObject json = new JSONObject();
        if(i==1){
            json.put("msg",true);
        }else{
            json.put("msg",false);
        }
        return json.toJSONString();
    }

    @RequestMapping(value = "/getRoleValid" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getAuthValid(Integer userId){
        List<Map<String, Object>> roleValid = roleService.queryValid(userId);
        System.out.println(JSON.toJSONString(roleValid));
        return JSON.toJSONString(roleValid);
    }

    @RequestMapping(value = "/grantAuth" ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String grantAuth(Integer roleId,Integer[] authIds){
        int i = roleService.grantAuth(roleId, authIds);
        JSONObject json = new JSONObject();
        if(i>0){
            json.put("msg",true);
        }else{
            json.put("msg",false);
        }
        return JSON.toJSONString(json);
    }

    @RequestMapping(value = "/queryRoleAuths", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String queryUserAuths(HttpSession session){
        Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
        List<Auth> queryUserAuth = authService.queryUserAuth((Integer) user.get("success"));
        System.out.println("queryUserAuth = " + queryUserAuth);
        JSONObject json = new JSONObject();
        for(int i=0;i<=queryUserAuth.size()-1;i++){
            Auth auth = queryUserAuth.get(i);
            System.out.println(auth.getDbid());
            if(auth.getDbid()==46){
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
