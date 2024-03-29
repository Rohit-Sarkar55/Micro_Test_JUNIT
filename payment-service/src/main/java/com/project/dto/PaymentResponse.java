package com.project.dto;

import com.project.entity.Payment;

public class PaymentResponse {
	private String message;
	private Payment payment;
	private Order order;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public PaymentResponse(String message, Payment payment, Order order) {
		super();
		this.message = message;
		this.payment = payment;
		this.order = order;
	}
	public PaymentResponse() {
	}
	
	
	
}
