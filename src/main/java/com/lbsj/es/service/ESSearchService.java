package com.lbsj.es.service;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class ESSearchService {

    /**
     * 判断索引是否存在
     */
    public boolean existsIndex(String index, RestHighLevelClient client) throws IOException {
        try {
            GetIndexRequest request = new GetIndexRequest(index);
            boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
            return exists;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

    /**
     * 创建索引
     */
    public boolean createIndex(String index, RestHighLevelClient client) throws IOException {
        try {
            CreateIndexRequest request = new CreateIndexRequest(index);
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            return createIndexResponse.isAcknowledged();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

    /**
     * 删除索引
     */
    public boolean deleteIndex(String index, RestHighLevelClient client) throws IOException {
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
            AcknowledgedResponse response = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            return response.isAcknowledged();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

    /**
     * 判断某索引下文档id是否存在
     */
    public boolean docExists(String index, String id, RestHighLevelClient client) throws IOException {
        try {
            GetRequest getRequest = new GetRequest(index, id);
            //只判断索引是否存在不需要获取_source
            //getRequest.fetchSourceContext(new FetchSourceContext(false));
            //getRequest.storedFields("_none_");
            boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
            return exists;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

    /**
     * 添加文档记录
     */
    public boolean addDoc(String index, String id, Map<String, String> t, RestHighLevelClient client) throws IOException {
        try {
            IndexRequest request = new IndexRequest(index);
            request.id(id);
            //设置超时时间
            request.timeout(TimeValue.timeValueSeconds(1));
            request.timeout("1s");
            //将前端获取来的数据封装成一个对象转换成JSON格式放入请求中
            request.source(JSON.toJSONString(t), XContentType.JSON);
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            RestStatus Status = indexResponse.status();
            System.out.println("Index Response Status: " + Status);
            return Status == RestStatus.OK || Status == RestStatus.CREATED;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

    /**
     * 根据id来获取记录
     */
    public GetResponse getDoc(String index, String id, RestHighLevelClient client) throws IOException {
        try {
            GetRequest request = new GetRequest(index, id);
            GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
            return getResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

    /**
     * 批量添加文档记录
     */
    public boolean bulkAdd(String index, List<Object> list, RestHighLevelClient client) throws IOException {
        try {
            BulkRequest bulkRequest = new BulkRequest();
            //设置超时时间
            bulkRequest.timeout(TimeValue.timeValueMinutes(2));
            bulkRequest.timeout("2m");
            for (int i = 0; i < list.size(); i++) {
                bulkRequest.add(new IndexRequest(index).source(JSON.toJSONString(list.get(i))));
            }
            BulkResponse bulkResponse = client.bulk(bulkRequest,
                    RequestOptions.DEFAULT);
            return !bulkResponse.hasFailures();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

    /**
     * 更新文档记录
     */
    public boolean updateDoc(String index, String id, Object t, RestHighLevelClient client) throws IOException {
        try {
            UpdateRequest request = new UpdateRequest(index, id);
            request.doc(JSON.toJSONString(t));
            request.timeout(TimeValue.timeValueSeconds(1));
            request.timeout("1s");
            UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
            return updateResponse.status() == RestStatus.OK;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

    /**
     * 删除文档记录
     */
    public boolean deleteDoc(String index, String id, RestHighLevelClient client) throws IOException {
        try {
            DeleteRequest request = new DeleteRequest(index, id);
            request.timeout(TimeValue.timeValueSeconds(1));
            request.timeout("1s");
            DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
            return deleteResponse.status() == RestStatus.OK;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

/**
 *根据某字段来搜索
 */
    /**
     * @param index  要查询的索引名称。这是您要在其中执行搜索的Elasticsearch索引
     * @param field  要查询的字段名称。这是您要在其中搜索的字段。
     * @param key    搜索关键词。这是您要在指定字段中查找的关键词或值。
     * @param from   结果集的起始位置。它用于分页，指定从搜索结果集的哪个位置开始返回结果。
     * @param size   每页的结果数量。它指定了每个分页的结果数目。
     * @param client RestHighLevelClient 客户端对象，用于与Elasticsearch进行通信。
     * @return
     * @throws IOException
     */
    public String search(String index, String field, String key, Integer from, Integer size, RestHighLevelClient client) throws IOException {
        try {
            SearchRequest searchRequest = new SearchRequest(index);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.termQuery(field, key));
            //控制搜素
            sourceBuilder.from(from);
            sourceBuilder.size(size);
            //最大搜索时间。
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(sourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return JSON.toJSONString(searchResponse.getHits());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

    /**
     * 根据字段模糊查询
     *
     * @param index
     * @param field
     * @param keyword
     * @param client
     * @return
     * @throws IOException
     */
    public SearchHit[] fuzzySearch(String index, String field, String keyword, RestHighLevelClient client) throws IOException {
        try {
            // 构建查询条件
            WildcardQueryBuilder queryBuilder = QueryBuilders.wildcardQuery(field, "*" + keyword + "*");

            // 构建搜索请求
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(queryBuilder);
            SearchRequest searchRequest = new SearchRequest(index).source(sourceBuilder);

            // 执行查询
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            // 获取命中的文档
            SearchHits hits = searchResponse.getHits();
            return hits.getHits();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

}
