package kloSpringServer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult {
    private int status = 0;

//    @JsonUnwrapped
    private Object result = null;

    public static final int STATUS_OK = 0;
    public static final int STATUS_WARN = 1;
    public static final int STATUS_ERROR = 2;

    public ApiResult(Object result) {
        if (null == result) {
            this.status = 1;
        } else {
            this.status = 0;
        }
        this.result = result;
    }

    public ApiResult(Object result, int status) {
        this.result = result;
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}