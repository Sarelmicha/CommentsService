package com.cloudcomputing.commentsservice.dao;

import com.cloudcomputing.commentsservice.data.CommentEntity;
import com.cloudcomputing.commentsservice.logic.utils.COMMENT_TYPE;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentDao  extends JpaRepository<CommentEntity, String> {

    List<CommentEntity> findAllByUser_Email_AndBlogId_AndCommentType(
            @Param("email") String email, @Param("blogId") String blogId, @Param("commentType") COMMENT_TYPE commentType);
    List<CommentEntity> findAllByBlogId(@Param("blogId") String blogId, Pageable pageable);
    List<CommentEntity> findAllByBlogId_AndCommentType(@Param("blogId") String blogId, @Param("commentType") COMMENT_TYPE commentType,  Pageable pageable);
    List<CommentEntity> findAllByBlogId_AndUser_Email(@Param("blogId") String blogId, @Param("email") String email, Pageable pageable);
    List<CommentEntity> findAllByBlogId_AndCountry(@Param("blogId") String blogId, @Param("country") String country, Pageable pageable);

    CommentEntity findById(@Param("id") Long id);

    void deleteById(@Param("id") Long id);
    void deleteAllByBlogId(@Param("blogId") String blogId);


}
