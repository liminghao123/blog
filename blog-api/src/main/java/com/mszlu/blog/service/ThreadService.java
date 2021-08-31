package com.mszlu.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mszlu.blog.dao.mapper.ArticleMapper;
import com.mszlu.blog.dao.pojo.Article;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    /**
     * 将更新操作放入线程池中进行
     * @param articleMapper
     * @param article
     */
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCount=article.getViewCounts();
        Article updateArticle=new Article();
        updateArticle.setViewCounts(viewCount+1);
        LambdaUpdateWrapper<Article> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());
        updateWrapper.eq(Article::getViewCounts,viewCount);
        articleMapper.update(updateArticle,updateWrapper);
    }
}
