package com.hwua.ssm.service;

import com.hwua.ssm.po.User;

import java.util.Map;

public interface UserService {

    public Map<String,Object> query(Map<String,Object> map);

    public int doInsert(User user);

    public int doUpdate(User user);

    public int grantRole(Integer userId,Integer[] roleIds);

    public Map<String, Object> login(User user);

    public int updatepwd(User user);

}
