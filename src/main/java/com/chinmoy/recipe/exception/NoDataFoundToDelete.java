package com.chinmoy.recipe.exception;

public class NoDataFoundToDelete extends RuntimeException {

    public NoDataFoundToDelete(String id){
        super(String.format("Recipe with id %s not found to delete", id));
    }
}
