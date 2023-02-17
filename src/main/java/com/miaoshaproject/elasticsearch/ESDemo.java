package com.miaoshaproject.elasticsearch;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import co.elastic.clients.json.JsonData;
import com.miaoshaproject.response.CommonReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ttes")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class ESDemo {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    //创建索引
    @PostMapping("/createIndex")
    @ResponseBody
    public CommonReturnType createIndex(@RequestParam String indexName) throws IOException {

        elasticsearchClient.indices().create(createIndex -> createIndex.index(indexName));
        return CommonReturnType.create("ok");
    }

    //新增对象类型数据
    @PostMapping("/insertIndex")
    @ResponseBody
    public CommonReturnType uploadProduct(ESUser param) {
        //new个新的
        ESUser esUser = new ESUser("lisi", 7);
        IndexRequest<ESUser> req;
        req = IndexRequest.of(b ->
                b.index("e_user").id("88").document(esUser));
        try {
            elasticsearchClient.index(req);
            return CommonReturnType.create("ok");
        } catch (IOException e) {
            return CommonReturnType.create(false, e.toString());
        }
    }

    //修改数据
    @PostMapping("/updateIndex")
    @ResponseBody
    public CommonReturnType updateIndex() throws IOException {
        UpdateResponse<ESUser> updateResponse = elasticsearchClient.update(u -> u
                        .index("e_user")
                        .id("1")
                        .doc(new ESUser("ws", 99))
                , ESUser.class);
        return CommonReturnType.create("OK");
    }

    //删除数据
    @PostMapping("/deleteIndex")
    @ResponseBody
    public CommonReturnType deleteIndex() throws IOException {
        DeleteResponse deleteResponse = elasticsearchClient.delete(d -> d
                .index("e_user")
                .id("88")
        );
        return CommonReturnType.create("OK");
    }

    //拿到单条数据
    @PostMapping("/getOne")
    @ResponseBody
    public CommonReturnType getOne() throws IOException {
        GetResponse<ESUser> e_user = elasticsearchClient.get(g -> g
                        .index("e_user")
                        .id("88")
                , ESUser.class);
        System.out.println(e_user.source());
        return CommonReturnType.create(e_user.source());
    }

    //搜索
    @PostMapping("/searchDemoOne")
    @ResponseBody
    public CommonReturnType queryTest(String name) throws IOException {
        List<ESUser> tProductList = new ArrayList<>();

        SearchResponse<ESUser> response = elasticsearchClient.search(
                s -> s.index("e_user")
                        .query(
                                q -> q.range(
                                        t -> t.field("age")
                                                .gte(JsonData.of(1))
                                                .lte(JsonData.of(99))
                                )

                        )
                ,
                ESUser.class

        );
        TotalHits total = response.hits().total();
        List<Hit<ESUser>> hits = response.hits().hits();
        for (Hit<ESUser> hit : hits) {
            ESUser product = hit.source();
            tProductList.add(product);
        }
        return CommonReturnType.create(tProductList);
    }
}
