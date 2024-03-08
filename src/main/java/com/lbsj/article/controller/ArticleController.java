package com.lbsj.article.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lbsj.article.dto.ArticleDTO;
import com.lbsj.article.entity.ArticleEntity;
import com.lbsj.article.service.ArticleService;
import com.lbsj.article.vo.ArticleReqVO;
import com.lbsj.article.vo.ArticleVO;
import com.lbsj.common.model.RequestResult;
import com.lbsj.utils.GeneralUtils;
import com.lbsj.utils.page.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @PostMapping("/add")
    public RequestResult add(@RequestBody ArticleDTO dto) {
        ArticleEntity add = articleService.add(dto);
        return RequestResult.e(add);
    }

    @GetMapping("/delete")
    public RequestResult delete(Long id) {
        boolean remove = articleService.removeById(id);
        return RequestResult.e(remove);
    }

    @PostMapping("/update")
    public RequestResult update(@RequestBody ArticleDTO dto) {
        ArticleEntity add = articleService.update(dto);
        return RequestResult.e(add);
    }

    @GetMapping("/info")
    public RequestResult info(Long id) {
        ArticleEntity add = articleService.getById(id);
        ArticleVO articleVO = GeneralUtils.cover2Bean(add, ArticleVO.class);
        return RequestResult.e(articleVO);
    }

    @PostMapping("/page")
    public RequestResult page(@RequestBody ArticleReqVO reqVO) {
        LambdaQueryWrapper<ArticleEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(reqVO.getTitle()),ArticleEntity::getTitle,reqVO.getTitle());
        wrapper.eq(StringUtils.isNotBlank(reqVO.getTopicType()),ArticleEntity::getTopicType,reqVO.getTopicType());
        wrapper.eq(StringUtils.isNotBlank(reqVO.getTrade()),ArticleEntity::getTrade,reqVO.getTrade());
        Page<ArticleEntity> page = Page.of(reqVO.getPage(), reqVO.getPageSize());
        page = articleService.page(page, wrapper);
        IPage<ArticleVO> vo = PageUtils.cover2Page(page, ArticleVO.class);
        return RequestResult.e(vo);
    }

}
