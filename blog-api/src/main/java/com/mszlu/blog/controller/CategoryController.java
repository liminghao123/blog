package com.mszlu.blog.controller;

import com.mszlu.blog.common.cache.Cache;
import com.mszlu.blog.service.CategoryService;
import com.mszlu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有分类
     * @return
     */
    @GetMapping
    @Cache(expire = 1*60*1000,name ="findAllCategory")
    public Result findAll(){
        return categoryService.findAll();
    }

    /**
     * 查看所有分类的详情
     * @return
     */
    @GetMapping("detail")
    @Cache(expire = 1*60*1000,name ="findAllCategoryDetail")
    public Result findAllDetail(){
        return categoryService.findAllDetail();
    }

    /**
     * 通过id查看该分类下的所有文章信息
     * @param id
     * @return
     */
    @GetMapping("detail/{id}")
    public Result CategoryDetailById(@PathVariable("id") Long id){
        return categoryService.categoryDetailById(id);
    }
}