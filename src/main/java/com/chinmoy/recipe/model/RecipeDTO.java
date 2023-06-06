package com.chinmoy.recipe.model;

import lombok.Data;

@Data
public class RecipeDTO extends BaseRecipeDTO {
    private String id;
    private Integer version;
}
