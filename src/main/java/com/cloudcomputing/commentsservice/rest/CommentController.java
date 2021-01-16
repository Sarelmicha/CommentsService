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
    @RequestMapping(path = "comment/{blogid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommentBoundary[] getAllComments(
            @PathVariable("blogid") String blogId,
            @RequestParam(name = "criteriaType", required = false) String criteriaType,
            @RequestParam(name = "criteriaValue", required = false) String criteriaValue,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdTimestamp") String sortBy,
            @RequestParam(name = "sortOrder", required = false, defaultValue = "DESC") String sortOrder) {
        return commentService.getAllComments(blogId,criteriaType, criteriaValue, size, page, sortBy, sortOrder).toArray(new CommentBoundary[0]);
    }

    /*--------------------- POST APIS ------------------- */
    @RequestMapping(path = "/comments/{blogid}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommentBoundary createComment(
            @PathVariable("blogid") String blogId,
            @RequestParam(name = "password") String password,
            @RequestBody CommentBoundary input) {
        return commentService.createComment(blogId, password, input);
    }

    /*--------------------- PUT APIS ------------------- */
    @RequestMapping(path = "comments/{commentid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateComment(
            @PathVariable("commentid") Long commentId,
            @RequestParam(name = "password") String password,
            @RequestBody CommentBoundary update) {
        commentService.updateComment(commentId, password, update);
    }


    /*--------------------- DELETE APIS ------------------- */
    @RequestMapping(path = "/comments/{email}/{commentid}",
            method = RequestMethod.DELETE)
    public void deleteAllCommentsOfSpecificBlog(
            @PathVariable("email") String email,
            @RequestParam(name = "password") String password,
            @PathVariable("commentid") Long commentId) {
        this.commentService.deleteComment(email, password,commentId);
    }

}
