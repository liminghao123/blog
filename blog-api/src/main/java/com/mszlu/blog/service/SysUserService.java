package com.mszlu.blog.service;

import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.UserVo;

public interface SysUserService {

    /**
     * 通过id查询用户
     * @param id
     * @return
     */
    SysUser findAuthorById(long id);

    /**
     * 通过账号和密码查找用户
     * @param account
     * @param password
     * @return
     */
    SysUser findUser(String account, String password);

    /**
     * 通过token查询用户
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 通过账号查询用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 增加用户
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 通过id查询用户
     * @param authorId
     * @return
     */
    UserVo findUserVoById(Long authorId);
}
