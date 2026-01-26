package com.example.tacocloud.utilities;
import com.example.tacocloud.controllers.DesignTacoApiController;
import com.example.tacocloud.controllers.IngredientController;
import com.example.tacocloud.models.Ingredient;
import com.example.tacocloud.repositories.IngredientRepository;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class IngredientModelAssembler extends RepresentationModelAssemblerSupport<Ingredient, IngredientResource> {

    public IngredientModelAssembler(){
        super(IngredientController.class, IngredientResource.class);
    }//needs the ingredient controller for it to work like taco in designTacoApiController


    @Override
    public IngredientResource toModel(Ingredient ingredient) {
        return createModelWithId(ingredient.getId(), ingredient);
    }

    @Override
    protected IngredientResource instantiateModel(Ingredient ingredient){
        return new IngredientResource(ingredient);
    }
}
