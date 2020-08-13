package com.xu.test;

import com.xu.es.ESClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.ml.EvaluateDataFrameRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.junit.Test;

import java.io.IOException;

public class Demo1 {

    public static String index = "person";

    RestHighLevelClient client = ESClient.getClient();

    @Test
    public void testConnect(){
        RestHighLevelClient client = ESClient.getClient();
        System.out.println("ok");
    }

    @Test
    public void createIndex() throws IOException {
        //构建索引的settings
        Settings.Builder settings = Settings.builder()
                .put("number_of_shards",3)
                .put("number_of_replicas",3);
        //构建索引的mappings
        XContentBuilder mapping = JsonXContent.contentBuilder()
                .startObject("properties")
                    .startObject("novel")
                        .startObject("properties")
                            .startObject("name")
                                .field("type","text")
                            .endObject()
                            .startObject("age")
                                .field("type","integer")
                            .endObject()
                            .startObject("birthday")
                                .field("type","date")
                                .field("format","yyyy-MM-dd")
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject();

        //将setting和mapping封装到一个Request对象
        CreateIndexRequest request = new CreateIndexRequest(index)
                .settings(settings)
                .mapping(mapping);
        //通过client对象去连接es
        CreateIndexResponse resp = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println("resp:"+resp.toString());

    }
}
