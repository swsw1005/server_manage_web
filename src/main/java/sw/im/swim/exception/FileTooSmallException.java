package sw.im.swim.exception;

public class FileTooSmallException extends Exception{

    public FileTooSmallException() {
    }

    public FileTooSmallException(String message) {
        super(message);
    }

    public FileTooSmallException(Throwable cause) {
        super(cause);
    }

    public FileTooSmallException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileTooSmallException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    
}
