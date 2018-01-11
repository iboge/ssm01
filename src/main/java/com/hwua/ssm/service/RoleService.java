package com.hwua.ssm.service;

import com.hwua.ssm.po.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

    public Map<String,Object> query(Map<String,Object> map);

    public int doInsert(Role role);

    public int doUpdate(Role role);

    public List<Map<String,Object>> queryValid(Integer userId);

    public int grantAuth(Integer roleId,Integer[] authIds);

}
