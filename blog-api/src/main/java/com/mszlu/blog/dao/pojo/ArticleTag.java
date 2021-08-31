package com.mszlu.blog.dao.pojo;

import lombok.Data;

/**
 * 文章和标签，多对多
 */
@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;
}