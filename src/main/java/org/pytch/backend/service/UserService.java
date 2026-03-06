package org.pytch.backend.service;

import org.pytch.backend.entity.PytchUser;

import java.util.List;

public interface UserService {

    PytchUser saveUser(PytchUser pytchUser);

    List<PytchUser> getUsers();

    PytchUser updateUser(PytchUser pytchUser, Long userId);
    
    void deleteUserById(Long userId);
}
