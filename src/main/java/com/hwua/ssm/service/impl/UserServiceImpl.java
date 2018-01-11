package com.hwua.ssm.service.impl;

import com.hwua.ssm.mapper.UserMapper;
import com.hwua.ssm.po.User;
import com.hwua.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> query(Map<String, Object> map) {
        int count = userMapper.queryCount(map);
        List<User> roles = userMapper.query(map);
        Map<String,Object> maps = new HashMap<>();
        maps.put("total",count);
        maps.put("rows",roles);
        return maps;
    }

    @Override
    public int doInsert(User user) {
        return userMapper.doInsert(user);
    }

    @Override
    public int doUpdate(User user) {
        return userMapper.doUpdate(user);
    }

    @Transactional
    @Override
    public int grantRole(Integer userId, Integer[] roleIds) {
        int i = userMapper.deleteByUserId(userId);
        List<Map<String,Integer>> param = new ArrayList<>();
        for(Integer roleId:roleIds){
            Map<String,Integer> map = new HashMap<>();
            map.put("userId",userId);
            map.put("roleId",roleId);
            param.add(map);
        }
        int insertRole = userMapper.insertRole(param);
        return insertRole+i;
    }

    @Override
    public Map<String, Object> login(User user) {
        Map<String, Object> map = new HashMap<>();
        User queryByUserName = userMapper.queryByUserName(user.getUserName());
        if(queryByUserName!=null) {
            User queryUser = userMapper.queryUser(user);
            if(queryUser!=null){
                if(queryUser.getValid().equals("1")){
                    map.put("success",queryUser.getDbid());
                    map.put("name",queryUser.getUserName());
                }else{
                    map.put("error","账号无效！");
                }
            }else{
                map.put("error","密码错误！");
            }
        }else{
            map.put("error","用户名不存在！");
        }
        return map;
    }

    @Override
    public int updatepwd(User user) {
        return userMapper.updatepwd(user);
    }
}
