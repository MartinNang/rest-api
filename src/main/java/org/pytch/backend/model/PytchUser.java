package org.pytch.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.pytch.backend.dto.request.PytchUserDto;
import org.pytch.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class PytchUser implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private Timestamp createdAt;

    // private boolean locked;
    @Getter
    private Set<GrantedAuthority> authorities = null;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    public PytchUser() {

    }

    public PytchUser(PytchUserDto pytchUserDto) {
        this.username = pytchUserDto.getUsername();
        this.email = pytchUserDto.getEmail();
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.authorities = new HashSet<>();
    }

    /*@Override
    public boolean isAccountNonLocked() {
        return locked;
    }*/

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
