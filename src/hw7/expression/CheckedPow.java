package hw7.expression;


import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;

public strictfp class CheckedPow extends AbstractBinaryOperator {
    public CheckedPow(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    private void check(int x, int y) throws OverflowException, UnexpectedNegativeNumberException, MathException {
        if (y < 0) {
            throw new UnexpectedNegativeNumberException(y);
        } else if (x == 0 && y == 0) {
            throw new MathException("try to power(0, 0)");
        }
    }

    protected int calculate(int x, int y) throws OverflowException, UnexpectedNegativeNumberException, MathException {
        check(x, y);
        if (x == 1) {
            return 1;
        } else if (x == -1) {
            if (y % 2 == 1) {
                return -1;
            } else {
                return 1;
            }
        } else if (x == 0) {
            return 0;
        }

        int z = 1;
        for (int i = 0; i < y; i++) {
            z = CheckedMultiply.calculateS(z, x);
        }
        return z;
    }
}