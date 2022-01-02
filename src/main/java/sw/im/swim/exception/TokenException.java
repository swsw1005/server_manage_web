package sw.im.swim.exception;

public class TokenException extends Exception {

    public static int CODE = -45;

    public TokenException() {
    }

    public TokenException(String message) {
        super(message);
    }

}