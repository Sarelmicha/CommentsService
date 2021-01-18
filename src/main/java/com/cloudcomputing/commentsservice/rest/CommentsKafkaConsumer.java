package com.cloudcomputing.commentsservice.rest;

import com.cloudcomputing.commentsservice.boundaries.CommentBoundary;
import com.cloudcomputing.commentsservice.logic.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class CommentsKafkaConsumer {

    private CommentService commentService;

    @Autowired
    public CommentsKafkaConsumer(CommentService commentService) {
        this.commentService = commentService;
    }

    @Bean
    public Consumer<CommentBoundary> receiveAndHandleComment(){
        return comment-> this.commentService.createComment(comment.getBlogId(),comment.getUser().getEmail(),comment);
    }
}

