package hw7.expression;

import exceptions.OverflowException;

public strictfp class CheckedSubtract extends AbstractBinaryOperator {
    public CheckedSubtract(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    private void check(int x, int y) throws OverflowException {
        if ((x >= 0 && y < 0 && -Integer.MAX_VALUE + x > y) || (x <= 0 && y > 0 && (Integer.MIN_VALUE - x) > -y)) {
            throw new OverflowException();
        }
    }

    protected int calculate(int x, int y) throws OverflowException {
        check(x, y);
        return x - y;
    }
}