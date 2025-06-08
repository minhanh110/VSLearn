package com.service.impl;

import javax.servlet.http.HttpServletRequest;

import com.dto.request.UserDataDTO;
import com.entities.User;
import org.modelmapper.ModelMapper;
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

  @Autowired
  private ModelMapper modelMapper;

  public String signin(String username, String password) {
    try {
      User user = userRepository.findByUserName(username)
              .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(username, password)
      );

      return jwtTokenProvider.createToken(user, user.getUserRole());
    } catch (AuthenticationException e) {
      throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }


  public String signup(UserDataDTO dto) {
    if (userRepository.existsByUserName(dto.getUsername())) {
      throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }
    if (userRepository.existsByUserEmail(dto.getEmail())) {
      throw new CustomException("Email is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // Dùng ModelMapper để map DTO -> Entity
    User user = modelMapper.map(dto, User.class);
    user.setUserPassword(passwordEncoder.encode(dto.getPassword()));
    user.setUserRole(dto.getUserRole() != null ? dto.getUserRole() : "ROLE_LEARNER");
    user.setIsActive(true);
    user.setCreatedAt(java.time.Instant.now());

    userRepository.save(user);
    return jwtTokenProvider.createToken(user, user.getUserRole());
  }

  private User mapDtoToEntity(User user, UserDataDTO dto) {
    modelMapper.map(dto, user);
    user.setUserPassword(passwordEncoder.encode(dto.getPassword()));
    user.setUserRole(dto.getUserRole() != null ? dto.getUserRole() : "ROLE_LEARNER");
    user.setIsActive(true);
    user.setCreatedAt(java.time.Instant.now());
    return user;
  }

  // Overload dùng cho khởi tạo mặc định (Entity thuần)
  public String signup(User user) {
    if (userRepository.existsByUserName(user.getUserName())) {
      throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }
    if (userRepository.existsByUserEmail(user.getUserEmail())) {
      throw new CustomException("Email is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
    user.setUserRole(user.getUserRole() != null ? user.getUserRole() : "ROLE_LEARNER");
    user.setIsActive(true);
    user.setCreatedAt(java.time.Instant.now());

    userRepository.save(user);
    return jwtTokenProvider.createToken(user, user.getUserRole());
  }

//  public void delete(String username) {
//    userRepository.deleteByUsername(username);
//  }

  public User search(String username) {
    return userRepository.findByUserName(username)
            .orElseThrow(() -> new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND));
  }

  public User whoami(HttpServletRequest req) {
    return userRepository.findByUserName(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)))
            .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
  }

  public String refresh(String username) {
    User user = userRepository.findByUserName(username)
            .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
    return jwtTokenProvider.createToken(user, user.getUserRole());
  }

  public boolean checkPassword(String password, String passwordHash) {
    return passwordEncoder.matches(password,passwordHash);
  }

//  private User mapDtoToEntity(User user, UserDataDTO dto) {
//    user.setUserName(dto.getUsername());
//    user.setUserPassword(passwordEncoder.encode(dto.getPassword()));
//    user.setUserEmail(dto.getEmail());
//    user.setFirstName(dto.getFirstName());
//    user.setLastName(dto.getLastName());
//    user.setPhoneNumber(dto.getPhoneNumber());
//    user.setUserRole(dto.getUserRole() != null ? dto.getUserRole() : "ROLE_LEARNER");
//    user.setIsActive(true);
//    user.setCreatedAt(java.time.Instant.now());
//    return user;
//  }

}
