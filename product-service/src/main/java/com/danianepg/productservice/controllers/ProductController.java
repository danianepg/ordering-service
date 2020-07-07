package com.danianepg.productservice.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danianepg.productservice.assemblers.ProductAssembler;
import com.danianepg.productservice.entities.Product;
import com.danianepg.productservice.exceptions.HateosMapperException;
import com.danianepg.productservice.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService service;

	private final ProductAssembler assembler;

	@Autowired
	public ProductController(final ProductService service, final ProductAssembler assembler) {
		this.service = service;
		this.assembler = assembler;
	}

	@GetMapping("/{id}")
	public EntityModel<Product> findById(@PathVariable final Long id) {
		return this.getService().findById(id).map(this.assembler::toEntityModel)
				.orElseThrow(HateosMapperException::new);
	}

	/**
	 * Create a product
	 *
	 * @param product
	 * @return
	 */
	@PostMapping("")
	public EntityModel<Product> create(@RequestBody final Product product) {
		return Optional.of(this.getService().save(product)).map(this.assembler::toEntityModel)
				.orElseThrow(HateosMapperException::new);
	}

	/**
	 * Update a product or create a new one if it does not exists, in compliance to
	 * PUT idempotency characteristics.
	 *
	 * @param product
	 * @param id
	 * @return
	 */
	@PutMapping("/{id}")
	public EntityModel<Product> update(@RequestBody final Product product, @PathVariable final Long id) {
		return Optional.of(this.getService().update(product, id)).map(this.assembler::toEntityModel)
				.orElseThrow(HateosMapperException::new);
	}

	/**
	 * Delete product by id
	 *
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable final Long id) {
		this.getService().deleteById(id);
	}

	/**
	 * Find all products paginated.
	 *
	 * @param page
	 * @return
	 */
	@GetMapping("")
	public PagedModel<EntityModel<Product>> findAll(final Pageable page) {
		return Optional.of(this.getService().findAll(page)).map(p -> this.assembler.toCollectionModel(p, page))
				.orElseThrow(HateosMapperException::new);
	}

	/**
	 * Increase the stock of a product
	 *
	 * @param productId
	 * @param quantityToAdd
	 * @return
	 */
	@PostMapping("/addToStock/{productId}")
	public EntityModel<Product> addToStock(@PathVariable final Long productId,
			@RequestBody final Integer quantityToAdd) {

		return this.getService().addToStock(productId, quantityToAdd).map(this.assembler::toEntityModel)
				.orElseThrow(HateosMapperException::new);
	}

	@PostMapping("/removeFromStock/{productId}")
	public EntityModel<Product> removeFromStock(@PathVariable final Long productId,
			@RequestBody final Integer quantity) {

		return this.getService().removeFromStock(productId, quantity).map(this.assembler::toEntityModel)
				.orElseThrow(HateosMapperException::new);
	}

	private ProductService getService() {
		return this.service;
	}

}
