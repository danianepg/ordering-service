package com.danianepg.orderingservice.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.danianepg.orderingservice.enums.OrderStatus;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime orderDate;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<OrderProduct> products;

	private String customer;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public LocalDateTime getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(final LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public Set<OrderProduct> getProducts() {
		if (Objects.isNull(this.products)) {
			this.products = new HashSet<>();
		}
		return this.products;
	}

	public void setProducts(final Set<OrderProduct> products) {
		this.products = products;
	}

	public String getCustomer() {
		return this.customer;
	}

	public void setCustomer(final String customer) {
		this.customer = customer;
	}

	public OrderStatus getStatus() {
		return this.status;
	}

	public void setStatus(final OrderStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.customer == null) ? 0 : this.customer.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.orderDate == null) ? 0 : this.orderDate.hashCode());
		result = prime * result + ((this.products == null) ? 0 : this.products.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Order other = (Order) obj;
		if (this.customer == null) {
			if (other.customer != null) {
				return false;
			}
		} else if (!this.customer.equals(other.customer)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.orderDate == null) {
			if (other.orderDate != null) {
				return false;
			}
		} else if (!this.orderDate.equals(other.orderDate)) {
			return false;
		}
		if (this.products == null) {
			if (other.products != null) {
				return false;
			}
		} else if (!this.products.equals(other.products)) {
			return false;
		}
		return true;
	}

}
