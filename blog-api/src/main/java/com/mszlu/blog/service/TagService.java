package com.mszlu.blog.service;

import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    /**
     * 查询该文章下的所有标签
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签
     * @param limit
     * @return
     */
    Result hots(int limit);

    /**
     * 查询所有标签
     * @return
     */
    Result findAll();

    /**
     *查询所有标签的详情
     * @return
     */
    Result findAllDetail();

    /**
     * 通过id查询该标签下所有文章
     * @param id
     * @return
     */
    Result findDetailById(Long id);
}
