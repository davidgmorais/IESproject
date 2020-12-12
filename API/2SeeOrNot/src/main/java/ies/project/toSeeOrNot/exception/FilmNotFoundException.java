package ies.project.toSeeOrNot.exception;

/**
 * @author Wei
 * @date 2020/12/10 20:40
 */
public class FilmNotFoundException extends RuntimeException{
    public FilmNotFoundException(){
        super("Film already exists!");
    }

    public FilmNotFoundException(String msg){
        super(msg);
    }
}