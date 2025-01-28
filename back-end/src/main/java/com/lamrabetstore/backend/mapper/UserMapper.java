package com.lamrabetstore.backend.mapper;

import com.lamrabetstore.backend.dto.UserDTO;
import com.lamrabetstore.backend.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toUserProfileDto(final User user) {
        return new UserDTO(user.getName(),user.getEmail(), user.getRole().name());
    }
}