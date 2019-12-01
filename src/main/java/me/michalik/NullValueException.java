package me.michalik;

public class NullValueException extends RuntimeException {

    private static final long serialVersionUID = 6769829250639411880L;

    public NullValueException() {
        super();
    }

    public NullValueException(String message) {
        super(message);
    }
}
