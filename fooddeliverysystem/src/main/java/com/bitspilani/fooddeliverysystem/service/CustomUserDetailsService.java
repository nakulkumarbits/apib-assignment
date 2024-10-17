package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.repository.UserRepository;
import com.bitspilani.fooddeliverysystem.utils.FoodDeliveryConstants;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.bitspilani.fooddeliverysystem.model.User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(FoodDeliveryConstants.USER_NOT_PRESENT);
        }

        if (user.getRole() == UserRole.ADMIN) {
            List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(FoodDeliveryConstants.ROLE_ADMIN));
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }

        if (user.getRole() == UserRole.CUSTOMER) {
            List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(FoodDeliveryConstants.ROLE_CUSTOMER));
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }

        if (user.getRole() == UserRole.RESTAURANT_OWNER) {
            List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(FoodDeliveryConstants.ROLE_RESTAURANT_OWNER));
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }

        if (user.getRole() == UserRole.DELIVERY_PERSONNEL) {
            List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(FoodDeliveryConstants.ROLE_DELIVERY_PERSONNEL));
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }

        throw new UsernameNotFoundException(FoodDeliveryConstants.USER_NOT_PRESENT);
    }
}
