package com.example.tacocloud.utilities;

import com.example.tacocloud.models.Ingredient;
import com.example.tacocloud.models.Taco;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class TacoResource extends RepresentationModel<TacoResource> {

    private static IngredientModelAssembler ingredientModelAssembler= new IngredientModelAssembler();
    private CollectionModel<IngredientResource> ingredients;

    private Date createdAt;

    private String name;

    public TacoResource(Taco taco){
        this.name=taco.getName();
        this.createdAt=taco.getCreatedAt();
        this.ingredients=ingredientModelAssembler.toCollectionModel(taco.getIngredients());
    }

}
//this way is no longer needed as now we can just use EntityModel which was resource before
//just for learning
