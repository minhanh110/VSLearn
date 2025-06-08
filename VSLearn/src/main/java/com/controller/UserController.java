package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.dto.request.UserLoginDTO;
import com.dto.response.ResponseData;
import com.entities.User;
import com.security.PublicEndpoint;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import com.dto.request.UserDataDTO;
import com.dto.response.UserResponseDTO;
import com.service.impl.UserService;

import java.time.Instant;

@RestController
@RequestMapping("/users")
@Api(tags = "users")
@CrossOrigin(origins = "http://localhost:3002")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping("/signin")
  @ApiOperation(value = "${UserController.signin}")
  @ApiResponses(value = {//
          @ApiResponse(code = 400, message = "Something went wrong"), //
          @ApiResponse(code = 422, message = "Invalid username/password supplied")})
  public ResponseEntity<ResponseData> login(//
                                      @RequestBody @Valid UserLoginDTO userLoginDTO) {
    return ResponseEntity.ok(ResponseData.builder()
            .message("Success")
                    .status("Success")
                    .data(userService.signin(userLoginDTO.getUsername(), userLoginDTO.getPassword()))
            .build());
  }

  @PublicEndpoint
  @PostMapping("/signup")
  @ApiOperation(value = "${UserController.signup}")
  @ApiResponses(value = {//
          @ApiResponse(code = 400, message = "Something went wrong"), //
          @ApiResponse(code = 403, message = "Access denied"), //
          @ApiResponse(code = 422, message = "Username is already in use")})
  public ResponseEntity<ResponseData> signup(@ApiParam("Signup User") @Valid @RequestBody UserDataDTO userDTO) {
    User user = new User();
    user.setUserName(userDTO.getUsername());
    user.setUserEmail(userDTO.getEmail());
    user.setUserPassword(userDTO.getPassword());
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    user.setPhoneNumber(userDTO.getPhoneNumber());
    user.setCreatedAt(Instant.now());
    user.setUserRole(userDTO.getUserRole() != null ? userDTO.getUserRole() : "ROLE_LEARNER");

    return ResponseEntity.ok(ResponseData.builder()
                    .status("Success")
                    .message("Tạo tài khoản thành công")
            .data(userService.signup(user)).build());
  }

  @DeleteMapping(value = "/{username}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ApiOperation(value = "${UserController.delete}", authorizations = { @Authorization(value="apiKey") })
  @ApiResponses(value = {//
          @ApiResponse(code = 400, message = "Something went wrong"), //
          @ApiResponse(code = 403, message = "Access denied"), //
          @ApiResponse(code = 404, message = "The user doesn't exist"), //
          @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public String delete(@ApiParam("Username") @PathVariable String username) {
    return username;
  }

  @GetMapping(value = "/{username}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ApiOperation(value = "${UserController.search}", response = UserResponseDTO.class, authorizations = { @Authorization(value="apiKey") })
  @ApiResponses(value = {//
          @ApiResponse(code = 400, message = "Something went wrong"), //
          @ApiResponse(code = 403, message = "Access denied"), //
          @ApiResponse(code = 404, message = "The user doesn't exist"), //
          @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public UserResponseDTO search(@ApiParam("Username") @PathVariable String username) {
    User user = userService.search(username);
    UserResponseDTO responseDTO = new UserResponseDTO();
    responseDTO.setId(user.getId());
    responseDTO.setUsername(user.getUserName());
    responseDTO.setEmail(user.getUserEmail());
    responseDTO.setFirstName(user.getFirstName());
    responseDTO.setLastName(user.getLastName());
    responseDTO.setPhoneNumber(user.getPhoneNumber());
    responseDTO.setUserAvatar(user.getUserAvatar());
    responseDTO.setUserRole(user.getUserRole());
    responseDTO.setIsActive(user.getIsActive());
    return responseDTO;
  }

  @GetMapping(value = "/me")
  @PreAuthorize("isAuthenticated()")
  @ApiOperation(value = "${UserController.me}", response = UserResponseDTO.class, authorizations = { @Authorization(value="apiKey") })
  @ApiResponses(value = {//
          @ApiResponse(code = 400, message = "Something went wrong"), //
          @ApiResponse(code = 403, message = "Access denied"), //
          @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public UserResponseDTO whoami(HttpServletRequest req) {
    User user = userService.whoami(req);
    UserResponseDTO responseDTO = new UserResponseDTO();
    responseDTO.setId(user.getId());
    responseDTO.setUsername(user.getUserName());
    responseDTO.setEmail(user.getUserEmail());
    responseDTO.setFirstName(user.getFirstName());
    responseDTO.setLastName(user.getLastName());
    responseDTO.setPhoneNumber(user.getPhoneNumber());
    responseDTO.setUserAvatar(user.getUserAvatar());
    responseDTO.setUserRole(user.getUserRole());
    responseDTO.setIsActive(user.getIsActive());
    return responseDTO;
  }

  @GetMapping("/refresh")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  public String refresh(HttpServletRequest req) {
    return userService.refresh(req.getRemoteUser());
  }
}