package com.cloudcomputing.commentsservice.logic.db;

import com.cloudcomputing.commentsservice.boundaries.BlogPostBoundary;
import com.cloudcomputing.commentsservice.boundaries.CommentBoundary;
import com.cloudcomputing.commentsservice.boundaries.UserBoundary;
import com.cloudcomputing.commentsservice.consumers.BlogManagementRestService;
import com.cloudcomputing.commentsservice.dao.CommentDao;
import com.cloudcomputing.commentsservice.data.CommentEntity;
import com.cloudcomputing.commentsservice.exceptions.BadRequestException;
import com.cloudcomputing.commentsservice.exceptions.ConflictException;
import com.cloudcomputing.commentsservice.exceptions.NotFoundException;
import com.cloudcomputing.commentsservice.exceptions.UnauthorizedException;
import com.cloudcomputing.commentsservice.logic.EnhancedCommentService;
import com.cloudcomputing.commentsservice.logic.utils.CRITERIA_TYPE;
import com.cloudcomputing.commentsservice.logic.utils.CommentConverter;
import com.cloudcomputing.commentsservice.logic.utils.COMMENT_TYPE;
import com.cloudcomputing.commentsservice.consumers.UserManagementRestService;
import com.cloudcomputing.commentsservice.producers.SupportManagementRestService;
import com.cloudcomputing.commentsservice.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabaseCommentService implements EnhancedCommentService {

    private CommentDao commentDao;
    private CommentConverter converter;
    private UserManagementRestService userManagementRestService;
    private BlogManagementRestService blogManagementRestService;
    private SupportManagementRestService supportManagementRestService;

    @Autowired
    public DatabaseCommentService(CommentDao commentDao, CommentConverter converter
            , UserManagementRestService userManagementRestService
            , BlogManagementRestService blogManagementRestService
            , SupportManagementRestService supportManagementRestService) {
        this.commentDao = commentDao;
        this.converter = converter;
        this.userManagementRestService = userManagementRestService;
        this.blogManagementRestService = blogManagementRestService;
        this.supportManagementRestService = supportManagementRestService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentBoundary> getAllComments(String blogId, String criteriaType, String criteriaValue, int size, int page, String sortBy, String sortOrder) {
        if(blogId!=null) {
            return getForSpecificBlog(blogId, criteriaType, criteriaValue, size, page, sortBy, sortOrder);
        }
        return getForAllBlogs(criteriaType, criteriaValue, size, page, sortBy, sortOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentBoundary getComment(Long commentId) {
        CommentEntity commentEntity = commentDao.findById(commentId);
        if(commentEntity==null){
            throw new NotFoundException("No comment found with id:" + commentId);
        }
        return this.converter.fromEntity(commentEntity);
    }

    @Override
    @Transactional
    public CommentBoundary createComment(CommentBoundary commentBoundary) {

        validateBlog(commentBoundary);

        userManagementRestService.login(commentBoundary.getUser().getEmail(), commentBoundary.getUser().getPassword());
        BlogPostBoundary blog = blogManagementRestService.getBlogById(commentBoundary.getBlog().getBlogId(), commentBoundary.getBlog().getBloggerEmail(), commentBoundary.getBlog().getProductId());

        // check if comment is reaction that user didn't post a reaction already for this blog
        if (commentBoundary.getCommentType() == COMMENT_TYPE.REACTION) {
            List<CommentEntity> allUserComments = commentDao.findAllByUser_Email_AndBlog_blogId_AndCommentType(
                    commentBoundary.getUser().getEmail(), commentBoundary.getBlog().getBlogId(), commentBoundary.getCommentType());
            if (!allUserComments.isEmpty()) {
                throw new ConflictException("User already reacted to this blog.");
            }
        }
        CommentEntity commentEntity = this.converter.toEntity(commentBoundary);

        this.commentDao.save(commentEntity);
        // check if comment is a text and tagged support to send comment for support
        if (commentBoundary.getCommentType() == COMMENT_TYPE.TEXT && commentBoundary.getTagSupport()) {
            this.supportManagementRestService.createTicket(commentEntity.getUser().getEmail(), commentEntity.getId());
        }

        return this.converter.fromEntity(commentEntity);
    }

    @Override
    @Transactional
    public void updateComment(Long commentId, CommentBoundary commentBoundary) {
        boolean updated = false;
        // Check if the comment is exists in the comments database
        CommentEntity commentEntity = commentDao.findById(commentId);
        if (commentEntity == null) {
            throw new NotFoundException("No comment found with id " + commentId);
        }

        // Check if User is exists in the User Service and login with his password
        userManagementRestService.login(commentBoundary.getUser().getEmail(), commentBoundary.getUser().getPassword());

        // set the updated date to the current date
        CommentEntity updatedComment = this.converter.toEntity(commentBoundary);
        if (!commentEntity.getUser().getEmail().equalsIgnoreCase(updatedComment.getUser().getEmail())) {
            throw new BadRequestException("The user cannot update comments of other user");
        }

        if (commentEntity.getCommentType() != updatedComment.getCommentType()) {
            throw new BadRequestException("Comment cannot be updated with different comment type");
        }
        if (updatedComment.getCommentType() == COMMENT_TYPE.TEXT && updatedComment.getTagSupport()) {
            this.supportManagementRestService.createTicket(commentEntity.getUser().getEmail(), commentEntity.getId());
        }
        // Can update only content
        if (updatedComment.getCommentContent() != null) {
            commentEntity.setUpdatedTimestamp(updatedComment.getCreatedTimestamp());
            commentEntity.setCommentContent(updatedComment.getCommentContent());

            commentDao.save(commentEntity);
        }
    }

    @Override
    @Transactional
    public void deleteAllComments(String email, String password, String blogId) {
        // Check user exists and it is an admin
        UserBoundary userBoundary = userManagementRestService.login(email, password);
        if (!Arrays.stream(userBoundary.getRoles()).anyMatch(Constants.ADMIN::equals)) {
            throw new UnauthorizedException("User does not have the permissions to make this operation.");
        }
        if (blogId != null) {
            this.commentDao.deleteAllByBlog_blogId(blogId);
        } else {
            this.commentDao.deleteAll();
        }
    }

    @Override
    @Transactional
    public void deleteComment(String email, String password, Long commentId) {
        // Check if user exist from user service
        UserBoundary userBoundary = userManagementRestService.login(email, password);

        // Get comment to delete
        CommentEntity toBeDeleteComment = this.commentDao.findById(commentId);

        // If not admin and not creator
        if (!Arrays.stream(userBoundary.getRoles()).anyMatch(Constants.ADMIN::equals)) {
            if (!toBeDeleteComment.getUser().getEmail().equalsIgnoreCase(email)) {
                throw new UnauthorizedException("The user cannot delete comments of other user");
            }
        }
        this.commentDao.deleteById(commentId);
    }

    private List<CommentBoundary> getForSpecificBlog(String blogId, String criteriaType, String criteriaValue, int size, int page, String sortBy, String sortOrder) {
        if (criteriaType != null && criteriaValue != null) {
            if (criteriaType.equalsIgnoreCase(CRITERIA_TYPE.BY_TYPE.toString())) {
                return this.commentDao
                        .findAllByBlog_blogId_AndCommentType(blogId, COMMENT_TYPE.findByString(criteriaValue.toLowerCase()),
                                PageRequest.of(page, size, Sort.Direction.valueOf(sortOrder), sortBy))
                        .stream().map(this.converter::fromEntity).collect(Collectors.toList());
            } else if (criteriaType.equalsIgnoreCase(CRITERIA_TYPE.BY_USER.toString())) {
                return this.commentDao
                        .findAllByBlog_blogId_AndUser_Email(blogId, criteriaValue,
                                PageRequest.of(page, size, Sort.Direction.valueOf(sortOrder), sortBy))
                        .stream().map(this.converter::fromEntity).collect(Collectors.toList());
            } else if (criteriaType.equalsIgnoreCase(CRITERIA_TYPE.BY_COUNTRY.toString())) {
                return this.commentDao.findAllByBlog_blogId_AndCountry(blogId, criteriaValue,
                        PageRequest.of(page, size, Sort.Direction.valueOf(sortOrder), sortBy))
                        .stream().map(this.converter::fromEntity).collect(Collectors.toList());
            }
        }
        return this.commentDao.findAllByBlog_blogId(blogId, PageRequest.of(page, size, Sort.Direction.valueOf(sortOrder), sortBy))
                .stream().map(this.converter::fromEntity).collect(Collectors.toList());
    }

    private List<CommentBoundary> getForAllBlogs(String criteriaType, String criteriaValue, int size, int page, String sortBy, String sortOrder) {
        if (criteriaType != null && criteriaValue != null) {
            if (criteriaType.equalsIgnoreCase(CRITERIA_TYPE.BY_TYPE.toString())) {
                return this.commentDao
                        .findAllByCommentType(COMMENT_TYPE.findByString(criteriaValue.toLowerCase()),
                                PageRequest.of(page, size, Sort.Direction.valueOf(sortOrder), sortBy))
                        .stream().map(this.converter::fromEntity).collect(Collectors.toList());
            } else if (criteriaType.equalsIgnoreCase(CRITERIA_TYPE.BY_USER.toString())) {
                return this.commentDao
                        .findAllByUser_Email(criteriaValue,
                                PageRequest.of(page, size, Sort.Direction.valueOf(sortOrder), sortBy))
                        .stream().map(this.converter::fromEntity).collect(Collectors.toList());
            } else if (criteriaType.equalsIgnoreCase(CRITERIA_TYPE.BY_COUNTRY.toString())) {
                return this.commentDao.findAllByCountry(criteriaValue,
                        PageRequest.of(page, size, Sort.Direction.valueOf(sortOrder), sortBy))
                        .stream().map(this.converter::fromEntity).collect(Collectors.toList());
            }
        }
        return this.commentDao.findAll(PageRequest.of(page, size, Sort.Direction.valueOf(sortOrder), sortBy))
                .stream().map(this.converter::fromEntity).collect(Collectors.toList());
    }

    public void validateBlog(CommentBoundary commentBoundary) {

        if(commentBoundary.getBlog().getBlogId() == null ||
                commentBoundary.getBlog().getBloggerEmail() == null ||
                commentBoundary.getBlog().getProductId() == null){
            throw new BadRequestException("Missing blog details. Blog details are requested.");
        }
    }
}
