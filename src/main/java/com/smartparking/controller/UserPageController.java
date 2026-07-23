package com.smartparking.controller;
import com.smartparking.model.User;
import com.smartparking.model.Booking;
import com.smartparking.model.ParkingSlot;
import com.smartparking.service.BookingService;
import com.smartparking.service.ParkingHistoryService;
import com.smartparking.service.ParkingSlotService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import com.smartparking.service.PaymentService;
import com.smartparking.service.QRCodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserPageController {
    @Autowired
private QRCodeService qrCodeService;
    @Autowired
private ParkingHistoryService parkingHistoryService;
    @Autowired
private PaymentService paymentService;
    @Autowired
private ParkingSlotService parkingSlotService;

@Autowired
private BookingService bookingService;

    
@GetMapping("/user/book-slot")
public String bookSlot(HttpSession session, Model model) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    model.addAttribute("slots",
            parkingSlotService.getAvailableSlots());

    return "book-slot";
}
    @GetMapping("/user/bookings")
    public String myBookings(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("bookings", bookingService.getBookingsByUser(user.getId()));

        return "user-bookings";
    }

    @GetMapping("/user/payments")
public String myPayments(HttpSession session, Model model) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    model.addAttribute("payments",
    paymentService.getPaymentsByUserId(user.getId()));

    return "user-payments";
}

    @GetMapping("/user/history")
public String myHistory(HttpSession session, Model model) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    model.addAttribute("historyList",
            parkingHistoryService.getHistoryByUserId(user.getId()));

    return "user-history";
}
@PostMapping("/user/book-slot")
public String saveBooking(@RequestParam Integer slotId,
                          @RequestParam String vehicleNumber,
                          HttpSession session) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    Booking booking = bookingService.bookParkingSlot(
            user.getId(),
            slotId,
            vehicleNumber
    );

    return "redirect:/user/payment/" + booking.getId();
}
@GetMapping("/user/bookings/cancel/{id}")
public String cancelBooking(@PathVariable Integer id,
                            HttpSession session) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    bookingService.cancelBooking(id);

    return "redirect:/user/bookings";
}
@GetMapping("/test-qr")
public String testQRCode() throws Exception {

    qrCodeService.generateQRCode(
            "Booking ID:1\nSlot:A101\nVehicle:AP39AB1234",
            "booking1"
    );

    return "redirect:/dashboard";
}
@GetMapping("/user/booking/{id}/qr")
public String viewQRCode(@PathVariable Integer id,
                         HttpSession session,
                         Model model) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    Booking booking = bookingService.getBookingById(id);

    if (!booking.getUser().getId().equals(user.getId())) {
        return "redirect:/user/bookings";
    }

    model.addAttribute("booking", booking);

    return "booking-qr";
}
@GetMapping("/user/payment/{bookingId}")
public String paymentPage(@PathVariable Integer bookingId,
                          HttpSession session,
                          Model model) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    Booking booking = bookingService.getBookingById(bookingId);

    model.addAttribute("booking", booking);

    return "payment";
}
@PostMapping("/user/payment/save")
public String savePayment(@RequestParam Integer bookingId,
                          @RequestParam String paymentMethod,
                          @RequestParam Double amount) {

    paymentService.makePayment(
            bookingId,
            amount,
            paymentMethod
    );

    return "redirect:/user/bookings";
}
}