package net.moopa3376.guard.exception;

/**
 * Created by Moopa on 17/07/2017.
 * blog: moopa.net
 *
 * @autuor : Moopa
 */
public class GuardException extends RuntimeException{
    public GuardError error;
    public String solution;

    public GuardException(GuardError error,String solution){
        this.error = error;
        this.solution = solution;
    }

    @Override
    public String toString() {
        return error.getMessage()+", solution: "+solution;
    }
}
