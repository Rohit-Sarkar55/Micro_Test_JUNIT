package com.project.service;

import java.util.List;

import com.project.entity.Order;

public interface OrderService {
	Order add (Order order);
	
	Order searchById (long orderId);
	
	void removeById(long orderId);
	
	List<Order> getAll();
}
