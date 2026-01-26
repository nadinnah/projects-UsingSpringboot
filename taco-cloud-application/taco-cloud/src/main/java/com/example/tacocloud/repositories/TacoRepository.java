package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Taco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface TacoRepository extends PagingAndSortingRepository<Taco,Long>, CrudRepository<Taco,Long> {

}
