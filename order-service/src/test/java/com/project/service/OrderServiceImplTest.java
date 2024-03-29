package com.project.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.project.entity.Order;
import com.project.repository.OrderRepository;


@SpringBootTest
@ComponentScan("com.project")
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
	
	@Mock
	OrderRepository orderRepository;
	
	@InjectMocks
	OrderServiceImpl orderService;

	@Test
	void testAdd() {
		Order order = new Order();
		
		when(orderRepository.save(any(Order.class))).thenReturn(order);
		
		Order savedOrder = orderService.add(order);
		
		assertNotNull(savedOrder.getOrderId());
		
		verify(orderRepository, times(1)).save(order);
	}

	@Test
	void testSearchById() {
		
		Optional<Order> order = Optional.ofNullable(new Order(102, 201,302, 899, LocalDate.of(2023, 12, 25), "completed"));
		
		long orderId = 102;
		when(orderRepository.findById(orderId)).thenReturn(order);
		
		assertTrue(orderService.searchById(orderId).getOrderId() == orderId);
	}

	@Test
	void testRemoveById() {
		Order order = new Order();
		
		when(orderRepository.save(any(Order.class))).thenReturn(order);
		
		Order savedOrder = orderService.add(order);
		
		assertNotNull(savedOrder.getOrderId());
		
		orderService.removeById(savedOrder.getOrderId());
		
		verify(orderRepository , times(1)).deleteById(savedOrder.getOrderId());
		
	}
	
	
	@Test
	void testGetAll() {
		List<Order> list = new ArrayList<>();
		list.add(new Order(101, 201,301, 99, LocalDate.now(), "pending"));
		list.add(new Order(102, 201,302, 899, LocalDate.of(2023, 12, 25), "completed"));
		list.add(new Order(103, 202, 303, 67, LocalDate.of(2024, 01, 31), "canceled"));
		
		
		when(orderRepository.findAll()).thenReturn(list);
		
		orderService.getAll();
		
		assertTrue(list.size() == 3);
	}

}
