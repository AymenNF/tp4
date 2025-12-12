package uspace.api.exceptions.mappers.uspace;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uspace.api.exceptions.ErrorResponse;

@Provider
public class CatchAllExceptionMapper implements ExceptionMapper<Exception> {

    private final Logger logger = LoggerFactory.getLogger(CatchAllExceptionMapper.class);

    @Context
    private HttpServletRequest request;

    @Override
    public Response toResponse(Exception exception) {
        var path = request.getPathInfo();
        logger.error("Unhandled exception in " + path, exception);
        return Response
            .status(Status.INTERNAL_SERVER_ERROR)
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity(new ErrorResponse("UNHANDLED EXCEPTION", exception.getMessage()))
            .build();
    }
}
