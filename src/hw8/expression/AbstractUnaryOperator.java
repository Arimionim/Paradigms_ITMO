package hw8.expression;

import exceptions.*;
import operations.Operations;

public abstract class AbstractUnaryOperator<T> implements TripleExpression<T> {
    private final TripleExpression<T> expression;
    protected final Operations<T> operation;


    AbstractUnaryOperator(TripleExpression<T> x, Operations<T> operation) {
        expression = x;
        this.operation = operation;
    }

    protected abstract T calculate(T x) throws OverflowException;

    public T evaluate(T x, T y, T z) throws OverflowException, DivisionByZeroException, MathException, UnexpectedNegativeNumberException, ParsingException {
        return calculate(expression.evaluate(x, y, z));
    }
}
