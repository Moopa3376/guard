package net.moopa.guard.demo;

/**
 * Created by Moopa on 06/04/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public enum ResponseCode {
    OK(200,"ok"),



    BAD_REQUEST(400,"bad request"),
    UNAUTHORIZED(401,"unauthorized"),
    FORBIDDEN(403,"forbidden"),
    NOT_FOUND(404,"not found"),



    INTERNAL_SERVER_ERROR(500,"internal server error");






    ResponseCode(int code,String m){
        this.code = code;
        this.message = m;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "\""+code+"|"+message+"\"";
    }
}
