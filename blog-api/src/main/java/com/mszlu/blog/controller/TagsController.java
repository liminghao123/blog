package com.mszlu.blog.controller;

import com.mszlu.blog.common.cache.Cache;
import com.mszlu.blog.service.TagService;
import com.mszlu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    /**
     * 展示最热标签
     * @return
     */
    @GetMapping("hot")
    @Cache(expire = 1*60*1000,name ="hot_tags")
    public Result hots(){
        int limit=6;
        return tagService.hots(limit);
    }

    /**
     * 查询所有标签
     * @return
     */
    @GetMapping
    @Cache(expire = 1*60*1000,name ="all_tags")
    public Result findAll(){
        return tagService.findAll();
    }

    /**
     * 查询所有标签的详情
     * @return
     */
    @GetMapping("detail")
    @Cache(expire = 1*60*1000,name ="all_tags_detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    /**
     * 通过id查询该标签下的所有文章
     * @param id
     * @return
     */
    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }
}
