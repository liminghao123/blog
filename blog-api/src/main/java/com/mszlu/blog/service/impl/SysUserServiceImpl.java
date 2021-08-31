package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.dao.mapper.SysUserMapper;
import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.service.LoginService;
import com.mszlu.blog.service.SysUserService;
import com.mszlu.blog.vo.ErrorCode;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.UserVo;
import com.mszlu.blog.vo.LoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginService loginService;

    @Override
    public SysUser findAuthorById(long id) {
        SysUser sysUser=sysUserMapper.selectById(id);
        if(sysUser==null){
            sysUser=new SysUser();
            sysUser.setNickname("码神之路");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getId,SysUser::getAvatar,SysUser::getNickname,SysUser::getAccount);
        queryWrapper.last(" limit 1 ");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        SysUser sysUser=loginService.checkToken(token);
        if(sysUser==null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginVo loginUser=new LoginVo();
        BeanUtils.copyProperties(sysUser,loginUser);
        loginUser.setId(String.valueOf(sysUser.getId()));
        return Result.success(loginUser);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last(" limit 1 ");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }

    @Override
    public UserVo findUserVoById(Long authorId) {
        SysUser sysUser=sysUserMapper.selectById(authorId);
        if(sysUser==null){
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("码神之路");
        }
        UserVo userVo=new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        userVo.setId(String.valueOf(sysUser.getId()));
        return userVo;
    }
}
