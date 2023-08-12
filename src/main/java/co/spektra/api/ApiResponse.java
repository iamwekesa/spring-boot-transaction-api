package co.spektra.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    @Getter
    public enum Status {
        SUCCESS("success"),
        ERROR("error");

        private final String value;

        Status(String value) {
            this.value = value;
        }

    }

    private String status;
    private String message;
    private T data;

    public ApiResponse(Status status, String message, T data) {
        this.status = status.getValue();
        this.message = message;
        this.data = data;
    }
    public ApiResponse(Status status, String message) {
        this.status = status.getValue();
        this.message = message;
    }


}
