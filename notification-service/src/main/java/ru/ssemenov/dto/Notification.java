package ru.ssemenov.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Notification {
    String vatCode;
    String message;
}
