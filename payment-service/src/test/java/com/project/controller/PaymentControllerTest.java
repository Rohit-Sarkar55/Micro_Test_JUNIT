package com.project.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entity.Payment;
import com.project.service.PaymentServiceImpl;

@WebMvcTest(controllers = { PaymentController.class })
class PaymentControllerTest {

	@MockBean
	PaymentServiceImpl paymentService;

	@Autowired
	MockMvc mockMvc;

	@Test
	void test_AddPayment() throws Exception {
		Payment p = new Payment();
		p.setPaymentId(1);
		p.setOrderId(1);
		p.setPaymentAmount(100);
		p.setPaymentMethod("cash");
		p.setPaymentStatus("completed");

		when(paymentService.add(any(Payment.class))).thenReturn(p);

		mockMvc.perform(post("/payments/createpayment").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(p))).andExpect(status().isCreated());

	}


	@Test
	void test_SearchPayment() throws Exception {
		Payment p = new Payment();
		p.setPaymentId(1);
		p.setOrderId(1);
		p.setPaymentAmount(100);
		p.setPaymentMethod("cash");
		p.setPaymentStatus("completed");

		when(paymentService.search(any(Long.class))).thenReturn(p);

		mockMvc.perform(get("/payments/{paymentId}", p.getPaymentId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound());
	}

	@Test
	void testRemovePayment() throws Exception {
		Payment p = new Payment();
		p.setPaymentId(1);
		p.setOrderId(1);
		p.setPaymentAmount(100);
		p.setPaymentMethod("cash");
		p.setPaymentStatus("completed");

		when(paymentService.add(any(Payment.class))).thenReturn(p);

		mockMvc.perform(post("/payments/createpayment").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(p))).andExpect(status().isCreated());

		when(paymentService.search(any(Long.class))).thenReturn(p);

		mockMvc.perform(get("/payments/{paymentId}", p.getPaymentId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound());
		
		doNothing().when(paymentService).remove(p.getPaymentId());
		mockMvc.perform(delete("/payments/delete/{paymentId}", p.getPaymentId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());

	}

	@Test
	void test_GetAllPayments() throws Exception {
		Payment p = new Payment();
		p.setPaymentId(1);
		p.setOrderId(1);
		p.setPaymentAmount(100);
		p.setPaymentMethod("cash");
		p.setPaymentStatus("completed");

		Payment p1 = new Payment();
		p1.setPaymentId(2);
		p1.setOrderId(2);
		p1.setPaymentAmount(200);
		p1.setPaymentMethod("upi");
		p1.setPaymentStatus("pending");

		Payment p2 = new Payment();
		p2.setPaymentId(3);
		p2.setOrderId(3);
		p2.setPaymentAmount(300);
		p2.setPaymentMethod("upi");
		p2.setPaymentStatus("cancelled");

		List<Payment> list = Arrays.asList(p, p1, p2);

		when(paymentService.getAll()).thenReturn(list);

		mockMvc.perform(get("/payments").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void test_UpdatePayment() throws Exception{
		Payment p = new Payment();
		p.setPaymentId(1);
		p.setOrderId(1);
		p.setPaymentAmount(100);
		p.setPaymentMethod("cash");
		p.setPaymentStatus("completed");
		
		
		
		
		
		when(paymentService.updateStatus(anyLong(), anyString())).thenReturn(p);
		
		String newStatus = "cancelled";
        mockMvc.perform(put("/payments/updatestatus/{paymentId}/{status}", p.getPaymentId() , newStatus)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
		
	}

}
