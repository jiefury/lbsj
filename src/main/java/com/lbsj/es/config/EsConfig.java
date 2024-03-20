package com.lbsj.es.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

//@Configuration
//public class EsConfig {
//    //    @Value("${elasticSearch.url}")
//    private String esUrl = "127.0.0.1:9200";
//
//    @Bean
//    public RestHighLevelClient client() {
//        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo(esUrl)//elasticsearch地址
//                .build();
//
//        return RestClients.create(clientConfiguration).rest();
//    }
//}

