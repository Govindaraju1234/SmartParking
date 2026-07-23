package com.smartparking.service;

import com.smartparking.model.Booking;
import com.smartparking.model.ParkingHistory;
import com.smartparking.repository.BookingRepository;
import com.smartparking.repository.ParkingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Service
public class ParkingHistoryService {

    @Autowired
    private ParkingHistoryRepository parkingHistoryRepository;

    @Autowired
    private BookingRepository bookingRepository;

    // Save Parking History
    public ParkingHistory saveHistory(ParkingHistory history) {

        Booking booking = bookingRepository.findById(
                history.getBooking().getId())
                .orElseThrow(() ->
                        new RuntimeException("Booking Not Found"));

        history.setBooking(booking);

        long minutes = Duration.between(
                history.getEntryTime(),
                history.getExitTime()).toMinutes();

        BigDecimal hours = BigDecimal.valueOf(minutes)
                .divide(BigDecimal.valueOf(60), 2,
                        java.math.RoundingMode.HALF_UP);

        history.setTotalHours(hours);

        return parkingHistoryRepository.save(history);
    }

    // Get All History
    public List<ParkingHistory> getAllHistory() {
        return parkingHistoryRepository.findAll();
    }

    // Get History By Id
    public ParkingHistory getHistoryById(Integer id) {

        return parkingHistoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("History Not Found"));
    }

    // Delete History
    public void deleteHistory(Integer id) {

        ParkingHistory history = getHistoryById(id);

        parkingHistoryRepository.delete(history);
    }
    public List<ParkingHistory> getHistoryByUserId(Integer userId) {

    return parkingHistoryRepository.findByBookingUserId(userId);

}

}