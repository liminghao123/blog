package com.mszlu.blog.dao.dos;

import lombok.Data;

/**
 * 临时实体类，数据库中没有对应表
 */
@Data
public class Archives {

    private Integer year;

    private Integer month;

    private Long count;
}
