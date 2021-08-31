package com.mszlu.blog.vo.params;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginParams {

    private String account;

    private String password;

    private String nickname;
}
