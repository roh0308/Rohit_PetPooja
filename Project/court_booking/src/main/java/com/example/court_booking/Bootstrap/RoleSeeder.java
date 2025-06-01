package com.example.Court_Booking.Bootstrap;

import com.example.Court_Booking.Entity.Roles;
import com.example.Court_Booking.Enum.Role;
import com.example.Court_Booking.Repository.RolesRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RolesRepository rolesRepository;

    public RoleSeeder(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadRoles();
    }

    private void loadRoles() {
        Arrays.stream(Role.values()).forEach(roleEnum -> {
            rolesRepository.findByRole(roleEnum).ifPresentOrElse(
                    existing -> System.out.println("Role " + roleEnum + " already exists."),
                    () -> {
                        Roles newRole = new Roles();
                        newRole.setId((long) roleEnum.ordinal() + 1);
                        newRole.setRole(roleEnum);
                        rolesRepository.save(newRole);
                        System.out.println("Role " + roleEnum + " inserted.");
                    }
            );
        });
    }
}
