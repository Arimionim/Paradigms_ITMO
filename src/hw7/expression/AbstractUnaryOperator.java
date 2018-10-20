package hw7.expression;

import exceptions.DivisionByZeroException;
import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;

public abstract class AbstractUnaryOperator implements TripleExpression {
    private final TripleExpression expression;

    AbstractUnaryOperator(TripleExpression x) {
        expression = x;
    }

    protected abstract int calculate(int x) throws OverflowException;

    public int evaluate(int x, int y, int z) throws OverflowException, DivisionByZeroException, MathException, UnexpectedNegativeNumberException {
        return calculate(expression.evaluate(x, y, z));
    }
}
