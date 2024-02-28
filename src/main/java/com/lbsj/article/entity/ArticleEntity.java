package com.lbsj.article.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class ArticleEntity extends Model<ArticleEntity> {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
}
