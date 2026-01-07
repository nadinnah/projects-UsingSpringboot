package com.example.tacocloud.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data //tells Lombok to generate all of missing methods as well as a constructor that accepts all final properties as arguments.
@RequiredArgsConstructor
public class Ingredient {

    private final String id; //final variable cant be reassigned once it's been initialized
    private final String name;
    private final Type type;

    public static enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
