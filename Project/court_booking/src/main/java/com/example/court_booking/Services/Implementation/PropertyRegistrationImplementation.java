package com.example.Court_Booking.Services.Implementation;
import com.example.Court_Booking.DTO.PropertyRegistrationDTO;
import com.example.Court_Booking.Entity.PropertyRegistration;
import com.example.Court_Booking.Entity.User;
import com.example.Court_Booking.Repository.PropertyRegistrationRepository;
import com.example.Court_Booking.Repository.UserRepository;
import com.example.Court_Booking.Services.PropertyRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyRegistrationImplementation implements PropertyRegistrationService {

    @Autowired
    PropertyRegistrationRepository propertyRegistrationRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<String> PropertyRegistration(PropertyRegistrationDTO propertyRegistrationDTO) {

        Optional<User> ownerOptional=userRepository.findById(propertyRegistrationDTO.getUser_id());
        User owner = ownerOptional.get();
        PropertyRegistration propertyOwner=new PropertyRegistration(propertyRegistrationDTO.getPropertyName(),propertyRegistrationDTO.getNoofCourts(),propertyRegistrationDTO.getTimings(),propertyRegistrationDTO.getHourlyRates());
        propertyOwner.setOwner(owner);
        propertyRegistrationRepository.save(propertyOwner);
        return ResponseEntity.ok("Property Registration Successfull");
    }


}
