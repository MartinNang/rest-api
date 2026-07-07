package org.pytch.backend.controller;

import org.pytch.backend.dto.request.PytchUserDto;
import org.pytch.backend.dto.request.SaveProjectDto;
import org.pytch.backend.model.Project;
import org.pytch.backend.model.PytchUser;
import org.pytch.backend.service.ProjectService;
import org.pytch.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

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

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart SaveProjectDto projectDto) {
        Map<String, Object> response = new HashMap<>();

        // TODO: check file type

        try {
            // TODO find user who created project in database
            PytchUserDto creatorDto = projectDto.getUserDto();
            PytchUser creator = userService.findUserByEmail(creatorDto.getEmail());
            if (creator != null) {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                Path userLocation = projectService.getUserStoragePath(projectDto);
                Path targetLocation = userLocation.resolve(fileName);

                Files.createDirectories(userLocation);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                response.put("fileName", fileName);
                response.put("fileSize", file.getSize());
                response.put("message", "File uploaded successfully!");

                Project project = new Project(projectDto, creator);
                projectService.saveProject(project);
            }
            else {
                // TODO: error handling
            }

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("error", "Could not upload the file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/users/{id}/projects")
    public List<Project> findUserProjects (@RequestBody PytchUser pytchUser,
                                           @PathVariable Long userId)
    {
        return projectService.findProjectsByUserId(pytchUser, userId);
    }
}
