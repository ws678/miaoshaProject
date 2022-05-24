package com.miaoshaproject.comment.dao;

import com.miaoshaproject.comment.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @Author wangshuo
 * @Date 2022/5/13, 8:47
 * 继承MongoRepository,绑定集合
 */
public interface CommentRepository extends MongoRepository<Comment, String> {

    //方法名不要写错，第二个参数用于分页
    Page<Comment> findByParentid(String parentid, PageRequest pageable);

    //使用@Query的原生查询语句的方式查询
    //使用这种方式方法名可以自定义
    @Query(value = "{ parentid: ?0 }" ,sort = "{publishtime : -1}")
    List<Comment> selectSortPagingDataByParentId(String parentid);
}
