package com.smartparking.repository;

import com.smartparking.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> findByBookingId(Integer bookingId);
    long count();
    List<Payment> findByBookingUserId(Integer userId);
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentStatus='SUCCESS'")
Double getTotalRevenue();

}