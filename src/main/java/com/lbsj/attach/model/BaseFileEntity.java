package com.lbsj.attach.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("base_file")
public class BaseFileEntity extends Model<BaseFileEntity> implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String filename;
    private String filePath;
    private String suffix;
    private String reFilename;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "deleted")
    @TableLogic
    private Boolean deleted = Boolean.FALSE;

}
