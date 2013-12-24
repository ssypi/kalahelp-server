package kloSpringServer.controller;

import kloSpringServer.ValidationException;
import kloSpringServer.model.ApiResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import javax.servlet.http.HttpServletResponse;

/**
 * Base controller class to be extended  by the controller class
 * when basic exception handling is desired.
 */
public class ControllerBase {
    /**
     * <p>Exception handler for {@link HttpStatusCodeException}</p>
     * <p>Creates {@link ApiResult} object and sets the status
     * and message fields to match the exception. Also sets the HttpResponse status code
     * to match the one in the exception.</p>
     *
     * @return {@link ApiResult} converted to json with no result body, only a message and status.
     */
    @ExceptionHandler(HttpStatusCodeException.class)
    @ResponseBody
    public ApiResult handleHttpException(HttpStatusCodeException exception, HttpServletResponse response) {
        ApiResult responseBody = new ApiResult();

        response.setStatus(exception.getStatusCode().value());
        responseBody.setStatus(exception.getStatusCode().value());

        if (exception.getStatusText() != null && !exception.getStatusText().isEmpty()) {
            responseBody.setMessage(exception.getStatusText());
        } else {
            responseBody.setMessage(exception.getStatusCode().getReasonPhrase());
        }
        return responseBody;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ApiResult handleValidationException(ValidationException exception, HttpServletResponse response) {
        ApiResult responseBody = new ApiResult();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        responseBody.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        responseBody.setMessage(exception.getMessage());
        return responseBody;
    }
}
