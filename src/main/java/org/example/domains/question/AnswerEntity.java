package org.example.domains.question;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import org.example.domains.Auditable;
import org.example.enums.AnswerStatus;

import java.sql.Timestamp;

@Entity
@Table(name = "answers", schema = "question")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class AnswerEntity extends Auditable {
    private String body;

    @Enumerated(EnumType.STRING)
    private AnswerStatus status;


    @Builder(builderMethodName = "childBuilder")
    public AnswerEntity(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt, Long updatedBy, Boolean deleted, String body, AnswerStatus status) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.body = body;
        this.status = status;
    }
}
