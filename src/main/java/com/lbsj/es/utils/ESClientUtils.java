package com.lbsj.es.utils;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.SSLContext;

public class ESClientUtils {
    private static final String ELASTICSEARCH_HOST = "110.41.185.137";
    private static final int ELASTICSEARCH_PORT = 9200;
    private static final String USERNAME = "elastic";
    private static final String PASSWORD = "lbsj_dev123";


    public static RestHighLevelClient storeElasticsearch() {
        try {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));

            // 创建SSLContext以跳过SSL证书验证
            SSLContext sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial((chain, authType) -> true)
                    .build();

            RestClientBuilder builder = restClientBuilder(credentialsProvider, sslContext);

            RestHighLevelClient client = new RestHighLevelClient(builder);
            return client;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @NotNull
    private static RestClientBuilder restClientBuilder(CredentialsProvider credentialsProvider, SSLContext sslContext) {
        // 配置HTTP客户端以使用SSLContext和跳过SSL主机名验证
        RestClientBuilder builder = RestClient.builder(
                        new HttpHost(ELASTICSEARCH_HOST, ELASTICSEARCH_PORT, "https"))
                .setHttpClientConfigCallback(
                        httpClientBuilder ->
                                httpClientBuilder.setSSLContext(sslContext)
                                        .setKeepAliveStrategy((httpResponse, httpContext) -> 5000)
                                        .setDefaultCredentialsProvider(credentialsProvider)
                                        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                                        .setDefaultIOReactorConfig(
                                                IOReactorConfig.custom()
                                                        .setIoThreadCount(1)
                                                        .build()));
        return builder;
    }

}
