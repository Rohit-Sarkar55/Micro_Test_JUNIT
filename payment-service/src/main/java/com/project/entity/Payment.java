package com.project.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;

@Entity
@Table(name = "payments")
public class Payment {
	
	
	@Id
	@TableGenerator(name = "pay_seq_gen", table = "pay_seq_tab", 
	pkColumnName = "pay_pk",valueColumnName = "pay_val", 
	pkColumnValue = "pay",initialValue = 40000, allocationSize = 1) 
	
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "pay_seq_gen")
	
	private long paymentId;
	
	private long orderId;
	
	private double paymentAmount;
	
	private LocalDate paymentDate;
	
	private String paymentStatus;
	
	private String paymentMethod;

	public long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = (paymentDate != null ? paymentDate : LocalDate.now());
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus == null ? "pending" : paymentStatus;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod == null ? "UPI" : paymentMethod;
	}
}
