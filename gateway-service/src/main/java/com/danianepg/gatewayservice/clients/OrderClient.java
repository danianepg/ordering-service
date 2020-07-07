package com.danianepg.gatewayservice.clients;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.danianepg.gatewayservice.data.Order;

@FeignClient("ordering-service/api/orders")
public interface OrderClient {

	@GetMapping("/{id}")
	public EntityModel<Order> findById(@PathVariable final Long id);

	@PostMapping("")
	public EntityModel<Order> create(@RequestBody final Order order);

	@PutMapping("/{id}")
	public EntityModel<Order> update(@RequestBody final Order order, @PathVariable final Long id);

	@DeleteMapping("/{id}")
	public void delete(@PathVariable final Long id);

	@GetMapping("")
	public PagedModel<EntityModel<Order>> findAll(final Pageable page);

	@PostMapping("/updateOrderStatus/{id}")
	public Optional<Order> updateOrderStatus(@PathVariable final Long id, @RequestBody final String status);

}
