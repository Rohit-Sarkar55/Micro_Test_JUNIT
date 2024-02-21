package com.project.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.project.entity.Payment;
import com.project.repository.PaymentRepository;

@SpringBootTest
@ComponentScan("com.project")
@ExtendWith(MockitoExtension.class)

class PaymentServiceImplTest {

	@Mock
	PaymentRepository paymentRepository;

	@InjectMocks
	PaymentServiceImpl paymentService;

	@Test
	void testAdd() {
		Payment payment = new Payment();

		when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

		Payment savedPayment = paymentService.add(payment);

		assertNotNull(savedPayment.getPaymentId());

		verify(paymentRepository, times(1)).save(payment);

	}

	@Test
	void testSearch() {
		Payment payment = new Payment();

		when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

		Payment savedPayment = paymentService.add(payment);

		assertNotNull(savedPayment.getPaymentId());
		
		when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));
		
		Payment searchedPayment = paymentService.search(savedPayment.getPaymentId());
		
		verify(paymentRepository , times(1)).findById(savedPayment.getPaymentId());
		
		assertEquals(searchedPayment, savedPayment);
		

	}

	@Test
	void testRemove() {
		Payment payment = new Payment();
		
		doNothing().when(paymentRepository).deleteById(anyLong());
		
		paymentService.remove(payment.getPaymentId());
		
		verify(paymentRepository , times(1)).deleteById(payment.getOrderId());
	}
//
	@Test
	void testGetAll() {
		Payment p1 = new Payment(); p1.setPaymentId(1);		
		Payment p2 = new Payment(); p2.setPaymentId(2);
		Payment p3 = new Payment(); p3.setPaymentId(3);
		
		List<Payment> list = new ArrayList<>();
		list.addAll(Arrays.asList(p1,p2,p3));
		
		when(paymentRepository.findAll()).thenReturn(list);
		
		paymentService.getAll();
		
		verify(paymentRepository, times(1)).findAll();
		assertTrue(list.size() == 3);
	}

	@Test
	void testUpdateStatus() {
		Payment p1 = new Payment();
		p1.setPaymentStatus("pending");

		when(paymentRepository.save(any(Payment.class))).thenReturn(p1);

		Payment savedPayment = paymentService.add(p1);

		when(paymentRepository.updatePaymentStatus ( anyLong(), anyString())).thenReturn(1);
		
		Payment updatedPayment = paymentService.updateStatus(savedPayment.getPaymentId(), "completed");
		
		verify(paymentRepository, times(1)).updatePaymentStatus(savedPayment.getPaymentId(), "completed");
		
	}

}
