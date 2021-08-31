package com.mszlu.blog.controller;

import com.mszlu.blog.service.LoginService;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录功能
     * @param loginParams
     * @return
     */
    @PostMapping
    public Result login(@RequestBody LoginParams loginParams){
        return loginService.login(loginParams);
    }
}
