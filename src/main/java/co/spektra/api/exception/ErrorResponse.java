package co.spektra.api.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

/*
*
* ErrorResponse class :  standardize error messages sent back to users,
*
* */
@Getter
@Setter
public class ErrorResponse {
    private UUID errorId;
    private String errorType;
    private int statusCode;
    private String status;
    private String message;
    private long timestamp;
    public ErrorResponse(Exception ex, HttpStatus status) {
        this.errorId = UUID.randomUUID();
        this.errorType = ex.getClass().getSimpleName();
        this.statusCode = status.value();
        this.status = status.getReasonPhrase();
        this.message = ex.getMessage();
        this.timestamp = System.currentTimeMillis();
    }
}