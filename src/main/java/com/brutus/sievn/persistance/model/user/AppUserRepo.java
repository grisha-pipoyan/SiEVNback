package com.brutus.sievn.persistance.model.user;

import com.brutus.sievn.security.AppUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepo extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findByEmail(String email);

    List<AppUser> findAppUserByRoleEquals(AppUserRole role);

}
