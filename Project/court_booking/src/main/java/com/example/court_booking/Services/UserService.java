package com.example.Court_Booking.Services;

import com.example.Court_Booking.DTO.LoginDTO;
import com.example.Court_Booking.DTO.UserRegistrationDTO;
import com.example.Court_Booking.Enum.Role;
import org.springframework.http.ResponseEntity;


public interface UserService {
    ResponseEntity<String> registerUser(UserRegistrationDTO userRegistrationDTO, Role role);
    ResponseEntity<String> loginUser(LoginDTO loginDTO);
}
