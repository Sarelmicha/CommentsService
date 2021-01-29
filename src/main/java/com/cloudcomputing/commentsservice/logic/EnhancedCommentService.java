package com.cloudcomputing.commentsservice.logic;

public interface EnhancedCommentService extends CommentService {

    void deleteAllComments(String email, String password, String blogId);
}
