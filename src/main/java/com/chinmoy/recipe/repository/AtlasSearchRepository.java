package com.chinmoy.recipe.repository;

import com.chinmoy.recipe.entity.Recipe;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AtlasSearchRepository {
    private final MongoClient client;
    private final MongoConverter converter;

    public List<Recipe> searchByKeyword(String keyword) {
        final List<Recipe> recipes = new ArrayList<>();
        MongoDatabase database = client.getDatabase("recipedb");
        MongoCollection<Document> collection =  database.getCollection("Recipe");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                new Document("text", new Document("query",keyword).append("path",Arrays.asList("instruction"))))
        ));
        result.forEach(doc -> recipes.add(converter.read(Recipe.class,doc)));
        return recipes;
    }
}
