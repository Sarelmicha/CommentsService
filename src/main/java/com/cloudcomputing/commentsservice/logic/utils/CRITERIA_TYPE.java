package com.cloudcomputing.commentsservice.logic.utils;

public enum  CRITERIA_TYPE {

    BY_TYPE("byType"),
    BY_USER("byUser"),
    BY_COUNTRY("byCountry");

    private final String criteriaType;

    CRITERIA_TYPE(final String criteriaType){
        this.criteriaType = criteriaType;
    }

    @Override
    public String toString() {
        return criteriaType;
    }
}
