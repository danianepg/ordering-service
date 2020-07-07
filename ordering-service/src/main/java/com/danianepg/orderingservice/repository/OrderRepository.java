package com.danianepg.orderingservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.danianepg.orderingservice.entities.Order;

@RepositoryRestResource(exported = false)
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

}
