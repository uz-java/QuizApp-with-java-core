package org.example.enums;

import jakarta.persistence.AttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum Status {
    BLOCKED(-100),
    PASSWORD_NOT_RESET(-10),
    ACTIVE(0);

    public final int priority;

    public static class StatusConvertor implements AttributeConverter<Status, String> {
        @Override
        public String convertToDatabaseColumn(Status attribute) {
            if (Objects.isNull(attribute))
                return null;
            return switch (attribute) {
                case ACTIVE -> "Active User";
                case PASSWORD_NOT_RESET -> "User that have not reset password";
                case BLOCKED -> "User block for some reason";
            };
        }

        @Override
        public Status convertToEntityAttribute(String dbData) {
            if (Objects.isNull(dbData))
                return null;

            return switch (dbData) {
                case "Active User" -> ACTIVE;
                case "User that have not reset password" -> PASSWORD_NOT_RESET;
                case "User block for some reason" -> BLOCKED;
                default -> BLOCKED;
            };
        }
    }

}
