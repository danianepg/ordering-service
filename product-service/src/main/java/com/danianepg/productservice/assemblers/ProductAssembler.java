package com.danianepg.productservice.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import com.danianepg.productservice.controllers.ProductController;
import com.danianepg.productservice.entities.Product;

/**
 * Assembler to convert entities to RESTful/HATEOAS format. Hyperlinks are added
 * to the response and make the api navigable.
 *
 * @author Daniane P. Gomes
 *
 */
@Component
public class ProductAssembler {

	/**
	 * Convert one product object to HATEOAS RESTful/HATEOAS format.
	 *
	 * @param product
	 * @return
	 */
	public EntityModel<Product> toEntityModel(final Product product) {

		final Link link = linkTo(ProductController.class).slash(product.getId()).withSelfRel();

		final EntityModel<Product> entityModel = EntityModel.of(product, link);
		entityModel.add(linkTo(methodOn(ProductController.class).findAll(null)).withRel("products"));
		entityModel.add(linkTo(methodOn(ProductController.class).addToStock(null, null)).withRel("addToStock"));

		return entityModel;
	}

	/**
	 * Convert a list of products to RESTful/HATEOAS format.
	 *
	 * @param productLst
	 * @param page
	 * @return
	 */
	public PagedModel<EntityModel<Product>> toCollectionModel(final Page<Product> productLst, final Pageable page) {

		final PagedModel.PageMetadata pageMetaData = new PagedModel.PageMetadata(page.getPageSize(),
				productLst.getNumber(), productLst.getTotalElements());

		final List<Product> content = productLst.getContent();
		final List<EntityModel<Product>> entityModels = content.stream().map(this::toEntityModel)
				.collect(Collectors.toList());

		final PagedModel<EntityModel<Product>> products = PagedModel.of(entityModels, pageMetaData);
		products.add(linkTo(methodOn(ProductController.class).findAll(page)).withSelfRel());
		products.add(linkTo(methodOn(ProductController.class).findById(null)).withRel("findById"));

		return products;
	}

}
