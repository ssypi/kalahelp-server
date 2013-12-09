package kloSpringServer.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * <p>Wrapper class for Api responses.
 * The actual result of an operation is to be included in the {@link #result} field.</p>
 *
 * <p>Example JSON representation of ApiResult with a List as result:<br>
 *    {
 *      "status" : "200",
 *      "message" : "List of tickets.",
 *      "result" : "
 *      [
 *          {"id" : "1"},
 *          {"id" : "2"}
 *      ]"
 *    }
 * </p>
 * @param <T> Class of the object in the result field.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {
    private int status = 0;
    private String message = null;

    public static final int STATUS_OK = 0;
    public static final int STATUS_WARN = 1;
    public static final int STATUS_ERROR = 2;

//    @JsonUnwrapped
    private T result = null;


    public ApiResult() {
        this.status = STATUS_OK;
    }

    public ApiResult(int status) {
        this.status = status;
    }

    public ApiResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiResult(T result) {
        if (null == result) {
            this.status = STATUS_WARN;
        } else {
            this.status = STATUS_OK;
        }
        this.result = result;
    }

    public ApiResult(T result, int status) {
        this.result = result;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
