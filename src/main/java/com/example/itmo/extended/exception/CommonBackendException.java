package com.example.itmo.extended.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class CommonBackendException extends RuntimeException {
    private final String msg;
    private final HttpStatus stat;
}
