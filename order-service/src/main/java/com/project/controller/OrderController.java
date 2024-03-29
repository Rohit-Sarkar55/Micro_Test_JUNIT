package com.project.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.project.dto.OrderResponse;
import com.project.dto.Payment;
import com.project.entity.Order;
import com.project.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	RestTemplate restTemplate;
	/*
	@PostMapping("create")
	public Order addOrder(@RequestBody Order order) {
		order.setOrderDate(order.getOrderDate());
		
		HttpHeaders httpHeaders = new HttpHeaders() ;
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Order> reqEntity = new HttpEntity<>(order,httpHeaders);
		
		Order newOrder = orderService.add(order);
		String paymentServiceURL = "http://localhost:8082/payments/createpayment";
		
		ResponseEntity<String> response = restTemplate.postForEntity(paymentServiceURL, reqEntity , String.class);
		
		System.out.println(response);
		
		return newOrder;
	}
	*/
	
	@PostMapping("create")
	public ResponseEntity<OrderResponse> addOrder(@RequestBody Order order) {
		order.setOrderDate(order.getOrderDate());
		
		HttpHeaders httpHeaders = new HttpHeaders() ;
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Order> reqEntity = new HttpEntity<>(order,httpHeaders);
		
		Order newOrder = orderService.add(order);
		String paymentServiceURL = "http://localhost:8082/payments/createpayment";
		
		ResponseEntity<String> paymentResponse = restTemplate.postForEntity(paymentServiceURL, reqEntity , String.class);
		System.out.println(paymentResponse);
		
		OrderResponse orderResp = new OrderResponse("New Order has succesfully created" , newOrder);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(orderResp);
	}
	
	/*
	@GetMapping("{orderId}")
	public Order searchOrder(@PathVariable long orderId) {
		return orderService.searchById(orderId);
	}*/
	
	@GetMapping("{orderId}")
	public ResponseEntity<OrderResponse> searchOrder(@PathVariable long orderId) {
		Order order = orderService.searchById(orderId);
		
		OrderResponse response = new OrderResponse();
		if(order != null) {
			response.setMessage("Order Found");
			response.setOrder(order);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
		}
		else {
			response.setMessage("Order with that Id does not exist");
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}
	
	
	@DeleteMapping("delete/{orderId}")
	public ResponseEntity<String> removeOrder(@PathVariable long orderId) {
		
		ResponseEntity< OrderResponse> searchResponse = searchOrder(orderId);
		if(searchResponse.getBody().getOrder() == null) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(searchResponse.getBody().getMessage());
		}
		else {
			orderService.removeById(orderId);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Order with id: " + orderId + " has been deleted");
		}
	}
	
	
	
	@GetMapping("")
	public ResponseEntity<List<Order>> getAllOrders(){
		List<Order> list =  orderService.getAll();
		if(! list.isEmpty()) {
			return ResponseEntity.ok(list);
		}else {
			return ResponseEntity.noContent().build();
		}
	} 
	
	
	
	@PostMapping("updateorderstatus")
	public ResponseEntity<Order> updateOrder(@RequestBody Payment payment){
		Order order = orderService.searchById(payment.getOrderId());
		if(order != null) {
			order.setStatus(payment.getPaymentStatus());
		
			orderService.add(order);
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(order);
	}
}
