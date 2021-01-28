package com.cloudcomputing.commentsservice.dao;

import com.cloudcomputing.commentsservice.consumers.BlogManagementRestService;
import com.cloudcomputing.commentsservice.data.CommentEntity;
import com.cloudcomputing.commentsservice.logic.utils.COMMENT_TYPE;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CommentDao  extends JpaRepository<CommentEntity, String> {

    List<CommentEntity> findAllByUser_Email_AndBlog_blogId_AndCommentType(
            @Param("email") String email, @Param("blogId") String blogId, @Param("commentType") COMMENT_TYPE commentType);
    List<CommentEntity> findAllByBlog_blogId(@Param("blogId") String blogId, Pageable pageable);
    List<CommentEntity> findAllByBlog_blogId_AndCommentType(@Param("blogId") String blogId, @Param("commentType") COMMENT_TYPE commentType,  Pageable pageable);
    List<CommentEntity> findAllByBlog_blogId_AndUser_Email(@Param("blogId") String blogId, @Param("email") String email, Pageable pageable);
    List<CommentEntity> findAllByBlog_blogId_AndCountry(@Param("blogId") String blogId, @Param("country") String country, Pageable pageable);

    List<CommentEntity> findAllByCommentType(@Param("commentType") COMMENT_TYPE commentType,  Pageable pageable);
    List<CommentEntity> findAllByUser_Email(@Param("email") String email, Pageable pageable);
    List<CommentEntity> findAllByCountry(@Param("country") String country, Pageable pageable);

    CommentEntity findById(@Param("id") Long id);

    void deleteById(@Param("id") Long id);
    void deleteAllByBlog_blogId(@Param("blogId") String blogId);

}
