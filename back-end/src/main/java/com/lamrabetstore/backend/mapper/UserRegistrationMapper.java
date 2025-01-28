package com.lamrabetstore.backend.mapper;

import com.lamrabetstore.backend.dto.RegistrationRequestDto;
import com.lamrabetstore.backend.dto.RegistrationResponseDto;
import com.lamrabetstore.backend.model.User;
import com.lamrabetstore.backend.model.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationMapper {

    public User toUser(RegistrationRequestDto registrationRequestDto) {
        final var user = new User();

        user.setEmail(registrationRequestDto.getEmail());
        user.setName(registrationRequestDto.getName());
        user.setPassword(registrationRequestDto.getPassword());

        if (registrationRequestDto.getRole() != null) {
            user.setRole(Role.valueOf(registrationRequestDto.getRole().toUpperCase()));
        } else {
            user.setRole(Role.ROLE_USER);
        }

        return user;
    }

    public RegistrationResponseDto toRegistrationResponseDto(final User user) {
        return new RegistrationResponseDto(
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}