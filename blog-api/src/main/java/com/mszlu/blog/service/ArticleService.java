package com.mszlu.blog.service;

import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.ArticleParam;
import com.mszlu.blog.vo.params.PageParams;

import java.util.List;

public interface ArticleService {

    /**
     * 展示查询到的文章
     * @param pageParams
     * @return
     */
    Result ListArticle(PageParams pageParams);

    /**
     * 展示最热文章
     * @param limit
     * @return
     */
    Result hotArticles(int limit);

    /**
     * 展示最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);

    /**
     * 文章归档
     * @return
     */
    Result ListArticle();

    /**
     * 通过id查询文章详情
     * @param id
     * @return
     */
    Result findArticleById(Long id);

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);
}
