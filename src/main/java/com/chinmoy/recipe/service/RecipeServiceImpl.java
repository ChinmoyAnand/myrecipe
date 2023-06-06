package com.chinmoy.recipe.service;

import com.chinmoy.recipe.model.BaseRecipeDTO;
import com.chinmoy.recipe.model.Diet;
import com.chinmoy.recipe.model.RecipeDTO;
import com.chinmoy.recipe.entity.Recipe;
import com.chinmoy.recipe.exception.NoDataFoundException;
import com.chinmoy.recipe.exception.NoSearchDataFoundException;
import com.chinmoy.recipe.exception.RecipeNotFoundException;
import com.chinmoy.recipe.repository.AtlasSearchRepository;
import com.chinmoy.recipe.repository.RecipeRepository;
import com.chinmoy.recipe.repository.SearchRepository;
import com.mongodb.DuplicateKeyException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository repository;
    private final SearchRepository searchRepository;
    private final AtlasSearchRepository atlasSearch;
    private final ModelMapper mapper;

    /**
     * Get All Recipes in DB.
     * @return RecipeDTO
     */
    @Override
    public List<RecipeDTO> findAllRecipes() {
        List<Recipe> recipes = repository.findAll();
        return entityToDto(recipes);
    }

    /**
     * Saving recipe by Id.
     * @param recipeDTO
     * @return RecipeDTO
     */
    @Override
    public RecipeDTO saveRecipe(BaseRecipeDTO recipeDTO) throws DuplicateKeyException {
        Recipe recipe = mapper.map(recipeDTO, Recipe.class);
        return mapper.map(repository.save(recipe), RecipeDTO.class);
    }

    /**
     * Find a Recipe By Id
     * @param id
     * @return RecipeDTO
     */
    @Override
    public RecipeDTO getById(String id) {
        Optional<Recipe> recipe = repository.findById(id);
        if (!recipe.isPresent()) {
            throw new RecipeNotFoundException(id);
        }
        return mapper.map(recipe, RecipeDTO.class);
    }

    /**
     * Find a Recipe By Name
     * @param recipeName
     * @return RecipeDTO
     */
    @Override
    public RecipeDTO getByRecipeName(String recipeName) {
        Optional<Recipe> recipe = repository.findItemByName(recipeName);
        if (!recipe.isPresent()) {
            throw new RecipeNotFoundException(recipeName);
        }
        return mapper.map(recipe, RecipeDTO.class);
    }

    /**
     * @return Total Count of recipes
     */
    @Override
    public Long getTotalCount() {
        return repository.count();
    }

    /**
     * Atlas Search based on keyword in Instruction Field
     * @param keyword
     * @return recipes
     */
    @Override
    public List<RecipeDTO> searchText(String keyword) {
        List<Recipe> recipes = atlasSearch.searchByKeyword(keyword);
        return entityToDto(recipes);
    }

    /**
     * Returns List of recipes by Diet i.e veg or nonVeg or vegan
     * @param diet
     * @return recipes
     */
    @Override
    public List<RecipeDTO> getRecipesByDiet(Diet diet) {
        List<Recipe> recipes = repository.findAllByDiet(diet.toString());
        if(recipes.isEmpty()){
            throw new NoDataFoundException();
        }
        return entityToDto(recipes);
    }

    /**
     * Returns List of recipes based on criteria
     *
     * @param customQuery
     * @return
     */
    @Override
    public List<RecipeDTO> searchRecipe(Map<String, String> customQuery) {
        List<Recipe> recipes = searchRepository.findRecipesByCriteria(customQuery);
        if(recipes.isEmpty()){
            throw new NoSearchDataFoundException();
        }
        return entityToDto(recipes);
    }

    /**
     * @param id
     * @return count of records deleted
     */
    @Override
    public int deleteById(String id) {
       return repository.deleteById(id);
    }

    /**
     * @param recipeDTO
     * @return
     */
    @Override
    public RecipeDTO updateRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = mapper.map(recipeDTO, Recipe.class);
        return mapper.map(repository.save(recipe), RecipeDTO.class);
    }

    private List<RecipeDTO> entityToDto(List<Recipe> recipes) {
        return recipes.stream()
                .map(recipe -> mapper.map(recipe, RecipeDTO.class))
                .collect(Collectors.toList());
    }
}
