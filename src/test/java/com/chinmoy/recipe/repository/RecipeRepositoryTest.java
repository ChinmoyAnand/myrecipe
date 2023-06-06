package com.chinmoy.recipe.repository;

import com.chinmoy.recipe.entity.Recipe;
import com.chinmoy.recipe.exception.NoDataFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DataMongoTest
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @AfterEach
    void cleanUpDatabase() {
        mongoTemplate.getDb().drop();
    }

    @Test
    public void testSaveRecipe() throws Exception {
        Recipe entity = createRecipe();
        repository.save(entity);
        assertEquals(1,repository.count());
    }

    @Test
    public void testFindRecipe() throws Exception {
        Recipe entity = createRecipe();
        repository.save(entity);
        assertEquals(entity, repository.findItemByName("Pizza").get());
    }

    @Test
    public void testFindRecipeByDiet() throws Exception {
        Recipe entity = createRecipe();
        repository.save(entity);
        assertEquals(entity, repository.findAllByDiet("VEG").get(0));
    }

    private Recipe createRecipe() {
        Recipe entity = new Recipe();
        entity.setName("Pizza");
        entity.setInstruction("Take flour and make dough and put in oven");
        entity.setDiet("VEG");
        entity.setCookTime(20);
        entity.setIngredient(Arrays.asList("flour","salt","mushroom"));
        return entity;
    }
}
