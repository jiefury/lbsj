package com.lbsj.es.controller;


import com.lbsj.es.vo.ESQueryVO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author geng
 * 2020/12/20
 */
@RestController
@RequestMapping("/app/client")
public class ESClientController {

    @Autowired
    private RestHighLevelClient client;

    @PostMapping("/search")
    public SearchResponse search(@RequestBody ESQueryVO vo) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("lbsj-pdf-index");//指定要查询的索引
        //1.创建 SearchSourceBuilder条件构造。builder模式这里就先不简写了
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder builder = QueryBuilders.matchQuery(vo.getKey(), vo.getValue());
        WildcardQueryBuilder query = QueryBuilders.wildcardQuery(vo.getKey(), vo.getValue());
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery(vo.getKey(), vo.getValue());
        searchSourceBuilder.query(fuzzyQueryBuilder);
        //2.将 SearchSourceBuilder 添加到 SearchRequest中
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = null;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

}
