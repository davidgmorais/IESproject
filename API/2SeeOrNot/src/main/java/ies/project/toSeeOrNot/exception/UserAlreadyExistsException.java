package ies.project.toSeeOrNot.exception;

/**
 * @author Wei
 * @date 2020/12/10 9:20
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(){
        super("User already exists!");
    }

    public UserAlreadyExistsException(String msg){
        super(msg);
    }
}
