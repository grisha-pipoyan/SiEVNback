package com.brutus.sievn;

import com.brutus.sievn.persistance.model.user.AppUser;
import com.brutus.sievn.persistance.model.user.AppUserRepo;
import com.brutus.sievn.security.AppUserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final AppUserRepo applicationUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CommandLineAppStartupRunner(AppUserRepo applicationUserRepository,
                                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) {

        List<AppUser> admins =
                applicationUserRepository.findAppUserByRoleEquals(AppUserRole.ADMIN);

        if(admins.size()==0){
            AppUser applicationUser = new AppUser();
            applicationUser.setName("Elen Aghajanyan");
            applicationUser.setEmail("sievn");
            applicationUser.setPassword(bCryptPasswordEncoder.encode("password"));
            applicationUser.setRole(AppUserRole.ADMIN);
            applicationUser.setEnabled(true);
            applicationUserRepository.save(applicationUser);
        }
    }
}
