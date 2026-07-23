package com.smartparking.repository;

import com.smartparking.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Integer> {

    List<ParkingSlot> findByStatus(String status);

    boolean existsBySlotNumber(String slotNumber);
    long count();
    long countByStatus(String status);
   

}

