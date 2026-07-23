package com.smartparking.controller;

import com.smartparking.model.Booking;
import com.smartparking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Create Booking
    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    // Get All Bookings
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // Get Booking By ID
    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Integer id) {
        return bookingService.getBookingById(id);
    }

    // Delete Booking
    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Integer id) {

        bookingService.deleteBooking(id);

        return "Booking cancelled successfully.";
    }

}