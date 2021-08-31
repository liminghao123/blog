package com.mszlu.blog.service;

import com.mszlu.blog.dao.pojo.Category;
import com.mszlu.blog.vo.CategoryVo;
import com.mszlu.blog.vo.Result;

public interface CategoryService {

    /**
     *通过id查询对应分类
     * @param categoryId
     * @return
     */
    CategoryVo findCategoryById(Long categoryId);

    /**
     *查询所有分类
     * @return
     */
    Result findAll();

    /**
     *查询所有分类详情
     * @return
     */
    Result findAllDetail();

    /**
     * 查询该分类下的所有文章
     * @param id
     * @return
     */
    Result categoryDetailById(Long id);
}
