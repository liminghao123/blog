package com.mszlu.blog.dao.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 分类
 */
@Data
public class Comment {

    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;
}