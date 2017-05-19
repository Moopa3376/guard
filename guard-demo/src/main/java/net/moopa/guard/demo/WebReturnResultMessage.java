package net.moopa.guard.demo;

/**
 * Created by Moopa on 06/04/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class WebReturnResultMessage {
    private ResponseCode responseCode;
    private String message;

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("{");
        sb.append("\"status\":"+responseCode+",");
        sb.append("\"message\":"+message);
        sb.append("}");
        return sb.toString();
    }

    public static WebReturnResultMessage ifParamLost(String paramName,String param){
        WebReturnResultMessage webReturnResultMessage = webReturnResultMessage = new WebReturnResultMessage();

        webReturnResultMessage.setResponseCode(ResponseCode.BAD_REQUEST);
        webReturnResultMessage.setMessage("\"Required:"+paramName+"\"");

        return webReturnResultMessage;
    }
}
