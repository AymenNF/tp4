package uspace.domain.exceptions;

public class MissingParameterException extends IllegalArgumentException {

    public MissingParameterException(String parameterName) {
        super("Missing parameter: " + parameterName);
    }
}
