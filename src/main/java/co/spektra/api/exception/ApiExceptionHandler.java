package co.spektra.api.exception;

import co.spektra.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleTransactionNotFound(TransactionNotFoundException ex) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorResponse error = new ErrorResponse(ex.getMessage(), httpStatus);

        ApiResponse<ErrorResponse> errorResponse =
                new ApiResponse<>(ApiResponse.Status.ERROR, "", error);

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleInvalidTransaction(InvalidTransactionException ex) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse error = new ErrorResponse(ex.getMessage(), httpStatus);
        ApiResponse<ErrorResponse> errorResponse =
                new ApiResponse<>(ApiResponse.Status.ERROR, "", error);

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleGlobalException(Exception ex) {

        /*
        TODO: Consider a better way of handling Global Exception that does not
         involve divulge system information to client.
         */
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse error = new ErrorResponse(ex.getLocalizedMessage(), httpStatus);
        ApiResponse<ErrorResponse> errorResponse =
                new ApiResponse<>(ApiResponse.Status.ERROR, "", error);

        // Return an error response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
