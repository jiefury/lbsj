package com.lbsj.article.model.vo;

import com.alibaba.fastjson.JSONObject;
import com.lbsj.common.model.FileVO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ArticleVO {

    private Long id;
    private String title;
    private String subTitle;
    private String author;
    private LocalDate publishDate;
    private String publishInst;
    private String summary;
    private String topicType;
    private String trade;
    private FileVO fileVO;
    private String files;
    private LocalDateTime createTime;
    private String createUser;

    public void setFiles(String files) {
        this.files = files;
        this.fileVO = JSONObject.parseObject(files, FileVO.class);
    }
}
