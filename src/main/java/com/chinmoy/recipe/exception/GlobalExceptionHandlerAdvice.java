package com.chinmoy.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(value
//            = { IllegalArgumentException.class, IllegalStateException.class })
//    protected ResponseEntity<Object> handleConflict(RuntimeException ex) {
//        return new ResponseEntity<>("Recipe not found", HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<Object> handleRecipeNotFoundException(RecipeNotFoundException ex) {
        return new ResponseEntity<>("Recipe not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Object> handleNodataFoundException(NoDataFoundException ex) {
        return new ResponseEntity<>("No recipes found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    protected ResponseEntity<Object> handleDuplicateKey(DuplicateKeyException ex) {
        return new ResponseEntity<>("Recipe Name already exists.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoDataFoundToDelete.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(NoDataFoundToDelete ex) {
        return new ResponseEntity<>("No Recipe found to delete", HttpStatus.BAD_REQUEST);
    }

}
