package com.smartparking.service;
import com.smartparking.model.ParkingHistory;
import com.smartparking.model.Booking;
import com.smartparking.model.Payment;
import com.smartparking.repository.BookingRepository;
import com.smartparking.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class PaymentService {
    @Autowired
private ParkingHistoryService parkingHistoryService;
    @Autowired
private QRCodeService qrCodeService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    // Create Payment
    public Payment createPayment(Payment payment) {

        Booking booking = bookingRepository.findById(
                payment.getBooking().getId())
                .orElseThrow(() ->
                        new RuntimeException("Booking Not Found"));

        if (paymentRepository.findByBookingId(booking.getId()).isPresent()) {
            throw new RuntimeException("Payment already exists for this booking");
        }

        payment.setBooking(booking);
        payment.setPaymentTime(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    // Get All Payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Get Payment By ID
    public Payment getPaymentById(Integer id) {

        return paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment Not Found"));
    }

    // Update Payment Status
    public Payment updatePaymentStatus(Integer id, String status) {

        Payment payment = getPaymentById(id);

        payment.setPaymentStatus(status);

        return paymentRepository.save(payment);
    }

    // Delete Payment
    public void deletePayment(Integer id) {

        Payment payment = getPaymentById(id);

        paymentRepository.delete(payment);
    }
    public List<Payment> getPaymentsByUserId(Integer userId) {

    return paymentRepository.findByBookingUserId(userId);

}
public Double getTotalRevenue() {

    Double revenue = paymentRepository.getTotalRevenue();

    return revenue == null ? 0.0 : revenue;

}
public Payment makePayment(Integer bookingId,
                           Double amount,
                           String paymentMethod) {

    Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() ->
                    new RuntimeException("Booking Not Found"));

    if (paymentRepository.findByBookingId(bookingId).isPresent()) {
        throw new RuntimeException("Payment already completed.");
    }

    Payment payment = new Payment();

    payment.setBooking(booking);
    payment.setAmount(BigDecimal.valueOf(amount));
    payment.setPaymentMethod(paymentMethod);
    payment.setPaymentStatus("SUCCESS");
    payment.setPaymentTime(LocalDateTime.now());

    Payment savedPayment = paymentRepository.save(payment);

    // Update booking status
    booking.setStatus("PAID");

    // Generate QR Code
    String qrData =
            "Booking ID : " + booking.getId() +
            "\nSlot : " + booking.getParkingSlot().getSlotNumber() +
            "\nVehicle : " + booking.getVehicleNumber();

    try {
        String qrPath = qrCodeService.generateQRCode(
                qrData,
                "booking_" + booking.getId());

        booking.setQrCodePath(qrPath);

    } catch (Exception e) {
        e.printStackTrace();
    }

    bookingRepository.save(booking);
    ParkingHistory history = new ParkingHistory();

history.setBooking(booking);

// Entry time = booking start time
history.setEntryTime(booking.getStartTime());

// Payment time becomes the exit time
history.setExitTime(LocalDateTime.now());

// Temporary value (saveHistory() recalculates it)
history.setTotalHours(BigDecimal.ZERO);

parkingHistoryService.saveHistory(history);

    return savedPayment;
}
}