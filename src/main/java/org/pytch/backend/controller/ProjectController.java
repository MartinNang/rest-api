package org.pytch.backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
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
            @RequestPart(value = "project") SaveProjectDto projectDto,
            Principal principal) {
        Map<String, Object> response = new HashMap<>();

        // TODO: check file type

        try {
            // TODO find user who created project in database
            PytchUser creator = userService.findUserByUsername(principal.getName());
            if (creator != null) {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(projectDto.getTitle()))+".zip";
                Path userLocation = projectService.getUserStoragePath(creator);
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

    @GetMapping("/users/{userId}/projects")
    public List<Project> findUserProjects (@PathVariable Long userId, Principal principal)
    {
        if (principal.getName().equals(userService.findUserById(userId).getUsername()))
        {
            return projectService.findProjectsByUserId(userId);
        }

        return projectService.findListedProjectsByUserId(userId);
    }

    @GetMapping("/users/profile/projects")
    public List<Project> findProjectById(Principal principal) throws Exception {
        PytchUser user = userService.findUserByUsername(principal.getName());
        if (user != null)
        {
            return projectService.findProjectsByUserId(user.getId());
        }

        throw new Exception("User not found");
    }

    @GetMapping("/projects/{projectId}")
    public Project findProjectById(@PathVariable Long projectId, Principal principal) throws Exception {
        return projectService.findProjectById(projectId, userService.findUserByUsername(principal.getName()));
    }

    @GetMapping("/projects/{projectId}/download")
    public void downloadProject(@PathVariable Long projectId, HttpServletResponse response, Principal principal) throws Exception {
        try {
            PytchUser user = userService.findUserByUsername(principal.getName());
            Project project = projectService.findProjectById(projectId, user);
            if (user != null && project != null) {
                Path userLocation = projectService.getUserStoragePath(user);
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(project.getTitle())) + ".zip";
                Path targetLocation = userLocation.resolve(fileName);

                InputStream is = Files.newInputStream(targetLocation);
                IOUtils.copy(is, response.getOutputStream());
                response.setContentType("application/zip");
                response.flushBuffer();
            }
            else {
                // TODO: error handling
            }

        } catch (IOException e) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }
}
