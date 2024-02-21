package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Payment;
import com.project.repository.PaymentRepository;


@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	PaymentRepository repository;
	
	@Override
	public Payment add(Payment payment) {
		return repository.save(payment);
	}

	@Override
	public Payment search(long paymentId) {
		return repository.findById(paymentId).orElse(null);
	}

	@Override
	public void remove(long paymentId) {
		repository.deleteById(paymentId);
	}

	@Override
	public List<Payment> getAll() {
		return repository.findAll();
	}

	@Override
	public Payment updateStatus(long paymentId, String status) {
		
		repository.updatePaymentStatus(paymentId, status);
		return search(paymentId);
	}
	
}