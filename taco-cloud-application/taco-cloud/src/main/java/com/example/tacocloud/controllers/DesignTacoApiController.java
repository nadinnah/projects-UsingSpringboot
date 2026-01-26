package com.example.tacocloud.controllers;

import com.example.tacocloud.DTOs.TacoRequestDto;
import com.example.tacocloud.configurationPropertyHolders.OrderProps;
import com.example.tacocloud.models.Order;
import com.example.tacocloud.models.Taco;
import com.example.tacocloud.repositories.OrderRepository;
import com.example.tacocloud.repositories.TacoRepository;
import com.example.tacocloud.services.TacoService;
import jakarta.annotation.Resources;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path="/api/design", produces = "application/json")
public class DesignTacoApiController{

    TacoService tacoService;
    TacoRepository tacoRepo;
    OrderProps orderProps;
    OrderRepository orderRepo;
    DesignTacoApiController(TacoRepository tacoRepo, OrderProps orderProps, TacoService tacoService, OrderRepository orderRepo){
        this.tacoRepo= tacoRepo;
        this.orderProps=orderProps;
        this.tacoService=tacoService;
        this.orderRepo=orderRepo;
    }

    //HATEOAS example
    //HATEOAS isn’t about fixing bugs
    //it’s about teaching APIs to describe themselves.
    @GetMapping("/recent")
    public CollectionModel<EntityModel<Taco>> recentTacos(){
        PageRequest page= PageRequest.of(0,orderProps.getPageSize(), Sort.by("createdAt").descending());

        List<Taco> tacos= tacoRepo.findAll(page).getContent();
        List<EntityModel<Taco>> tacoResources =
                tacos.stream().map(taco -> EntityModel.of(taco, linkTo(methodOn(DesignTacoApiController.class).findById(taco.getId())).withSelfRel())).toList();


        return CollectionModel.of(tacoResources, linkTo(methodOn(DesignTacoApiController.class).recentTacos()).withRel("recents"));
    }

    @GetMapping("/{id}")
    public EntityModel<Taco> findById(@PathVariable Long id){
        Taco taco= tacoRepo.findById(id).orElseThrow(()->new NoSuchElementException());
            return EntityModel.of(
                    taco,
                    linkTo(methodOn(DesignTacoApiController.class).findById(id)).withSelfRel(),
                    linkTo(methodOn(DesignTacoApiController.class).recentTacos()).withRel("recents")
            );

//        if(taco.isPresent()){
//            return new ResponseEntity<>(taco.get(), HttpStatus.OK);
//        }
       //return new ResponseEntity<>((HttpHeaders) null, HttpStatus.NOT_FOUND);

    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody TacoRequestDto tacoRequestDto) {
        return tacoService.createTaco(tacoRequestDto);
    }


    //Thymeleaf does NOT cross the wire because it runs on the server and produces HTML, not JSON
    //The browser never sees your Java objects or entities — it only sees already-rendered HTML
    //Anything that becomes JSON and goes to/from Angular/Postman is crossing the wire

    //An Entity:
    //Is a database object
    //Annotated with @Entity
    //Designed for JPA/Hibernate, not APIs

    //entities should NOT cross the wire
    //If you send this directly to the client:

    //You expose database structure

    //You risk:
    //Lazy-loading errors
    //Infinite JSON loops
    //Breaking the frontend if DB changes

    //You tightly couple:
    //Frontend ↔ Database
    //That’s bad architecture.

    //DTO = Data Transfer Object
    //Think of it as:
    //“A clean package of data meant ONLY for communication”

    //A DTO:
    //Has no JPA annotations
    //Has only the fields you want to expose
    //Matches JSON shape, not database shape
    //DTOs exist only to cross the wire safely.

    //Controller:
    //Talks HTTP
    //Talks JSON
    //Talks DTOs

    //Repository:
    //Talks database
    //Talks Entities

    //Service: (middle layer)
    //Converts DTO ⇄ Entity
}
