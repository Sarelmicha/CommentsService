package com.cloudcomputing.commentsservice.logic;

import com.cloudcomputing.commentsservice.boundaries.CommentBoundary;

import java.util.List;


public interface CommentService {

    List<CommentBoundary> getAllComments(String blogId, String criteriaType, String criteriaValue, int size, int page, String sortBy, String sortOrder);
    CommentBoundary getComment(Long commentId);
    CommentBoundary createComment(CommentBoundary commentBoundary);
    void updateComment(Long commentId, CommentBoundary commentBoundary);
    void deleteComment(String email, String password, Long commentId);


}
