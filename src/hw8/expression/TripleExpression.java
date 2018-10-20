package hw8.expression;

import exceptions.*;

public interface TripleExpression<T> {
    T evaluate(T x, T y, T z) throws OverflowException, DivisionByZeroException, MathException, UnexpectedNegativeNumberException, ParsingException;
}