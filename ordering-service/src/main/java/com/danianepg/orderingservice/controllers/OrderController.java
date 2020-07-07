package com.danianepg.orderingservice.controllers;

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

import com.danianepg.orderingservice.assemblers.OrderAssembler;
import com.danianepg.orderingservice.entities.Order;
import com.danianepg.orderingservice.enums.OrderStatus;
import com.danianepg.orderingservice.exceptions.HateosMapperException;
import com.danianepg.orderingservice.services.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService service;

	private final OrderAssembler assembler;

	@Autowired
	public OrderController(final OrderService service, final OrderAssembler assembler) {
		this.service = service;
		this.assembler = assembler;
	}

	@GetMapping("/{id}")
	public EntityModel<Order> findById(@PathVariable final Long id) {
		return this.getService().findById(id).map(this.assembler::toEntityModel)
				.orElseThrow(HateosMapperException::new);
	}

	/**
	 * Create a order
	 *
	 * @param order
	 * @return
	 */
	@PostMapping("")
	public EntityModel<Order> create(@RequestBody final Order order) {
		return Optional.of(this.getService().create(order)).map(this.assembler::toEntityModel)
				.orElseThrow(HateosMapperException::new);
	}

	/**
	 * Update a order or create a new one if it does not exists, in compliance to
	 * PUT idempotency characteristics.
	 *
	 * @param order
	 * @param id
	 * @return
	 */
	@PutMapping("/{id}")
	public EntityModel<Order> update(@RequestBody final Order order, @PathVariable final Long id) {
		return Optional.of(this.getService().update(order, id)).map(this.assembler::toEntityModel)
				.orElseThrow(HateosMapperException::new);
	}

	/**
	 * Delete order by id
	 *
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable final Long id) {
		this.getService().deleteById(id);
	}

	/**
	 * Find all orders paginated.
	 *
	 * @param page
	 * @return
	 */
	@GetMapping("")
	public PagedModel<EntityModel<Order>> findAll(final Pageable page) {
		return Optional.of(this.getService().findAll(page)).map(p -> this.assembler.toCollectionModel(p, page))
				.orElseThrow(HateosMapperException::new);
	}

	@PostMapping("/updateOrderStatus/{id}")
	public Optional<Order> updateOrderStatus(@PathVariable final Long id, @RequestBody final String status) {
		return this.getService().updateOrderStatus(id, OrderStatus.valueOf(status.trim()));
	}

	private OrderService getService() {
		return this.service;
	}

}
