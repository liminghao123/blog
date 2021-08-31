package com.mszlu.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id查询其所有标签
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询指定数量的标签id
     * @param limit
     * @return
     */
    List<Long> findTagIds(int limit);
}
