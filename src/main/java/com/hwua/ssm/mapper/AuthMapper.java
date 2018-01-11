package com.hwua.ssm.mapper;

import com.hwua.ssm.po.Auth;

import java.util.List;
import java.util.Map;

public interface AuthMapper {

    public List<Auth> queryByDbid(Integer dbid);

    public int doUpdate(Auth auth);

    public int doInsert(Auth auth);

    public List<Map<String,Object>> queryValid(Integer dbid);

    public List<Integer> queryByRoleId(Integer roleId);

    public List<Auth> queryByUserId(Integer userId);

    public List<Auth> queryUserAuth(Integer userId);
}
