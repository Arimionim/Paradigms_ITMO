package hw7.expression;

import exceptions.DivisionByZeroException;
import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;

public interface TripleExpression {
    public int evaluate(int x, int y, int z) throws OverflowException, DivisionByZeroException, MathException, UnexpectedNegativeNumberException;
}