package com.lbsj.article.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ArticleDTO {

    private Long id;
    private String title;
    private String subTitle;
    private String author;
    private LocalDate publishDate;
    private String publishInst;
    private String summary;
    private String topicType;
    private String trade;
    private String files;

}
