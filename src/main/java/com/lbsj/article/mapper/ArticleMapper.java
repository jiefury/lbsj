package com.lbsj.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lbsj.article.entity.ArticleEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<ArticleEntity>  {
}
