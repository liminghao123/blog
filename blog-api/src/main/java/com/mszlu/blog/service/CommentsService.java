package com.mszlu.blog.service;

import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.CommentParams;

public interface CommentsService {

    /**
     * 查看该文章下的所有评论
     * @param id
     * @return
     */
    Result commentsList(Long id);

    /**
     * 评论文章
     * @param commentParams
     * @return
     */
    Result comment(CommentParams commentParams);
}
