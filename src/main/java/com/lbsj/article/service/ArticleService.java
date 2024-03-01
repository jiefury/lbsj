package com.lbsj.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbsj.article.dto.ArticleDTO;
import com.lbsj.article.entity.ArticleEntity;

public interface ArticleService extends IService<ArticleEntity> {

    ArticleEntity add(ArticleDTO dto);
    ArticleEntity update(ArticleDTO dto);
}
