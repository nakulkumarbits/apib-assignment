package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.enums.UserRole;
import com.bitspilani.fooddeliverysystem.repository.AdministratorRepository;
import com.bitspilani.fooddeliverysystem.repository.CustomerRepository;
import com.bitspilani.fooddeliverysystem.repository.DeliveryPersonnelRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantOwnerRepository;
import com.bitspilani.fooddeliverysystem.repository.UserRepository;
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
    private AdministratorRepository administratorRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    private DeliveryPersonnelRepository deliveryPersonnelRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.bitspilani.fooddeliverysystem.model.User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (user.getRole() == UserRole.ADMIN) {
            List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }

        if (user.getRole() == UserRole.CUSTOMER) {
            List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }

        if (user.getRole() == UserRole.RESTAURANT_OWNER) {
            List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority("RESTAURANT_OWNER"));
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }

        if (user.getRole() == UserRole.DELIVERY_PERSONNEL) {
            List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority("DELIVERY_PERSONNEL"));
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }

        throw new UsernameNotFoundException("User not found");
    }
}
