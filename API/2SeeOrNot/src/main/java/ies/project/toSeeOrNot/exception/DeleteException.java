package ies.project.toSeeOrNot.exception;

/**
 * @author Wei
 * @date 14/01/2021 17:15
 */
public class DeleteException extends RuntimeException {
    public DeleteException(){
        super("Can not delete!");
    }

    public DeleteException(String msg){
        super(msg);
    }
}
