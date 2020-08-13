package com.xu.es;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

@Slf4j
public class ESClient {

    public static RestHighLevelClient getClient(){
        log.debug("this is debug");
        HttpHost httpHost = new HttpHost("localhost",9200);
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);
        return restHighLevelClient;
    }

    public static void main(String[] args) {

    }
}
