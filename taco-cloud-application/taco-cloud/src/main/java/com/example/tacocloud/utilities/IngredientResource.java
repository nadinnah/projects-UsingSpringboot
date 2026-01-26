package com.example.tacocloud.utilities;

import com.example.tacocloud.models.Ingredient;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class IngredientResource extends RepresentationModel<IngredientResource> {

    private String name;
    private Ingredient.Type type;

    public IngredientResource(Ingredient ingredient){
        this.type= ingredient.getType();
        this.name= ingredient.getName();
    }
}
