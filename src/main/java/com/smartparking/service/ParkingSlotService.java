package com.smartparking.service;

import com.smartparking.model.ParkingSlot;
import com.smartparking.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSlotService {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    public ParkingSlot addSlot(ParkingSlot slot) {

        if (parkingSlotRepository.existsBySlotNumber(slot.getSlotNumber())) {
            throw new RuntimeException("Slot already exists");
        }

        return parkingSlotRepository.save(slot);
    }

    public List<ParkingSlot> getAllSlots() {
        return parkingSlotRepository.findAll();
    }

    public List<ParkingSlot> getAvailableSlots() {
        return parkingSlotRepository.findByStatus("Available");
    }

    public ParkingSlot updateSlot(Integer id, ParkingSlot updatedSlot) {

        ParkingSlot slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        slot.setSlotNumber(updatedSlot.getSlotNumber());
        slot.setVehicleType(updatedSlot.getVehicleType());
        slot.setStatus(updatedSlot.getStatus());

        return parkingSlotRepository.save(slot);
    }

    public ParkingSlot saveSlot(ParkingSlot slot) {
    return parkingSlotRepository.save(slot);
}
// Get Slot By ID
public ParkingSlot getSlotById(Integer id) {

    return parkingSlotRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("Parking Slot Not Found"));
}

// Update Slot
public ParkingSlot updateSlot(ParkingSlot slot) {

    ParkingSlot existingSlot = getSlotById(slot.getId());

    existingSlot.setSlotNumber(slot.getSlotNumber());
    existingSlot.setStatus(slot.getStatus());

    return parkingSlotRepository.save(existingSlot);
}

// Delete Slot
public void deleteSlot(Integer id) {

    ParkingSlot slot = getSlotById(id);

    parkingSlotRepository.delete(slot);
}

public long getBookedSlots() {
    return parkingSlotRepository.countByStatus("BOOKED");
}
}