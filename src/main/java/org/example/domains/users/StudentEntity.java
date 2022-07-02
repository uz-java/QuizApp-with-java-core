package org.example.domains.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domains.auth.AuthUser;

@Entity
@Table(name = "students", schema = "auth")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;

    @OneToOne(targetEntity = AuthUser.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private AuthUser user;
}
