package ru.ssemenov.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExportUserDto {
    String username;
    String email;
}
