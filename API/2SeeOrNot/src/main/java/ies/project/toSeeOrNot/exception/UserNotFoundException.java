package ies.project.toSeeOrNot.exception;

/**
 * @author Wei
 * @date 2020/12/2 21:05
 */
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("User Not Found");
    }

    public UserNotFoundException(String msg){
        super(msg);
    }
}
