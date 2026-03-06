package org.pytch.backend.repository;

import org.pytch.backend.entity.PytchUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<PytchUser,Long> {
}
