package hw7.exceptions;

public class UnexpectedNegativeNumberException extends Exception {
    public UnexpectedNegativeNumberException(int x) {
        super("Unexpected negative number: " + x);
    }
}
