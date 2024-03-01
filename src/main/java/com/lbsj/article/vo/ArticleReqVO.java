package com.lbsj.article.vo;

import com.lbsj.common.model.PageDTO;
import lombok.Data;

@Data
public class ArticleReqVO extends PageDTO {

    private String title;
    private String topicType;
    private String trade;

}
