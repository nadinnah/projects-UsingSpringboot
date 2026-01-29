package com.example.tacocloud.services;

import com.example.tacocloud.models.Ingredient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IngredientService {

    //This is how Spring talks HTTP as a client

    private RestTemplate rest;
    //Your app needs data from a different service.
    //Example:
    //Your app: Taco Cloud
    //Other app: Inventory Service
    //Other app: Payment Service
    //Other app: External API (Google, Stripe, weather, etc.)

    //You cannot call:
    //inventoryService.getIngredient()
    //it’s in another process
    //maybe another server
    //maybe another language
    //maybe owned by another company
    //The only bridge is HTTP. RestTemplate is that bridge.

    IngredientService(RestTemplate rest){
        this.rest= rest;
    }

    public Ingredient getIngredientById(String ingredientId) {
        return rest.getForObject("http://localhost:8080/ingredients/{id}",
                Ingredient.class, ingredientId);
    }

    //Real-world analogy
    //Repository call:
    //“Hey database, give me row #5.”

    //Method call:
    //“Hey class, give me an object.”

    //RestTemplate call:
    //“Hey another program on the internet, send me some JSON.”


}
