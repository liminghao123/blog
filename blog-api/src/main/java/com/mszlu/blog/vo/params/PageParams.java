package com.mszlu.blog.vo.params;

import lombok.Data;

@Data
public class PageParams {

    private int page=1;

    private int pageSize=5;

    private Long categoryId;

    private Long tagId;

    private String year;

    private String month;

    public String getMonth() {
        if(month!=null&&month.length()==1){
            return "0"+month;
        }
        return month;
    }
}
