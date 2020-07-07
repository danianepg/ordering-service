package com.danianepg.productservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.danianepg.productservice.entities.Product;
import com.danianepg.productservice.enums.StockOperation;
import com.danianepg.productservice.exceptions.InvalidDataException;
import com.danianepg.productservice.exceptions.NotFoundException;
import com.danianepg.productservice.repositories.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	@Autowired
	public ProductService(final ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	/**
	 * Save product
	 *
	 * @param product
	 * @return
	 */
	public Product save(final Product product) {
		return this.getRepository().save(product);
	}

	/**
	 * Update product
	 *
	 * @param product
	 * @param id
	 * @return
	 */
	public Product update(final Product product, final Long id) {

		return this.getRepository().findById(id).map(existingProduct -> {
			existingProduct.setName(product.getName());
			existingProduct.setType(product.getType());
			existingProduct.setDescription(product.getDescription());
			return this.save(existingProduct);

		}).orElseGet(() -> this.save(product));
	}

	/**
	 * Find all products
	 *
	 * @param pageParam
	 * @return
	 */
	public Page<Product> findAll(final Pageable pageParam) {

		Pageable page = pageParam;
		if (page == null) {
			page = Pageable.unpaged();
		}

		return this.getRepository().findAll(page);
	}

	/**
	 * Delete product by id
	 *
	 * @param id
	 */
	public void deleteById(final Long id) {
		this.getRepository().deleteById(id);
	}

	/**
	 * Find product by id
	 *
	 * @param id
	 * @return
	 */
	public Optional<Product> findById(final Long id) {
		return this.getRepository().findById(id);
	}

	/**
	 * Add or remove items from stock
	 *
	 * @param productId
	 * @param quantity
	 * @param stockOperation
	 * @return
	 */
	private Optional<Product> moveStock(final Long productId, final Integer quantity,
			final StockOperation stockOperation) {

		final Product existingProduct = this.getRepository().findById(productId).orElseThrow(NotFoundException::new);
		Integer newStock = existingProduct.getStock();

		if (newStock == null) {
			newStock = 0;
		}

		if (stockOperation == StockOperation.ADD) {
			newStock += quantity;
		} else {

			if (newStock - quantity < 0) {
				throw new InvalidDataException("Insuficient stock!");
			}

			newStock -= quantity;
		}

		existingProduct.setStock(newStock);
		return Optional.of(this.save(existingProduct));

	}

	/**
	 * Add to stock
	 *
	 * @param productId
	 * @param quantityToAdd
	 * @return
	 */
	public Optional<Product> addToStock(final Long productId, final Integer quantityToAdd) {
		return this.moveStock(productId, quantityToAdd, StockOperation.ADD);
	}

	/**
	 * Remove from stock
	 *
	 * @param productId
	 * @param quantityToRemove
	 * @return
	 */
	public Optional<Product> removeFromStock(final Long productId, final Integer quantityToRemove) {
		return this.moveStock(productId, quantityToRemove, StockOperation.REMOVE);
	}

	private ProductRepository getRepository() {
		return this.productRepository;
	}

}
