package com.service;

import javax.servlet.http.HttpServletRequest;

import com.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exception.CustomException;
import com.repository.UserRepository;
import com.security.JwtTokenProvider;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private AuthenticationManager authenticationManager;

  public String signin(String username, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      User user = userRepository.findByUsername(username).get();
      return jwtTokenProvider.createToken(user, user.getUserRole());
    } catch (AuthenticationException e) {
      throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public String signup(User user) {
    if (!userRepository.existsByUsername(user.getUserName())) {
      user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
      userRepository.save(user);
      return jwtTokenProvider.createToken(user, user.getUserRole());
    } else {
      throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

//  public void delete(String username) {
//    userRepository.deleteByUsername(username);
//  }

  public User search(String username) {
    User user = userRepository.findByUsername(username).get();
    if (user == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
    return user;
  }

  public User whoami(HttpServletRequest req) {
    return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req))).get();
  }

  public String refresh(String username) {
    User user = userRepository.findByUsername(username).get();
    return jwtTokenProvider.createToken(user, user.getUserRole());
  }

}
