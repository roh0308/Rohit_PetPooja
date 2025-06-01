package com.example.Court_Booking.Controller;

import com.example.Court_Booking.DTO.LoginDTO;
import com.example.Court_Booking.DTO.UserRegistrationDTO;
import com.example.Court_Booking.Enum.Role;
import com.example.Court_Booking.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    public UserController(){

    }
    @PostMapping("/registerOwner")
    public ResponseEntity<String> registerCourtOwner(@Validated @RequestBody UserRegistrationDTO dto) {
        return userService.registerUser(dto, Role.OWNER);
    }

    @PostMapping("/registerUser")
    public ResponseEntity<String> registerNormalUser(@Validated @RequestBody UserRegistrationDTO dto) {
        return userService.registerUser(dto, Role.USER);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<String> loginUser(@Validated @RequestBody LoginDTO dto) {
        return userService.loginUser(dto);
    }
}
