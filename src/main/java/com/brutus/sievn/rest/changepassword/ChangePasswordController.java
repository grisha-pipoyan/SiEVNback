package com.brutus.sievn.rest.changepassword;

import com.brutus.sievn.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sievn/management/password/change")
@CrossOrigin("*")
public class ChangePasswordController {

    private final UserService userService;

    public ChangePasswordController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User password changing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Password changed successfully"),
            @ApiResponse(responseCode = "400",description = "Wrong password"),
            @ApiResponse(responseCode = "403")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> changePassword(@RequestParam("newPassword") String newPassword,
                                            @RequestParam("oldPassword") String oldPassword){

        userService.changePassword(newPassword, oldPassword);

        return ResponseEntity.ok().build();
    }


}
