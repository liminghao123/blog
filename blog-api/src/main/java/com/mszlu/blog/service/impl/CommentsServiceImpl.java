package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.dao.mapper.CommentMapper;
import com.mszlu.blog.dao.pojo.Comment;
import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.service.CommentsService;
import com.mszlu.blog.service.SysUserService;
import com.mszlu.blog.service.ThreadService;
import com.mszlu.blog.utils.UserThreadLocal;
import com.mszlu.blog.vo.CommentVo;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.UserVo;
import com.mszlu.blog.vo.params.CommentParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;
    
    @Override
    public Result commentsList(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVos=copyList(comments);
        return Result.success(commentVos);
    }

    @Override
    public Result comment(CommentParams commentParams) {
        Comment comment=new Comment();
        comment.setContent(commentParams.getContent());
        comment.setArticleId(commentParams.getArticleId());
        comment.setCreateDate(System.currentTimeMillis());
        SysUser sysUser = UserThreadLocal.get();
        comment.setAuthorId(sysUser.getId());
        Long parentId=commentParams.getParent();
        if(parentId==null||parentId==0){
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parentId==null?0:parentId);
        comment.setToUid(commentParams.getToUserId()==null?0:commentParams.getToUserId());
        commentMapper.insert(comment);
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVos=new ArrayList<>();
        for (Comment comment : comments) {
            commentVos.add(copy(comment));
        }
        return commentVos;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo=new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd mm:hh"));
        UserVo userVo=sysUserService.findUserVoById(comment.getAuthorId());
        commentVo.setAuthor(userVo);

        List<CommentVo> list=findCommentVosByParentId(comment.getId());
        commentVo.setChildrens(list);
        if(comment.getLevel()>1){
            commentVo.setToUser(sysUserService.findUserVoById(comment.getToUid()));
        }
        return commentVo;
    }

    private List<CommentVo> findCommentVosByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return copyList(comments);
    }
}
