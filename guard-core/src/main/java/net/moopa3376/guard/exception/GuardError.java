package net.moopa3376.guard.exception;


/**
 * Created by Moopa on 17/07/2017.
 * blog: moopa.net
 *
 * @autuor : Moopa
 */
public enum GuardError {

    UNAUTHORIZED(401,"Haven't been authorized, maybe need login."),

    FORBIDDEN(403,"This operation is not allowed."),
    EXPIRED(488,"The token has expired, please login again"),


    JWT_NOTCORRECT(555,"The token is not corrent."),

    LOGIN_FAIL(666,"Please check your username and password."),

    API_PARAM_NOT_MATCH(1,"Not Match Regex."),
    API_PARAM_REQUIRED(2,"Need Parameter."),
    API_CHECK_ERROR(3,"Api check error");



    private int httpErrorCode;
    private String message;

    GuardError(int code,String msg){
        this.httpErrorCode = code;
        this.message = msg;
    }

    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    public String getMessage() {
        return message;
    }
}
