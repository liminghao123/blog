package com.mszlu.blog.controller;


import com.mszlu.blog.utils.QiniuUtils;
import com.mszlu.blog.vo.ErrorCode;
import com.mszlu.blog.vo.Result;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    /**
     * 上传图片到七牛云对象存储
     * @param file
     * @return
     */
    @PostMapping
    public Result upload(@RequestParam("image")MultipartFile file){
        String fileName= UUID.randomUUID().toString()+"."+ StringUtils.substringAfterLast(file.getOriginalFilename(),".");
        boolean upload = qiniuUtils.upload(file, fileName);
        if(upload){
            return Result.success(qiniuUtils.url+fileName);
        }else{
            return Result.fail(ErrorCode.UPLOAD_ERROR.getCode(),ErrorCode.UPLOAD_ERROR.getMsg());
        }
    }
}
