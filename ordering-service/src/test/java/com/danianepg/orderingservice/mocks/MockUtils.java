package com.danianepg.orderingservice.mocks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.danianepg.orderingservice.data.Product;
import com.danianepg.orderingservice.entities.Order;
import com.danianepg.orderingservice.entities.OrderProduct;
import com.danianepg.orderingservice.enums.OrderStatus;
import com.danianepg.orderingservice.enums.ProductTypes;

public class MockUtils {

	public static List<Product> getProducts() {

		final List<Product> products = new ArrayList<>();

		final Product product = new Product();
		product.setId(1L);
		product.setName("iPhone 11 MOCK");
		product.setStock(10);
		product.setDescription("iPhone 11 64gb");
		product.setType(ProductTypes.MOBILE_PHONE);
		products.add(product);

		final Product product2 = new Product();
		product2.setId(2L);
		product2.setName("iPhone 11 Pro MOCK");
		product2.setStock(5);
		product2.setType(ProductTypes.MOBILE_PHONE);
		product2.setDescription("iPhone 11 Pro 64gb");
		products.add(product2);

		final Product product3 = new Product();
		product3.setId(3L);
		product3.setName("SIM Card nano MOCK");
		product3.setStock(50);
		product3.setType(ProductTypes.SIM_CARD);
		product3.setDescription("SIM Card nano Swisscom");
		products.add(product3);

		final Product product4 = new Product();
		product4.setId(4L);
		product4.setName("Landline Phone MOCK");
		product4.setStock(5);
		product4.setType(ProductTypes.PHONE);
		product4.setDescription("Landline Phone");
		products.add(product4);

		final Product product5 = new Product();
		product5.setId(5L);
		product5.setName("Samsung 10s MOCK");
		product5.setStock(20);
		product5.setType(ProductTypes.MOBILE_PHONE);
		product5.setDescription("Samsung 10s 11 64gb");
		products.add(product5);

		final Product product6 = new Product();
		product6.setId(6L);
		product6.setName("Huawei Pro MOCK");
		product6.setStock(6);
		product6.setType(ProductTypes.MOBILE_PHONE);
		product6.setDescription("Huawei Pro 64gb 64gb");
		products.add(product6);

		return products;
	}

	public static Product getProduct(final int position) {
		return MockUtils.getProducts().get(position);
	}

	public static List<Order> getOrders() {

		final List<Order> orders = new ArrayList<>();

		orders.add(getOrderOpen());
		orders.add(getOrderInTransit());
		orders.add(getOrderDelivered());
		orders.add(getOrderCanceled());

		return orders;

	}

	public static Order newOrder() {
		final Set<OrderProduct> orderProducts = Set.of(new OrderProduct(1L, getProduct(0).getId(), 1),
				new OrderProduct(2L, getProduct(1).getId(), 2));

		final Order order = new Order();
		order.setCustomer("Daniane");
		order.setProducts(orderProducts);
		return order;
	}

	public static Order getOrderOpen() {
		final Set<OrderProduct> orderProducts = Set.of(new OrderProduct(1L, getProduct(0).getId(), 1),
				new OrderProduct(2L, getProduct(1).getId(), 2));

		final Order order = new Order();
		order.setId(1L);
		order.setCustomer("Daniane");
		order.setOrderDate(LocalDateTime.now());
		order.setProducts(orderProducts);
		order.setStatus(OrderStatus.OPEN);

		return order;
	}

	public static Order getOrderInTransit() {
		final Set<OrderProduct> orderProducts = Set.of(new OrderProduct(2L, getProduct(2).getId(), 1),
				new OrderProduct(3L, getProduct(3).getId(), 2));

		final Order order = new Order();
		order.setId(2L);
		order.setCustomer("Dobby");
		order.setOrderDate(LocalDateTime.now());
		order.setProducts(orderProducts);
		order.setStatus(OrderStatus.IN_TRANSIT);

		return order;
	}

	public static Order getOrderDelivered() {
		final Set<OrderProduct> orderProducts = Set.of(new OrderProduct(4L, getProduct(4).getId(), 1),
				new OrderProduct(5L, getProduct(5).getId(), 2));

		final Order order = new Order();
		order.setId(3L);
		order.setCustomer("Arya");
		order.setOrderDate(LocalDateTime.now());
		order.setProducts(orderProducts);
		order.setStatus(OrderStatus.DELIVERED);

		return order;
	}

	public static Order getOrderCanceled() {
		final Set<OrderProduct> orderProducts = Set.of(new OrderProduct(7L, getProduct(5).getId(), 1),
				new OrderProduct(8L, getProduct(4).getId(), 2));

		final Order order = new Order();
		order.setId(4L);
		order.setCustomer("Gimli");
		order.setOrderDate(LocalDateTime.now());
		order.setProducts(orderProducts);
		order.setStatus(OrderStatus.CANCELED);

		return order;
	}

}
