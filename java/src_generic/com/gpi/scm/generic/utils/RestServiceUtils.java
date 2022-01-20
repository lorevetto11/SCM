package com.gpi.scm.generic.utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Rest service utils
 *
 * @see HTTP 200 = OK HTTP 401 = Unauthorized HTTP 403 = Forbidden HTTP 500 =
 * Server error
 */
public abstract class RestServiceUtils {
    /**
     * HTTP Header used to store the reason of a 204 No Content response
     */
    public static final String X_NGH_NO_CONTENT_REASON = "X-NGH-NoContentReason";


    /**
     * Create OK response
     *
     * @return OK Response
     */
    public static Response createHttp200OkResponse() {
        return RestServiceUtils.createHttp200OkResponse(null);
    }

    /**
     * Create OK response
     *
     * @param result -> result object
     * @return OK Response
     */
    public static Response createHttp200OkResponse(Object result) {
        return RestServiceUtils.createResponse(result, Response.Status.OK);
    }

    /**
     * Create a NO CONTENT response from an {@link Exception}
     */
    public static Response createHttp204NoContentResponseForException(Exception ex) {
        return Response.status(Response.Status.NO_CONTENT)
            .header(X_NGH_NO_CONTENT_REASON, errorCodeFromException(ex))
            .build();
    }

    /**
     * Create a file download response
     *
     * @param content
     * @param fileName
     * @param mimeType
     * @return
     */
    public static Response createFileDownloadResponse(byte[] content, String fileName, String mimeType) {
        ResponseBuilder response = Response.ok(content);
        response.header("Content-Disposition", "attachment; filename=" + fileName);
        response.header("Content-Type", mimeType);
        return response.build();
    }

    /**
     * Create unauthorized response
     *
     * @param exception -> exception object
     * @return OK Response
     */
    public static Response createHttp401UnauthorizedResponse(String message) {
        return RestServiceUtils.createResponse(message, Response.Status.UNAUTHORIZED);
    }

    /**
     * Create unauthorized response
     *
     * @param exception -> exception object
     * @return OK Response
     */
    public static Response createHttp401UnauthorizedResponse(Throwable exception) {
        return RestServiceUtils.createResponse(exception.getMessage(), Response.Status.UNAUTHORIZED);
    }

    /**
     * Create Forbidden response
     *
     * @param exception -> exception object
     * @return OK Response
     */
    public static Response createHttp403ForbiddenResponse(Throwable exception) {
        return RestServiceUtils.createResponse(exception.getMessage(), Response.Status.FORBIDDEN);
    }


    /**
     * Create INTERNAL_SERVER_ERROR response
     *
     * @param exception -> exception error
     * @return INTERNAL_SERVER_ERROR response
     */
    public static Response createHttp500ServerErrorResponse(Throwable exception) {
        return RestServiceUtils.createResponse(exception.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
    }

    /**
     * Create INTERNAL_SERVER_ERROR response for TEXT/PLAIN responses
     *
     * @param exception -> exception error
     * @return INTERNAL_SERVER_ERROR response
     */
    public static Response createHttp500ServerErrorResponse(String error) {
        return RestServiceUtils.createResponse(error, Response.Status.INTERNAL_SERVER_ERROR);
    }


    private static String errorCodeFromException(Throwable exception) {
        return exception.getClass().getSimpleName();
    }

    /**
     * Create rest Response object
     *
     * @param object -> result object
     * @return Response rest object
     */
    private static Response createResponse(Object result, Response.Status type) {
        if (result == null) {
            return Response.status(type).build();
        }
        return Response.status(type).entity(result).build();
    }


}
