package com.brutus.sievn.services;

import com.brutus.sievn.exception.BadRequestException;
import com.brutus.sievn.persistance.model.user.AppUser;
import com.brutus.sievn.rest.registration.models.RegistrationModel;
import com.brutus.sievn.security.AppUserRole;
import org.springframework.stereotype.Service;


@Service
public class RegistrationService {

    private final UserService userService;

    private final EmailValidatorService emailValidatorService;

    public RegistrationService(UserService userService,
                               EmailValidatorService emailValidatorService) {
        this.userService = userService;
        this.emailValidatorService = emailValidatorService;
    }


    public void register(RegistrationModel request) {

        if (!emailValidatorService.test(request.getEmail())) {
            throw new BadRequestException("Invalid email");
        }

        userService.signUpUser( new AppUser(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER
        ));

    }
}
