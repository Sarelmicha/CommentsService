package com.cloudcomputing.commentsservice.data;
import com.cloudcomputing.commentsservice.logic.utils.COMMENT_TYPE;
import com.cloudcomputing.commentsservice.utils.Blog;
import com.cloudcomputing.commentsservice.utils.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="COMMENT")
public class CommentEntity {

    @Id
    @GeneratedValue
    private Long id; // ID PK VARCHAR(255)

    @Embedded
    private User user;

    @Embedded
    private Blog blog;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTimestamp; // CREATED_TIME_STAMP TIMESTAMP

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTimestamp; // UPDATED_TIME_STAMP TIMESTAMP

    @NotEmpty(message="country can not be empty")
    @NotNull(message="country can not be null")
    private String country;  // COUNTRY VARCHAR(255)

    private boolean tagSupport;

    @Enumerated(EnumType.STRING)
    @NotNull(message="comment type can not be null")
    private COMMENT_TYPE commentType;

    @Lob
    @Convert(converter = com.cloudcomputing.commentsservice.logic.utils.MapToJsonConverter.class)
    @NotNull(message="comment content can not be null")
    @NotEmpty(message="comment content can not be empty")
    private Map<String, Object> commentContent; // ELEMENT_ATTRIBUTES CLOB

    public CommentEntity() {
        this.commentContent = new HashMap<>();
    }

    public CommentEntity(Long id, User user, Blog blog, Date createdTimestamp, Date updatedTimestamp, @NotEmpty(message = "country can not be empty") String country,boolean tagSupport, COMMENT_TYPE commentType, Map<String, Object> commentContent) {
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

    public User getUser(){
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Blog getBlog() { return blog; }

    public void setBlog(Blog blog) { this.blog = blog; }

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

    public boolean getTagSupport() {
        return tagSupport;
    }

    public void setTagSupport(boolean tagSupport) {
        this.tagSupport = tagSupport;
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

    public void setCommentContent(Map<String, Object> commentContent) {
        this.commentContent = commentContent;
    }
}
