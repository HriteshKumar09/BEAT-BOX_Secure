package com.kodnest.tunehub.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.kodnest.tunehub.entity.AppUser;
import com.kodnest.tunehub.repository.UserRepository;
import com.kodnest.tunehub.service.UserService;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // For password encryption and validation

    @Override
    public void saveUser(AppUser user) {
        System.out.println("Raw password before encoding: " + user.getPassword());

        // Encode the password before saving it
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("Encoded password being saved: " + user.getPassword());

        userRepository.save(user);
        System.out.println("User saved successfully: " + user);
    }

    @Override
    public boolean emailExists(AppUser user) {
        // Check if email exists in the database
        AppUser existingUser = userRepository.findByEmail(user.getEmail());
        return existingUser != null;
    }

    @Override
    public boolean validUser(String email, String password) {
        System.out.println("Attempting to validate user with email: " + email);

        // Fetch user from the database
        AppUser user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("User not found for email: " + email);
            return false;
        }

        System.out.println("User found: " + user);

        // Check the password using PasswordEncoder
        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
        System.out.println("Raw password provided: " + password);
        System.out.println("Encoded password in DB: " + user.getPassword());
        System.out.println("Password matches: " + passwordMatches);

        return passwordMatches;
    }

    @Override
    public String getRole(String email) {
        AppUser user = userRepository.findByEmail(email);
        System.out.println("User retrieved: " + user);  // Log the user object

        if (user != null) {
            return user.getRole();  // Ensure the role is returned from the user
        }
        return "USER";  // Default role if user not found
    }
    
    @Override
    public void updateUser(AppUser user) {
        // Update user (ensure password is encrypted if being updated)
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }
}