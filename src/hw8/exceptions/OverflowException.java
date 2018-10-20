package hw8.exceptions;

public class OverflowException extends Exception {
    public OverflowException() {
        super("overflow");
    }

    public OverflowException(int x, String s) {
        super("overflow at index " + x + " " + s);
    }
}
