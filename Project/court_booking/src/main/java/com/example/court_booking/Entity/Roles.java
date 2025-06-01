package com.example.Court_Booking.Entity;

import com.example.Court_Booking.Enum.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Roles {
    @Id
    private Long id;

    private Role role;

    public Roles() {}

    public Roles(Long id, Role role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
