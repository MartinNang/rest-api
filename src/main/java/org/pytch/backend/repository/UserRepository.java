package org.pytch.backend.repository;

import org.pytch.backend.model.PytchUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<PytchUser,Long> {
    Optional<PytchUser> findPytchUserByEmail(String email);

    Optional<PytchUser> findPytchUserByUsername(String username);
}
