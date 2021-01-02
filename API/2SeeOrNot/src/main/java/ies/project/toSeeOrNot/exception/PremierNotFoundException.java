package ies.project.toSeeOrNot.exception;

/**
 * @author Wei
 * @date 2021/1/1 16:44
 */
public class PremierNotFoundException extends RuntimeException {
    public PremierNotFoundException(){
        super("Premier Not Found!");
    }

    public PremierNotFoundException(String msg){
        super(msg);
    }
}