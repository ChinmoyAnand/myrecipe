package com.chinmoy.recipe.controller;

import com.chinmoy.recipe.model.BaseRecipeDTO;
import com.chinmoy.recipe.model.Diet;
import com.chinmoy.recipe.model.RecipeDTO;
import com.chinmoy.recipe.exception.NoDataFoundException;
import com.chinmoy.recipe.exception.NoDataFoundToDelete;
import com.chinmoy.recipe.exception.NoSearchDataFoundException;
import com.chinmoy.recipe.exception.RecipeNotFoundException;
import com.chinmoy.recipe.service.RecipeService;
import com.mongodb.DuplicateKeyException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/recipe")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Backend API for MY-Recipe app", description = "This is a recipe backend api which performs all CRUD operations and search based on filter criteria")
public class RecipeController {
    private final RecipeService service;

    @Operation(summary = "Status API for Controller")
    @GetMapping("/status")
    public String status() {
        return "Welcome to My Recipe Application";
    }

    @Operation(summary = "API to Save a new Recipe")
    @ApiResponse(responseCode = "201", description = "Create Recipe")
    @PostMapping
    public ResponseEntity<RecipeDTO> addRecipe(@RequestBody @Valid BaseRecipeDTO recipe) throws DuplicateKeyException{
        RecipeDTO response = service.saveRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "API to get All Recipes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all the Recipes present in table"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")})
    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        List<RecipeDTO> recipes = service.findAllRecipes();
        if (recipes.isEmpty()) {
            throw new NoDataFoundException();
        }
        return ResponseEntity.ok(recipes);
    }

    @Operation(summary = "API to get Recipe by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Recipe"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")})
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable("id") final String id) {
        RecipeDTO response = service.getById(id);
        if (response == null) {
            throw new RecipeNotFoundException(id);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "API to Update a Recipe by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the Recipe"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")})
    @PutMapping("/{id}")
    public ResponseEntity <RecipeDTO> updateRecipe(@PathVariable(value = "id") String id,
                                                      @RequestBody @Valid BaseRecipeDTO recipe) {
        RecipeDTO response = service.getById(id);
        if (response == null) {
            throw new RecipeNotFoundException(id);
        }
        response.setId(id);
        response.setName(recipe.getName());
        response.setCourse(recipe.getCourse());
        response.setInstruction(recipe.getInstruction());
        response.setDiet(recipe.getDiet());
        response.setCookTime(recipe.getCookTime());
        response.setIngredient(recipe.getIngredient());
        response.setCuisine(recipe.getCuisine());
        response.setVersion(response.getVersion());
        final RecipeDTO updatedRecipe = service.updateRecipe(response);
        return ResponseEntity.ok(updatedRecipe);
    }

    @Operation(summary = "API to get Recipe by Recipe Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Recipe"),
            @ApiResponse(responseCode = "400", description = "Invalid name supplied"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")})
    @GetMapping("/search/name/{name}")
    public ResponseEntity<RecipeDTO> getRecipeByName(@PathVariable("name") final String name) {
        RecipeDTO response = service.getByRecipeName(name);
        if (response == null) {
            throw new RecipeNotFoundException(name);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "API to search recipe by Atlas search on instruction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Recipe based on search"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")})
    @GetMapping("search/{instruction}")
    public ResponseEntity<List<RecipeDTO>> findRecipeBySearchText(@PathVariable("instruction") final String keyword) {
        log.info("Searching instruction based on keyword {}", keyword);
        List<RecipeDTO> recipes = service.searchText(keyword);
        if (recipes.isEmpty()) {
            throw new NoSearchDataFoundException();
        }
        return ResponseEntity.ok(recipes);
    }

    @Operation(summary = "API to filter based on different criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Recipe based on filter"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")})
    @GetMapping("search")
    public ResponseEntity<List<RecipeDTO>> findRecipeBySearchCriteria(@RequestParam Map<String, String> customQuery) {
        log.info("customQuery = serve {}", customQuery.containsKey("serve"));
        log.info("customQuery = ingredient {}", customQuery.containsKey("ingredient"));
        log.info("customQuery = instruction {}", customQuery.containsKey("instruction"));
        log.info("customQuery = diet {}", customQuery.containsKey("diet"));
        List<RecipeDTO> recipes = service.searchRecipe(customQuery);
        if (recipes.isEmpty()) {
            throw new NoSearchDataFoundException();
        }
        return ResponseEntity.ok(recipes);
    }

    @Operation(summary = "API to find Recipes by VEG/NON-VEG diet types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Recipe based on filter"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")})
    @GetMapping("search/diet/{diet}")
    public ResponseEntity<List<RecipeDTO>> getRecipeByDiet(@PathVariable("diet") final Diet diet) {
        List<RecipeDTO> recipes = service.getRecipesByDiet(diet);
        if (recipes.isEmpty()) {
            throw new NoSearchDataFoundException();
        }
        return ResponseEntity.ok(recipes);
    }

    @Operation(summary = "API to find total count of Recipes")
    @ApiResponse(responseCode = "200", description = "Found the Total count of Recipes")
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalCount() {
        return ResponseEntity.ok(service.getTotalCount());
    }

    @Operation(summary = "API to delete recipe based on Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted recipe"),
            @ApiResponse(responseCode = "404", description = "No Recipe found to delete")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") final String id) {
        int deleted = service.deleteById(id);
        if (deleted == 0) {
            throw new NoDataFoundToDelete(id);
        }
        return ResponseEntity.ok(deleted);
    }
}
