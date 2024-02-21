package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.entity.Payment;

import jakarta.transaction.Transactional;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	@Modifying
	@Transactional
	@Query("UPDATE Payment p SET p.paymentStatus = :newStatus WHERE p.paymentId = :paymentId")
    int updatePaymentStatus(@Param("paymentId") Long paymentId, @Param("newStatus") String newStatus);
	
	

}
