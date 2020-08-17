package com.xu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xu.domain.User;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestOne {

    @Autowired
    private RestHighLevelClient esClient;

    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("home");
        CreateIndexResponse createIndexResponse = esClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    //get index
    @Test
    public void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("store");
        boolean exists = esClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //delete index
    @Test
    public void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("hone");
        AcknowledgedResponse delete = esClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

    //insert doc
    @Test
    public void testInsertDoc() throws IOException {
        User user = new User("zhangsan", 1);
        IndexRequest indexRequest = new IndexRequest("home");
        indexRequest.id("1");
        indexRequest.timeout(TimeValue.timeValueSeconds(1));
        ObjectMapper mapper = new ObjectMapper();
        indexRequest.source(mapper.writeValueAsString(user), XContentType.JSON);
        IndexResponse indexResponse = esClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse);
        System.out.println(indexResponse.status());
    }

    //get doc
    @Test
    public void testGetDoc() throws IOException {
        GetRequest getRequest = new GetRequest("home", "1");
        GetResponse getResponse = esClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());
        System.out.println(getRequest);
    }

    //bulk insert
    @Test
    public void testBulkInsert() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        ArrayList userList = new ArrayList();
        userList.add(new User("lisi",2));
        userList.add(new User("wangwu",3));
        userList.add(new User("zhaoliu",4));
        ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < userList.size(); i++) {
            bulkRequest.add(
                    new IndexRequest("home").id(""+(i+2)).source(mapper.writeValueAsString(userList.get(i)),XContentType.JSON)
            );
        }
        BulkResponse bulkItemResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkItemResponse.hasFailures());

    }
    //search
    @Test
    public void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("home");
        //构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "zhangsan");
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        SearchRequest source = searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = esClient.search(source, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(hits));

    }

    @Test
    public void testGetInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        User user = new User();
        Class<? extends User> userClass = user.getClass();
        User o = (User)Class.forName(userClass.getName()).newInstance();
        System.out.println(o);

    }

    @Test
    public void testAssert(){
        assert false:1+1;
        System.out.println("ok");
    }

}
