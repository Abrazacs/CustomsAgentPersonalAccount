package ru.ssemenov.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {
    private String username;
    private String password;
    private String email;
    private List<String> rolesNames;
}
