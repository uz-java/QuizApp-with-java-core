package org.example.domains.users;

import jakarta.persistence.*;
import lombok.*;
import org.example.domains.auth.AuthUser;
import org.example.domains.subject.SubjectEntity;

import java.util.List;

@Entity
@Table(name = "teachers", schema = "auth")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class TeacherEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @ManyToMany(targetEntity = SubjectEntity.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"),
            schema = "subject"
    )
    private List<SubjectEntity> subjectList;
    @OneToOne(targetEntity = AuthUser.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private AuthUser user;
}
