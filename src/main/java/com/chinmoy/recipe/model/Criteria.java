package com.chinmoy.recipe.model;

public enum Criteria {

    SERVE("serve"),
    INGREDIENT_INCL("ingredient"),
    INGREDIENT_EXCL("ingredient"),
    DIET("diet"),
    INSTRUCTION("instruction");

    private String field;

    private Criteria(String field){
        this.field = field;
    }
}
