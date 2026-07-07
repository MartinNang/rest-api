package org.pytch.backend.repository;

import org.pytch.backend.model.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project,Long> {
    List<Project> findProjectsByUser_Id(Long userId);
}
