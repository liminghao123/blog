package com.mszlu.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.service.LoginService;
import com.mszlu.blog.service.SysUserService;
import com.mszlu.blog.utils.JWTUtils;
import com.mszlu.blog.vo.ErrorCode;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String SALT="mszlu!@#";

    @Override
    public Result login(LoginParams loginParams) {
        String account=loginParams.getAccount();
        String password=loginParams.getPassword();
        if(StringUtils.isBlank(account)||StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        password= DigestUtils.md5Hex(password+SALT);
        SysUser sysUser=sysUserService.findUser(account,password);
        if(sysUser==null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("token_"+token, JSON.toJSONString(sysUser),1l, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> map = JWTUtils.checkToken(token);
        if(map==null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("token_" + token);
        if(userJson==null){
            return null;
        }
        return JSON.parseObject(userJson, SysUser.class);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("token_"+token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParams registerParams) {
        String account=registerParams.getAccount();
        String nickname=registerParams.getNickname();
        String password=registerParams.getPassword();
        if(StringUtils.isBlank(account)||StringUtils.isBlank(nickname)||StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser=sysUserService.findUserByAccount(account);
        if(sysUser!=null){
            return Result.fail(ErrorCode.ACCOUNT_IS_EXIST.getCode(),ErrorCode.ACCOUNT_IS_EXIST.getMsg());
        }

        sysUser=new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+SALT));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);

        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("token_"+token,JSON.toJSONString(sysUser),1l,TimeUnit.DAYS);
        return Result.success(token);
    }
}
