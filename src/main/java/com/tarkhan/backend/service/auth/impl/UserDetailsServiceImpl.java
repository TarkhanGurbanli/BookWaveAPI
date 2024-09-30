package com.tarkhan.backend.service.auth.impl;

import com.tarkhan.backend.entity.User;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(identifier);

        if (user.isEmpty()) {
            user = userRepository.findByEmail(identifier);
        }

        return user.orElseThrow(() ->
                new ResourceNotFoundException("User", "identifier", identifier));
    }
}
