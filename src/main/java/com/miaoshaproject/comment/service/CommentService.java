package com.miaoshaproject.comment.service;

import com.miaoshaproject.comment.dao.CommentRepository;
import com.miaoshaproject.comment.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wangshuo
 * @Date 2022/5/13, 8:49
 * Please add a comment
 */
@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> findCommentList() {
        return commentRepository.findAll();
    }

    public Comment findCommentById(String id) {
        return commentRepository.findById(id).get();
    }

    public void deleteCommentById(String id) {
        commentRepository.deleteById(id);
    }

    public Page<Comment> findByParentId(String parentid, int page, int size) {

        //需要注意默认page是从0开始的，为了更易于应用，我们在这里直接给他减一，这样我们查询第一页的时候就可以直接传入参数1
        Page<Comment> byParentid = commentRepository.findByParentid(parentid, PageRequest.of(page - 1, size));
        return byParentid;
    }

    //使用MongoTemplate来进行操作，好处是在一些情境下可以减少IO操作
    public void incrCommentLikeNum(String id) {

        //参数1 查询条件 可以一直and
        Query query = Query.query(Criteria.where("commentid").is(id))/*.addCriteria()*/;
        //参数2 更新条件
        Update update = new Update();
        update.inc("likenum");
        //.updateFirst 修改一条 .updateMulti 修改多条
        mongoTemplate.updateFirst(query, update, Comment.class);
    }

    public List<Comment> selectSortPagingDataByParentId(String parentid) {
        return commentRepository.selectSortPagingDataByParentId(parentid);
    }
}
