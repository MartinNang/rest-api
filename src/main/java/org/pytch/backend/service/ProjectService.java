package org.pytch.backend.service;

import org.pytch.backend.dto.request.PytchUserDto;
import org.pytch.backend.dto.request.SaveProjectDto;
import org.pytch.backend.model.Project;
import org.pytch.backend.model.PytchUser;

import java.nio.file.Path;
import java.util.List;

public interface ProjectService {

    Project saveProject(Project project);

    List<Project> getProjects();

    Project updateProject(Project project, Long projectId);
    
    void deleteProjectById(Long projectId);

    public Path getFileStoragePath();

    public Path getUserStoragePath(PytchUser user);

    List<Project> findProjectsByUserId(Long userId);

    List<Project> findListedProjectsByUserId(Long userId);

    Project findProjectById(Long projectId, PytchUser pytchUser) throws Exception;

    Project downloadProjectById(Long projectId, PytchUser userByUsername) throws Exception;
}
