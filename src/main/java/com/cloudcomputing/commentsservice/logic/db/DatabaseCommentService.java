package com.cloudcomputing.commentsservice.logic.db;

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
import com.cloudcomputing.commentsservice.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DatabaseCommentService implements EnhancedCommentService {

    private CommentDao commentDao;
    private CommentConverter converter;
    private UserManagementRestService userManagementRestService;
    private BlogManagementRestService blogManagementRestService;

    @Autowired
    public DatabaseCommentService(CommentDao commentDao, CommentConverter converter
            ,UserManagementRestService userManagementRestService
            ,BlogManagementRestService blogManagementRestService) {
        this.commentDao = commentDao;
        this.converter = converter;
        this.userManagementRestService = userManagementRestService;
        this.blogManagementRestService = blogManagementRestService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentBoundary> getAllComments(String blogId, String criteriaType, String criteriaValue, int size, int page, String sortBy, String sortOrder) {

        checkBlogExists(blogId);

        if (criteriaType != null && criteriaValue != null) {
            if (criteriaType.equals(CRITERIA_TYPE.BY_TYPE.toString())) {
                return this.commentDao
                        .findAllByBlogId_AndCommentType(blogId, COMMENT_TYPE.valueOf(criteriaValue),
                                PageRequest.of(page, size, Sort.Direction.valueOf(sortOrder), sortBy))
                        .stream().map(this.converter::fromEntity).collect(Collectors.toList());
            } else if (criteriaType.equals(CRITERIA_TYPE.BY_USER.toString())) {
                return this.commentDao
                        .findAllByBlogId_AndUser_Email(blogId, criteriaValue,
                                PageRequest.of(page, size, Sort.Direction.valueOf(sortOrder), sortBy))
                        .stream().map(this.converter::fromEntity).collect(Collectors.toList());
            } else if (criteriaType.equals(CRITERIA_TYPE.BY_COUNTRY.toString())) {
                return this.commentDao.findAllByBlogId_AndCountry(blogId, criteriaValue,
                        PageRequest.of(page, size, Sort.Direction.valueOf(sortOrder), sortBy))
                        .stream().map(this.converter::fromEntity).collect(Collectors.toList());
            }
        }
        return this.commentDao.findAllByBlogId(blogId, PageRequest.of(page, size, Sort.Direction.valueOf(sortOrder), sortBy))
                .stream().map(this.converter::fromEntity).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentBoundary getComment(Long commentId) {
        CommentEntity commentEntity = this.commentDao.findById(commentId);
        if(commentEntity == null){
            throw new NotFoundException("Comment with id " + commentId + " is not exists.");
        }
        return this.converter.fromEntity(commentEntity);
    }

    @Override
    @Transactional
    public CommentBoundary createComment(String blogId, String password, CommentBoundary commentBoundary) {

        userManagementRestService.login(commentBoundary.getUser().getEmail(), password);
        checkBlogExists(blogId);

        commentBoundary.validate();

        if (commentBoundary.getCommentType() == COMMENT_TYPE.REACTION) {
            List<CommentEntity> allUserComments = commentDao.findAllByUser_Email_AndBlogId_AndCommentType(
                    commentBoundary.getUser().getEmail(), blogId, commentBoundary.getCommentType());

            if (allUserComments.size() > 0) {
                throw new ConflictException("User already reacted to this blog.");
            }
        }

        CommentEntity commentEntity = this.converter.toEntity(commentBoundary);
        commentEntity.setBlogId(blogId);

        this.commentDao.save(commentEntity);

        return this.converter.fromEntity(commentEntity);

    }

    @Override
    @Transactional
    public CommentBoundary updateComment(Long commentId, String password, CommentBoundary commentBoundary) {

        userManagementRestService.login(commentBoundary.getUser().getEmail(), password);

        // Check if User is exists in the User Service and login with his password
        commentBoundary.validate();

        // Check if the comment is exists in the comments database
        CommentEntity commentEntity = commentDao.findById(commentId);
        if (commentEntity == null) {
            throw new NotFoundException("No comment found with id " + commentId);
        }
        // set the updated date to the current date
        CommentEntity updatedComment = this.converter.toEntity(commentBoundary);
        if (!commentEntity.getUser().getEmail().equalsIgnoreCase(updatedComment.getUser().getEmail())) {
            throw new BadRequestException("The user cannot update comments of other user");
        }
        if (commentEntity.getCommentType() != updatedComment.getCommentType()) {
            throw new BadRequestException("Comment cannot be updated with different comment type");
        }

        commentEntity.setUpdatedTimestamp(updatedComment.getCreatedTimestamp());
        commentEntity.setCommentContent(updatedComment.getCommentContent());
        commentEntity.setTagSupport(updatedComment.getTagSupport());

        commentDao.save(commentEntity);

        return this.converter.fromEntity(commentEntity);
    }

    @Override
    @Transactional
    public void deleteAllComments(String email, String password) {
        UserBoundary userBoundary = userManagementRestService.login(email, password);
        checkAdminRole(userBoundary.getRoles());
        this.commentDao.deleteAll();
    }

    @Override
    @Transactional
    public void deleteAllCommentsOfSpecificBlog(String blogId, String email, String password) {

        checkBlogExists(blogId);

        UserBoundary userBoundary = userManagementRestService.login(email, password);
        checkAdminRole(userBoundary.getRoles());
        this.commentDao.deleteAllByBlogId(blogId);
    }

    @Override
    @Transactional
    public void deleteComment(String email, String password, Long commentId) {

        userManagementRestService.login(email, password);
        CommentEntity toBeDeleteComment = this.commentDao.findById(commentId);

        if (toBeDeleteComment == null) {
            throw new NotFoundException("No comment found with id " + commentId);
        }
        if (!toBeDeleteComment.getUser().getEmail().equalsIgnoreCase(email)) {
            throw new BadRequestException("The user cannot delete comments of other user");
        }
        this.commentDao.deleteById(commentId);
    }

    private void checkAdminRole(String[] roles) {

        if (!Arrays.stream(roles).anyMatch(Constants.ADMIN::equals)) {
            throw new UnauthorizedException("User does not have the permissions to make this operation.");
        }
    }

    private void checkBlogExists(String blogId) {

        Map<String, Object> blog = blogManagementRestService.getBlog(blogId);
        if (blog == null) {
            throw new NotFoundException("No blog found with the blogId " + blogId);
        }
    }
}
