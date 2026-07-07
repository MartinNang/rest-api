package org.pytch.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.pytch.backend.PytchProgramKind;
import org.pytch.backend.PytchProjectStatus;

@Data
@Builder
@AllArgsConstructor
public class SaveProjectDto {

    private String title;

    private PytchProgramKind programKind;

    private PytchProjectStatus status;

    private PytchUserDto userDto;
}
