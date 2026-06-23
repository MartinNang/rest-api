package org.pytch.backend.controller;

import jakarta.validation.Valid;
import org.pytch.backend.dto.request.SaveProjectDto;
import org.pytch.backend.model.Project;
import org.pytch.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/projects")
    public Project saveProject(
            @Valid @RequestBody SaveProjectDto saveProjectDto) throws IOException {
        // TODO encode zip file as binary and store zip file to var/data
        File projectFile = saveProjectDto.getProjectFile();
        Files.move(projectFile.toPath(), projectFile.toPath());

        return projectService.saveProject(new Project(saveProjectDto));
    }

    @GetMapping("/projects")
    public List<Project> getProjects()
    {
        return projectService.getProjects();
    }

    @PutMapping("/projects/{id}")
    public Project updateProject (@RequestBody Project project,
                                 @PathVariable("id") Long projectId)
    {
        return projectService.updateProject(project, projectId);
    }

    @DeleteMapping("/projects/{id}")
    public String deleteUserById(@PathVariable("id") Long projectId)
    {
        return "Deleted successfully";
    }
}
