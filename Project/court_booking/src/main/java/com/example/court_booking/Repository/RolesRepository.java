package com.example.Court_Booking.Repository;

import com.example.Court_Booking.Entity.Roles;
import com.example.Court_Booking.Enum.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRole(Role role);
}
