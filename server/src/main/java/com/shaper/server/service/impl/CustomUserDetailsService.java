package com.shaper.server.service.impl;

import com.shaper.server.CustomUserDetails;
import com.shaper.server.exception.DataNotFoundException;
import com.shaper.server.model.entity.User;
import com.shaper.server.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            System.out.println("User not found with email: " + username);
            throw new DataNotFoundException("User not found with email: " + username);
        }
        return new CustomUserDetails(user);
    }
}
