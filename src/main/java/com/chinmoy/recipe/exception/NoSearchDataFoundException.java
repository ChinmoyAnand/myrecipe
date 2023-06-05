package com.chinmoy.recipe.exception;

public class NoSearchDataFoundException extends RuntimeException {
    public NoSearchDataFoundException() {
        super("No data found for search criteria.Refine your search condition");
    }
}
