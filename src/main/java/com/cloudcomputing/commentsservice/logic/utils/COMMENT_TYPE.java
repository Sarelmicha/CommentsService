package com.cloudcomputing.commentsservice.logic.utils;

import java.util.HashMap;
import java.util.Map;

public enum COMMENT_TYPE {

    TEXT("text"),
    REACTION("reaction");

    private final String commentType;

    COMMENT_TYPE(final String commentType){
        this.commentType =commentType;
    }

    private static final Map<String,COMMENT_TYPE> map;
    static {
        map = new HashMap<>();
        for (COMMENT_TYPE v : COMMENT_TYPE.values()) {
            map.put(v.commentType, v);
        }
    }
    public static COMMENT_TYPE findByString(String value) {
        return map.get(value);
    }

    @Override
    public String toString() {
        return commentType;
    }
}
