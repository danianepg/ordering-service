package com.danianepg.gatewayservice.data;

import java.io.Serializable;

public class OrderProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long productId;

	private Integer quantity;

	public OrderProduct() {
		super();
	}

	public OrderProduct(final Long id, final Long productId, final Integer quantity) {
		super();
		this.id = id;
		this.productId = productId;
		this.quantity = quantity;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(final Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final Integer quantity) {
		this.quantity = quantity;
	}

}
