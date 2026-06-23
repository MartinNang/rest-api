package org.pytch.backend.model;// Source - https://stackoverflow.com/a/19060219
// Posted by Łukasz Jarzyna
// Retrieved 2026-06-14, License - CC BY-SA 3.0

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;
    @Getter
    private String username;
    @Getter
    private String password;
    // private boolean locked;
    @Getter
    private Set<GrantedAuthority> authorities = null;

    private PytchUser pUser;

    public UserDetailsImpl(Long id, String username, String password/*, boolean locked */) {
        this.id = id;
        this.username = username;
        this.password = password;
        //this.locked = locked;
    }

    public UserDetailsImpl(PytchUser pUser) {
        this.id = pUser.getId();
        this.username = pUser.getUsername();
        this.password = pUser.getPassword();
        //this.locked = false;
    }

    /*public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return locked;
    }*/

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public void setAuthorities( Set<GrantedAuthority> authorities ) {
        if ( this.authorities == null ) {
            this.authorities = authorities;
        }
    }

    // setters, getters
}
