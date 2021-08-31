package com.mszlu.blog.dao.pojo;

import lombok.Data;

/**
 * 分类
 */
@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}