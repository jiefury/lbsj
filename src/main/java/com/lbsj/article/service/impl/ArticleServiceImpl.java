package com.lbsj.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbsj.article.dto.ArticleDTO;
import com.lbsj.article.entity.ArticleEntity;
import com.lbsj.article.mapper.ArticleMapper;
import com.lbsj.article.service.ArticleService;
import com.lbsj.utils.GeneralUtils;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity> implements ArticleService {


    @Override
    public ArticleEntity add(ArticleDTO dto) {
        ArticleEntity entity = GeneralUtils.cover2Bean(dto, ArticleEntity.class);
        super.save(entity);
        return entity;
    }

    @Override
    public ArticleEntity update(ArticleDTO dto) {
        ArticleEntity entity = GeneralUtils.cover2Bean(dto, ArticleEntity.class);
        super.updateById(entity);
        return entity;
    }
}
