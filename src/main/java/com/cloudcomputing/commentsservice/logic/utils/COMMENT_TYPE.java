package com.cloudcomputing.commentsservice.logic.utils;

public enum COMMENT_TYPE {

    TEXT("text"),
    REACTION("reaction");

    private final String commentType;

    COMMENT_TYPE(final String commentType){
        this.commentType =commentType;
    }

    @Override
    public String toString() {
        return commentType;
    }
}
