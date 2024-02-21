package com.project.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entity.Order;
import com.project.service.OrderServiceImpl;


@WebMvcTest(controllers = {OrderController.class})
class OrderControllerTest {
	
	
	@MockBean
	OrderServiceImpl orderService;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	void test_AddOrder() throws Exception {
		Order order = new Order();
		order.setCustomerId(1);
		order.setAmount(200);
		order.setProductId(1);
		order.setStatus("pending");
		
		when(orderService.add(any(Order.class))).thenReturn(order);
		
		mockMvc.perform( post("/orders/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(order)))
				.andExpect(status().isCreated());
		
	}

	@Test
	void test_SearchOrder() throws Exception{
		Order order = new Order();
		order.setCustomerId(1);
		order.setAmount(200);
		order.setProductId(1);
		order.setStatus("pending");
		order.setOrderId(1);
		
		when(orderService.searchById(any(Long.class))).thenReturn(order);
		
		mockMvc.perform( get("/orders/{orderId}" ,order.getOrderId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted());
	}
	
	
	@Test
	void test_getAllOrders() throws Exception {
		List<Order> orders =new ArrayList<>();
		orders.add (new Order(1, 1, 1, 200, LocalDate.now(), "pending")); 
		orders.add (new Order(2, 2, 1, 200, LocalDate.of(2023,10,12), "completed"));
		orders.add (new Order(3, 1, 3, 400, LocalDate.of(2024,02,10), "completed"));
		orders.add (new Order(4, 3, 5, 600, LocalDate.of(2024,01,25), "cancelled"));
		
		when(orderService.getAll()).thenReturn(orders);
		mockMvc.perform( get("/orders")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void test_deleteOrder() throws Exception {
		Order orderToAdd = new Order();
        orderToAdd.setCustomerId(1);
        orderToAdd.setAmount(200);
        orderToAdd.setProductId(1);
        orderToAdd.setStatus("pending");

        when(orderService.add(any(Order.class))).thenReturn(orderToAdd);

        mockMvc.perform(post("/orders/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderToAdd)))
                .andExpect(status().isCreated());

        // Search Order
        Order orderToSearch = new Order();
        orderToSearch.setCustomerId(1);
        orderToSearch.setAmount(200);
        orderToSearch.setProductId(1);
        orderToSearch.setStatus("pending");
        orderToSearch.setOrderId(1);

        when(orderService.searchById(any(Long.class))).thenReturn(orderToSearch);

        mockMvc.perform(get("/orders/{orderId}", orderToSearch.getOrderId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        // Delete Order
        Order orderToDelete = new Order(1, 1, 1, 1, LocalDate.now(), "pending");
        doNothing().when(orderService).removeById(orderToDelete.getOrderId());

        mockMvc.perform(delete("/orders/delete/{orderId}", orderToDelete.getOrderId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
	}
}
