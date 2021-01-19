package com.cloudcomputing.commentsservice.logic;

import com.cloudcomputing.commentsservice.boundaries.CommentBoundary;

import java.util.List;


public interface CommentService {

    List<CommentBoundary> getAllComments(String blogId, String criteriaType, String criteriaValue, int size, int page, String sortBy, String sortOrder);
    CommentBoundary getComment(Long commentId);
    CommentBoundary createComment(String blogId, String password, CommentBoundary input);
    CommentBoundary updateComment(Long commentId, String password, CommentBoundary commentBoundary);
    void deleteComment(String email, String password, Long commentId);


}
