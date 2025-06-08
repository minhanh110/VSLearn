package com.security;
import com.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.repository.UserRepository;

@Service
public class MyUserDetails implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));

    // Debug để kiểm tra
    System.out.println("=== MyUserDetails DEBUG ===");
    System.out.println("Username: " + user.getUserName());
    System.out.println("Password: " + user.getUserPassword()); // hoặc getPassword()
    System.out.println("Role: " + user.getUserRole());

    return org.springframework.security.core.userdetails.User
            .withUsername(user.getUserName())
            .password(user.getUserPassword()) // Đảm bảo method này đúng
            .authorities("ROLE_" + user.getUserRole()) // Thêm prefix ROLE_
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build();
  }
}