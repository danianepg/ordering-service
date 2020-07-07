package com.danianepg.productservice.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.danianepg.productservice.entities.Product;

@RepositoryRestResource(exported = false)
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

}
