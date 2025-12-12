package uspace.api.exceptions;

public class ErrorResponse {

    public String error;
    public String description;

    public ErrorResponse() {
    }

    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }
}
