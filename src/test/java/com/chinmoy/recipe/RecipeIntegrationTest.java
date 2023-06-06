package com.chinmoy.recipe;

import com.chinmoy.recipe.model.BaseRecipeDTO;
import com.chinmoy.recipe.model.Diet;
import com.chinmoy.recipe.model.RecipeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testPostApi() throws Exception {
        BaseRecipeDTO entity = new BaseRecipeDTO();
        entity.setName("Pizza");
        entity.setInstruction("Take flour and make dough and put in oven");
        entity.setDiet(Diet.VEG);
        entity.setCookTime(20);
        entity.setIngredient(Arrays.asList("flour","salt","mushroom"));
        ResponseEntity responseEntity = restTemplate.postForEntity(
                "/api/v1/recipe", entity, RecipeDTO.class);
        RecipeDTO recipe = (RecipeDTO) responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(recipe.getId());
        assertEquals("Pizza",recipe.getName());

    }

    @Test
    public void testGetApi() throws Exception {
        ResponseEntity response = restTemplate.getForEntity(
                "/api/v1/recipe", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }

    @Test
    public void findRecipeIdNotFound404() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/recipe/445", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}

