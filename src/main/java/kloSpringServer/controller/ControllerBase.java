package kloSpringServer.controller;

import kloSpringServer.model.ApiResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import javax.servlet.http.HttpServletResponse;

public class ControllerBase {
    /**
     * Exception handler for {@link HttpStatusCodeException}
     * to return {@link ApiResult} object as json to user with correct error messages
     * @return {@link ApiResult} converted to json
     */
    @ExceptionHandler(HttpStatusCodeException.class)
    @ResponseBody
    public ApiResult handleException(HttpStatusCodeException exception, HttpServletResponse response) {
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
}
