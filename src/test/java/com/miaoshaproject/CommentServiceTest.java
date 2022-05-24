package com.miaoshaproject;

import com.miaoshaproject.comment.pojo.Comment;
import com.miaoshaproject.comment.service.CommentService;
import com.miaoshaproject.util.snowflake.SnowFlakeID;
import com.miaoshaproject.service.impl.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @Author wangshuo
 * @Date 2022/5/13, 9:42
 * 测试mongodb数据交互
 * 依赖于spring-boot-starter-test的Test
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService commentService;
    @Autowired
    OrderServiceImpl orderService;

    @Test
    public void testFindCommentList() {

        List<Comment> commentList = commentService.findCommentList();
        for (Comment comment : commentList) {
            System.out.println(comment);
        }
    }

    @Test
    public void testSaveComment(){

        Comment comment = new Comment();
        comment.setComment("我看你");
        //自定义算法生成
        //comment.setCommentid(orderService.generateOrderNO());
        //基于雪花算法生成
        SnowFlakeID idWorker = new SnowFlakeID(0, 0);
        String s = String.valueOf(idWorker.nextId());
        System.out.println(s);
        comment.setCommentid(s);
        comment.setLikenum(8);
        comment.setNickname("mup");
        //父级ID
        comment.setParentid("0");
        comment.setPublishtime(new Date());
        comment.setReplynum(0);
        comment.setState(1);
        comment.setUserid(17);
        comment.setUnlikenum(0);
        commentService.saveComment(comment);
    }

    //条件查询和分页：查询根据子评论ID查询父评论详情
    @Test
    public void testFindByParentId(){

        Page<Comment> page = commentService.findByParentId("4", 1, 5);
        System.out.println(page.getTotalElements());
        for (Comment comment : page.getContent()) {
            System.out.println(comment);
        }
    }

    //根据ID 喜欢数加一
    @Test
    public void testIncrCommentLikeNum(){

        commentService.incrCommentLikeNum("974733071427305472");
    }

    /*
        关于@Query分页：
            问题: 在互联网发展的今天，大部分数据的体量都是庞大的，跳页的需求将消耗更多的内存和cpu，对应的就是查询慢。
        当然，如果数量不大，如果不介意慢一点，那么skip也不是啥问题，关键要看业务场景。
        看起来，分页已经实现了，但是官方文档并不推荐，说会扫描全部文档，然后再返回结果。
        The cursor.skip() method requires the server to scan from the beginning of the input results set before beginning to return results. As the offset increases, cursor.skip() will become slower.
        所以，需要一种更快的方式。其实和mysql数量大之后不推荐用limit m,n一样，解决方案是先查出当前页的第一条，然后顺序数pageSize条。MongoDB官方也是这样推荐的。
            来看看大厂们怎么做的
        Google最常用了，看起来是有跳页选择的啊。再仔细看，只有10页，多的就必须下一页，并没有提供一共多少页，跳到任意页的选择。这不就是我们的find-condition-then-limit方案吗，只是他的一页数量比较多，前端或者后端把这一页给切成了10份。
        同样，看Facebook，虽然提供了总count，但也只能下一页。
        其他场景，比如Twitter，微博，朋友圈等，根本没有跳页的概念的。
     */

    @Test
    public void selectSortPagingDataByParentId(){

        List<Comment> comments = commentService.selectSortPagingDataByParentId("4");
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }
}
