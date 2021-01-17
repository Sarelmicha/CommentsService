package com.cloudcomputing.commentsservice.rest;

import com.cloudcomputing.commentsservice.logic.EnhancedCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class AdminController  {

    private EnhancedCommentService enhancedCommentService;

    @Autowired
    public AdminController(EnhancedCommentService enhancedCommentService) {
        this.enhancedCommentService = enhancedCommentService;
    }

    /*--------------------- DELETE APIS ------------------- */
    @RequestMapping(path = "/comments/admin/{email}",
            method = RequestMethod.DELETE)
    public void deleteAllComments(
            @PathVariable("email") String email,
            @RequestParam(name = "password") String password) {
        this.enhancedCommentService.deleteAllComments(email, password);
    }

    @RequestMapping(path = "/comments/admin/{blogid}/{email}",
            method = RequestMethod.DELETE)
    public void deleteAllCommentsOfSpecificBlog(
            @PathVariable("blogid") String blogId,
            @PathVariable("email") String email,
            @RequestParam(name = "password") String password) {
        this.enhancedCommentService.deleteAllCommentsOfSpecificBlog(blogId, email, password);
    }
}
