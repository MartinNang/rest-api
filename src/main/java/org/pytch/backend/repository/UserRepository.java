package org.pytch.backend.repository;

import org.pytch.backend.model.PytchUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<PytchUser,Long> {
    Optional<PytchUser> findPytchUserByEmail(String email);
}
