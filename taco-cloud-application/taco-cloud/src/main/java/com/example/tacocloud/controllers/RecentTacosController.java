package com.example.tacocloud.controllers;

import com.example.tacocloud.configurationPropertyHolders.OrderProps;
import com.example.tacocloud.models.Taco;
import com.example.tacocloud.repositories.TacoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RepositoryRestController
public class RecentTacosController {

    private TacoRepository tacoRepo;
    private OrderProps orderProps;
    public RecentTacosController(TacoRepository tacoRepo, OrderProps orderProps) {
        this.tacoRepo = tacoRepo;
        this.orderProps = orderProps;
    }

    @GetMapping(path="/tacos/recent", produces="application/hal+json")
    public ResponseEntity<CollectionModel<EntityModel<Taco>>> recentTacos(){
        PageRequest page= PageRequest.of(0,orderProps.getPageSize(), Sort.by("createdAt").descending());

        List<Taco> tacos= tacoRepo.findAll(page).getContent();
        List<EntityModel<Taco>> tacoResources =
                tacos.stream().map(taco -> EntityModel.of(taco, linkTo(methodOn(DesignTacoApiController.class).findById(taco.getId())).withSelfRel())).toList();


        return new ResponseEntity<>(CollectionModel.of(tacoResources, linkTo(methodOn(DesignTacoApiController.class).recentTacos()).withRel("recents")), HttpStatus.OK);
    }

    @Bean
    public RepresentationModelProcessor<PagedModel<EntityModel<Taco>>>
    tacoProcessor(EntityLinks links) {
        return new RepresentationModelProcessor<PagedModel<EntityModel<Taco>>>() {
            @Override
            public PagedModel<EntityModel<Taco>> process(
                    PagedModel<EntityModel<Taco>> resource) {
                resource.add(
                        links.linkFor(Taco.class)
                                .slash("recent")
                                .withRel("recents"));
                return resource;
            }
        };
    }
}
