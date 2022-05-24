package com.miaoshaproject.comment.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author wangshuo
 * @Date 2022/5/12, 21:43
 * Please add a comment
 */
@Document(collection = "comment")//绑定mongodb集合
//@CompoundIndex(def = "{'commentid':1,'comment':-1}")：添加复合索引，索引建议用小黑窗创建
public class Comment implements Serializable {

    //主键id
    @Id
    private String id;

    //评论id
    private String commentid;

    //评论内容
    //@Field("comment")： 对应的mongodb字段名，若这里的命名与mongodb的名字一样就不需要加这个注解了
    private String comment;

    //发布日期
    private Date publishtime;

    //添加单列索引
    @Indexed
    private Integer userid;

    //用户昵称
    private String nickname;

    private Integer likenum;

    private Integer unlikenum;

    //回复数
    private Integer replynum;

    //状态：1.评论正常 2.评论违规被折叠 3.已删除
    private Integer state;

    //子评论属性：上一级id
    private String parentid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentid='" + commentid + '\'' +
                ", comment='" + comment + '\'' +
                ", publishtime=" + publishtime +
                ", userid=" + userid +
                ", nickname='" + nickname + '\'' +
                ", likenum=" + likenum +
                ", unlikenum=" + unlikenum +
                ", replynum=" + replynum +
                ", state='" + state + '\'' +
                ", parentid='" + parentid + '\'' +
                '}';
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getLikenum() {
        return likenum;
    }

    public void setLikenum(Integer likenum) {
        this.likenum = likenum;
    }

    public Integer getUnlikenum() {
        return unlikenum;
    }

    public void setUnlikenum(Integer unlikenum) {
        this.unlikenum = unlikenum;
    }

    public Integer getReplynum() {
        return replynum;
    }

    public void setReplynum(Integer replynum) {
        this.replynum = replynum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
}
