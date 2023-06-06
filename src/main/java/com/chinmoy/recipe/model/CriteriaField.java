package com.chinmoy.recipe.model;

public enum CriteriaField {

    SERVINGS("serve"),
    INGREDIENT("ingredient"),
    DIET("diet"),
    INSTRUCTIONS("instruction");

    private String field;

    private CriteriaField(String field){
        this.field = field;
    }
}
