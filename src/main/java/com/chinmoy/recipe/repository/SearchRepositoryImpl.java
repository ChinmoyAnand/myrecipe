package com.chinmoy.recipe.repository;

import com.chinmoy.recipe.entity.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepository{

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Recipe> findRecipesByCriteria(Map<String, String> customQuery) {
        Query query;
        if(!customQuery.containsKey("instruction")) {
            query = new Query();
        }else{
            TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(customQuery.get("instruction"));
            query = TextQuery.queryText(criteria);
        }

        if(customQuery.containsKey("serve")){
            query.addCriteria(Criteria.where("serve").is(Integer.parseInt(customQuery.get("serve"))));
        }
        if (customQuery.containsKey("diet")) {
            query.addCriteria(Criteria.where("diet").is(customQuery.get("diet")));
        }
        if(customQuery.containsKey("ingredient")){
            if(!customQuery.get("ingredient").startsWith("!")) {
                query.addCriteria(Criteria.where("ingredient").in(customQuery.get("ingredient")));
            }else {
                query.addCriteria(Criteria.where("ingredient").nin(customQuery.get("ingredient").substring(1)));
            }
        }
        List<Recipe> recipes = mongoTemplate.find(query,Recipe.class);
        return recipes;
    }

}
