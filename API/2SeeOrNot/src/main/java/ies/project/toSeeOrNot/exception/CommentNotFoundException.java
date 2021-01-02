package ies.project.toSeeOrNot.exception;

/**
 * @author Wei
 * @date 2021/1/1 16:27
 */
public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(){
        super("Comment Not Found!");
    }

    public CommentNotFoundException(String msg){
        super(msg);
    }
}
