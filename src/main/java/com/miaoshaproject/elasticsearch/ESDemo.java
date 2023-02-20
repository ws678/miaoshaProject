package com.miaoshaproject.elasticsearch;


import cn.hutool.core.util.RandomUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
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
import java.util.*;

@Controller
@RequestMapping("/ttes")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class ESDemo {

    @Autowired
    private ElasticsearchClient elasticsearchClient;
    private MatrixStatsAggregation.Builder mx;
    private MatrixStatsAggregation.Builder builder;
    private MatrixStatsAggregation.Builder builder1;

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
    @GetMapping("/searchDemoOne")
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


    //搜索
    @GetMapping("/searchDemoTwo")
    @ResponseBody
    public CommonReturnType queryTest1() throws IOException {
        List<ESUserPlus> tProductList = new ArrayList<>();

        SearchResponse<ESUserPlus> response = elasticsearchClient.search(
                s -> s.index("index01")
                        .query(
                                q -> q.term(
                                        t -> t.field("age")
                                                .value(72)
                                )

                        )
                ,
                ESUserPlus.class

        );
        TotalHits total = response.hits().total();
        return getCommonReturnType(tProductList, response);
    }

    //复合查询 v2.0增加可以扩展的空判断
    @GetMapping("/boolDemoOne")
    @ResponseBody
    public CommonReturnType boolTest() throws IOException {
        int age = RandomUtil.randomInt();
        List<ESUserPlus> esUserPlusList = new ArrayList<>();
        SearchResponse<ESUserPlus> response = elasticsearchClient.search(
                s -> s.index("index01")
                        .query(
                                q -> q.bool(
                                        builder -> {
                                            if (age > 10) {
                                                builder.must(
                                                        m -> m.term(
                                                                t -> t.field("age")
                                                                        .value(72)
                                                        )
                                                ).filter(
                                                        f -> f.term(
                                                                t -> t.field("email")
                                                                        .value("744")
                                                        )
                                                );
                                            }
                                            return builder;
                                        }
                                )

                        )
                ,
                ESUserPlus.class

        );
        return getCommonReturnType(esUserPlusList, response);
    }

    //分组查询
    @GetMapping("/aggsDemoOne")
    @ResponseBody
    public CommonReturnType aggsTestOne() throws IOException {
        HashMap<Long, Long> integerStringHashMap = new HashMap<>();
        SearchResponse<ESUserPlus> response = elasticsearchClient.search(
                s -> s.index("index01")
                        .query(
                                q -> q.matchAll(m -> m)
                        )
                        .size(0)
                        .aggregations("bucketDemo",
                                a -> a.terms(
                                        t -> t.field("age")
                                                .size(10)
                                )
                        ).aggregations("demoEmail",
                                    a -> a.terms(
                                            t -> t.field("email")
                                                    .size(15)
                                    )
                                )
                , ESUserPlus.class
        );
        Aggregate aggregate = response.aggregations().get("bucketDemo");
        Buckets<LongTermsBucket> buckets = aggregate.lterms().buckets();
        for (LongTermsBucket b : buckets.array()) {
            System.out.println(b.key() + " : " + b.docCount());
            integerStringHashMap.put(b.key(), b.docCount());
        }
        return CommonReturnType.create(integerStringHashMap);
    }

    //聚合查询
    @GetMapping("/aggsDemoTwo")
    @ResponseBody
    public CommonReturnType aggsTestTwo() throws IOException {
        Double max = null;
        SearchResponse<ESUserPlus> response = elasticsearchClient.search(
                s -> s.index("index01")
                        .query(
                                q -> q.matchAll(m -> m)
                        )
                        .size(0)
                        .aggregations("maxDemo",
                                a -> a.max(
                                        ma -> ma.field("age")
                                )
                        )
                , ESUserPlus.class
        );
        for (Map.Entry<String, Aggregate> entry : response.aggregations().entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().max().value());
            max = entry.getValue().max().value();
        }
        return CommonReturnType.create(max);
    }

    //排序和分页
    @GetMapping("/boolDemoTwo")
    @ResponseBody
    public CommonReturnType boolTestTwo() throws IOException {
        List<ESUserPlus> esUserPlusList = new ArrayList<>();
        SearchResponse<ESUserPlus> response = elasticsearchClient.search(
                s -> s.index("index01")
                        .query(
                                q -> q.matchAll(
                                        m -> m
                                )
                        )
                        .from(0)
                        .size(10)
                        .sort(
                                a -> a.field(
                                        f -> f.field("age")
                                                .order(SortOrder.Desc)
                                                .field("email")
                                                .order(SortOrder.Asc)
                                )

                        )
                ,
                ESUserPlus.class

        );
        return getCommonReturnType(esUserPlusList, response);
    }

    //高亮
    @GetMapping("/boolDemoThree")
    @ResponseBody
    public CommonReturnType boolTestThree() throws IOException {
        List<Object> esUserPlusList = new ArrayList<>();
        SearchResponse<ESUserPlus> response = elasticsearchClient.search(
                s -> s.index("index01")
                        .query(
                                q -> q.match(
                                        m -> m.field("info")
                                                .query("三国")
                                )
                        )
                        .from(0)
                        .size(10)
                        .highlight(
                                h -> h.fields("info", f -> f
                                        .preTags("<font color = 'red'>")
                                        .postTags("</font>")
                                )
                        )
                ,
                ESUserPlus.class

        );

        System.out.println(response.took());
        System.out.println(response.hits().total().value());
        response.hits().hits().forEach(e -> {
            System.out.println(e.source().toString());
            for (Map.Entry<String, List<String>> entry : e.highlight().entrySet()) {
                System.out.println("Key = " + entry.getKey());
                e.source().setInfo(String.valueOf(entry.getValue()));
                esUserPlusList.add(e.source());
            }
        });
        return CommonReturnType.create(esUserPlusList);
    }

    private CommonReturnType getCommonReturnType
            (List<ESUserPlus> esUserPlusList, SearchResponse<ESUserPlus> response) {
        List<Hit<ESUserPlus>> hits = response.hits().hits();
        for (Hit<ESUserPlus> hit : hits) {
            ESUserPlus product = hit.source();
            esUserPlusList.add(product);
        }
        return CommonReturnType.create(esUserPlusList);
    }

}
