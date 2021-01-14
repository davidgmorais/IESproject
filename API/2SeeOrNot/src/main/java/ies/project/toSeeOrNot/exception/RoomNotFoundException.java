package ies.project.toSeeOrNot.exception;

/**
 * @author Wei
 * @date 14/01/2021 17:08
 */
public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(){
        super("Room not found!");
    }

    public RoomNotFoundException(String msg){
        super(msg);
    }
}