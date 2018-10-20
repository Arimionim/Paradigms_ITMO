package hw8.expression;

import exceptions.DivisionByZeroException;
import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;
import operations.Operations;

public class Max<T> extends AbstractBinaryOperator<T> {

    public Max(TripleExpression<T> x, TripleExpression<T> y, Operations<T> operation) {
        super(x, y, operation);
    }

    @Override
    protected T calculate(T x, T y) throws OverflowException, DivisionByZeroException, UnexpectedNegativeNumberException, MathException {
        return operation.max(x, y);
    }
}