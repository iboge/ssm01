package com.hwua.ssm.service.impl;

import com.hwua.ssm.mapper.AuthMapper;
import com.hwua.ssm.po.Auth;
import com.hwua.ssm.po.User;
import com.hwua.ssm.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> queryAllAuth() {
        return authMapper.queryByDbid(-1);
    }

    @Override
    public int doUpdate(Auth auth) {
        return authMapper.doUpdate(auth);
    }

    @Override
    public int doInsert(Auth auth) {
        return authMapper.doInsert(auth);
    }

    @Override
    public List<Map<String, Object>> getAuthValid(Integer roleId) {
        List<Map<String, Object>> authValid = authMapper.queryValid(-1);
        List<Integer> auths = authMapper.queryByRoleId(roleId);
        parseAuth(authValid,auths);
        return authValid;
    }

    /**
     * 递归设置checked属性
     * @param validAuth
     * @param auths
     */
    private void parseAuth(List<Map<String,Object>> validAuth,List<Integer> auths){
        for(Map<String,Object> auth : validAuth){
            if(auths.contains(auth.get("id"))){
                auth.put("checked",true);
            }
            List<Map<String,Object>> children = (List<Map<String,Object>>)auth.get("children");
            parseAuth(children,auths);
        }
    }

    @Override
    public List<Auth> queryByUserId(Integer userId) {
        List<Auth> userAuths = authMapper.queryByUserId(userId);
        Auth father = null,son = null;
        List<Auth> children = null;
        for(int i=userAuths.size()-1;i>=0;i--){
            father = userAuths.get(i);
            children = new ArrayList<>();
            for(int j=userAuths.size()-1;j>=0;j--){
                son = userAuths.get(j);
                if(son.getParentId().equals(father.getDbid())){
                    children.add(son);
                    userAuths.remove(son);
                }
            }
            father.setChildren(children);
        }
        for(int i=0;i<userAuths.size();i++){
            Auth auth = userAuths.get(i);
            if(auth.getLayer()!=1){
                userAuths.remove(i);
                i--;
            }
        }
        return userAuths;
    }

    @Override
    public List<Auth> queryUserAuth(Integer userId) {
        List<Auth> queryUserAuths = authMapper.queryUserAuth(userId);
        return queryUserAuths;
    }

}
