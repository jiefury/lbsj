package com.lbsj.es.config;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ElasticsearchConfig {

    @Autowired(required = false)
    private ElasticsearchEnv esRuntimeEnvironment;


    //当前es相关的配置存在则实例化RestHighLevelClient,如果不存在则不实例化RestHighLevelClient
    @Bean
    @ConditionalOnBean(value = ElasticsearchEnv.class)
    @Qualifier
    public RestHighLevelClient restHighLevelClient() {

        //es地址，以逗号分隔
        String nodes = esRuntimeEnvironment.getAddress();
        nodes = nodes.contains("https://") ? nodes.replace("https://", "") : nodes;
        //es密码
        String password = esRuntimeEnvironment.getPassword();
        String username = esRuntimeEnvironment.getUsername();
        String scheme = esRuntimeEnvironment.getScheme();
        List<HttpHost> httpHostList = new ArrayList();
        //拆分es地址0
        for (String address : nodes.split(",")) {
            int index = address.lastIndexOf(":");
            httpHostList.add(new HttpHost(address.substring(0, index), Integer.parseInt(address.substring(index + 1)), scheme));
        }
        //转换成 HttpHost 数组
        HttpHost[] httpHosts = httpHostList.toArray(new HttpHost[httpHostList.size()]);

        //构建连接对象
        RestClientBuilder builder = RestClient.builder(httpHosts);
        //使用账号密码连接
//        if (StringUtils.isNotEmpty(password)) {
//            String username = esRuntimeEnvironment.getUsername();
//            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
//            builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));
//        }

        // 异步连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(esRuntimeEnvironment.getConnectTimeout());
            requestConfigBuilder.setSocketTimeout(esRuntimeEnvironment.getSocketTimeout());
            requestConfigBuilder.setConnectionRequestTimeout(esRuntimeEnvironment.getConnectionRequestTimeout());
            return requestConfigBuilder;
        });

        // 异步连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setSSLContext(getSslContent());
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            httpClientBuilder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
            httpClientBuilder.setMaxConnTotal(esRuntimeEnvironment.getMaxConnectNum());
            httpClientBuilder.setMaxConnPerRoute(esRuntimeEnvironment.getMaxConnectPerRoute());
//            httpClientBuilder.setKeepAliveStrategy((httpResponse, httpContext) -> 50000);
//            httpClientBuilder.setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(1).build());
            return httpClientBuilder;
        });

        return new RestHighLevelClient(builder);
    }

    private SSLContext getSslContent() {
        // 创建SSLContext以跳过SSL证书验证
        try {
            SSLContext sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial((chain, authType) -> true)
                    .build();
            return sslContext;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}



