package com.cloudcomputing.commentsservice.logic.db;

import com.cloudcomputing.commentsservice.boundaries.CommentBoundary;
import com.cloudcomputing.commentsservice.data.CommentEntity;
import com.cloudcomputing.commentsservice.exceptions.BadRequestException;
import com.cloudcomputing.commentsservice.logic.TicketService;
import com.cloudcomputing.commentsservice.logic.utils.COMMENT_TYPE;
import com.cloudcomputing.commentsservice.producers.SupportManagementRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class DatabaseTicketService implements TicketService {

    private SupportManagementRestService supportManagementRestService;

    @Autowired
    public DatabaseTicketService(SupportManagementRestService supportManagementRestService) {
        this.supportManagementRestService = supportManagementRestService;
    }

    @Override
    @Transactional
    public void createTicket(CommentBoundary commentBoundary) {

        if (commentBoundary.getCommentType() == COMMENT_TYPE.TEXT && commentBoundary.getTagSupport())
            this.supportManagementRestService.createTicket(commentBoundary.getUser().getEmail(), commentBoundary.getId());
    }
}
