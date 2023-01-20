package com.brutus.sievn.services;


import com.brutus.sievn.exception.BadRequestException;
import com.brutus.sievn.exception.ConflictException;
import com.brutus.sievn.persistance.model.user.AppUser;
import com.brutus.sievn.persistance.model.user.AppUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final AppUserRepo userRepo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(AppUserRepo userRepo,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException
                (String.format("User with email %s not found", email)));
    }


    public void signUpUser(AppUser user) {

        // If user with email exists
        if(userRepo.findByEmail(user.getUsername()).isPresent()){
            throw new ConflictException(
                    String.format("User with email %s already exists", user.getUsername()));
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

    }

    public void changePassword(String newPassword, String oldPassword) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();

        AppUser applicationUser = (AppUser) loadUserByUsername(principal);

        if(!bCryptPasswordEncoder.matches(oldPassword, applicationUser.getPassword())){
            throw new BadRequestException("Wrong password");
        }

        String newEncodedPassword = bCryptPasswordEncoder.encode(newPassword);
        applicationUser.setPassword(newEncodedPassword);

        userRepo.save(applicationUser);

        log.info("Password changed successfully.");

    }


}
