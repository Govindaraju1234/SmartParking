package com.smartparking.controller;

import com.smartparking.model.Payment;
import com.smartparking.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Create Payment
    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }

    // Get All Payments
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    // Get Payment By ID
    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Integer id) {
        return paymentService.getPaymentById(id);
    }

    // Update Payment Status
    @PutMapping("/{id}/status")
    public Payment updatePaymentStatus(
            @PathVariable Integer id,
            @RequestParam String status) {

        return paymentService.updatePaymentStatus(id, status);
    }

    // Delete Payment
    @DeleteMapping("/{id}")
    public String deletePayment(@PathVariable Integer id) {

        paymentService.deletePayment(id);

        return "Payment deleted successfully.";
    }
}