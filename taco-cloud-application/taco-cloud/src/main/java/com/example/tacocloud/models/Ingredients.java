package com.example.tacocloud.models;

import lombok.Data;

@Data
public class Ingredients {

    private final String id; //final variable cant be reassigned once it's been initialized
    private final String name;
    private final Type type;

    public static enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
