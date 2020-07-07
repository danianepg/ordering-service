package com.danianepg.gatewayservice.clients;

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

import com.danianepg.gatewayservice.data.Product;

@FeignClient("product-service/api/products")
public interface ProductClient {

	@GetMapping("/{id}")
	public EntityModel<Product> findById(@PathVariable final Long id);

	@PostMapping("")
	public EntityModel<Product> create(@RequestBody final Product product);

	@PutMapping("/{id}")
	public EntityModel<Product> update(@RequestBody final Product product, @PathVariable final Long id);

	@DeleteMapping("/{id}")
	public void delete(@PathVariable final Long id);

	@GetMapping("")
	public PagedModel<EntityModel<Product>> findAll(final Pageable page);

	@PostMapping("/addToStock/{productId}")
	public EntityModel<Product> addToStock(@PathVariable final Long productId,
			@RequestBody final Integer quantityToAdd);

	@PostMapping("/removeFromStock/{productId}")
	public EntityModel<Product> removeFromStock(@PathVariable final Long productId,
			@RequestBody final Integer quantity);

}
