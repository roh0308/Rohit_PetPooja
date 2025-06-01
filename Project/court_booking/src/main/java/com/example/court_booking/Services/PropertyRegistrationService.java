package com.example.Court_Booking.Services;

import com.example.Court_Booking.DTO.PropertyRegistrationDTO;
import org.springframework.http.ResponseEntity;

public interface PropertyRegistrationService {
    ResponseEntity<String>PropertyRegistration(PropertyRegistrationDTO propertyRegistrationDTO);
}
