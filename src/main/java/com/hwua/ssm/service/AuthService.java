package com.hwua.ssm.service;

import com.hwua.ssm.po.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {

    public List<Auth> queryAllAuth();

    public int doUpdate(Auth auth);

    public int doInsert(Auth auth);

    public List<Map<String, Object>> getAuthValid(Integer roleId);

    public List<Auth> queryByUserId(Integer userId);

    public List<Auth> queryUserAuth(Integer userId);

}
