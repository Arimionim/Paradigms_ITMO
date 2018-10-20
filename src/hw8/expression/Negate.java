package hw8.expression;

import exceptions.OverflowException;
import operations.Operations;

public class Negate<T> extends AbstractUnaryOperator<T> {

    public Negate(TripleExpression<T> x, Operations<T> operation) {
        super(x, operation);
    }

    @Override
    protected T calculate(T x) throws OverflowException {
        return operation.neg(x);
    }
}
