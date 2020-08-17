package com.xu.controller;

import com.xu.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ContentController {
    @Autowired
    private ContentService contentService;

    @RequestMapping("/paresJD4Es/{indexName}&{keyword}")
    public Boolean paresJD4Es(@PathVariable String indexName,@PathVariable String keyword) throws Exception {
        return contentService.parseContent(indexName, keyword);
    }

    @RequestMapping("/createIndex/{indexName}")
    public Boolean createIndex(@PathVariable String indexName) throws Exception{
        return contentService.createEsIndex(indexName);
    }

    @RequestMapping("/test/{indexName}&{keyword}")
    public void test(@PathVariable String indexName,@PathVariable String keyword) throws IOException {
        System.out.println(indexName);
        System.out.println(keyword);
        System.out.println("ok");
    }

    @RequestMapping("/search/{pageNum}&{pageSize}&{keyword}")
    public List<Map<String,Object>> searchPage(@PathVariable String keyword,@PathVariable int pageNum,@PathVariable int pageSize) throws IOException {
        return contentService.searchHighLightPage(keyword, pageNum, pageSize);
    }


}
