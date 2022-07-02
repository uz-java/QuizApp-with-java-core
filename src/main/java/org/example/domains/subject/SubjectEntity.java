package org.example.domains.subject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.domains.Auditable;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(schema = "subject", name = "subjects")
@NoArgsConstructor
@Getter
@ToString
public class SubjectEntity extends Auditable {
    @Column(nullable = false, unique = true)
    private String title;

    @Builder(builderMethodName = "childBuilder")
    public SubjectEntity(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt, Long updatedBy, Boolean deleted, String title) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectEntity that = (SubjectEntity) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
