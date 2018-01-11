package com.hwua.ssm.mapper;

import com.hwua.ssm.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    public int queryCount(Map<String,Object> map);

    public List<User> query(Map<String,Object> map);

    public int doInsert(User user);

    public int doUpdate(User user);

    public int deleteByUserId(Integer userId);

    public int insertRole(List<Map<String,Integer>> list);

    public User queryUser(User user);

    public User queryByUserName(String userName);

    public int updatepwd(User user);

}
