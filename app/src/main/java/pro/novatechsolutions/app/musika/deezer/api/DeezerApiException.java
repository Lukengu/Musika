package pro.novatechsolutions.app.musika.deezer.api;

public class DeezerApiException  extends Exception {
    private static final long serialVersionUID = 1L;

    public DeezerApiException() {
        super();
    }

    public DeezerApiException(String message) {
        super(message);
    }

    public DeezerApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeezerApiException(Throwable cause) {
        super(cause);
    }
}
