package com.example.Court_Booking.Services.Implementation;

import com.example.Court_Booking.DTO.LoginDTO;
import com.example.Court_Booking.DTO.UserRegistrationDTO;
import com.example.Court_Booking.Entity.Roles;
import com.example.Court_Booking.Entity.User;
import com.example.Court_Booking.Enum.Role;
import com.example.Court_Booking.Repository.RolesRepository;
import com.example.Court_Booking.Repository.UserRepository;
import com.example.Court_Booking.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;


    PasswordEncoder passwordEncoder;

    public UserImplementation(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<String> registerUser(UserRegistrationDTO userRegistrationDTO, Role role) {

        if(userRepository.existsByEmail(userRegistrationDTO.getEmail())){
            return ResponseEntity.status(403).body("User email exists!!");
        }

        User user = new User(userRegistrationDTO.getName(), userRegistrationDTO.getEmail(), userRegistrationDTO.getPhone(), userRegistrationDTO.getPassword());
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Roles userRole = rolesRepository.findByRole(role)
                .orElseThrow(() -> new RuntimeException(role + " role not found in the database"));

        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        return ResponseEntity.status(200).body("Registration Successful");
    }

    @Override
    public ResponseEntity<String>loginUser(LoginDTO loginDTO) {
        Optional<User>user=userRepository.findByEmail(loginDTO.getEmail());

        if(user.isEmpty()){
            return ResponseEntity.status(403).body("User not found");
        }
        boolean passwordMatch = passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword());
        if(passwordMatch){
            return ResponseEntity.status(200).body("Login Successful");
        }else{
            return ResponseEntity.status(403).body("login failed");
        }
    }

}
