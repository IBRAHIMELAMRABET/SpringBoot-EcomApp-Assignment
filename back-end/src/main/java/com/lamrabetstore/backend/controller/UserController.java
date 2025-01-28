package com.lamrabetstore.backend.controller;


import com.lamrabetstore.backend.dto.UserDTO;
import com.lamrabetstore.backend.mapper.UserMapper;
import com.lamrabetstore.backend.model.User;
import com.lamrabetstore.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(userMapper.toUserProfileDto(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateCurrentUser(@RequestBody UserDTO user) {
        return ResponseEntity.ok(userMapper.toUserProfileDto(userService.updateCurrentUser(user)));
    }
}