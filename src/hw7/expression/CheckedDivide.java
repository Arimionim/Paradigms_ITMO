package hw7.expression;

import exceptions.DivisionByZeroException;
import exceptions.OverflowException;

public strictfp class CheckedDivide extends AbstractBinaryOperator {
    public CheckedDivide(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    private void check(int x, int y) throws DivisionByZeroException, OverflowException {
        if (y == 0) {
            throw new DivisionByZeroException();
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException();
        }
    }

    protected int calculate(int x, int y) throws DivisionByZeroException, OverflowException {
        check(x, y);
        return x / y;
    }
}