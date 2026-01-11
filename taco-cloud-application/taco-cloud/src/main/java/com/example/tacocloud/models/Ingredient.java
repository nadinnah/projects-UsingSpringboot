package com.example.tacocloud.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data //tells Lombok to generate all of missing methods as well as a constructor that accepts all final properties as arguments.
@RequiredArgsConstructor
@NoArgsConstructor(access= AccessLevel.PRIVATE, force = true)
@Entity
public class Ingredient {

    @Id
    private final String id; //final variable cant be reassigned once it's been initialized
    private final String name;
    private final Type type;

    public static enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
