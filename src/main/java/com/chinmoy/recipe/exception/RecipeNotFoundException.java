package com.chinmoy.recipe.exception;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException(String id) {
        super("Recipe not found for "+ id);
    }
}
