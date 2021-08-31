package com.mszlu.blog.controller;

import com.mszlu.blog.common.aop.LogAnnotation;
import com.mszlu.blog.common.cache.Cache;
import com.mszlu.blog.service.ArticleService;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.ArticleParam;
import com.mszlu.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    @LogAnnotation(module = "文章",operation = "获取文章列表")
    public Result listArticles(@RequestBody PageParams pageParams){
        return articleService.ListArticle(pageParams);
    }

    /**
     * 最热文章展示
     * @return
     */
    @PostMapping("hot")
    @Cache(expire = 1*60*1000,name ="hot_articles")
    public Result hotArticles(){
        int limit=5;
        return articleService.hotArticles(limit);
    }

    /**
     * 最新文章展示
     * @return
     */
    @PostMapping("new")
    @Cache(expire = 1*60*1000,name ="new_articles")
    public Result newArticles(){
        int limit=5;
        return articleService.newArticles(limit);
    }

    /**
     * 文章归档
     * @return
     */
    @PostMapping("listArchives")
    @Cache(expire = 1*60*1000,name ="list_archives")
    public Result listArchives(){
        return articleService.ListArticle();
    }

    /**
     * 查看文章详情
     * @param id
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long id){
        return articleService.findArticleById(id);
    }

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}











