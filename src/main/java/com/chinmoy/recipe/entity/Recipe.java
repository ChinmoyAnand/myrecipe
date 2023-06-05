package com.chinmoy.recipe.entity;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Recipe")
public class Recipe {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String cuisine;
    @TextIndexed
    private String instruction;
    private int cookTime;
    private int serve;
    private String course;
    private List<String> ingredient;
    private String author;
    private String diet;
    private String notes;
    @CreatedDate
    private Date createdOn;
    @LastModifiedDate
    private Date modifiedOn;
    @Version
    private Integer version;


}
