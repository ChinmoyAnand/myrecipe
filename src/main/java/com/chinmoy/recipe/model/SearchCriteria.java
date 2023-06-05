package com.chinmoy.recipe.model;


import lombok.Data;

@Data
public class SearchCriteria {
    private final Criteria criteria;
    private final String value;
}
