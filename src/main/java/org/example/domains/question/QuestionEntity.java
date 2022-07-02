package org.example.domains.question;

import jakarta.persistence.*;
import lombok.*;
import org.example.domains.Auditable;
import org.example.domains.subject.SubjectEntity;
import org.example.enums.QuestionStatus;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.util.List;


@Entity
@Table(name = "questions", schema = "question")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QuestionEntity extends Auditable {

    private String body;
    @Enumerated(EnumType.STRING)
    private QuestionStatus status;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<AnswerEntity> answers;


    @OneToOne(cascade = CascadeType.MERGE, targetEntity = SubjectEntity.class)
    @JoinColumn(name = "subject_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private SubjectEntity subject;

    @Builder(builderMethodName = "childBuilder")
    public QuestionEntity(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt, Long updatedBy, Boolean deleted, String body, QuestionStatus status, List<AnswerEntity> answers, SubjectEntity subject) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.body = body;
        this.status = status;
        this.answers = answers;
        this.subject = subject;
    }
}
