package com.lbsj.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbsj.article.model.dto.ArticleDTO;
import com.lbsj.article.model.entity.ArticleEntity;

public interface ArticleService extends IService<ArticleEntity> {

    ArticleEntity add(ArticleDTO dto);
    ArticleEntity update(ArticleDTO dto);
}
