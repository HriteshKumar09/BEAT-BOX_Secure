package com.kodnest.tunehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.kodnest.tunehub.entity.AppUser;
import com.kodnest.tunehub.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Handles user registration.
     */

    @PostMapping("/register")
    public String registerUser(@ModelAttribute AppUser user) {
        System.out.println("Received user: " + user);
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        System.out.println("Gender: " + user.getGender());
        System.out.println("Role: " + user.getRole());
        System.out.println("Address: " + user.getAddress());

        if (userService.emailExists(user)) {
            System.out.println("Email already exists: " + user.getEmail());
            return "register"; // Redirect back to registration page
        }

        userService.saveUser(user);
        System.out.println("User registered successfully!");
        return "login"; // Redirect to login page
    }
    
    
   
    /**
     * Handles user login validation (handled by Spring Security)
     */
    @PostMapping("/validate")
    public String validate(@RequestParam("email") String email,
                           @RequestParam("password") String password,
                           HttpSession session) {
        System.out.println("Received login request with email: " + email);

        // Step 1: Check if user credentials are valid
        if (userService.validUser(email, password)) {
            System.out.println("User validated successfully.");

            // Step 2: Store email in session
            session.setAttribute("email", email);
            System.out.println("Email stored in session: " + email);

            // Step 3: Get and check user role
            String role = userService.getRole(email);
            System.out.println("User role retrieved: " + role);

            // Store the role with the "ROLE_" prefix in the session
            session.setAttribute("role", "ROLE_" + role);  // Add ROLE_ prefix

            if (role.equals("admin")) {
                System.out.println("Redirecting to adminhome...");
                return "adminhome"; // Ensure "adminhome" page exists
            } else if (role.equals("customer")) {
                System.out.println("Redirecting to customerhome...");
                return "customerhome"; // Ensure "customerhome" page exists
            } else {
                System.out.println("Unknown role: " + role);
                return "redirect:/login?error=role";
            }
        }
        return "redirect:/login?error=invalid";
    }

  
    /**
     * Handles user logout.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session
        return "login"; // Redirect to login page
    }
}
