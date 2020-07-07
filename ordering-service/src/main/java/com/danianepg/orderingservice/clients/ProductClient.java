package com.danianepg.orderingservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.danianepg.orderingservice.data.Product;

@FeignClient("product-service/api/products")
public interface ProductClient {

	@GetMapping("/{id}")
	public EntityModel<Product> findById(@PathVariable final Long id);

	@PostMapping("/addToStock/{productId}")
	public EntityModel<Product> addToStock(@PathVariable final Long productId,
			@RequestBody final Integer quantityToAdd);

	@PostMapping("/removeFromStock/{productId}")
	public EntityModel<Product> removeFromStock(@PathVariable final Long productId,
			@RequestBody final Integer quantity);

}
