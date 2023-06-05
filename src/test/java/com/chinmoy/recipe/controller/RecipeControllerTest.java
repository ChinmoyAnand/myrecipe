package com.chinmoy.recipe.controller;

import com.chinmoy.recipe.model.Course;
import com.chinmoy.recipe.model.Diet;
import com.chinmoy.recipe.model.BaseRecipeDTO;
import com.chinmoy.recipe.model.RecipeDTO;
import com.chinmoy.recipe.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecipeService recipeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRecipe() throws Exception {
        BaseRecipeDTO recipe = new BaseRecipeDTO();
        recipe.setName("Paneer");
        recipe.setCourse(Course.MAIN);
        recipe.setDiet(Diet.VEG);
        recipe.setIngredient(Arrays.asList("Paneer","salt","oil"));

        RecipeDTO response = new RecipeDTO();
        response.setName("Paneer");
        response.setCourse(Course.MAIN);
        response.setDiet(Diet.VEG);
        response.setIngredient(Arrays.asList("Paneer","salt","oil"));

        // Mocking the service
        when(recipeService.saveRecipe(any(BaseRecipeDTO.class))).thenReturn(response);

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.course").value("MAIN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Paneer"));
        // Add assertions for other properties if needed
    }

    @Test
    public void testGetAllRecipes() throws Exception {
        // Prepare expected response
        RecipeDTO recipe1 = new RecipeDTO();
        recipe1.setName("Paneer Tikka");
        recipe1.setCourse(Course.MAIN);
        recipe1.setDiet(Diet.VEG);

        RecipeDTO recipe2 = new RecipeDTO();
        recipe2.setName("Chicken Tikka");
        recipe2.setCourse(Course.MAIN);
        recipe2.setDiet(Diet.NONVEG);

        List<RecipeDTO> response = Arrays.asList(recipe1, recipe2);

        // Mock the service method
        when(recipeService.findAllRecipes()).thenReturn(response);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipe"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Paneer Tikka"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].course").value("MAIN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].course").value("MAIN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Chicken Tikka"));
    }

}
