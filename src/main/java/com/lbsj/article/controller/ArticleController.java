package com.lbsj.article.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lbsj.article.service.ArticleService;
import com.lbsj.common.model.RequestResult;
import com.lbsj.common.model.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


}
