package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.dao.mapper.TagMapper;
import com.mszlu.blog.dao.pojo.Tag;
import com.mszlu.blog.service.TagService;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.TagVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        List<Tag> tags=tagMapper.findTagsByArticleId(articleId);
        List<TagVo> tagVos=copyList(tags);
        return tagVos;
    }

    private List<TagVo> copyList(List<Tag> tags) {
        List<TagVo> list=new ArrayList<>();
        for(Tag tag:tags){
            list.add(copy(tag));
        }
        return list;
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo=new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }

    @Override
    public Result hots(int limit) {
        List<Long> tagIds= tagMapper.findTagIds(limit);
        if(CollectionUtils.isEmpty(tagIds)){
            return Result.success(Collections.emptyList());
        }
        List<TagVo> list=new ArrayList<>();
        for(long id:tagIds){
            Tag tag = tagMapper.selectById(id);
            list.add(copy(tag));
        }
        return Result.success(list);
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tags));
    }

    @Override
    public Result findAllDetail() {
        List<Tag> tags = tagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(tags));
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(copy(tag));
    }
}
