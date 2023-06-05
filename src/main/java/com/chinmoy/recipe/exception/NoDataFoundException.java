package com.chinmoy.recipe.exception;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException() {
        super("No data found. Table is Empty");
    }
}
