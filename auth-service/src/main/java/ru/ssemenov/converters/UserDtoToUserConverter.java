package ru.ssemenov.converters;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.ssemenov.dtos.UserDto;
import ru.ssemenov.entities.Role;
import ru.ssemenov.entities.User;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDtoToUserConverter {
    private final PasswordEncoder passwordEncoder;

    public User convert (UserDto dto, String vatCode, List<Role> roles){
        return User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .companyVAT(vatCode)
                .roles(roles)
                .build();

    }

}
