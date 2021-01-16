package com.cloudcomputing.commentsservice.logic;

public interface EnhancedCommentService extends CommentService {

    void deleteAllComments(String email, String password);
    void deleteAllCommentsOfSpecificBlog(String blogId, String email, String password);

}
