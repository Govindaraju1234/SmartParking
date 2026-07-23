package com.smartparking.service;

import com.smartparking.model.Booking;
import com.smartparking.model.ParkingSlot;
import com.smartparking.repository.BookingRepository;
import com.smartparking.repository.ParkingSlotRepository;
import com.smartparking.repository.UserRepository;
import com.smartparking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
private QRCodeService qrCodeService;
@Autowired
private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    // Create Booking
    public Booking createBooking(Booking booking) {

        ParkingSlot slot = parkingSlotRepository
                .findById(booking.getParkingSlot().getId())
                .orElseThrow(() -> new RuntimeException("Parking Slot Not Found"));

        if (!slot.getStatus().equalsIgnoreCase("Available")) {
            throw new RuntimeException("Parking Slot Already Occupied");
        }

        slot.setStatus("Occupied");
        parkingSlotRepository.save(slot);

        booking.setStatus("Booked");

        return bookingRepository.save(booking);
    }

    // Get All Bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get Booking By Id
    public Booking getBookingById(Integer id) {

        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking Not Found"));
    }

    // Delete Booking
    public void deleteBooking(Integer id) {

        Booking booking = getBookingById(id);

        ParkingSlot slot = booking.getParkingSlot();

        slot.setStatus("Available");

        parkingSlotRepository.save(slot);

        bookingRepository.deleteById(id);
    }
    public List<Booking> getBookingsByUser(Integer userId) {

    return bookingRepository.findByUserId(userId);

}
public Booking bookParkingSlot(Integer userId,
                               Integer slotId,
                               String vehicleNumber) {

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    ParkingSlot slot = parkingSlotRepository.findById(slotId)
            .orElseThrow(() -> new RuntimeException("Parking Slot not found"));

    if (!"AVAILABLE".equalsIgnoreCase(slot.getStatus())) {
        throw new RuntimeException("Parking Slot is already occupied.");
    }

    Booking booking = new Booking();

    booking.setUser(user);
    booking.setParkingSlot(slot);
    booking.setVehicleNumber(vehicleNumber);
    booking.setBookingDate(java.time.LocalDate.now());
    booking.setStartTime(java.time.LocalDateTime.now());

    // User has booked but not paid yet
    booking.setStatus("BOOKED");

    slot.setStatus("OCCUPIED");
    parkingSlotRepository.save(slot);

    return bookingRepository.save(booking);
}

public List<Booking> getBookingsByUserId(Integer userId) {

    return bookingRepository.findByUserId(userId);

}
public void cancelBooking(Integer bookingId) {

    Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

    booking.setStatus("CANCELLED");

    ParkingSlot slot = booking.getParkingSlot();
    slot.setStatus("AVAILABLE");

    parkingSlotRepository.save(slot);
    bookingRepository.save(booking);
}
public List<Booking> searchBookings(String vehicleNumber) {

    return bookingRepository
            .findByVehicleNumberContainingIgnoreCase(vehicleNumber);

}
}