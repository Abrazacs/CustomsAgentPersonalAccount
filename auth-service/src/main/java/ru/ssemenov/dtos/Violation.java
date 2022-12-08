package ru.ssemenov.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Violation {
    private final String fieldName;
    private final String message;
}
