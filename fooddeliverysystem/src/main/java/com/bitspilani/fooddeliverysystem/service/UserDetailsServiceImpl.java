//package com.bitspilani.fooddeliverysystem.service;
//
//import com.bitspilani.fooddeliverysystem.model.User;
//import com.bitspilani.fooddeliverysystem.repository.UserRepository;
//import java.util.Collection;
//import java.util.Collections;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
//            getAuthority(user));
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthority(User user) {
//        return Collections.singletonList(new SimpleGrantedAuthority(user.getUserRole().name()));
//    }
//}
