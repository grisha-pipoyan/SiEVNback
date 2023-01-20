package com.brutus.sievn.rest.registration;

import com.brutus.sievn.rest.registration.models.RegistrationModel;
import com.brutus.sievn.services.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sievn/public/register")
@CrossOrigin("*")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Operation(summary = "User registering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Invalid email"),
            @ApiResponse(responseCode = "409", description = "Email exists"),
    })
    @PostMapping("/user")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationModel request) {

        registrationService.register(request);

        return ResponseEntity.ok().build();
    }

}
