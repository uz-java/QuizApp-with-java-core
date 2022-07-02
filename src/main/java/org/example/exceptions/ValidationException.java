package org.example.exceptions;

import java.sql.Timestamp;
import java.util.Date;

public class ValidationException extends RuntimeException {
    private Timestamp timestamp;

    public ValidationException(String message) {
        super(message);
        this.timestamp = new Timestamp(new Date().getTime());
    }
}
