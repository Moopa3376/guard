package net.moopa3376.guard.api;

import net.moopa3376.guard.exception.GuardError;

/**
 * Created by Moopa on 10/05/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
public class ApiCheckResult {

    private boolean passed = false;
    private String wrongParamter = null;
    private String wrongMessage = null;
    private GuardError error = null;

    public ApiCheckResult(boolean passed, String wrongParamter, GuardError guardError, String ms){
        this.passed = passed;
        this.wrongMessage = ms;
        this.wrongParamter = wrongParamter;
        this.error = guardError;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getWrongParamter() {
        return wrongParamter;
    }

    public void setWrongParamter(String wrongParamter) {
        this.wrongParamter = wrongParamter;
    }


    public String getWrongMessage() {
        return wrongMessage;
    }

    public void setWrongMessage(String wrongMessage) {
        this.wrongMessage = wrongMessage;
    }

    public GuardError getError() {
        return error;
    }

    public void setError(GuardError error) {
        this.error = error;
    }
}
