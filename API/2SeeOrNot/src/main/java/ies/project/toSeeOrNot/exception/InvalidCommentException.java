package ies.project.toSeeOrNot.exception;

/**
 * @author Wei
 * @date 2020/12/30 23:04
 */
public class InvalidCommentException extends RuntimeException {
    public InvalidCommentException(){
        super("Invalid Comment!");
    }

    public InvalidCommentException(String msg){
        super(msg);
    }
}