package com.chinmoy.recipe.repository;

import com.chinmoy.recipe.entity.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe,Integer> {
    Optional<Recipe> findById(String id);
    Optional<Recipe> findItemByName(String name);
    List<Recipe> findAllByDiet(String diet);
    int deleteById(String id);
}
