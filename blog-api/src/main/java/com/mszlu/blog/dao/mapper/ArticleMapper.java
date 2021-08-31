package com.mszlu.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.blog.dao.dos.Archives;
import com.mszlu.blog.dao.pojo.Article;
import com.mszlu.blog.vo.params.PageParams;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 文章归档查询
     * @return
     */
    List<Archives> listArchives();

    /**
     * 查询文章列表，可能带有多个条件
     * @param page
     * @param categoryId
     * @param tagId
     * @param year
     * @param month
     * @return
     */
    IPage<Article> listArticles(Page<Article> page,
                                Long categoryId,
                                Long tagId,
                                String year,
                                String month);
}
