package hw7.exceptions;

public class MissingOperandException extends ParsingException {
    public MissingOperandException(String s, int x) {
        super("Missing operand at index " + x + " expression: " + s);
    }
}
