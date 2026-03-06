package org.pytch.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PytchUser {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String password;
}
