package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Order;
import com.project.repository.OrderRepository;


@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderRepository repository;
	
	@Override
	public Order add(Order order) {
		return repository.save(order);
	}

	@Override
	public Order searchById(long orderId) {
		return repository.findById(orderId).orElse(null);
	}


	@Override
	public void removeById(long orderId) {
		repository.deleteById(orderId);
	}

	@Override
	public List<Order> getAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
