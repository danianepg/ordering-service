package com.danianepg.productservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.danianepg.productservice.entities.Product;
import com.danianepg.productservice.exceptions.InvalidDataException;
import com.danianepg.productservice.exceptions.NotFoundException;
import com.danianepg.productservice.repositories.ProductRepository;
import com.danianepg.productservice.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

	private ProductService productService;

	@MockBean
	private ProductRepository productRepository;

	@Before
	public void init() {
		this.productService = new ProductService(this.productRepository);
	}

	@Test
	public void addToStock_ok() {

		final Product product = MockUtils.getProduct(0);
		final Integer previousQuantity = product.getStock();
		final int quantityToAdd = 5;

		when(this.productRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(product));
		when(this.productRepository.save(product)).thenAnswer(invocation -> invocation.getArgument(0));

		final Product productSaved = this.productService.addToStock(product.getId(), quantityToAdd).get();
		assertThat(productSaved.getStock()).isEqualTo(previousQuantity + quantityToAdd);

	}

	@Test(expected = NotFoundException.class)
	public void addToStock_failWhenProductNotFound() {

		when(this.productRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
		when(this.productRepository.save(ArgumentMatchers.any())).thenAnswer(invocation -> invocation.getArgument(0));

		this.productService.addToStock(10L, 10);
	}

	@Test
	public void removeFromStock_ok() {

		final Product product = MockUtils.getProduct(0);
		final Integer previousQuantity = product.getStock();
		final int quantityToRemove = 5;

		when(this.productRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(product));
		when(this.productRepository.save(product)).thenAnswer(invocation -> invocation.getArgument(0));

		final Product productUpdated = this.productService.removeFromStock(product.getId(), quantityToRemove).get();

		assertThat(productUpdated.getStock()).isEqualTo(previousQuantity - quantityToRemove);
	}

	@Test(expected = InvalidDataException.class)
	public void removeFromStock_failWhenQuantityIsLessThanZero() {

		final Product product = MockUtils.getProduct(0);
		final int quantityToRemove = 11;

		when(this.productRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(product));
		when(this.productRepository.save(product)).thenAnswer(invocation -> invocation.getArgument(0));

		this.productService.removeFromStock(product.getId(), quantityToRemove).get();

	}

	@Test
	public void update_ok() {

		final Product existingProduct = MockUtils.getProduct(0);
		final Product productSaved = MockUtils.getProduct(1);
		productSaved.setId(1L);

		when(this.productRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(existingProduct));
		when(this.productRepository.save(existingProduct)).thenAnswer(invocation -> invocation.getArgument(0));

		final Product result = this.productService.update(productSaved, existingProduct.getId());
		assertThat(result.getId()).isEqualTo(productSaved.getId());
		assertThat(result.getName()).isEqualTo(productSaved.getName());
		assertThat(result.getDescription()).isEqualTo(productSaved.getDescription());

	}

	@Test
	public void update_createNewWhenIdIsNull_ok() {

		final Product product = MockUtils.getProduct(0);

		when(this.productRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
		when(this.productRepository.save(product)).thenAnswer(invocation -> invocation.getArgument(0));

		assertThat(product.getId()).isNotNull();

	}

}
