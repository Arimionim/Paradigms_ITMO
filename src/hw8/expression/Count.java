package hw8.expression;

import exceptions.OverflowException;
import operations.Operations;

public class Count<T> extends AbstractUnaryOperator<T> {

    public Count(TripleExpression<T> x, Operations<T> operation) {
        super(x, operation);
    }

    @Override
    protected T calculate(T x) throws OverflowException {
        return operation.cnt(x);
    }
}
