package com.example.Court_Booking.Config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableAutoConfiguration
public class securityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/registerOwner", "/registerUser","/loginUser"
//                          ,"/property/register")
//                )
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/loginUser", "/registerOwner", "/registerUser","/property/register").permitAll() // Public endpoints
//                        .requestMatchers("/users/**", "/settings/**").hasAuthority("Admin") // Admin-specific
//                        .requestMatchers("/admin/**").hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper") // Specific role-based section
//                        .anyRequest().authenticated() // Everything else must be authenticated
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .usernameParameter("email")
//                        .permitAll()
//                )
//                .rememberMe(remember -> remember
//                        .key("AbcdEfghIjklmNopQrsTuvXyz_0123456789")
//                )
//                .logout(logout -> logout.permitAll())
//                .headers(headers -> headers.frameOptions().sameOrigin());
//
//        return http.build();

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }


}
