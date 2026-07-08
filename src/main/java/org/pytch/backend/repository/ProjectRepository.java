package org.pytch.backend.repository;

import org.pytch.backend.PytchProjectStatus;
import org.pytch.backend.model.Project;
import org.pytch.backend.model.PytchUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Long> {
    List<Project> findProjectsByUser_Id(Long userId);

    List<Project> findProjectsByStatusAndUser_Id(PytchProjectStatus status, Long userId);

    Long user(PytchUser user);

    Project findProjectByIdIs(Long projectId);
}
