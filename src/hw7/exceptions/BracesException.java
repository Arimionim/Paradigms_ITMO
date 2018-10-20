package hw7.exceptions;

public class BracesException extends Exception {
    public BracesException(String s) {
        super("Wrong braces seq expression: " + s);
    }
}
