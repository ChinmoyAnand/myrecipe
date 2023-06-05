package com.chinmoy.recipe.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BaseRecipeDTO {
    @NotNull
    private String name;
    private Cuisine cuisine;
    private String instruction;
    private int cookTime;
    private int serve;
    private Course course;
    private List<String> ingredient;
    private String author;
    private Diet diet;
    private String notes;
}
