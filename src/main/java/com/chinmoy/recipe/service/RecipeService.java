package com.chinmoy.recipe.service;

import com.chinmoy.recipe.model.BaseRecipeDTO;
import com.chinmoy.recipe.model.Diet;
import com.chinmoy.recipe.model.RecipeDTO;
import com.chinmoy.recipe.model.RecipeDTO;

import java.util.List;
import java.util.Map;

public interface RecipeService {
    List<RecipeDTO> findAllRecipes();

    RecipeDTO saveRecipe(BaseRecipeDTO recipe);

    RecipeDTO getById(String id);

    RecipeDTO getByRecipeName(String recipeName);

    Long getTotalCount();

    List<RecipeDTO> searchText(String keyword);

    List<RecipeDTO> getRecipesByDiet(Diet diet);

    List<RecipeDTO> searchRecipe(Map<String, String> customQuery);

    int deleteById(String id);

    RecipeDTO updateRecipe(RecipeDTO response);
}
