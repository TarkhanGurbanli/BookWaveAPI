//package com.tarkhan.backend.runner;
//
//import com.tarkhan.backend.entity.User;
//import com.tarkhan.backend.entity.enums.Role;
//import com.tarkhan.backend.repository.UserRepository;
//import com.tarkhan.backend.service.auth.JwtService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@RequiredArgsConstructor
//public class DataInitializer {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtService jwtService;
//
//    @Bean
//    public CommandLineRunner run() {
//        return args -> {
//            User admin = new User();
//            admin.setUsername("admin");
//            admin.setPassword(passwordEncoder.encode("admin"));
//            admin.setEmail("admin@gmail.com");
//            admin.setRole(Role.ADMIN);
//
//            userRepository.save(admin);
//
//            String token = jwtService.generateToken(admin);
//            System.out.println("-------------------------------------------");
//            System.out.println("Admin token: " + token);
//            System.out.println("-------------------------------------------");
//        };
//    }
//}
