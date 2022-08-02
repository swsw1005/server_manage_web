package sw.im.swim.exception;

public class CertException extends Exception {


    public CertException() {
    }

    public CertException(String message) {
        super(message);
    }

    public CertException(String message, Throwable cause) {
        super(message, cause);
    }

    public CertException(Throwable cause) {
        super(cause);
    }

    public CertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}