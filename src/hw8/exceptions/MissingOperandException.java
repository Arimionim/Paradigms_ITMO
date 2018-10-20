package hw8.exceptions;

public class MissingOperandException extends ParsingException {
    public MissingOperandException(int x) {
        super("Missing operand at index " + x);
    }
}
