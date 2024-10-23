package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.repository.UserRepository;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.bitspilani.fooddeliverysystem.model.User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(FoodDeliveryConstants.USER_NOT_PRESENT);
        }

        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    public void updateLastLogin(String username) {
        com.bitspilani.fooddeliverysystem.model.User user = userRepository.findByUsername(username);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }
}
