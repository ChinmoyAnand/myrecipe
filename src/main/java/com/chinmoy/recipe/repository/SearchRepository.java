package com.chinmoy.recipe.repository;

import com.chinmoy.recipe.entity.Recipe;

import java.util.List;
import java.util.Map;

public interface SearchRepository {

    List<Recipe> findRecipesByCriteria(Map<String, String> query);

}
