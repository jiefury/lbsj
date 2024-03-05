package com.lbsj.article.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("article")
public class ArticleEntity extends Model<ArticleEntity> implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "deleted")
    @TableLogic
    private Boolean deleted = Boolean.FALSE;

}
