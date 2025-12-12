package uspace.multiComponents.util;

import io.restassured.response.Response;

public class ResponseMemory {
    private static Response response;

    public static Response getResponse() {
        return response;
    }

    public static void setResponse(Response response) {
        ResponseMemory.response = response;
    }

    public static void clear() {
        response = null;
    }
}
