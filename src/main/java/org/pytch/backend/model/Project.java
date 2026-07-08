package org.pytch.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pytch.backend.PytchProgramKind;
import org.pytch.backend.PytchProjectStatus;
import org.pytch.backend.dto.request.SaveProjectDto;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private PytchProgramKind programKind;

    private String path;

    private PytchProjectStatus status;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private PytchUser user;


    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Project(SaveProjectDto saveProjectDto, PytchUser user) {
        this.title = saveProjectDto.getTitle();
        this.programKind = saveProjectDto.getProgramKind();
        this.status = saveProjectDto.getStatus();
        this.user = user;
    }
}
