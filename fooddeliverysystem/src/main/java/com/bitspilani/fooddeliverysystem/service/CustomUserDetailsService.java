package com.bitspilani.fooddeliverysystem.service;

import com.bitspilani.fooddeliverysystem.model.Administrator;
import com.bitspilani.fooddeliverysystem.model.Customer;
import com.bitspilani.fooddeliverysystem.model.DeliveryPersonnel;
import com.bitspilani.fooddeliverysystem.model.RestaurantOwner;
import com.bitspilani.fooddeliverysystem.repository.AdministratorRepository;
import com.bitspilani.fooddeliverysystem.repository.CustomerRepository;
import com.bitspilani.fooddeliverysystem.repository.DeliveryPersonnelRepository;
import com.bitspilani.fooddeliverysystem.repository.RestaurantOwnerRepository;
import java.util.ArrayList;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrator admin = administratorRepository.findByUsername(username);
        if (admin != null) {
            return new User(admin.getUsername(), admin.getPassword(), new ArrayList<>());
        }

        Customer customer = customerRepository.findByUsername(username);
        if (customer != null) {
            List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
            return new User(customer.getUsername(), customer.getPassword(), grantedAuthorities);
        }

        RestaurantOwner owner = restaurantOwnerRepository.findByUsername(username);
        if (owner != null) {
            return new User(owner.getUsername(), owner.getPassword(), new ArrayList<>());
        }

        DeliveryPersonnel personnel = deliveryPersonnelRepository.findByUsername(username);
        if (personnel != null) {
            return new User(personnel.getUsername(), personnel.getPassword(), new ArrayList<>());
        }

        throw new UsernameNotFoundException("User not found");
    }
}
