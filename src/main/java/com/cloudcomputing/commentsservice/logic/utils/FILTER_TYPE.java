package com.cloudcomputing.commentsservice.logic.utils;

public enum FILTER_TYPE {
    BY_PRODUCT("byProduct");

    private final String filterType;

    FILTER_TYPE(final String filterType){
        this.filterType=filterType;
    }

    @Override
    public String toString() {
        return filterType;
    }
}