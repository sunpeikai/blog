package com.sandman.blog.entity.user;

import java.time.ZonedDateTime;
import java.util.List;

public class Comment {
    private Long id;
    private Long bloggerId;
    private Blogger blogger;//评论人详细信息
    private String content;//评论内容
    private Long parentId;//回复的时候用，标识回复的哪一条评论id .没有就是-1
    private String parentName;//回复的时候用，标识回复的谁
    private Integer replayed;//1 被回复了 ；2 没有回复
    private Long blogId;
    private Blog blog;//本篇博客
    private Integer status;//审核状态 3 待审核 1 审核通过 2 审核未通过
    private Long createBy;
    private ZonedDateTime createTime;
    private Long updateBy;
    private ZonedDateTime updateTime;
    private Integer delFlag;
    private List<Comment> commentList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBloggerId() {
        return bloggerId;
    }

    public void setBloggerId(Long bloggerId) {
        this.bloggerId = bloggerId;
    }

    public Blogger getBlogger() {
        return blogger;
    }

    public void setBlogger(Blogger blogger) {
        this.blogger = blogger;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getReplayed() {
        return replayed;
    }

    public void setReplayed(Integer replayed) {
        this.replayed = replayed;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", bloggerId=" + bloggerId +
                ", blogger=" + blogger +
                ", content='" + content + '\'' +
                ", parentId=" + parentId +
                ", parentName='" + parentName + '\'' +
                ", replayed=" + replayed +
                ", blogId=" + blogId +
                ", blog=" + blog +
                ", status=" + status +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", updateBy=" + updateBy +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                ", commentList=" + commentList +
                '}';
    }
}
