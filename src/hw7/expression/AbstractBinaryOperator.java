package hw7.expression;

import exceptions.DivisionByZeroException;
import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;

public abstract class AbstractBinaryOperator implements TripleExpression {
    private final TripleExpression firstExpression;
    private final TripleExpression secondExpression;

    AbstractBinaryOperator(TripleExpression x, TripleExpression y) {
        firstExpression = x;
        secondExpression = y;
    }

    protected abstract int calculate(int x, int y) throws OverflowException, DivisionByZeroException, UnexpectedNegativeNumberException, MathException;

    public int evaluate(int x, int y, int z) throws OverflowException, DivisionByZeroException, MathException, UnexpectedNegativeNumberException {
        return calculate(firstExpression.evaluate(x, y, z), secondExpression.evaluate(x, y, z));
    }
}