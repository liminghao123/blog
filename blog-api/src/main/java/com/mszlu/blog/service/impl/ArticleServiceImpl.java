package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.blog.dao.dos.Archives;
import com.mszlu.blog.dao.mapper.ArticleBodyMapper;
import com.mszlu.blog.dao.mapper.ArticleMapper;
import com.mszlu.blog.dao.mapper.ArticleTagMapper;
import com.mszlu.blog.dao.pojo.*;
import com.mszlu.blog.service.*;
import com.mszlu.blog.utils.UserThreadLocal;
import com.mszlu.blog.vo.*;
import com.mszlu.blog.vo.params.ArticleParam;
import com.mszlu.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private ArticleTagMapper articleTagMapper;


    @Override
    public Result ListArticle(PageParams pageParams) {
        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> list=articleMapper.listArticles(page,pageParams.getCategoryId(),pageParams.getTagId(),
                pageParams.getYear(),pageParams.getMonth());
        return Result.success(copyList(list.getRecords(),true,true));
    }



//    @Override
//    public Result ListArticle(PageParams pageParams) {
//        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPageSize());
//        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
//        if(pageParams.getCategoryId()!=null){
//            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
//        }
//        List<Long> articleIds=new ArrayList<>();
//        if(pageParams.getTagId()!=null){
//            LambdaQueryWrapper<ArticleTag> queryWrapper2=new LambdaQueryWrapper<>();
//            queryWrapper2.eq(ArticleTag::getTagId,pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(queryWrapper2);
//            for (ArticleTag articleTag : articleTags) {
//                articleIds.add(articleTag.getArticleId());
//            }
//            if(articleIds.size()>0){
//                queryWrapper.in(Article::getId,articleIds);
//            }
//        }
//
//        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//        Page<Article> articlePage=articleMapper.selectPage(page,queryWrapper);
//        List<Article> articleList = articlePage.getRecords();
//        List<ArticleVo> articleVoList=copyList(articleList,true,true);
//        //System.out.println(articleVoList);
//        return Result.success(articleVoList);
//    }

    @Override
    public Result hotArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.last(" limit "+limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last(" limit "+limit);
        queryWrapper.orderByDesc(Article::getCreateDate);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result ListArticle() {
        List<Archives> list=articleMapper.listArchives();
        return Result.success(list);
    }

    @Override
    public Result findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo=copy(article,true,true,true,true);
        threadService.updateArticleViewCount(articleMapper,article);
        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        Article article=new Article();
        article.setViewCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        article.setSummary(articleParam.getSummary());
        SysUser sysUser = UserThreadLocal.get();
        article.setAuthorId(sysUser.getId());
        article.setCommentCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setWeight(Article.Article_Common);

        ArticleBody articleBody=new ArticleBody();
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.insert(article);

        List<TagVo> tags = articleParam.getTags();
        if(tags!=null){
            for (TagVo tag : tags) {
                ArticleTag articleTag=new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTagMapper.insert(articleTag);
            }
        }
        Map<String,String> map=new HashMap<>();
        map.put("id",article.getId().toString());
        return Result.success(map);
    }

    private List<ArticleVo> copyList(List<Article> articleList,boolean isTag,boolean isAuthor) {
        List<ArticleVo> list=new ArrayList<>();
        for(Article article:articleList){
            list.add(copy(article,isTag,isAuthor,false,false));
        }
        return list;
    }

    private List<ArticleVo> copyList(List<Article> articleList,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> list=new ArrayList<>();
        for(Article article:articleList){
            list.add(copy(article,isTag,isAuthor,isBody,isCategory));
        }
        return list;
    }

    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo=new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setId(String.valueOf(article.getId()));
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd hh-mm"));
        if(isTag){
            List<TagVo> list=tagService.findTagsByArticleId(article.getId());
            articleVo.setTags(list);
        }
        if(isAuthor){
            SysUser user=sysUserService.findAuthorById(article.getAuthorId());
            articleVo.setAuthor(user.getNickname());
        }
        if(isBody){
            ArticleBodyVo articleBodyVo=findBodyById(article.getBodyId());
            articleVo.setBody(articleBodyVo);
        }
        if(isCategory){
            CategoryVo categoryVo=categoryService.findCategoryById(article.getCategoryId());
            articleVo.setCategory(categoryVo);
        }
        return articleVo;
    }

    private ArticleBodyVo findBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo=new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
