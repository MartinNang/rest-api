package org.pytch.backend.service;

import org.pytch.backend.model.Project;

import java.util.List;

public interface ProjectService {

    Project saveProject(Project project);

    List<Project> getProjects();

    Project updateProject(Project project, Long projectId);
    
    void deleteProjectById(Long projectId);
}
