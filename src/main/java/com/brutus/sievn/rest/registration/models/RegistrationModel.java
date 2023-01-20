package com.brutus.sievn.rest.registration.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
@AllArgsConstructor

public class RegistrationModel {

    @NonNull
    private final String name;
    @NonNull
    private final String email;
    @NonNull
    private final String password;
}
