package ies.project.toSeeOrNot.exception;

/**
 * @author Wei
 * @date 2020/12/3 10:28
 */
public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(){
        super("Authentication failed");
    }

    public AuthenticationFailedException(String msg){
        super(msg);
    }
}
