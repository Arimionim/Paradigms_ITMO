package hw7.expression;


import exceptions.OverflowException;

public strictfp class CheckedMultiply extends AbstractBinaryOperator {
    public CheckedMultiply(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    private static void check(int x, int y) throws OverflowException {
        if (x > 0 && y > 0) {
            if (Integer.MAX_VALUE / x < y) {
                throw new OverflowException();
            }
        } else if (x < 0 && y < 0) {
            if (Integer.MAX_VALUE / x > y) {
                throw new OverflowException();
            }
        } else if (x != 0 && y != 0) {
            if (x < 0) {
                if (Integer.MIN_VALUE / y > x) {
                    throw new OverflowException();
                }
            } else if (y < 0) {
                if (Integer.MIN_VALUE / x > y) {
                    throw new OverflowException();
                }
            }
        }
    }

    protected int calculate(int x, int y) throws OverflowException {
        check(x, y);
        return x * y;
    }

    protected static int calculateS(int x, int y) throws OverflowException {
        check(x, y);
        return x * y;
    }
}