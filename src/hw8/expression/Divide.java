package hw8.expression;

import exceptions.DivisionByZeroException;
import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;
import operations.Operations;

public strictfp class Divide<T> extends AbstractBinaryOperator<T> {
    public Divide(TripleExpression<T> x, TripleExpression<T> y, Operations<T> operation) {
        super(x, y, operation);
    }

    T calculate(T x, T y) throws OverflowException, DivisionByZeroException, UnexpectedNegativeNumberException, MathException {
        return operation.div(x, y);
    }
}