package com.cloudcomputing.commentsservice.rest;

import com.cloudcomputing.commentsservice.boundaries.CommentBoundary;
import com.cloudcomputing.commentsservice.logic.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /*--------------------- GET APIS ------------------- */
    @RequestMapping(path = "/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommentBoundary[] getAllComments(
            @RequestParam(name = "blogId", required = false) String blogId,
            @RequestParam(name = "criteriaType", required = false) String criteriaType,
            @RequestParam(name = "criteriaValue", required = false) String criteriaValue,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdTimestamp") String sortBy,
            @RequestParam(name = "sortOrder", required = false, defaultValue = "DESC") String sortOrder) {
        return commentService.getAllComments(blogId, criteriaType, criteriaValue, size, page, sortBy, sortOrder).toArray(new CommentBoundary[0]);
    }

    @RequestMapping(path = "/comment/{commentid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommentBoundary getComment(
            @PathVariable("commentid") Long commentId) {
        return commentService.getComment(commentId);
    }

    /*--------------------- POST APIS ------------------- */
    @RequestMapping(path = "/comments",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CommentBoundary createComment(
            @RequestBody CommentBoundary input) {
        return commentService.createComment(input);
    }

    /*--------------------- PUT APIS ------------------- */
    @RequestMapping(path = "comments/{commentid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateComment(
            @PathVariable("commentid") Long commentId,
            @RequestBody CommentBoundary update) {
        commentService.updateComment(commentId, update);
    }

    /*--------------------- DELETE APIS ------------------- */
    @RequestMapping(path = "/comments/{commentid}/{email}",
            method = RequestMethod.DELETE)
    public void deleteComment(
            @PathVariable("commentid") Long commentId,
            @PathVariable("email") String email,
            @RequestParam(name = "password") String password) {
        this.commentService.deleteComment(email, password,commentId);
    }

}
