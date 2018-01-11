package com.hwua.ssm.service.impl;

import com.hwua.ssm.mapper.RoleMapper;
import com.hwua.ssm.po.Role;
import com.hwua.ssm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Map<String, Object> query(Map<String, Object> map) {
        int count = roleMapper.queryCount(map);
        List<Role> roles = roleMapper.query(map);
        Map<String,Object> maps = new HashMap<>();
        maps.put("total",count);
        maps.put("rows",roles);
        return maps;
    }

    @Override
    public int doInsert(Role role) {
        return roleMapper.doInsert(role);
    }

    @Override
    public int doUpdate(Role role) {
        return roleMapper.doUpdate(role);
    }

    @Override
    public List<Map<String, Object>> queryValid(Integer userId) {
        List<Map<String, Object>> maps = roleMapper.queryValid();
        List<Integer> integers = roleMapper.queryByUserId(userId);
        for(Map<String,Object> map : maps){
            if(integers.contains(map.get("id"))){
                map.put("checked",true);
            }
        }
        return maps;
    }

    @Transactional
    @Override
    public int grantAuth(Integer roleId, Integer[] authIds) {
        int i = roleMapper.deleteByRoleId(roleId);
        List<Map<String,Integer>> param = new ArrayList<>();
        for(Integer authId:authIds){
            Map<String,Integer> map = new HashMap<>();
            map.put("roleId",roleId);
            map.put("authId",authId);
            param.add(map);
        }
        int insertAuth = roleMapper.insertAuth(param);

        return insertAuth+i;
    }

}
