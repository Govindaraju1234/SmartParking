package com.smartparking.repository;
import com.smartparking.model.User;
import com.smartparking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByStatus(String status);
    long count();
    List<Booking> findByUserId(Integer userId);
    List<Booking> findByVehicleNumberContainingIgnoreCase(String vehicleNumber);

}