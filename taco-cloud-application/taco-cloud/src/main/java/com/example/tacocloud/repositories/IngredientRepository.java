package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;


public interface IngredientRepository extends JpaRepository<Ingredient,String> {
//Spring Data repositories can automatically be exposed as REST APIs using Spring Data REST
}
