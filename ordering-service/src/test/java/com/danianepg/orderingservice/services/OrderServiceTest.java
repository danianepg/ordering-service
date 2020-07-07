package com.danianepg.orderingservice.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.junit4.SpringRunner;

import com.danianepg.orderingservice.clients.ProductClient;
import com.danianepg.orderingservice.data.Product;
import com.danianepg.orderingservice.entities.Order;
import com.danianepg.orderingservice.entities.OrderProduct;
import com.danianepg.orderingservice.enums.OrderStatus;
import com.danianepg.orderingservice.exceptions.InvalidDataException;
import com.danianepg.orderingservice.mocks.MockUtils;
import com.danianepg.orderingservice.repository.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

	private OrderService orderService;

	@MockBean
	private ProductClient productClient;

	@MockBean
	private OrderRepository orderRepository;

	@Before
	public void init() {
		this.orderService = new OrderService(this.orderRepository, this.productClient);
	}

	@Test
	public void create_ok() {

		Order order = MockUtils.newOrder();
		order.getProducts();

		when(this.orderRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(order));
		when(this.orderRepository.save(order)).thenAnswer(invocation -> invocation.getArgument(0));

		when(this.productClient.findById(ArgumentMatchers.any())).thenReturn(EntityModel.of(MockUtils.getProduct(0)));

		order = this.orderService.create(order);
		assertThat(order.getOrderDate()).isBefore(LocalDateTime.now());
		assertThat(order.getStatus()).isEqualTo(OrderStatus.OPEN);

	}

	@Test
	public void updateOrderStatus_moveStatusToInTransit_ok() {

		final Order order = MockUtils.getOrderOpen();

		when(this.orderRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(order));
		when(this.orderRepository.save(order)).thenAnswer(invocation -> invocation.getArgument(0));

		when(this.productClient.findById(ArgumentMatchers.any())).thenReturn(EntityModel.of(MockUtils.getProduct(0)));

		when(this.productClient.removeFromStock(ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(EntityModel.of(MockUtils.getProduct(0)));

		final Order orderSaved = this.orderService.updateOrderStatus(order.getId(), OrderStatus.IN_TRANSIT).get();

		assertThat(orderSaved.getStatus()).isEqualTo(OrderStatus.IN_TRANSIT);

	}

	@Test
	public void updateOrderStatus_moveStatusToCanceled_ok() {

		final Order order = MockUtils.getOrderOpen();
		when(this.orderRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(order));
		when(this.orderRepository.save(order)).thenAnswer(invocation -> invocation.getArgument(0));

		when(this.productClient.findById(ArgumentMatchers.any())).thenReturn(EntityModel.of(MockUtils.getProduct(0)));

		when(this.productClient.addToStock(ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(EntityModel.of(MockUtils.getProduct(0)));

		final Order orderSaved = this.orderService.updateOrderStatus(order.getId(), OrderStatus.CANCELED).get();
		assertThat(orderSaved.getStatus()).isEqualTo(OrderStatus.CANCELED);

	}

	@Test(expected = InvalidDataException.class)
	public void save_failWhenQuantityOrderedIsGreaterThanStock() {

		final Product product = MockUtils.getProduct(2);
		final Order order = MockUtils.getOrderOpen();
		final Set<OrderProduct> products = order.getProducts();

		products.stream().forEach(orderProduct -> {
			orderProduct.setQuantity(1 + product.getStock());
		});

		when(this.orderRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(order));
		when(this.orderRepository.save(order)).thenAnswer(invocation -> invocation.getArgument(0));
		when(this.productClient.findById(ArgumentMatchers.any())).thenReturn(EntityModel.of(product));

		this.orderService.update(order, order.getId());

	}

}
