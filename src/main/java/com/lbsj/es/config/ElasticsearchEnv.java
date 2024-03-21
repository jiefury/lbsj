package com.lbsj.es.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "elasticsearch")
@ConditionalOnProperty("elasticsearch.address")
public class ElasticsearchEnv {

    /**
     * es连接地址，如果有多个用,隔开
     */
    private String address;

    /**
     * es用户名
     */
    private String username;

    /**
     * es密码
     */
    private String password;

    /**
     * 协议
     */
    private String scheme;

    /**
     * 连接超时时间
     */
    private int connectTimeout;

    /**
     * Socket 连接超时时间
     */
    private int socketTimeout;

    /**
     * 获取连接的超时时间
     */
    private int connectionRequestTimeout;

    /**
     * 最大连接数
     */
    private int maxConnectNum;

    /**
     * 最大路由连接数
     */
    private int maxConnectPerRoute;

}

