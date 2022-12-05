package ru.ssemenov.converters;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.ssemenov.dtos.UserDto;
import ru.ssemenov.entities.User;
import ru.ssemenov.services.RoleService;

@Component
@RequiredArgsConstructor
public class UserDtoToUserConverter {
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public User convert (UserDto dto, String vatCode){
        return User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .companyVAT(vatCode)
                .roles(roleService.findAllByNames(dto.getRolesNames()))
                .build();

    }

}
