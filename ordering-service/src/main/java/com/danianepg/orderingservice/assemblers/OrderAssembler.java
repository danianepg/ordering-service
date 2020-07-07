package com.danianepg.orderingservice.assemblers;

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

import com.danianepg.orderingservice.controllers.OrderController;
import com.danianepg.orderingservice.entities.Order;

@Component
public class OrderAssembler {

	/**
	 * Convert one order object to HATEOAS RESTful/HATEOAS format.
	 *
	 * @param order
	 * @return
	 */
	public EntityModel<Order> toEntityModel(final Order order) {

		final Link link = linkTo(OrderController.class).slash(order.getId()).withSelfRel();

		final EntityModel<Order> entityModel = EntityModel.of(order, link);
		final OrderController methodOn = methodOn(OrderController.class);
		entityModel.add(linkTo(methodOn.findAll(null)).withRel("orders"));

		return entityModel;
	}

	/**
	 * Convert a list of orders to RESTful/HATEOAS format.
	 *
	 * @param orderLst
	 * @param page
	 * @return
	 */
	public PagedModel<EntityModel<Order>> toCollectionModel(final Page<Order> orderLst, final Pageable page) {

		final PagedModel.PageMetadata pageMetaData = new PagedModel.PageMetadata(page.getPageSize(),
				orderLst.getNumber(), orderLst.getTotalElements());

		final List<Order> content = orderLst.getContent();
		final List<EntityModel<Order>> entityModels = content.stream().map(this::toEntityModel)
				.collect(Collectors.toList());

		final PagedModel<EntityModel<Order>> orders = PagedModel.of(entityModels, pageMetaData);
		orders.add(linkTo(methodOn(OrderController.class).findAll(page)).withSelfRel());
		orders.add(linkTo(methodOn(OrderController.class).findById(null)).withRel("findById"));

		return orders;
	}
}
