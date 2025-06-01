package com.example.Court_Booking.Controller;

import com.example.Court_Booking.DTO.PropertyRegistrationDTO;
import com.example.Court_Booking.Services.PropertyRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertyRegistrationController {

    @Autowired
    PropertyRegistrationService propertyRegistrationService;


    @PostMapping("/property/register")
    public ResponseEntity<String>PropertyRegistration(@Validated @RequestBody PropertyRegistrationDTO dto) {
        return propertyRegistrationService.PropertyRegistration(dto);

    }
}
