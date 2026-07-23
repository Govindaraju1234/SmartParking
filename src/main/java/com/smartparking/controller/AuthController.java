package com.smartparking.controller;
import org.springframework.ui.Model;
import com.smartparking.model.User;
import com.smartparking.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Show Registration Page
 @GetMapping("/register")
public String showRegisterPage(Model model) {
    model.addAttribute("user", new User());
    return "register";
}

    // Process Registration
    @PostMapping("/register")
    public String registerUser(User user) {

        userService.registerUser(user);

        return "redirect:/login";
    }
@PostMapping("/login")
public String loginUser(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

    User user = userService.loginUser(email, password);

    if (user == null) {

        model.addAttribute("error", "Invalid Email or Password");

        return "login";
    }

    session.setAttribute("loggedInUser", user);

    if ("ADMIN".equals(user.getRole())) {
    return "redirect:/admin/dashboard";
}

return "redirect:/dashboard";
}
@GetMapping("/dashboard")
public String dashboard(HttpSession session) {

    User user = (User) session.getAttribute("loggedInUser");

    if (user == null) {
        return "redirect:/login";
    }

    return "dashboard";
}
@GetMapping("/logout")
public String logout(HttpSession session) {

    session.invalidate();

    return "redirect:/login";
}
    // Show Login Page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

}