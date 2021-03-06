package hw8.expression;

import exceptions.DivisionByZeroException;
import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;
import operations.Operations;

public strictfp class Subtract<T> extends AbstractBinaryOperator<T> {
    public Subtract(TripleExpression<T> x, TripleExpression<T> y, Operations<T> operation) {
        super(x, y, operation);
    }

    @Override
    T calculate(T x, T y) throws OverflowException, DivisionByZeroException, UnexpectedNegativeNumberException, MathException {
        return operation.sub(x, y);
    }
}