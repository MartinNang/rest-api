package org.pytch.backend.service;

import org.pytch.backend.dto.request.PytchUserDto;
import org.pytch.backend.model.PytchUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    PytchUser saveUser(PytchUser pytchUser);

    List<PytchUser> getUsers();

    PytchUser findUserById(Long id);

    PytchUser findUserByUsername(String username);

    PytchUser updateUser(PytchUser pytchUser, Long userId);
    
    void deleteUserById(Long userId);

    String registerUser(PytchUserDto pytchUserDto);

}
