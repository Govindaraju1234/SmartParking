package com.smartparking.controller;

import com.smartparking.model.User;
import com.smartparking.model.Booking;
import com.smartparking.service.BookingService;
import com.smartparking.model.ParkingHistory;
import com.smartparking.service.ParkingHistoryService;
import com.smartparking.service.ParkingSlotService;
import com.smartparking.model.Payment;
import com.smartparking.service.PaymentService;
import com.smartparking.service.UserService;
import com.smartparking.service.ParkingSlotService;
import com.smartparking.model.ParkingSlot;
import jakarta.servlet.http.HttpSession;
import com.smartparking.service.UserService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
private ParkingHistoryService parkingHistoryService;
    @Autowired
private PaymentService paymentService;
    @Autowired
private BookingService bookingService;
    @Autowired
private ParkingSlotService parkingSlotService;
         @Autowired
private UserService userService;
    

    @GetMapping("/admin/dashboard")
public String adminDashboard(HttpSession session, Model model) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    if (!"ADMIN".equals(user.getRole())) {
        return "redirect:/dashboard";
    }

    model.addAttribute("totalUsers",
            userService.getAllUsers().size());

    model.addAttribute("totalSlots",
            parkingSlotService.getAllSlots().size());

    model.addAttribute("totalBookings",
            bookingService.getAllBookings().size());

    model.addAttribute("totalPayments",
            paymentService.getAllPayments().size());

    model.addAttribute("totalHistory",
            parkingHistoryService.getAllHistory().size());
    model.addAttribute("totalRevenue",
            paymentService.getTotalRevenue());
    model.addAttribute("bookedSlots",
            parkingSlotService.getBookedSlots());
    model.addAttribute("availableSlots",
            parkingSlotService.getAllSlots().size() - parkingSlotService.getBookedSlots());

    return "admin-dashboard";
}
    @GetMapping("/admin/users")
public String viewUsers(HttpSession session, Model model) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    if (!"ADMIN".equals(user.getRole())) {
        return "redirect:/dashboard";
    }

    model.addAttribute("users", userService.getAllUsers());

    return "admin-users";
}
@GetMapping("/admin/slots")
public String viewSlots(HttpSession session, Model model) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    if (!"ADMIN".equals(user.getRole())) {
        return "redirect:/dashboard";
    }

    model.addAttribute("slots", parkingSlotService.getAllSlots());

    return "admin-slots";
}
@PostMapping("/admin/slots/add")
public String addSlot(ParkingSlot slot) {

    parkingSlotService.saveSlot(slot);

    return "redirect:/admin/slots";
}
// Show Edit Slot Page
@GetMapping("/admin/slots/edit/{id}")
public String editSlot(@PathVariable Integer id,
                       HttpSession session,
                       Model model) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    if (!"ADMIN".equals(user.getRole())) {
        return "redirect:/dashboard";
    }

    model.addAttribute("slot", parkingSlotService.getSlotById(id));

    return "edit-slot";
}
// Update Slot
@PostMapping("/admin/slots/update")
public String updateSlot(ParkingSlot slot) {

    parkingSlotService.updateSlot(slot);

    return "redirect:/admin/slots";
}
// Delete Slot
@GetMapping("/admin/slots/delete/{id}")
public String deleteSlot(@PathVariable Integer id) {

    parkingSlotService.deleteSlot(id);

    return "redirect:/admin/slots";
}
@GetMapping("/admin/bookings")
public String viewBookings(HttpSession session, Model model) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    if (!"ADMIN".equals(user.getRole())) {
        return "redirect:/dashboard";
    }

    model.addAttribute("bookings", bookingService.getAllBookings());

    return "admin-bookings";
}
@GetMapping("/admin/payments")
public String viewPayments(HttpSession session, Model model) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    if (!"ADMIN".equals(user.getRole())) {
        return "redirect:/dashboard";
    }

    model.addAttribute("payments", paymentService.getAllPayments());

    return "admin-payments";
}
@GetMapping("/admin/history")
public String viewParkingHistory(HttpSession session, Model model) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    if (!"ADMIN".equals(user.getRole())) {
        return "redirect:/dashboard";
    }

    model.addAttribute("historyList", parkingHistoryService.getAllHistory());

    return "admin-history";
}
@GetMapping("/admin/verify-qr")
public String verifyQRPage(HttpSession session) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    if (!"ADMIN".equals(user.getRole())) {
        return "redirect:/dashboard";
    }

    return "verify-qr";
}
@PostMapping("/admin/verify-qr")
public String verifyQRCode(@RequestParam Integer bookingId,
                           Model model) {

    Booking booking = bookingService.getBookingById(bookingId);

    model.addAttribute("booking", booking);

    return "verify-result";
}
@GetMapping("/admin/bookings/search")
public String searchBookings(@RequestParam String vehicleNumber,
                             HttpSession session,
                             Model model) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null || !"ADMIN".equals(user.getRole())) {
        return "redirect:/login";
    }

    model.addAttribute("bookings",
            bookingService.searchBookings(vehicleNumber));

    return "admin-bookings";
}
}