package com.example.tacocloud.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date createdAt;

    @NotNull
    @Size(min=5, message = "name must be at least 5 characters long")
    private String name;

    @ManyToMany(targetEntity=Ingredient.class)
    @Size(min=1, message = "must choose at least 1 ingredient")
    private List<String> ingredients;

    @PrePersist
    //use this to set the createdAt property to the current date and
    //time before Taco is persisted
    void createdAt(){
        this.createdAt=new Date();
    }
}
