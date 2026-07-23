package com.smartparking.controller;

import com.smartparking.model.ParkingHistory;
import com.smartparking.service.ParkingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class ParkingHistoryController {

    @Autowired
    private ParkingHistoryService parkingHistoryService;

    // Save Parking History
    @PostMapping
    public ParkingHistory saveHistory(@RequestBody ParkingHistory history) {
        return parkingHistoryService.saveHistory(history);
    }

    // Get All Parking History
    @GetMapping
    public List<ParkingHistory> getAllHistory() {
        return parkingHistoryService.getAllHistory();
    }

    // Get Parking History By ID
    @GetMapping("/{id}")
    public ParkingHistory getHistoryById(@PathVariable Integer id) {
        return parkingHistoryService.getHistoryById(id);
    }

    // Delete Parking History
    @DeleteMapping("/{id}")
    public String deleteHistory(@PathVariable Integer id) {

        parkingHistoryService.deleteHistory(id);

        return "Parking history deleted successfully.";
    }
}