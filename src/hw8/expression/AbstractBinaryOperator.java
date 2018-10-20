package hw8.expression;

import exceptions.*;
import operations.Operations;

public abstract class AbstractBinaryOperator<T> implements TripleExpression<T> {
    private final TripleExpression<T> firstExpression;
    private final TripleExpression<T> secondExpression;
    protected final Operations<T> operation;

    AbstractBinaryOperator(TripleExpression<T> x, TripleExpression<T> y, Operations<T> operation) {
        firstExpression = x;
        secondExpression = y;
        this.operation = operation;
    }

    abstract T calculate(T x, T y) throws OverflowException, DivisionByZeroException, UnexpectedNegativeNumberException, MathException;

    public T evaluate(T x, T y, T z) throws OverflowException, DivisionByZeroException, MathException, UnexpectedNegativeNumberException, ParsingException {
        return calculate(firstExpression.evaluate(x, y, z), secondExpression.evaluate(x, y, z));
    }
}