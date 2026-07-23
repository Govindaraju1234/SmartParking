package com.smartparking.repository;
import java.util.List;
import com.smartparking.model.ParkingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingHistoryRepository extends JpaRepository<ParkingHistory, Integer> {
    long count();
    List<ParkingHistory> findByBookingUserId(Integer userId);

}