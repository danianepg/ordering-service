package com.danianepg.productservice;

import java.util.ArrayList;
import java.util.List;

import com.danianepg.productservice.entities.Product;
import com.danianepg.productservice.enums.ProductTypes;

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

}
