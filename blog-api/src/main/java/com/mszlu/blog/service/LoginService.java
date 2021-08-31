package com.mszlu.blog.service;

import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginParams;

public interface LoginService {

    /**
     * 登录
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);

    /**
     * 通过token查询用户
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 退出登录，清除token
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册账户
     * @param registerParams
     * @return
     */
    Result register(LoginParams registerParams);
}
