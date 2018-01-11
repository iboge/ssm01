package com.hwua.ssm.mapper;

import com.hwua.ssm.po.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper {

    public int queryCount(Map<String,Object> map);

    public List<Role> query(Map<String,Object> map);

    public int doInsert(Role role);

    public int doUpdate(Role role);

    public List<Map<String,Object>> queryValid();

    public List<Integer> queryByUserId(Integer userId);

    public int deleteByRoleId(Integer roleId);

    public int insertAuth(List<Map<String,Integer>> list);

}
