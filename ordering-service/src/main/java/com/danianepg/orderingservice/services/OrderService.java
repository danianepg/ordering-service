package com.danianepg.orderingservice.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.danianepg.orderingservice.clients.ProductClient;
import com.danianepg.orderingservice.data.Product;
import com.danianepg.orderingservice.entities.Order;
import com.danianepg.orderingservice.enums.OrderStatus;
import com.danianepg.orderingservice.exceptions.InvalidDataException;
import com.danianepg.orderingservice.repository.OrderRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;

	private final ProductClient productClient;

	@Autowired
	public OrderService(final OrderRepository orderRepository, final ProductClient productClient) {
		this.orderRepository = orderRepository;
		this.productClient = productClient;
	}

	/**
	 * Save order
	 *
	 * @param order
	 * @return
	 */
	private Order save(final Order order) {

		final List<String> errors = this.validateOrder(order);
		if (!errors.isEmpty()) {
			throw new InvalidDataException(errors.stream().map(Object::toString).collect(Collectors.joining(",")));
		}

		return this.getRepository().save(order);
	}

	/**
	 * Create new order
	 *
	 * @param order
	 * @return
	 */
	public Order create(final Order order) {
		order.setStatus(OrderStatus.OPEN);
		order.setOrderDate(LocalDateTime.now());
		return this.save(order);
	}

	/**
	 * Update order
	 *
	 * @param order
	 * @param id
	 * @return
	 */
	public Order update(final Order order, final Long id) {

		return this.getRepository().findById(id).map(existingOrder -> {

			existingOrder.setProducts(order.getProducts());
			existingOrder.setCustomer(order.getCustomer());

			return this.save(existingOrder);

		}).orElseGet(() -> this.create(order));
	}

	/**
	 * Find all orders
	 *
	 * @param pageParam
	 * @return
	 */
	public Page<Order> findAll(final Pageable pageParam) {

		Pageable page = pageParam;
		if (page == null) {
			page = Pageable.unpaged();
		}

		return this.getRepository().findAll(page);
	}

	/**
	 * Delete order by id
	 *
	 * @param id
	 */
	public void deleteById(final Long id) {
		this.getRepository().deleteById(id);
	}

	/**
	 * Find order by id
	 *
	 * @param id
	 * @return
	 */
	public Optional<Order> findById(final Long id) {
		return this.getRepository().findById(id);
	}

	/**
	 * Update order status
	 *
	 * @param id
	 * @param status
	 * @return
	 */
	public Optional<Order> updateOrderStatus(final Long id, final OrderStatus status) {

		final Order order = this.findById(id).get();

		if (status == OrderStatus.IN_TRANSIT) {
			order.getProducts().stream().forEach(orderProduct -> {
				this.productClient.removeFromStock(orderProduct.getProductId(), orderProduct.getQuantity());
			});
		} else if (status == OrderStatus.CANCELED) {
			order.getProducts().stream().forEach(orderProduct -> {
				this.productClient.addToStock(orderProduct.getProductId(), orderProduct.getQuantity());
			});
		}

		order.setStatus(status);
		return Optional.of(this.save(order));

	}

	/**
	 * Check if order is valid
	 *
	 * @param order
	 * @return
	 */
	private List<String> validateOrder(final Order order) {

		final List<String> messageErrors = new ArrayList<>();
		messageErrors.addAll(this.checkStock(order));
		return messageErrors;

	}

	/**
	 * Check products stock
	 *
	 * @param order
	 * @return
	 */
	private List<String> checkStock(final Order order) {

		final List<String> messageErrors = new ArrayList<>();

		order.getProducts().stream().forEach(orderProduct -> {
			final Product product = Optional.of(this.productClient.findById(orderProduct.getProductId()).getContent())
					.orElseThrow(() -> new InvalidDataException("Ordered product does not exists on catalog!"));

			if (product.getStock() < orderProduct.getQuantity()) {
				messageErrors.add("Product " + product.getName() + " has insuficient stock!");
			}
		});

		return messageErrors;

	}

	private OrderRepository getRepository() {
		return this.orderRepository;
	}

}
