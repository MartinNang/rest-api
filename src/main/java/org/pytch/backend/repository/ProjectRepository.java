package org.pytch.backend.repository;

import org.pytch.backend.model.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project,Long> {
}
