package com.smartparking.controller;

import com.smartparking.model.ParkingSlot;
import com.smartparking.service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slots")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService parkingSlotService;

    @PostMapping
    public ParkingSlot addSlot(@RequestBody ParkingSlot slot) {
        return parkingSlotService.addSlot(slot);
    }

    @GetMapping
    public List<ParkingSlot> getAllSlots() {
        return parkingSlotService.getAllSlots();
    }

    @GetMapping("/available")
    public List<ParkingSlot> getAvailableSlots() {
        return parkingSlotService.getAvailableSlots();
    }

    @PutMapping("/{id}")
    public ParkingSlot updateSlot(@PathVariable Integer id,
                                  @RequestBody ParkingSlot slot) {
        return parkingSlotService.updateSlot(id, slot);
    }

    @DeleteMapping("/{id}")
    public String deleteSlot(@PathVariable Integer id) {

        parkingSlotService.deleteSlot(id);

        return "Parking slot deleted successfully.";
    }
}