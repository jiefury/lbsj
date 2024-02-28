package com.lbsj.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbsj.article.entity.ArticleEntity;
import com.lbsj.article.mapper.ArticleMapper;
import com.lbsj.article.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity> implements ArticleService {
}
