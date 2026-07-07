package org.pytch.backend.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.pytch.backend.PytchProgramKind;
import org.pytch.backend.dto.request.SaveProjectDto;
import org.pytch.backend.model.Project;
import org.pytch.backend.model.PytchUser;
import org.pytch.backend.repository.ProjectRepository;
import org.pytch.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Getter
    private Path fileStoragePath;

    @PostConstruct
    public void init() {
        try {
            fileStoragePath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(fileStoragePath);
            System.out.println("Upload directory initialized at: " + fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> getProjects() {
        return (List<Project>) projectRepository.findAll();
    }

    @Override
    public Project updateProject(Project project, Long projectId) {
        Project projectDb = projectRepository.findById(projectId).get();

        String title = projectDb.getTitle();
        if(title != null && title.isEmpty()) {
            projectDb.setTitle(title);
        }

        String path = projectDb.getPath();
        if (path != null && path.isEmpty()) {
            projectDb.setPath(path);
        }

        PytchProgramKind programKind = projectDb.getProgramKind();
        if (programKind != null) {
            projectDb.setProgramKind(programKind);
        }

        Timestamp createdAt = projectDb.getCreatedAt();
        if (createdAt != null) {
            projectDb.setCreatedAt(createdAt);
        }

        Timestamp updatedAt = projectDb.getUpdatedAt();
        if (updatedAt != null) {
            projectDb.setUpdatedAt(updatedAt);
        }

        return projectRepository.save(projectDb);
    }

    @Override
    public void deleteProjectById(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    public Path getUserStoragePath(SaveProjectDto projectDto) {
        return fileStoragePath.resolve(projectDto.getUserDto().getUsername());
    }

    @Override
    public List<Project> findProjectsByUserId(PytchUser pytchUser, Long userId) {
        return projectRepository.findProjectsByUser_Id(userId);
    }
}
