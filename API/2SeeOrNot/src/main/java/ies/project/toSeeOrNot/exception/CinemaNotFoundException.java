package ies.project.toSeeOrNot.exception;

/**
 * @author Wei
 * @date 2021/1/3 17:38
 */
public class CinemaNotFoundException extends RuntimeException {
    public CinemaNotFoundException(){
        super("Cinema Not Found!");
    }

    public CinemaNotFoundException(String msg){
        super(msg);
    }
}