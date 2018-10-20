package hw8.expression;

import exceptions.DivisionByZeroException;
import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;
import operations.Operations;

public class Add<T> extends AbstractBinaryOperator<T> {

    public Add(TripleExpression<T> x, TripleExpression<T> y, Operations<T> operation) {
        super(x, y, operation);
    }

    @Override
    protected T calculate(T x, T y) throws OverflowException, DivisionByZeroException, UnexpectedNegativeNumberException, MathException {
        return operation.add(x, y);
    }
}