package hw8.expression;

import exceptions.DivisionByZeroException;
import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;

public strictfp class Const<T> implements TripleExpression<T> {
    private final T value;

    public Const(T x) {
        value = x;
    }

    @Override
    public T evaluate(T x, T y, T z) throws OverflowException, DivisionByZeroException, MathException, UnexpectedNegativeNumberException {
        return value;
    }
}