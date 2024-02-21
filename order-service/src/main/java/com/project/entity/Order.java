package com.project.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@TableGenerator(name = "ord_seq_gen", table = "ord_seq_tab", 
	pkColumnName = "ord_pk",valueColumnName = "ord_val", 
	pkColumnValue = "ord",initialValue = 10001, allocationSize = 1) 
	
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ord_seq_gen")
	
	private long orderId;
	
	private long customerId;
	
	private long productId;

	private double amount;
	
	private LocalDate orderDate;
	
	private String status;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = (orderDate == null ? LocalDate.now() : orderDate);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Order() {
		
	}
	public Order(long orderId, long customerId, long productId, double amount, LocalDate orderDate, String status) {
		
		this.orderId = orderId;
		this.customerId = customerId;
		this.productId = productId;
		this.amount = amount;
		this.orderDate = orderDate;
		this.status = status;
	}
	
	
}
