package com.brutus.sievn.persistance.model.user;

import com.brutus.sievn.persistance.model.addData.HouseInfo;
import com.brutus.sievn.security.AppUserRole;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name="users")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue
    private UUID Id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private AppUserRole role;

    private Boolean locked = false;

    private Boolean enabled = false;

    @OneToMany(
            mappedBy = "appUser",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<HouseInfo> houseInfos;

    public AppUser(String name, String email, String password, AppUserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singleton(authority);

    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public AppUserRole getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public Set<HouseInfo> getHouseInfos() {
        return houseInfos;
    }

    public void setHouseInfos(Set<HouseInfo> houseInfos) {
        this.houseInfos = houseInfos;
    }
}
