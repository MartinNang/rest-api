package org.pytch.backend.service;

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

    public Path getUserStoragePath(SaveProjectDto projectDto);

    List<Project> findProjectsByUserId(PytchUser pytchUser, Long userId);
}
