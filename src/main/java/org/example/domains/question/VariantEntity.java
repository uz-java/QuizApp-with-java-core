package org.example.domains.question;

import jakarta.persistence.*;
import lombok.*;
import org.example.domains.Auditable;
import org.example.domains.auth.AuthUser;
import org.example.enums.QuestionStatus;

import java.sql.Timestamp;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "variants", schema = "test")
@Getter
@Setter
public class VariantEntity extends Auditable {
    @OneToOne(targetEntity = AuthUser.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private AuthUser user;
    @Enumerated(EnumType.STRING)
    private QuestionStatus status;

    @Column(columnDefinition = "smallint default 0", nullable = false)
    @Convert(converter = org.hibernate.type.NumericBooleanConverter.class)
    private Boolean completed;
    @ManyToMany(targetEntity = QuestionEntity.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "variant_question",
            joinColumns = @JoinColumn(name = "variant_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"),
            schema = "test"
    )
    private List<QuestionEntity> questions;
    @Column(name = "result")
    private Integer numberOfRightAnswers;

    @Column(name = "questions")
    private Integer numberOfQuestions;

    @Builder(builderMethodName = "childBuilder")
    public VariantEntity(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt, Long updatedBy, Boolean deleted,
                         AuthUser user, List<QuestionEntity> questions, Integer numberOfRightAnswers, Integer numberOfQuestions) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.user = user;
        this.questions = questions;
        this.numberOfRightAnswers = numberOfRightAnswers;
        this.completed = false;
        this.numberOfQuestions = numberOfQuestions;
    }
}
