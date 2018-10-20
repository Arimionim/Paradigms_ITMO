package hw8.exceptions;

public class UnexpectedNegativeNumberException extends Exception {
    public UnexpectedNegativeNumberException(int x) {
        super("Unexpected negative number: " + x);
    }

    public UnexpectedNegativeNumberException(String s){
        this(Integer.parseInt(s));
    }
}
