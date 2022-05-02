package com.example.restfuzz_backend.core.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum HttpMethod {
    GET ("GET"),
    POST ("POST");

    private final String methodName;

    @Override
    public String toString() {
        return methodName;
    }
}
