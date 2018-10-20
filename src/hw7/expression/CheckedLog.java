package hw7.expression;


import exceptions.MathException;
import exceptions.OverflowException;
import exceptions.UnexpectedNegativeNumberException;

public strictfp class CheckedLog extends AbstractBinaryOperator {
    public CheckedLog(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    private void check(int x, int y) throws OverflowException, UnexpectedNegativeNumberException, MathException {
        if (y <= 0 || x <= 0) {
            throw new UnexpectedNegativeNumberException(y);
        } else if (y == 1) {
            throw new MathException("Try to log with 1");
        }
    }

    protected int calculate(int x, int y) throws OverflowException, UnexpectedNegativeNumberException, MathException {
        check(x, y);
        return (int) (Math.log(x) / Math.log(y));
    }
}