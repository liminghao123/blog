package com.mszlu.blog.utils;

import com.mszlu.blog.dao.pojo.SysUser;

/**
 * 对本地线程变量的操作
 */
public class UserThreadLocal {

    private UserThreadLocal(){}

    private static final ThreadLocal<SysUser> LOCAL=new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    public static SysUser get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
}
