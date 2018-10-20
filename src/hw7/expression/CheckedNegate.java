package hw7.expression;

import exceptions.OverflowException;

public class CheckedNegate extends AbstractUnaryOperator {

    public CheckedNegate(TripleExpression x) {
        super(x);
    }

    private void check(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    @Override
    protected int calculate(int x) throws OverflowException {
        check(x);
        return -x;
    }
}
