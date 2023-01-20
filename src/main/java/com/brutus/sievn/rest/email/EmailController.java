package com.brutus.sievn.rest.email;

import com.brutus.sievn.services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

@RestController
@RequestMapping("/sievn/public/email")
@CrossOrigin("*")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public ResponseEntity<?> sendEmail(@RequestParam("name") String name,
                                       @RequestParam("email") @Email String email,
                                       @RequestParam("message") @NonNull String message){

        emailService.send(email, message, name);

        return ResponseEntity.ok().build();
    }

}
