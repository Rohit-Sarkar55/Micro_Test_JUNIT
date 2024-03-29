package com.project.service;

import java.util.List;

import com.project.entity.Payment;

public interface PaymentService {
	Payment add (Payment payment);
	
	Payment search(long paymentId);
	
	void remove (long paymentId);
	
	List<Payment> getAll();
	
	Payment updateStatus(long paymentId, String status);
}
