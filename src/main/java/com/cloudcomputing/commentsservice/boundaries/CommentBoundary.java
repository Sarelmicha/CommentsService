package com.cloudcomputing.commentsservice.boundaries;

import com.cloudcomputing.commentsservice.exceptions.BadRequestException;
import com.cloudcomputing.commentsservice.logic.utils.COMMENT_TYPE;
import com.cloudcomputing.commentsservice.utils.Blog;
import com.cloudcomputing.commentsservice.utils.User;

import java.util.Date;
import java.util.Map;

public class CommentBoundary {

    private Long id;
    private User user;
    private Blog blog;
    private Date createdTimestamp;
    private Date updatedTimestamp;
    private String country;
    private boolean tagSupport;
    private COMMENT_TYPE commentType;
    private Map<String, Object> commentContent;

    public CommentBoundary() {

    }

    public CommentBoundary(Long id, User user, Blog blog, Date createdTimestamp, Date updatedTimestamp, String country,boolean tagSupport,COMMENT_TYPE commentType, Map<String, Object> commentContent) {
        this.id = id;
        this.user = user;
        this.blog=blog;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
        this.country = country;
        this.tagSupport = tagSupport;
        this.commentType = commentType;
        this.commentContent = commentContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Date getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Date updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public COMMENT_TYPE getCommentType() {
        return commentType;
    }

    public void setCommentType(COMMENT_TYPE commentType) {
        this.commentType = commentType;
    }

    public Map<String, Object> getCommentContent() {
        return commentContent;
    }

    public boolean getTagSupport() {
        return tagSupport;
    }

    public void setTagSupport(boolean tagSupport) {
        this.tagSupport = tagSupport;
    }

    public void setCommentContent(Map<String, Object> commentContent) {
        this.commentContent = commentContent;
    }
}
